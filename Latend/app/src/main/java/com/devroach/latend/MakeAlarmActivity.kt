package com.devroach.latend

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.ToggleButton
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_make_alarm.*
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class MakeAlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_alarm)


        val day_btn = findViewById(R.id.alarm_btn_day) as ToggleButton
        day_btn.setOnClickListener {
            if(day_btn.isChecked){
               day_btn.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_blue))
            }else{
                day_btn.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_gray))
            }
        }

        val week_btn = findViewById(R.id.alarm_btn_week) as ToggleButton
        week_btn.setOnClickListener {
            if(week_btn.isChecked){
                week_btn.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_blue))
            }else{
                week_btn.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_gray))
            }
        }

        val month_btn = findViewById(R.id.alarm_btn_month) as ToggleButton
        month_btn.setOnClickListener {
            if(month_btn.isChecked){
                month_btn.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_blue))
            }else{
                month_btn.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_gray))
            }
        }

        val year_btn = findViewById(R.id.alarm_btn_year) as ToggleButton
        year_btn.setOnClickListener {
            if(year_btn.isChecked){
                year_btn.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_blue))
            }else{
                year_btn.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_gray))
            }
        }

        val dawn_btn = findViewById(R.id.alarm_btn_dawn) as ToggleButton
        dawn_btn.setOnClickListener {
            if(dawn_btn.isChecked){
                dawn_btn.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_blue))
            }else{
                dawn_btn.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_gray))
            }
        }

        val morning_btn = findViewById(R.id.alarm_btn_morning) as ToggleButton
        morning_btn.setOnClickListener {
            if(morning_btn.isChecked){
                morning_btn.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_blue))
            }else{
                morning_btn.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_gray))
            }
        }

        val afternoon_btn = findViewById(R.id.alarm_btn_afternoon) as ToggleButton
        afternoon_btn.setOnClickListener {
            if(afternoon_btn.isChecked){
                afternoon_btn.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_blue))
            }else{
                afternoon_btn.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_gray))
            }
        }

        val night_btn = findViewById(R.id.alarm_btn_night) as ToggleButton
        night_btn.setOnClickListener {
            if(night_btn.isChecked){
                night_btn.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_blue))
            }else{
                night_btn.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_gray))
            }
        }

        val textView: TextView  = findViewById(R.id.alarm_btn_calendar)
        textView.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())

        var cal = Calendar.getInstance()

            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd.MM.yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                textView.text = sdf.format(cal.time)

            }

            textView.setOnClickListener {
                DatePickerDialog(this@MakeAlarmActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
            }


        alarm_detail_btn.setOnClickListener {
            val intent = Intent(this, AlarmDetailActivity::class.java) // 다음 화면으로 이동하기 위한 Intent 객체 생성
            //intent.putExtra("user_id", id_et.text.toString()) // id_et의 값을 user_id라는 이름으로 넘긴다.
            startActivity(intent) // intent에 저장되어있는 activity로 이동한다.
        }

        alarm_complete_btn.setOnClickListener{
            val intent = Intent(this, DefaultActivity::class.java) // 다음 화면으로 이동하기 위한 Intent 객체 생성
            //intent.putExtra("user_id", id_et.text.toString()) // id_et의 값을 user_id라는 이름으로 넘긴다.
            startActivity(intent) // intent에 저장되어있는 activity로 이동한다.
        }

    }
}