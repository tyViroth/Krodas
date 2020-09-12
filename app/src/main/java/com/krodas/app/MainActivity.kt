package com.krodas.app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.krodas.app.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private val storagePermissionCode: Int = 111;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val requiredPermission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val checkVal: Int = applicationContext.checkCallingOrSelfPermission(requiredPermission)

        fullScreen()
        checkPermission(isGranted = checkVal)
    }

    private fun initView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commitNow()
    }

    private fun fullScreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )

    }

    private fun checkPermission(isGranted: Int = 0) {
        if (isGranted == PackageManager.PERMISSION_GRANTED) {
            initView()
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            Toast.makeText(
                this,
                "Write External Storage permission allows us to read  files. Please allow this permission in App Settings.",
                Toast.LENGTH_LONG
            ).show();
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                storagePermissionCode
            );
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == storagePermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] <= PackageManager.PERMISSION_GRANTED) {
                initView()
            } else {
                println("Permission Denied, You cannot use local drive .");
            }
        }
    }
}