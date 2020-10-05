package com.devroach.latend

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_default.*
import kotlinx.android.synthetic.main.activity_logout_popup.*
import javax.security.auth.login.LoginException

class DefaultActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)

        database = FirebaseDatabase.getInstance()//데이터베이스 연동
        databaseReference = database.getReference("User")

        menu_icon.setOnClickListener{
            layout_drawer.openDrawer(GravityCompat.START) // START : left, END : right 랑 같은 말
        }

        naviView.setNavigationItemSelectedListener(this) // 네비게이션 메뉴 아이템에 클릭 속성 부여

        goto_all_alarm_btn.setOnClickListener {
            val intent = Intent(this, MakeAlarmActivity::class.java) // 다음 화면으로 이동하기 위한 Intent 객체 생성
            startActivity(intent) // intent에 저장되어있는 activity로 이동한다.
        }

        profile_iv.setOnClickListener {
            var intent = Intent(this,ProfileChangePopup::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            }
            startActivity(intent)
        }

        if(intent.hasExtra("image")){
            profile_iv.setImageResource(intent.getIntExtra("image",R.drawable.kakao_default_profile_image))
            Toast.makeText(this, "프로필 사진이 변경되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.updateinfo -> gotoUpdatePage()
            R.id.review -> gotoPlaystore()
            R.id.food -> gotoFoodPate()
            R.id.ad -> gotoAddSuggest()
            R.id.setting -> gotoSetting()
            R.id.help -> gotoHelp()
            R.id.logout -> goLogout()
        }
        layout_drawer.closeDrawers() // 네비게이션 뷰 닫기
        return false
    }

    private fun gotoPlaystore() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(
                "https://play.google.com/store/apps/details?id=com.example.android")
            setPackage("com.android.vending")
        }
        startActivity(intent)
    }

    private fun gotoUpdatePage() {
        val intent = Intent(this, UpdateInfoActivity::class.java) // 다음 화면으로 이동하기 위한 Intent 객체 생성
        //intent.putExtra("user_id", id_et.text.toString()) // id_et의 값을 user_id라는 이름으로 넘긴다.
        startActivity(intent) // intent에 저장되어있는 activity로 이동한다.
    }

    private fun gotoFoodPate() {
        val intent = Intent(this, DevFoodActivity::class.java) // 다음 화면으로 이동하기 위한 Intent 객체 생성
        startActivity(intent) // intent에 저장되어있는 activity로 이동한다.
    }

    private fun gotoAddSuggest() {
        val intent = Intent(this, AdSuggestActivity::class.java) // 다음 화면으로 이동하기 위한 Intent 객체 생성
        startActivity(intent) // intent에 저장되어있는 activity로 이동한다.
    }

    private fun gotoSetting() {
        val intent = Intent(this, SettingsActivity::class.java) // 다음 화면으로 이동하기 위한 Intent 객체 생성
        startActivity(intent) // intent에 저장되어있는 activity로 이동한다.
    }

    private fun gotoHelp() {
        val intent = Intent(this, HelpActivity::class.java) // 다음 화면으로 이동하기 위한 Intent 객체 생성
        startActivity(intent) // intent에 저장되어있는 activity로 이동한다.
    }

    private fun goLogout(){
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.activity_logout_popup, null)
        val tv_logout_inflater: TextView = view.findViewById(R.id.tv_logout)

        var alertDialog = AlertDialog.Builder(this)
            .setTitle("로그아웃")
            .setPositiveButton("확인"){dialog, which ->
                logOutComplete()
            }
            .setNeutralButton("취소", null)
            .create()

        alertDialog.setView(view)
        alertDialog.show()
    }

    private fun logOutComplete(){
        Toast.makeText(applicationContext, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
        val logout_restart = Intent(this, LogInActivity::class.java)
        logout_restart.putExtra("logout", "logout done")
        startActivity(logout_restart)

        finish()
    }



    override fun onBackPressed() {
        if(layout_drawer.isDrawerOpen(GravityCompat.START)){
            layout_drawer.closeDrawers()
        }
        else{
            super.onBackPressed() // 일반 백버튼 기능 실행
        }
    }
}

