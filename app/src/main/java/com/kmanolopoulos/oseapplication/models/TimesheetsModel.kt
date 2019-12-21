package com.kmanolopoulos.oseapplication.models

import android.content.Context

class TimesheetsModel(val context: Context) {

    // Parse JSON file data to rebuild database data
    // TODO: Implement this method
    fun parseJsonData(jsonData: String): ParsingModel {
        return ParsingModel(context, ParsingModel.Values.FILE_WRONG_FORMAT)
        //return ParsingModel(context, ParsingModel.Values.FILE_OK)
    }
}