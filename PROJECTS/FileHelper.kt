package com.fati.todolist

import java.io.FileOutputStream
import java.io.ObjectInputStream
import android.content.Context
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.ObjectOutputStream

class FileHelper {

    val FILENAME = "listinfo.dat"

    fun writeData(item: ArrayList<String>, context: Context) {
        val fos: FileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)
        oos.writeObject(item)
        oos.close()
    }

    fun readData(context: Context): ArrayList<String> {
        var itemList: ArrayList<String> = ArrayList() // Declare itemList at the start

        try {
            val fis: FileInputStream = context.openFileInput(FILENAME)
            val ois = ObjectInputStream(fis)
            itemList = ois.readObject() as ArrayList<String>
            ois.close()
        } catch (e: FileNotFoundException) {
            // File not found, return an empty list
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return itemList
    }
}
