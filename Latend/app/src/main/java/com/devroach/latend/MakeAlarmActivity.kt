package com.devroach.latend

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateUtils
import android.util.Log
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import kotlinx.android.synthetic.main.activity_make_alarm.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.stream.IntStream.range


class MakeAlarmActivity : AppCompatActivity() {
    // 알람의 최대 시간을 millis 로 저장해놓은 것.
    public var targetMillis:Long = System.currentTimeMillis();
    public var howManyAlarm:Int? = 1;
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_alarm)

        val day_btn = findViewById(R.id.alarm_btn_day) as ToggleButton
        val week_btn = findViewById(R.id.alarm_btn_week) as ToggleButton
        val month_btn = findViewById(R.id.alarm_btn_month) as ToggleButton
        val year_btn = findViewById(R.id.alarm_btn_year) as ToggleButton
        val calendar_btn = findViewById(R.id.alarm_btn_calendar) as ToggleButton

        val alarm_times = findViewById(R.id.alarm_times_et) as EditText

        val dawn_btn = findViewById(R.id.alarm_btn_dawn) as ToggleButton
        val morning_btn = findViewById(R.id.alarm_btn_morning) as ToggleButton
        val afternoon_btn = findViewById(R.id.alarm_btn_afternoon) as ToggleButton
        val night_btn = findViewById(R.id.alarm_btn_night) as ToggleButton
        val clock_btn = findViewById(R.id.alarm_btn_custom_time) as ToggleButton

        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.number_picker_dialog, null)
        var dialogText = dialogView.findViewById<TextView>(R.id.dialogTitle)
        val dialogStart = dialogView.findViewById<NumberPicker>(R.id.start_number_picker)
        val dialogEnd = dialogView.findViewById<NumberPicker>(R.id.end_number_picker)

        val term_arr:Array<ToggleButton> = arrayOf( // 기간 버튼 array
            day_btn,
            week_btn,
            month_btn,
            year_btn,
            calendar_btn
        )
        val time_arr:Array<ToggleButton> = arrayOf( // 시간 버튼 array
            dawn_btn,
            morning_btn,
            afternoon_btn,
            night_btn,
            clock_btn
        )

        val abs_time:Array<Long> = arrayOf( // 각 기간 버튼에 맞는 절대적인 시간 경과 millis ( Calendar 에 경우 따로 처리한다.)
            DateUtils.DAY_IN_MILLIS,
            DateUtils.WEEK_IN_MILLIS,
            DateUtils.DAY_IN_MILLIS * 30,
            DateUtils.YEAR_IN_MILLIS + 86400000
        )

        // 기간 버튼에 대한 하나 씩 누르게 하고 targetMillis 설정하기
        term_arr.forEachIndexed { index, toggleButton ->
            toggleButton.setOnCheckedChangeListener { v, isChecked ->
                if (isChecked){ // 버튼 눌렀을 때
                    v.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_blue));
                    term_arr.forEachIndexed { idx, btn ->
                        if(idx!=index)
                        {
                            btn.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_gray));
                            btn.isChecked = false;
                        }
                    }
                    if(index<4)
                        targetMillis = System.currentTimeMillis() + abs_time[index];
                }
                else{
                    toggleButton.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_gray));
                }


                // Locale 은 지역의 언어, 나라 등을 담고 있는 클래스다. getDefault 메소드는 JVM 에 적용된 디폴트 세팅 값(Locale)을 출력해준다.
                val date_text =
                    SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(
                        targetMillis
                    )
                Toast.makeText(
                    applicationContext,
                    "다음 알람은 " + date_text + "으로 알람이 설정되었습니다!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        // Calendar 버튼 눌렀을 때 팝업으로 달력 뜨고 targetMillis 설정하기
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        alarm_btn_calendar.setOnClickListener{
            if(alarm_btn_calendar.isChecked){
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    val calendar = Calendar.getInstance()
                    calendar[view.getYear(), view.getMonth(), view.getDayOfMonth(), 23, 59] = 0
                    val startTime = calendar.timeInMillis;
                    targetMillis = startTime;
                    alarm_btn_calendar.setText("" + (mYear % 100) + "/" + (mMonth + 1) + "/" + mDay)
                    // targetY,M,D 에는 사용자가 지정한 날짜가 String 형태로 저장된다.
                },
                year,
                month,
                day
            )
            dpd.datePicker.minDate = System.currentTimeMillis();
            dpd.show()
            }
        }

        // 횟수 관련
        howManyAlarm = Integer.parseInt(alarm_times.text.toString());
        Log.e("횟수", howManyAlarm.toString());

        val textWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(edit: Editable) { // editText 가 변화하면 호출된다.
                val s = edit.toString()
                if(s!=null && s!="") // 안해주면 NumberFormatException: For input string 에러가 뜬다.
                {howManyAlarm = Integer.parseInt(s);
                Log.e("반복 횟수", howManyAlarm.toString()); }
            }
        }

        alarm_times.addTextChangedListener(textWatcher);

        //////////////////////////////////////////////////////////////////////////////////////////////////////// 시간 관련

        val time_interval:Array<Int> = arrayOf(
            0, 5, 6, 11, 12, 17, 18, 23
        )

        var alarmTime = BooleanArray(24) { false };

        time_arr.forEachIndexed { index, toggleButton ->
            toggleButton.setOnCheckedChangeListener { v, isChecked ->
                if (isChecked){ // 버튼 눌렀을 때
                    v.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_blue));
                    if(index < 4)
                    { for(i in range(time_interval[index * 2], time_interval[index * 2 + 1] + 1))
                        alarmTime[i] = true
                    }
                }
                else{
                    toggleButton.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_gray));
                    if(index == 4){
                        if(dialogStart.value > dialogEnd.value)
                            dialogEnd.value +=24
                        for(i in range(dialogStart.value, dialogEnd.value + 1)){
                            if(i>23)
                            {
                                alarmTime[i-24] = false
                            }
                            else alarmTime[i] = false
                        }

                        alarm_btn_custom_time.setText("직접 선택")
                    }
                    else {
                        for(i in range(time_interval[index * 2], time_interval[index * 2 + 1] + 1))
                        alarmTime[i] = false
                    }
                }

