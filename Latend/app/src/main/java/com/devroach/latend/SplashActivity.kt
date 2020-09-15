package com.devroach.latend

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
    }

    fun slideUp(view: View, time: Int){
        val animation = TranslateAnimation(0f, 0f, 0f,-600f)
        animation.fillAfter = true
        animation.duration = time.toLong()
        view.startAnimation(animation)
    }



}