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

//        val intent : Intent = Intent(this, LoginActivity::class.java)
//        startActivity(intent)

        test_btn.setOnClickListener{
            val intent = Intent(this, DefaultActivity::class.java) // 다음 화면으로 이동하기 위한 Intent 객체 생성
            //intent.putExtra("user_id", id_et.text.toString()) // id_et의 값을 user_id라는 이름으로 넘긴다.
            startActivity(intent) // intent에 저장되어있는 activity로 이동한다.
            finish() // 자기 자신 액티비티를 파괴한다.
        }
    }

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