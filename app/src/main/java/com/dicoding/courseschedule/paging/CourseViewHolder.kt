package com.dicoding.courseschedule.paging

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.util.DayName.Companion.getByNumber

class CourseViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private lateinit var course: Course
    private val timeString = itemView.context.resources.getString(R.string.time_format)

    //TODO 7 : Complete ViewHolder to show item
    fun bind(course: Course, clickListener: (Course) -> Unit) {
        this.course = course
        val txt_course = itemView.findViewById<TextView>(R.id.tv_course)
        val txt_lecturer = itemView.findViewById<TextView>(R.id.tv_lecturer)
        val txt_time = itemView.findViewById<TextView>(R.id.tv_time)

        course.apply {
            val dayName = getByNumber(day)
            val timeFormat = String.format(timeString, dayName, startTime, endTime)
            val course = this.courseName
            val lecturer = this.lecturer
            txt_course.text = course
            txt_lecturer.text = lecturer
            txt_time.text = timeFormat

        }

        itemView.setOnClickListener {
            clickListener(course)
        }
    }

    fun getCourse(): Course = course
}