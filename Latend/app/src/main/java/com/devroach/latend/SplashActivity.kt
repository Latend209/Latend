package com.devroach.latend

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity(){

    val TAG: String = "Log"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        var trans_anim = TranslateAnimation(0f, 0f, 0f, -350f)
        trans_anim.setFillAfter(true)
        trans_anim.setDuration(2000)

        val fade_in_anim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        fade_in_anim.setFillAfter(true)
        fade_in_anim.setDuration(2000)

        tv_logo.setAnimation(fade_in_anim);
        iv_logo.setAnimation(trans_anim);


        Handler().postDelayed({
            val intent = Intent(this, LogInActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
            finish()
        },2000)


    }
    fun slideUp(view: View, time: Int){
        val animation = TranslateAnimation(0f, 0f, 0f, -350f)
        animation.fillAfter = true
        animation.duration = time.toLong()
        view.startAnimation(animation)
    }


}