package com.merveoz.sqliteexample.data.source.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.merveoz.sqliteexample.data.model.Note

class Database(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL("CREATE TABLE dailyNotes (id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR, description VARCHAR, priorityColorId INTEGER)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) = Unit

    fun addDailyNote(note: Note) {

        ContentValues().apply {
            put("title", note.title)
            put("description", note.description)
            put("priorityColorId", note.priorityColorId)
            put("isChecked", note.isChecked)

            writableDatabase.insert("dailyNotes", null, this)
        }
    }

    fun getDailyNotes(): List<Note> {
        val columns = arrayOf("id", "title", "description", "priorityColorId")
        val cursor = readableDatabase.query("dailyNotes",columns,null,null,null,null,null)

        val dailyNotes = mutableListOf<Note>()

        val idIndex = cursor.getColumnIndex("id")
        val titleIndex = cursor.getColumnIndex("title")
        val descriptionIndex = cursor.getColumnIndex("description")
        val priorityIndex = cursor.getColumnIndex("priorityColorId")

        while (cursor.moveToNext()){
            dailyNotes.add(
                Note(
                    id = cursor.getInt(idIndex),
                    title = cursor.getString(titleIndex),
                    description = cursor.getString(descriptionIndex),
                    priorityColorId = cursor.getInt(priorityIndex)
                )
            )
        }
        readableDatabase.close()
        return dailyNotes
    }

    fun deleteDailyNote(id: Int){
        writableDatabase.delete("dailyNotes","id=$id", null)
    }

    companion object {
        private const val DATABASE_NAME = "Notes"
        private const val DATABASE_VERSION = 1
    }
}