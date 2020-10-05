package com.devroach.latend

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.actirivy_profile.*
import kotlinx.android.synthetic.main.activity_default.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class ProfileChangePopup :Activity(){

    val REQUEST_IMAGE_CAPTURE = 1 //카메라 사진 촬영 요청코드
    val REQUEST_SELECT_IMAGE = 2 //이미지 선택 요청코드
    lateinit var curPhotoPath: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.actirivy_profile)



        normal_profile.setOnClickListener {
            val intent = Intent(this, DefaultActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            intent.putExtra("image", R.drawable.kakao_default_profile_image)
            startActivity(intent)
            finish()
        }
        album_profile.setOnClickListener{
            selectImageInAlbum()
        }
        camera_profile.setOnClickListener {
            setPermission()
            takeCapture()
        }

    }

    fun mOnClose(view: View){
        finish()
    }

    fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_SELECT_IMAGE)
        }
    }

    //카메라 실행
    private fun takeCapture() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile : File? = try {
                    createImageFile()
                }catch (ex: IOException){
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.devroach.latend.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)//갔다가 돌아오는 경우
                }
            }
        }
    }
    //이미지파일 생성
    private fun createImageFile(): File {
        val timestamp: String = SimpleDateFormat("yyyyMMdd-HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timestamp}_", ".jpg", storageDir)
            .apply { curPhotoPath = absolutePath}
        Log.d("절대 경로 : ",curPhotoPath)
    }

    private fun setPermission() {
        val permission = object : PermissionListener {
            override fun onPermissionGranted() {
                //Toast.makeText(this@ProfileChangePopup, "권한이 허용되었습니다", Toast.LENGTH_SHORT).show()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                //Toast.makeText(this@ProfileChangePopup, "권한이 거부되었습니다", Toast.LENGTH_SHORT).show()
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permission)
            .setRationaleMessage("카메라 앱을 사용하시려면 권한을 허용해주세요")
            .setDeniedMessage("권한을 거부하셨네요? 뒤지실래요???")
            .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA)
            .check()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {//받아온 사진 결과값
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val bitmap: Bitmap
            val file = File(curPhotoPath)
            if(Build.VERSION.SDK_INT < 28 ){
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.fromFile(file))
                //profile_iv.setImageBitmap(bitmap)
                val intent = Intent(this, DefaultActivity::class.java)
                intent.putExtra("image", bitmap)
                startActivity(intent)
            }
            else{
                val decode = ImageDecoder.createSource(this.contentResolver, Uri.fromFile(file))
                bitmap = ImageDecoder.decodeBitmap(decode)
                profile_iv.setImageBitmap(bitmap)
            }
            savePhoto(bitmap)
        }
        if(requestCode == REQUEST_SELECT_IMAGE && resultCode == Activity.RESULT_OK){



        }

    }

    private fun savePhoto(bitmap: Bitmap) {
        val folderPath = Environment.getExternalStorageDirectory().absolutePath + "/Pictures/"
        Log.d("folderPath : ",folderPath.toString())
        val timestamp: String = SimpleDateFormat("yyyyMMdd-HHmmss").format(Date())
        val fileName = "${timestamp}.jpeg"
        val folder = File(folderPath)
        if(!folder.isDirectory){//현재 해당 경로에 폴더가 존재하지 않는다면
            folder.mkdirs()//해당경로에 폴더 생성
        }
        val out = FileOutputStream(folderPath + fileName)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        Toast.makeText(this, "사진이 저장되었습니다.", Toast.LENGTH_SHORT).show()
    }
}