package com.baseer.app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private val CAMERA_PERMISSION_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // تهيئة محرك النطق الصوتي
        tts = TextToSpeech(this, this)

        val btnStartHelp = findViewById<Button>(R.id.btnStartHelp)
        val btnWhatIsFront = findViewById<Button>(R.id.btnWhatIsFront)
        val btnGoToPlace = findViewById<Button>(R.id.btnGoToPlace)
        val btnWhereAmI = findViewById<Button>(R.id.btnWhereAmI)
        val btnSavedPlaces = findViewById<Button>(R.id.btnSavedPlaces)
        val btnStopHelp = findViewById<Button>(R.id.btnStopHelp)

        btnStartHelp.setOnClickListener {
            speakOut("مرحبًا بك في بصير، مساعدك الذكي.")
        }

        // زر ماذا يوجد أمامي مع التحقق من صلاحية الكاميرا
        btnWhatIsFront.setOnClickListener {
            checkCameraPermissionAndScan()
        }

        btnGoToPlace.setOnClickListener {
            speakOut("إلى أين تريد الذهاب؟")
        }

        btnWhereAmI.setOnClickListener {
            speakOut("جاري تحديد موقعك الحالي...")
        }

        btnSavedPlaces.setOnClickListener {
            speakOut("عرض الوجهات المحفوظة.")
        }

        btnStopHelp.setOnClickListener {
            speakOut("تم إيقاف المساعدة.")
        }
    }

    private fun checkCameraPermissionAndScan() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // طلب الصلاحية إذا لم تكن ممنوحة
            speakOut("الرجاء السماح بتطبيق بصير باستخدام الكاميرا لفحص ما أماه")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        } else {
            speakOut("جاري فحص ما هو أمامك عبر الكاميرا...")
            // هنا سيتم لاحقاً تشغيل نموذج الكاميرا واكتشاف الأشياء
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                speakOut("تمت الموافقة على الكاميرا. جاري فحص المشهد...")
            } else {
                speakOut("عذراً، لا يمكن فحص المشهد بدون إذن الكاميرا.")
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale("ar"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "اللغة العربية غير متوفرة في محرك الصوت", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }
        super.onDestroy()
    }
}
