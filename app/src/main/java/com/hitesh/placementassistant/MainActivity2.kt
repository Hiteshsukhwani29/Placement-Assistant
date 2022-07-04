package com.hitesh.placementassistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container1,Home()).commit()
    }
}