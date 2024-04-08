package com.example.noteapp_wapip.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Notes::class], version=1)
abstract class NotesDatabase : RoomDatabase() {

    // declaring abstract reference for the interface

    abstract  val notesDao: NotesDao

    // singleton instances

    companion object{
        // THIS MAKES THE FIELD IMMEDIATELY VISIBILE TO OTHER THREAD
        @Volatile
        private var INSTANCE : NotesDatabase? = null
        fun getInstance (context: Context) : NotesDatabase{
            synchronized(this){
                var instance  = INSTANCE

                if (instance == null){

                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NotesDatabase::class.java,
                        "notes_database"


                    ).build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }


}