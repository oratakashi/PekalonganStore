package id.oratakashi.pekalonganstore.ui.storeuser

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.oratakashi.pekalonganstore.R
import id.oratakashi.pekalonganstore.data.db.Sessions
import id.oratakashi.pekalonganstore.root.App

class StoreUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_user)

        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        supportFragmentManager.beginTransaction()
            .replace(R.id.flOpenStore, StoreUserFragment()).commit()
    }

    override fun onResume() {
        super.onResume()
        if(App.sessions!!.getString(Sessions.store_id).isNotEmpty()){
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
