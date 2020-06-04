package id.oratakashi.pekalonganstore.ui.splash

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.util.Pair
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import id.oratakashi.pekalonganstore.R
import id.oratakashi.pekalonganstore.root.App
import id.oratakashi.pekalonganstore.ui.login.LoginActivity
import id.oratakashi.pekalonganstore.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window){
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
//            enterTransition = Explode()
            exitTransition = Explode()
        }
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            if(App.sessions!!.isLogin()){
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }else{
                startActivity(Intent(applicationContext, LoginActivity::class.java),
                    ActivityOptions.makeSceneTransitionAnimation(this, Pair(ivLogo, "ivLogo")).toBundle())
//                overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            }
            finish()
        }, 2000L)
    }
}
