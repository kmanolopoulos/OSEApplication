package com.kmanolopoulos.oseapplication.models

import android.content.Context
import com.kmanolopoulos.oseapplication.R

class ParsingModel(val context: Context, val result: Values) {

    enum class Values {
        FILE_OK,
        FILE_NOT_DOWNLOADED,
        FILE_WRONG_FORMAT
    }

    // Get a message equivalent to parsing result
    fun getMessage(): String = when (result) {
        Values.FILE_OK -> context.getString(R.string.file_ok)
        Values.FILE_NOT_DOWNLOADED -> context.getString(R.string.file_not_downloaded)
        Values.FILE_WRONG_FORMAT -> context.getString(R.string.file_wrong_format)
    }
}