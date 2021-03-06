package id.oratakashi.pekalonganstore.ui.register

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.util.Pair
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.material.snackbar.Snackbar
import dmax.dialog.SpotsDialog
import id.oratakashi.pekalonganstore.R
import id.oratakashi.pekalonganstore.data.db.Sessions
import id.oratakashi.pekalonganstore.root.App
import id.oratakashi.pekalonganstore.ui.login.LoginActivity
import id.oratakashi.pekalonganstore.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window){
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            exitTransition = Explode().setDuration(5000)
            enterTransition = Explode().setDuration(550)
        }
        setContentView(R.layout.activity_register)

        ViewCompat.setTransitionName(imageView, "ivLogo")

        ButterKnife.bind(this)

        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)

        setupViewModel()
    }

    fun setupViewModel(){
        viewModel.showMessage.observe(this, Observer { message ->
            message?.let {
                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.errorRegister.observe(this, Observer { error ->
            error?.let {
                if(it) Snackbar.make(rlBase, "Gagal melakukan pendaftaran!",
                    Snackbar.LENGTH_SHORT)
                    .setAction("Coba lagi!"){
                        viewModel.postRegister(
                            etEmail.text.toString(),
                            etPassword.text.toString(),
                            etName.text.toString()
                        )
                    }.show()
            }
        })
        viewModel.progressRegister.observe(this, Observer { progress ->
            progress?.let {
                when(it){
                    true -> {
                        btnRegister.visibility = View.GONE
                        shRegister.visibility = View.VISIBLE
                        shRegister.startShimmerAnimation()
                    }
                    false -> {
                        btnRegister.visibility = View.VISIBLE
                        shRegister.visibility = View.GONE
                        shRegister.stopShimmerAnimation()
                    }
                }
            }
        })
        viewModel.responseRegister.observe(this, Observer { response ->
            response?.let {
                when(it.status){
                    true -> {
                        Toast.makeText(applicationContext, it.message!!, Toast.LENGTH_SHORT).show()
                        App.sessions!!.putString(Sessions.id_user, it.data!!.id!!)
                        App.sessions!!.putString(Sessions.name, it.data.name!!)
                        App.sessions!!.putString(Sessions.email, it.data.email!!)

                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        finish()
                    }
                    false -> {
                        Toast.makeText(applicationContext, it.message!!, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    override fun onBackPressed() {
        startActivity(Intent(applicationContext, LoginActivity::class.java),
            ActivityOptions.makeSceneTransitionAnimation(this,
                Pair<View, String>(imageView, "ivLogo")
            ).toBundle())
        finish()
        overridePendingTransition(R.anim.bergerak_maju, R.anim.bergerak_mundur)
    }

    @OnClick(R.id.btnLogin) fun onLogin(){
        startActivity(Intent(applicationContext, LoginActivity::class.java),
            ActivityOptions.makeSceneTransitionAnimation(this,
                Pair<View, String>(imageView, "ivLogo")
            ).toBundle())
        finish()
        overridePendingTransition(R.anim.bergerak_maju, R.anim.bergerak_mundur)
    }

    @OnClick(R.id.btnRegister) fun onRegister(){
        if(
            etEmail.text.toString().isNotEmpty() &&
            etName.text.toString().isNotEmpty() &&
            etPassword.text.toString().isNotEmpty()
        ){
            viewModel.postRegister(
                etEmail.text.toString(),
                etPassword.text.toString(),
                etName.text.toString()
            )
        }else{
            Toast.makeText(applicationContext, "Isi data dengan lengkap!", Toast.LENGTH_SHORT).show()
        }
    }
}
