package com.example.bookstore

import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookstore.adapters.BookAdapter
import com.example.bookstore.databinding.ActivityScrollingBinding
import com.example.bookstore.model.BookViewModel
import com.example.bookstore.model.WebViewModelProvider
import com.example.bookstore.model.dtos.BookDto
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding
    private val viewModel by lazy {
        ViewModelProvider(this, WebViewModelProvider(this)).get(BookViewModel::class.java)
    }
    private val loadingDialog by lazy {
        AlertDialog.Builder(this).setView(LayoutInflater.from(this).inflate(R.layout.loading_dialog, null)).create()
    }
    private var skipCount = 0
    private val recyclerView by lazy<RecyclerView> {
        findViewById(R.id.recycler_view)
    }

    private val filterButton by lazy {
        findViewById<FloatingActionButton>(R.id.filter_fab)
    }

    private val recyclerViewAdapter by lazy {
        BookAdapter(
            viewModel = viewModel,
            getDrawable = resources::getDrawable,
            clickUrl = {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(browserIntent)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title


        setButtonsOnClickListeners()

        setUpRecyclerView()
    }

    private fun setButtonsOnClickListeners() {
        binding.fab.setOnClickListener {
            findViewById<FloatingActionButton>(R.id.fab).isGone = false
            findViewById<FloatingActionButton>(R.id.filter_fab).isGone = false
            findViewById<TextView>(R.id.welcome_text).isGone = true

            loadingDialog.show()

            recyclerView.adapter = recyclerViewAdapter

            viewModel.initViewModel()

            registerLiveDataObserve()
        }

        filterButton.setOnClickListener {
            viewModel.isFiltered = !viewModel.isFiltered

            viewModel.changeFilterStatus {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }

            if(viewModel.isFiltered){
                viewModel.liveData.removeObservers(this)
                viewModel.initDbLiveData()
                viewModel.liveDataDb.observe(this){
                    recyclerViewAdapter.submitList(it)
                }
            }
            else {
                registerLiveDataObserve()
            }
        }
    }

    private fun registerLiveDataObserve() {
        viewModel.observe(this) {
            if (it.isEmpty()) return@observe
            viewModel.liveDataDb.removeObservers(this)
            recyclerViewAdapter.submitList(it)
            loadingDialog.hide()
        }
    }

    private fun setUpRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                with(outRect) {
                    if (parent.getChildAdapterPosition(view) == 0) {
                        top = 18
                    }
                    left = 18
                    right = 18
                    bottom = 18
                }
            }
        })
        Picasso.get().isLoggingEnabled = true
    }

}