//                alarmTime.forEachIndexed { index, b ->
//                    Log.e("버튼 반영 잘 되는지 ",index.toString()+b.toString())
//                }

            }
        }


        val mPickTimeBtn = findViewById<Button>(R.id.alarm_btn_custom_time)

        mPickTimeBtn.setOnClickListener {
            if(alarm_btn_custom_time.isChecked){

//            val cur_hour = SimpleDateFormat(
//                "a hh",
//                Locale.getDefault()
//            ).format(
//                System.currentTimeMillis()
//            )
//
//            var hour_arr = cur_hour.split(" ");
//            var defaultHour = hour_arr[1].toInt() // 현재 몇 시인지가 담기게 된다.
//            if(hour_arr[0] == "오후")
//                defaultHour+=12
//            if(defaultHour == 24)
//                defaultHour = 12
//            else if(defaultHour == 12)
//                defaultHour = 0


            dialogStart.minValue = 0
            dialogStart.maxValue = 23
            dialogEnd.minValue = 1
            dialogEnd.maxValue = 24
            dialogStart.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            dialogEnd.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS


            var check_correctTime = true

//            dialogEnd.setOnValueChangedListener { numberPicker, i1, i2 ->
//                /* i2가 변경될 때마다 실행할 when 조건문 */
//                when {
//                    i2 == dialogStart.value ->  {
//                        dialogText.text = "끝나는 시간이 시작시간은 같을 수 없습니다. 시간을 재설정해주세요."
//                        check_correctTime = false
//                    }
//                    i1 == dialogEnd.value -> {
//                        dialogText.text = "끝나는 시간이 시작시간은 같을 수 없습니다. 시간을 재설정해주세요."
//                        check_correctTime = false
//                    }
//                    else -> {
//                        dialogText.text = "알람 시작 시간과 끝나는 시간을 입력하세요"
//                        check_correctTime = true
//                    }
//                }
//            }

            // view 는 하나의 부모 뷰에만 추가될 수 있는데, 여러번 다이얼로그를 띄우는 순간 중복으로 view 가 참조되어 에러를 일으킨다. 따라서 뷰의 참조 여부를 확인한 후 setView 를 사용하면 된다.
            if (dialogView.getParent() != null) (dialogView.getParent() as ViewGroup).removeView(
                dialogView
            )

            builder.setView(dialogView)
                .setPositiveButton("확인") { dialogInterface, i ->
                    if(check_correctTime){
                        if(dialogStart.value > dialogEnd.value)
                            dialogEnd.value +=24
                        for(i in range(dialogStart.value, dialogEnd.value + 1)){
                            if(i>23)
                            {
                                alarmTime[i-24] = true
                            }
                            else alarmTime[i] = true
                        }
                        alarm_btn_custom_time.setText("" + dialogStart.value + "시 ~ " + dialogEnd.value + "시")
                    }
                    else{
                        alarm_btn_custom_time.isChecked = false
                        alarm_btn_custom_time.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_gray));
                    }
                }
                .setNegativeButton("취소") { dialogInterface, i ->
                    /* 취소일 때 아무 액션이 없으므로 빈칸 */
                    alarm_btn_custom_time.isChecked = false
                    alarm_btn_custom_time.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_gray));

                }
                .show()
            }
        }


        // 알람 디테일 페이지로 이동
        alarm_detail_btn.setOnClickListener {
            val intent = Intent(this, AlarmDetailActivity::class.java) // 다음 화면으로 이동하기 위한 Intent 객체 생성
            //intent.putExtra("user_id", id_et.text.toString()) // id_et의 값을 user_id라는 이름으로 넘긴다.
            startActivity(intent) // intent에 저장되어있는 activity로 이동한다.
        }

        // 알람 완료 버튼 눌렀을 경우
        alarm_complete_btn.setOnClickListener{

            // millis 에는 현재시간이 millis 로 저장되어있다.
            val millis: Long = Calendar.getInstance().timeInMillis
            // millis 에 있는 값을 통해 날짜를 구한다.
            val nextNotifyTime: Calendar = GregorianCalendar()
            nextNotifyTime.timeInMillis = millis
            val currentTime = nextNotifyTime.time

            val dateFormat = SimpleDateFormat("dd")
            val dateString = dateFormat.format(targetMillis)
            val monthFormat = SimpleDateFormat("MM")
            val monthString = monthFormat.format(targetMillis)
            val yearFormat = SimpleDateFormat("yyyy")
            val yearString = yearFormat.format(targetMillis)


            // 현재 지정된 시간으로 알람 시간 설정
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            calendar[Calendar.DAY_OF_MONTH] = Integer.parseInt(dateString) -1
            calendar[Calendar.MONTH] = Integer.parseInt(monthString)
            calendar[Calendar.YEAR] = Integer.parseInt(yearString)
            calendar[Calendar.HOUR_OF_DAY] = 23
            calendar[Calendar.MINUTE] = 59
            calendar[Calendar.SECOND] = 0

            // 이미 지난 시간을 지정했다면 다음날 같은 시간으로 설정
            if (calendar.before(Calendar.getInstance())) {
                calendar.add(Calendar.DATE, 1)
                Toast.makeText(
                    applicationContext,
                    "이미 지난 시간으로 설정하셨습니다! 우선 내일 이 시간으로 알람을 설정합니다. ",
                    Toast.LENGTH_SHORT
                ).show()
            }

            val currentMillis = System.currentTimeMillis()
            val difMillis = targetMillis - currentMillis.toFloat()

            // 중복없는 랜덤한 수를 repeatInt 만큼 생성한다. -> 시간에 비해 반복 횟수가 너무 많으면 앱이 멈춘다. -> 어떠한 상한선 ex. 1시간 당 최대 2번 알람 가능 등의 리미트를 뒤야.
            val set: MutableSet<Long> = HashSet()
            while (set.size < howManyAlarm!!) {
                val randomNum: Number = Math.random()
                val mill = (targetMillis - difMillis * randomNum.toFloat()).toLong()
                val check_time = SimpleDateFormat(
                    "a hh",
                    Locale.getDefault()
                ).format(
                    mill
                )

                var arr = check_time.split(" ");
                var hourCheck = arr[1].toInt()
                if(arr[0] == "오후")
                    hourCheck+=12
                if(hourCheck == 24)
                    hourCheck = 12
                else if(hourCheck == 12)
                    hourCheck = 0

                // 원하는 시간대의 알람만 추가한다.
                if(alarmTime[hourCheck])
                    set.add(mill)
            }

            val test = SimpleDateFormat(
                "yyyy년 MM월 dd일 EE요일 a hh시 mm분 ss초 ",
                Locale.getDefault()
            ).format(
                targetMillis
            )

            Log.e("최대 기간", test);

            val list: List<Long> = ArrayList(set)
            Collections.sort(list)
            // targetList 안에 알람이 울릴 정보가 millis 로 들어가 있다.
            for (i in 0 until howManyAlarm!!) {
                val date_text = SimpleDateFormat(
                    "yyyy년 MM월 dd일 EE요일 a hh시 mm분 ss초 ",
                    Locale.getDefault()
                ).format(
                    list[i]
                )
                Log.e("targetList 안에 있는 값", date_text)
                // Log.e("targetlist 원래 값 mille", targetList.toString());
                calendar.timeInMillis = list[i]
                diaryNotification(calendar, i)
            }

            val date_text =
                SimpleDateFormat(
                    "최대 yyyy년 $monthString 월 dd일 EE요일 a hh시 mm분 ss초 안에 $howManyAlarm 번의 랜덤 알람이 울립니다. ",
                    Locale.getDefault()
                ).format(
                    targetMillis
                )

            Toast.makeText(applicationContext, date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_LONG)
                .show()

            val intent = Intent(this, DefaultActivity::class.java) // 다음 화면으로 이동하기 위한 Intent 객체 생성
//            intent.putExtra("alarm_title", alarm_name_et.text.toString()) // id_et의 값을 alarm_title 이라는 이름으로 넘긴다.
            startActivity(intent) // intent에 저장되어있는 activity로 이동한다.
            finish();
        }

    }

    fun diaryNotification(calendar: Calendar, index: Int) {
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//        Boolean dailyNotify = sharedPref.getBoolean(SettingsActivity.KEY_PREF_DAILY_NOTIFICATION, true);
        val dailyNotify = true // 무조건 알람을 사용

        // PackageManager : 스마트폰 내에 설치된 앱 목록을 알기 위해 사용하는 서비스, 인텐트에 반응할 액티비티가 존재하는지도 체크할 수 있다.
        val pm = this.packageManager
        // ComponentName : 외부 어플리케이션을 명시적으로 호출할 때
        val receiver = ComponentName(this, DeviceBootReceiver::class.java)
        // Intent 는 이벤트를 의미한다. BroadcastReceiver 로 Intent 가 전달된다.
        val alarmIntent = Intent(this, AlarmReceiver::class.java)
        // AlarmManager 가 Intent 를 가지고 있다가 일정 시간이 흐른 뒤 전달하기 때문에 PendingIntent 로 만들어야 한다. 여러 개의 PendingIntent 를 사용한다면 requestCode 를 다르게 해줘야한다.
        // 여기서는 BroadcastReceiver 를 시작하는 Intent 이다.
        alarmIntent.putExtra("alarm_title", alarm_name_et.text.toString()) // id_et의 값을 alarm_title 이라는 이름으로 넘긴다.
        alarmIntent.putExtra("alarm_times", howManyAlarm) // id_et의 값을 alarm_title 이라는 이름으로 넘긴다.
        alarmIntent.putExtra("alarm_index", index + 1) // id_et의 값을 alarm_title 이라는 이름으로 넘긴다.
        val pendingIntent = PendingIntent.getBroadcast(this, index, alarmIntent, 0)
        // AlarmManager 가 이벤트를 보내기에 App 이 실행 중이 아니더라도 알림을 받아 어떤 작업을 처리하도록 구현할 수 있다.
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager


        // 사용자가 매일 알람을 허용했다면
        if (dailyNotify) {
            if (alarmManager != null) {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY, pendingIntent
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // setExact 와 동일하지만 절전 모드에서도 동작한다.
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                }
            }

            // 부팅 후 실행되는 리시버 사용가능하게 설정
            pm.setComponentEnabledSetting(
                receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        }
        // 알람 비활성화하는 코드
//        else { //Disable Daily Notifications
//            if (PendingIntent.getBroadcast(this, 0, alarmIntent, 0) != null && alarmManager != null) {
        // 알람을 취소할 때는 등록한 PendingIntent 를 인자로 전달한다.
//                alarmManager.cancel(pendingIntent);
//                //Toast.makeText(this,"Notifications were disabled",Toast.LENGTH_SHORT).show();
//            }
//            pm.setComponentEnabledSetting(receiver,
//                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                    PackageManager.DONT_KILL_APP);
//        }
    }

}