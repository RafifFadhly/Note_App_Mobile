package com.example.noteapp_wapip.db

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteapp_wapip.R


@Entity(tableName="NOTES")
data class Notes (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("notes_id")
    var id: Int,


    @ColumnInfo("notes_title")
    var title : String,


    @ColumnInfo("notes_subtitle")
    var subtitle : String,

    @ColumnInfo("notes_text")
    var notesText: String,




    )