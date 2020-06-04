package id.oratakashi.pekalonganstore.ui.main

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import id.oratakashi.pekalonganstore.R
import id.oratakashi.pekalonganstore.ui.home.HomeFragment
import id.oratakashi.pekalonganstore.ui.kategori.CategoryFragment
import id.oratakashi.pekalonganstore.ui.profile.ProfileFragment
import id.oratakashi.pekalonganstore.ui.transaksi.TransactionFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object{
        var activity : MainActivity ?= null

        fun getIntence() : MainActivity{
            return activity!!
        }
    }

    var tab_position : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        supportActionBar!!.title = ""
//        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        supportActionBar!!.hide()

        openFragment(HomeFragment(), "beranda")

        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }

        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }

        container.setPadding(0, getStatusBarHeight(), 0, 0)

        activity = this

        bnMenu.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home -> {
                    if(tab_position != 0) {
                        tab_position = 0
                        openFragment(HomeFragment(), "beranda")
                    }
                    true
                }
                R.id.navigation_transaksi -> {
                    if(tab_position != 1) {
                        tab_position = 1
                        openFragment(TransactionFragment(), "transaksi")
                    }
                    true
                }
                R.id.navigation_kategori -> {
                    if(tab_position != 2) {
                        tab_position = 2
                        openFragment(CategoryFragment(), "kategori")
                    }
                    true
                }
                R.id.navigation_profil -> {
                    if(tab_position != 3) {
                        tab_position = 3
                        openFragment(ProfileFragment(), "profil")
                    }
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    override fun onBackPressed() {
        when(tab_position){
            0 -> {
                finish()
            }
            else -> {
                tab_position = 0
                openFragment(HomeFragment(), "beranda")
            }
        }
    }

    fun openFragment(fragment : Fragment, name : String){
        supportFragmentManager.beginTransaction()
            .replace(R.id.flHome, fragment, name)
            .commit()

        when(name) {
            "beranda" -> {
                bnMenu.menu[0].isChecked = true
                tab_position = 0
            }
            "transaksi" -> {
                bnMenu.menu[1].isChecked = true
                tab_position = 1
            }
            "kategori" -> {
                bnMenu.menu[2].isChecked = true
                tab_position = 2
            }
            "profile" -> {
                bnMenu.menu[3].isChecked = true
                tab_position = 3
            }
        }
    }
}
