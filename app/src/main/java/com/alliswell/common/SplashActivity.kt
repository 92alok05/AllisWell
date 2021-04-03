package com.alliswell.common

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alliswell.patients.PatientsActivity
import java.lang.Math.atan
import java.lang.Math.tan

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        simulateHeavyLayout()
        startActivity(Intent(this, PatientsActivity::class.java))
        finish()
    }

    // This function is used to make sure splash screen is visible for a brief readable time
    private fun simulateHeavyLayout() {
        // simulate some heavy UI inflation
        for (i in 0..1000000) {
            val d = tan(atan(tan(atan(tan(atan(tan(atan(tan(atan(123456789.123456789))))))))))
            d.toString()
        }
    }
}