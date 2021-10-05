package com.example.bookstore

import java.lang.Exception

class DbNotInitializedException(override val message: String?):Exception(message)