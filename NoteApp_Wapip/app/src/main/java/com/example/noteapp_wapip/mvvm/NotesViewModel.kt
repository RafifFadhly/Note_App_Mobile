package com.example.noteapp_wapip.mvvm

import android.app.Application
import android.database.Observable
import android.util.Log
import androidx.lifecycle.*
import androidx.navigation.NavController
import com.example.noteapp_wapip.R
import com.example.noteapp_wapip.db.Notes
import com.example.noteapp_wapip.db.NotesDao
import com.example.noteapp_wapip.db.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {


    val inputTitle = MutableLiveData<String>()
    val inputSubtitle = MutableLiveData<String>()
    val inputNoteText = MutableLiveData<String>()



    val displayAllnotes = repository.allnotes()



    fun addNotes(){

        val title  = inputTitle.value
        val subtitle = inputSubtitle.value
        val text = inputNoteText.value
        insertNote(Notes(0, title!!, subtitle!!, text!!))


    }

    fun insertNote(note: Notes) = viewModelScope.launch(Dispatchers.IO){
        repository.insertNote(note)


    }

    fun editupdateNote(note: Notes) = viewModelScope.launch(Dispatchers.IO){
        repository.updateNotes(note)
    }



    fun editUpdate(id: Int, title: String, sub: String, text: String ){

        editupdateNote(Notes(id, title, sub, text))

        Log.e("MYVIEWMODEL", sub)
        Log.e("MYVIEWMODEL", id.toString())
        Log.e("MYVIEWMODEL", title)
        Log.e("MYVIEWMODEL", sub)

    }


    fun delete(notes: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNote(notes)
    }

    fun deleteOneNote(id: Int, title: String, sub: String, text: String){

        delete(Notes(id, title, sub, text))

    }


    fun clearAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }


}