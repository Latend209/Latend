package com.devroach.latend

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.ToggleButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_alarm_test.*
import kotlinx.android.synthetic.main.activity_make_alarm.*
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class MakeAlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_alarm)

        val day_btn = findViewById(R.id.alarm_btn_day) as ToggleButton
        val week_btn = findViewById(R.id.alarm_btn_week) as ToggleButton
        val month_btn = findViewById(R.id.alarm_btn_month) as ToggleButton
        val year_btn = findViewById(R.id.alarm_btn_year) as ToggleButton
        val calendar_btn = findViewById(R.id.alarm_btn_calendar) as ToggleButton

        val dawn_btn = findViewById(R.id.alarm_btn_dawn) as ToggleButton
        val morning_btn = findViewById(R.id.alarm_btn_morning) as ToggleButton
        val afternoon_btn = findViewById(R.id.alarm_btn_afternoon) as ToggleButton
        val night_btn = findViewById(R.id.alarm_btn_night) as ToggleButton
        val clock_btn = findViewById(R.id.alarm_btn_custom_time) as ToggleButton

        val term_arr:Array<ToggleButton> = arrayOf(day_btn, week_btn, month_btn, year_btn,calendar_btn)
        val time_arr:Array<ToggleButton> = arrayOf(dawn_btn,morning_btn,afternoon_btn,night_btn, clock_btn)

        term_arr.forEach {
            it.setOnCheckedChangeListener { v, isChecked ->
                if (isChecked){
                    term_arr.filter { it != v && it.isChecked }.forEach {
                        it.isChecked = false
                        it.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_gray))
                    }
                    it.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_blue))
                }
            }
        }

        time_arr.forEach {
            it.setOnCheckedChangeListener { v, isChecked ->
                if (isChecked){
                    it.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_blue))
                    if(it == clock_btn){
                        time_arr.filter { it != clock_btn && it.isChecked }.forEach {
                            it.isChecked = false
                            it.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_gray))
                        }
                    }
                    else if(clock_btn.isChecked){
                        clock_btn.isChecked = false
                        clock_btn.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_gray))
                    }
                }
                else {
                    it.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_gray))
                }
            }
        }

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        alarm_btn_calendar.setOnClickListener{
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                alarm_btn_calendar.setText(""+(mYear%100)+"/"+(mMonth+1)+"/"+mDay)
            }, year, month, day)
            dpd.show()
        }

        val mPickTimeBtn = findViewById<Button>(R.id.alarm_btn_custom_time)

        mPickTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                mPickTimeBtn.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }


        alarm_detail_btn.setOnClickListener {
            val intent = Intent(this, AlarmDetailActivity::class.java) // 다음 화면으로 이동하기 위한 Intent 객체 생성
            //intent.putExtra("user_id", id_et.text.toString()) // id_et의 값을 user_id라는 이름으로 넘긴다.
            startActivity(intent) // intent에 저장되어있는 activity로 이동한다.
        }

        alarm_complete_btn.setOnClickListener{
            val intent = Intent(this, AlarmTestActivity::class.java) // 다음 화면으로 이동하기 위한 Intent 객체 생성
            intent.putExtra("alarm_title", alarm_name_et.text.toString()) // id_et의 값을 user_id라는 이름으로 넘긴다.
            startActivity(intent) // intent에 저장되어있는 activity로 이동한다.
        }

    }

}