package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener{

    private lateinit var addCourseViewModel: AddCourseViewModel
    private lateinit var startTime: String
    private lateinit var endTime: String
    private lateinit var txt_start: TextView
    private lateinit var txt_end: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        val factory = AddCourseViewModelFactory.createFactory(this)
        addCourseViewModel = ViewModelProvider(this, factory)[AddCourseViewModel::class.java]
        timePickerSetup()
        txt_start = findViewById(R.id.txt_starttime)
        txt_end = findViewById(R.id.txt_endttime)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_insert -> {
                val courseName = findViewById<TextInputEditText>(R.id.edt_coursename).text.toString().trim()
                val day = findViewById<Spinner>(R.id.spinner_date).selectedItemPosition
                val lecturer = findViewById<TextInputEditText>(R.id.edt_lecture).text.toString().trim()
                val note = findViewById<TextInputEditText>(R.id.edt_notes).text.toString().trim()
                if (courseName.isEmpty() || lecturer.isEmpty() || note.isEmpty() || startTime.isEmpty() || endTime.isEmpty()){
                    Toast.makeText(applicationContext, "Tolong Isi Field Terlebih dahulu", Toast.LENGTH_SHORT).show()
                }else{
                    addCourseViewModel.insertCourse(courseName, day, startTime, endTime, lecturer, note)
                    finish()
                    Toast.makeText(applicationContext, "Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun timePickerSetup(){
        val timePickerFragment = TimePickerFragment()
        val startPicker = findViewById<ImageButton>(R.id.imageButton)
        val endPicker = findViewById<ImageButton>(R.id.imageButton2)
        startPicker.setOnClickListener {
            timePickerFragment.show(supportFragmentManager, "start")
        }
        endPicker.setOnClickListener {
            timePickerFragment.show(supportFragmentManager, "end")
        }
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        if (tag == "start"){
            val time = String.format("%02d", hour) + ":" + String.format("%02d", minute)
            txt_start.text = time
            startTime = time
        }
        else{
            val time = String.format("%02d", hour) + ":" + String.format("%02d", minute)
            txt_end.text = time
            endTime = time
        }
    }


}