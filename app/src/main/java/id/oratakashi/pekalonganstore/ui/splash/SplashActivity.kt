package id.oratakashi.pekalonganstore.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import id.oratakashi.pekalonganstore.R
import id.oratakashi.pekalonganstore.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }, 2000L)
    }
}
