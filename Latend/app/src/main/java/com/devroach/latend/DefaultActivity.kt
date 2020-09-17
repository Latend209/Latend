package com.devroach.latend

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_default.*

class DefaultActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)

        menu_icon.setOnClickListener{
            layout_drawer.openDrawer(GravityCompat.START) // START : left, END : right 랑 같은 말
        }

        naviView.setNavigationItemSelectedListener(this) // 네비게이션 메뉴 아이템에 클릭 속성 부여

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.updateinfo -> gotoUpdatePage()
            R.id.review -> gotoPlaystore()
            R.id.food -> gotoFoodPate()
            R.id.ad -> gotoAddSuggest()
            R.id.setting -> gotoSetting()
            R.id.help -> gotoHelp()
            R.id.logout -> Toast.makeText(applicationContext, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
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



    override fun onBackPressed() {
        if(layout_drawer.isDrawerOpen(GravityCompat.START)){
            layout_drawer.closeDrawers()
        }
        else{
            super.onBackPressed() // 일반 백버튼 기능 실행
        }
    }
}