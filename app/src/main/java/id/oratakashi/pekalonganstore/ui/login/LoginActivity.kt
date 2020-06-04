package id.oratakashi.pekalonganstore.ui.login

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
import id.oratakashi.pekalonganstore.R
import id.oratakashi.pekalonganstore.data.db.Sessions
import id.oratakashi.pekalonganstore.root.App
import id.oratakashi.pekalonganstore.ui.main.MainActivity
import id.oratakashi.pekalonganstore.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window){
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

            exitTransition = Explode().setDuration(5000)
            enterTransition = Explode().setDuration(550)
        }
        setContentView(R.layout.activity_login)

        ViewCompat.setTransitionName(imageView, "ivLogo")

        ButterKnife.bind(this)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        setupViewModel()
    }

    fun setupViewModel(){
        viewModel.showMessage.observe(this, Observer { message ->
            message.let {
                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.errorLogin.observe(this, Observer { error ->
            error?.let {
                Snackbar.make(clBase, "Gagal melakukan login!", Snackbar.LENGTH_SHORT)
                    .setAction("Coba Lagi"){
                        viewModel.postLogin(
                            etEmail.text.toString(),
                            etPassword.text.toString()
                        )
                    }.show()
            }
        })
        viewModel.progressLogin.observe(this, Observer { progress ->
            progress?.let {
                when(it){
                    true -> {
                        btnLogin.visibility = View.GONE
                        shLogin.visibility = View.VISIBLE
                        shLogin.startShimmerAnimation()
                    }
                    false -> {
                        btnLogin.visibility = View.VISIBLE
                        shLogin.visibility = View.GONE
                        shLogin.stopShimmerAnimation()
                    }
                }
            }
        })
        viewModel.responseLogin.observe(this, Observer { response ->
            response?.let{
                when(it.status){
                    true -> {
                        App.sessions!!.putString(Sessions.id_user, it.data!!.id!!)
                        App.sessions!!.putString(Sessions.name, it.data.name!!)
                        App.sessions!!.putString(Sessions.email, it.data.email!!)

                        if(it.data.address != null) App.sessions!!.putString(Sessions.address,
                            it.data.address
                        )

                        if(it.data.phone != null) App.sessions!!.putString(Sessions.phone,
                            it.data.phone
                        )

                        if(it.data.photo != null) App.sessions!!.putString(Sessions.photo,
                            it.data.photo
                        )

                        if(it.data.village_id != null) App.sessions!!.putString(Sessions.village_id,
                            it.data.village_id
                        )

                        if(it.data.store_id != null) App.sessions!!.putString(Sessions.store_id,
                            it.data.store_id
                        )

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

    @OnClick(R.id.btnRegister) fun onRegister(){
        startActivity(Intent(applicationContext, RegisterActivity::class.java),
            ActivityOptions.makeSceneTransitionAnimation(this,
                Pair<View, String>(imageView, "ivLogo")).toBundle())
        finish()
        overridePendingTransition(R.anim.bergerak_maju, R.anim.bergerak_mundur)
    }

    @OnClick(R.id.btnLogin) fun onLogin(){
        if(
            etEmail.text.toString().isNotEmpty() &&
            etPassword.text.toString().isNotEmpty()
        ){
            viewModel.postLogin(
                etEmail.text.toString(),
                etPassword.text.toString()
            )
        }else{
            Toast.makeText(applicationContext, "Mohon isi email dan password!", Toast.LENGTH_SHORT).show()
        }
    }
}
