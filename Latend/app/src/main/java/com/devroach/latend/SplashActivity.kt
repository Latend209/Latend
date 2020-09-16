package com.devroach.latend

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.AnimationUtils.loadAnimation
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*
import java.sql.Time

class SplashActivity : AppCompatActivity(){

    val TAG: String = "Log"
    //lateinit var logo_anim: Animation

    private val mRunnable: Runnable = Runnable {
        if(!isFinishing){
            slideUp(iv_logo, 1000)
            Handler().postDelayed({
                var fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
                tv_logo.startAnimation(fadeInAnimation)
                et_id.startAnimation(fadeInAnimation)
                et_pw.startAnimation(fadeInAnimation)
                tv_logo.visibility = View.VISIBLE
                et_id.visibility = View.VISIBLE
                et_pw.visibility = View.VISIBLE
            },1000)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(mRunnable, 1000)

//        var slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
//        iv_logo.startAnimation(slideUpAnimation)



//        logo_anim = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_up)
//        iv_logo.startAnimation(logo_anim)

//        val intent : Intent = Intent(this, LoginActivity::class.java)
//        startActivity(intent)


        test_btn.setOnClickListener{
            val intent = Intent(this, DefaultActivity::class.java) // 다음 화면으로 이동하기 위한 Intent 객체 생성
            //intent.putExtra("user_id", id_et.text.toString()) // id_et의 값을 user_id라는 이름으로 넘긴다.
            startActivity(intent) // intent에 저장되어있는 activity로 이동한다.
            finish() // 자기 자신 액티비티를 파괴한다.
        }

    }

    fun slideUp(view: View, time: Int){
        val animation = TranslateAnimation(0f, 0f, 0f,-600f)
        animation.fillAfter = true
        animation.duration = time.toLong()
        view.startAnimation(animation)
    }



}