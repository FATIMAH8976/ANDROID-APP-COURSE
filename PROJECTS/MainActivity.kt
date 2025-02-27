package com.fati.todolist

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var item: EditText
    lateinit var add: Button
    lateinit var listView: ListView

    var itemlist = ArrayList<String>()
    var fileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        item = findViewById(R.id.editText)
        add = findViewById(R.id.button)
        listView = findViewById(R.id.list)

        // Read saved data
        itemlist = fileHelper.readData(applicationContext)

        // Initialize ArrayAdapter
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemlist)
        listView.adapter = arrayAdapter

        add.setOnClickListener {
            val itemName: String = item.text.toString()
            itemlist.add(itemName)
            item.setText("")
            fileHelper.writeData(itemlist, applicationContext)
            arrayAdapter.notifyDataSetChanged()
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val alert = AlertDialog.Builder(this@MainActivity)
            alert.setTitle("DELETE")
            alert.setMessage("Do you want to delete this item from the list?")
            alert.setCancelable(false)

            alert.setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.cancel()
            }

            alert.setPositiveButton("Yes") { _, _ ->
                itemlist.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
                fileHelper.writeData(itemlist, applicationContext)
            }

            alert.create().show()
        }

        // Apply window insets for UI adjustments
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
