package com.example.contextmenu

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var ET_enterValue: EditText
    private lateinit var TV_result: TextView
    private lateinit var BTN_randomValue: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ET_enterValue = findViewById(R.id.ET_enterValue)
        TV_result = findViewById(R.id.TV_result)
        BTN_randomValue = findViewById(R.id.BTN_randomValue)
        registerForContextMenu(ET_enterValue)
        registerForContextMenu(TV_result)
        BTN_randomValue.setOnClickListener(this)

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val view = currentFocus

        if (view is TextView) {
            when (item.itemId) {
                R.id.context_menu_color -> {
                    toColorIt(view)
                }

                R.id.context_menu_exit -> finish()
            }
        }

        return super.onContextItemSelected(item)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.BTN_randomValue) {
            TV_result.text = IntRange(1, 50).random().toString()
            TV_result.requestFocus()
        }
    }

    private fun getValue(v: TextView): Int {
        var value = v.text.toString()
        var valueInt = 0
        if (!value.contains(""".*\D+.*""".toRegex()) && value.isNotEmpty()) {
            valueInt = value.toInt()
        }
        return valueInt
    }

    private fun toColorIt(v: TextView) { // порядок цветов сохранен в соответствии с заданием
        val value = getValue(v)
        if (v is EditText && value in 1..5) {
            when (value) {
                1 -> v.setBackgroundColor(resources.getColor(R.color.orange))
                2 -> v.setBackgroundColor(resources.getColor(R.color.yellow))
                3 -> v.setBackgroundColor(resources.getColor(R.color.green))
                4 -> v.setBackgroundColor(resources.getColor(R.color.blue))
                5 -> v.setBackgroundColor(resources.getColor(R.color.red))
            }
        } else if (v !is EditText && value in 1..50) {
            when (value) {
                in 1..10 -> v.setBackgroundColor(resources.getColor(R.color.red))
                in 11..20 -> v.setBackgroundColor(resources.getColor(R.color.orange))
                in 21..30 -> v.setBackgroundColor(resources.getColor(R.color.yellow))
                in 31..40 -> v.setBackgroundColor(resources.getColor(R.color.green))
                in 41..50 -> v.setBackgroundColor(resources.getColor(R.color.blue))
            }
        } else {
            v.setBackgroundColor(resources.getColor(R.color.transparent))

        }

    }

}