package id.oratakashi.pekalonganstore.ui.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import id.oratakashi.pekalonganstore.R
import id.oratakashi.pekalonganstore.ui.home.HomeFragment
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

        openFragment(HomeFragment(), "beranda")

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
            "profile" -> {
                bnMenu.menu[2].isChecked = true
                tab_position = 2
            }
        }
    }
}
