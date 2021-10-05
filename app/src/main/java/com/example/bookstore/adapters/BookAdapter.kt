package com.example.bookstore.adapters

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bookstore.R
import com.example.bookstore.model.BookViewModel
import com.example.bookstore.model.dtos.BookDto
import com.squareup.picasso.Picasso


class BookAdapter(
    private val viewModel: BookViewModel,
    private val getDrawable: (drawableId: Int) -> Drawable,
    private val clickUrl: (String) -> Unit
) :
    PagedListAdapter<BookDto, BookViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val bookThumbnailLayout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.book_thumbnail, null) as CardView
        return BookViewHolder(bookThumbnailLayout, parent.context, getDrawable, viewModel, clickUrl)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bindTo(getItem(position)!!)
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<BookDto>() {

            override fun areItemsTheSame(oldBook: BookDto, newBook: BookDto) = oldBook.id == newBook.id

            override fun areContentsTheSame(oldBook: BookDto, newBook: BookDto) = oldBook == newBook
        }
    }
}

class BookViewHolder(
    private val view: CardView,
    private val context: Context,
    getDrawable: (drawableId: Int) -> Drawable,
    private val viewModel: BookViewModel,
    private val clickUrl: (String) -> Unit
) :
    RecyclerView.ViewHolder(view) {

    private val bookTitleTextView = view.findViewById<TextView>(R.id.title)
    private val bookAuthorTitleView = view.findViewById<TextView>(R.id.author)
    private val bookFavoriteButton = view.findViewById<ImageButton>(R.id.favorite_button)

    private val favoriteFilledDrawable = getDrawable(R.drawable.ic_favorite_filled_24)
    private val favoriteBorderDrawable = getDrawable(R.drawable.ic_favorite_border_24)

    fun bindTo(book: BookDto) {
        bookTitleTextView.text = context.getString(R.string.book_title, book.volumeInfo?.title ?: "N/A")

        val authorsString = book.volumeInfo?.authors?.joinToString(", ") ?: "N/A"

        bookAuthorTitleView.text =
            context.getString(
                R.string.book_authors,
                if (authorsString.length > 50) "${authorsString.subSequence(0, 49)}..."
                else authorsString
            )

        viewModel.getFavoriteBook(book.id ?: "") {
            if (it.isEmpty()) bookFavoriteButton.background = favoriteBorderDrawable
            else bookFavoriteButton.background = favoriteFilledDrawable
        }

        createOnClickListeners(book, favoriteFilledDrawable, favoriteBorderDrawable)
    }

    private fun createOnClickListeners(
        book: BookDto,
        favoriteFilledDrawable: Drawable,
        favoriteBorderDrawable: Drawable
    ) {
        bookFavoriteButton.setOnClickListener {
            viewModel.getFavoriteBook(book.id ?: "") { favorites ->
                if (favorites.isEmpty()) {
                    viewModel.insertFavoriteBook(book.id ?: "") {
                        Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
                        bookFavoriteButton.background = favoriteFilledDrawable
                    }
                } else {
                    viewModel.deleteFavoriteBook(book.id ?: "") {
                        Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show()
                        bookFavoriteButton.background = favoriteBorderDrawable
                    }
                }
            }
        }

        view.setOnClickListener {
            val alertDialog = AlertDialog
                .Builder(context)
                .setNegativeButton("Back") { dialogInterface, _ -> dialogInterface.cancel() }
                .create()
            val detailsLayout = LayoutInflater.from(context).inflate(R.layout.book_detail, null)

            val imageUrl = getViewsAndAssignValues(detailsLayout, book)

            createAndShowDetailDialog(alertDialog, detailsLayout)

            toastIfNotImage(imageUrl)
        }
    }

    private fun getViewsAndAssignValues(
        detailsLayout: View,
        book: BookDto,
    ): String? {
        val imageView = detailsLayout.findViewById<ImageView>(R.id.detail_image)
        val imageUrl = book.volumeInfo?.imageLinks?.thumbnail
        Picasso.get().load(imageUrl?.replace("http", "https")).into(imageView);

        val title = detailsLayout.findViewById<TextView>(R.id.detail_title)
        val authors = detailsLayout.findViewById<TextView>(R.id.detail_authors)
        val description = detailsLayout.findViewById<TextView>(R.id.detail_description)
        val isbn = detailsLayout.findViewById<TextView>(R.id.detail_isbn)
        val price = detailsLayout.findViewById<TextView>(R.id.detail_price)
        val buyUrl = detailsLayout.findViewById<TextView>(R.id.detail_buy_url)


        title.text = context.getString(R.string.book_title, book.volumeInfo?.title ?: "N/A")
        authors.text = context.getString(R.string.book_authors, book.volumeInfo?.authors?.joinToString(",") ?: "N/A")
        description.text = context.getString(R.string.book_description, book.volumeInfo?.description ?: "N/A")
        isbn.text = context.getString(R.string.book_isbn, book.volumeInfo?.industryIdentifiers?.get(0)?.identifier ?: "N/A")
        price.text = context.getString(R.string.book_price,book.saleInfo?.listPrice?.amount ?: "N/A",book.saleInfo?.listPrice?.currencyCode?:"")

        if (book.saleInfo?.buyLink != null)
            book.saleInfo.buyLink.let {
                val underlinedText = SpannableString(it)
                underlinedText.setSpan(UnderlineSpan(), 0, underlinedText.length, 0)
                underlinedText.setSpan(ForegroundColorSpan(Color.BLUE), 0, underlinedText.length, 0)
                buyUrl.text = underlinedText

                buyUrl.setOnClickListener { _ -> clickUrl(it) }
            }
        else
            buyUrl.text = context.getString(R.string.na)
        return imageUrl
    }

    private fun toastIfNotImage(imageUrl: String?) {
        if (imageUrl == null) {
            Toast.makeText(context, "Image not available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createAndShowDetailDialog(alertDialog: AlertDialog, detailsLayout: View?) {
        alertDialog.setView(detailsLayout)
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.window!!.setBackgroundDrawableResource(R.drawable.white_background_black_border_round_20)

        alertDialog.show()
    }

}