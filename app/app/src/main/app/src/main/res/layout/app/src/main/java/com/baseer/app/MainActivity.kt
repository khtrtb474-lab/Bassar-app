package com.baseer.app

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStartHelp = findViewById<Button>(R.id.btnStartHelp)
        val btnWhatIsFront = findViewById<Button>(R.id.btnWhatIsFront)
        val btnGoToPlace = findViewById<Button>(R.id.btnGoToPlace)
        val btnWhereAmI = findViewById<Button>(R.id.btnWhereAmI)
        val btnSavedPlaces = findViewById<Button>(R.id.btnSavedPlaces)
        val btnStopHelp = findViewById<Button>(R.id.btnStopHelp)

        btnStartHelp.setOnClickListener {
            showToast("مرحبًا بك في بصير، مساعدك الذكي.")
        }

        btnWhatIsFront.setOnClickListener {
            showToast("جاري فحص ما هو أمامك...")
        }

        btnGoToPlace.setOnClickListener {
            showToast("إلى أين تريد الذهاب؟")
        }

        btnWhereAmI.setOnClickListener {
            showToast("جاري تحديد موقعك الحالي...")
        }

        btnSavedPlaces.setOnClickListener {
            showToast("عرض الوجهات المحفوظة.")
        }

        btnStopHelp.setOnClickListener {
            showToast("تم إيقاف المساعدة.")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
