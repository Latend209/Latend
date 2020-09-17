package com.devroach.latend

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AlarmTestActivity : AppCompatActivity() {
    // 말그대로 현재 알람 기능이 완료되지 않아서 임시로 푸쉬알림 기능을 해보려고 만든 액티비티
    lateinit var notificationManager:NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    val channelId = "com.devroach.notification"
    val description = "My Notification"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_test)

        lateinit var alarmTitle:String

        if(intent.hasExtra("alarm_title")){
            alarmTitle = intent.getStringExtra("alarm_title").toString()
        }

        val show = findViewById<Button>(R.id.btn_show)
        notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        show.setOnClickListener{

            val intent = Intent(applicationContext, AlarmTestActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel =
                    NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.RED
                notificationChannel.enableVibration(true)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(this, channelId)
                    .setContentTitle(alarmTitle)
                    .setContentText("1/3 번째 알람입니다. (몇 번째 알람인지 알려주는 정보를 여기에 적자)")
                    .setSmallIcon(R.mipmap.ic_launcher_latend)
                    .setContentIntent(pendingIntent)
            }

            else{
                builder = Notification.Builder(this)
                    .setContentTitle("Android")
                    .setContentText("New Message")
                    .setSmallIcon(R.mipmap.ic_launcher_latend)
                    .setContentIntent(pendingIntent)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                notificationManager.notify(0,builder.build())
            }


        }
    }
}