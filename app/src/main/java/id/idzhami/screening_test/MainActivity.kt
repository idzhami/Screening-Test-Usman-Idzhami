package id.idzhami.screening_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import id.idzhami.screening_test.ui.screenOne.ScreenOneActivity
import id.idzhami.screening_test.ui.screenThree.ScreenThreeActivity
import id.idzhami.screening_test.utils.startNewActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try {

            Handler().postDelayed({
                this@MainActivity.startNewActivity(ScreenOneActivity::class.java)
            }, 2000)
        }catch (e : Exception){

        }

    }
}