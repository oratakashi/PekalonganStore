package id.oratakashi.pekalonganstore.ui.storeuser.create

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.material.snackbar.Snackbar
import id.oratakashi.pekalonganstore.R
import id.oratakashi.pekalonganstore.data.db.Sessions
import id.oratakashi.pekalonganstore.root.App
import id.oratakashi.pekalonganstore.ui.region.subdistrict.SubdistrictFragment
import id.oratakashi.pekalonganstore.ui.region.subdistrict.SubdistrictInterface
import kotlinx.android.synthetic.main.activity_store_user_create.*

class StoreUserCreateActivity : AppCompatActivity(), SubdistrictInterface.Activity {

    lateinit var viewModel: StoreUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_user_create)

        ButterKnife.bind(this)

        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        tvName.text = App.sessions!!.getString(Sessions.name)
        shStore.startShimmerAnimation()

        viewModel = ViewModelProviders.of(this).get(StoreUserViewModel::class.java)

        setupViewModel()
    }

    fun setupViewModel(){
        viewModel.showMessage.observe(this, Observer { message ->
            message?.let {
                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.errorStore.observe(this, Observer { error ->
            error?.let {
                if(it) Snackbar.make(llBase, "Gagal membuat toko!", Snackbar.LENGTH_SHORT)
                    .setAction("Coba Lagi"){
                        viewModel.postStoreCreate(
                            etName.text.toString(),
                            etDescription.text.toString(),
                            App.sessions!!.getString(Sessions.store_subdistrict_id)
                        )
                    }
            }
        })
        viewModel.progressStore.observe(this, Observer { progress ->
            progress?.let {
                when(it){
                    true -> {
                        llContent.visibility = View.GONE
                        shStore.visibility = View.VISIBLE
                        shStore.startShimmerAnimation()
                    }
                    false -> {
                        shStore.visibility = View.GONE
                        shStore.stopShimmerAnimation()
                        llContent.visibility = View.VISIBLE
                    }
                }
            }
        })
        viewModel.responseStore.observe(this, Observer { response ->
            response?.let {
                when(it.status){
                    true -> {
                        App.sessions!!.putString(Sessions.store_id, it.data.id!!)
                        App.sessions!!.putString(Sessions.store_name, it.data.name!!)
                        App.sessions!!.putString(Sessions.store_description, it.data.description!!)
                        App.sessions!!.putString(Sessions.store_subdistrict_id, it.data.subdistrict_id!!)
                        finish()
                    }
                    false -> {
                        Toast.makeText(applicationContext, it.message!!, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    @OnClick(R.id.etLocation) fun onSearch(){
        SubdistrictFragment.newInstance(this).show(supportFragmentManager, "dialog")
    }

    @OnClick(R.id.btnRegister) fun onRegister(){
        if(etName.text!!.isNotEmpty() || etDescription.text!!.isNotEmpty() || etLocation.text!!.isNotEmpty()){
            viewModel.postStoreCreate(
                etName.text.toString(),
                etDescription.text.toString(),
                App.sessions!!.getString(Sessions.store_subdistrict_id)
            )
        }else{
            Toast.makeText(applicationContext, "Harap isi data toko dengan lengkap!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSelect(
        subdiscrict_id: String,
        provinsi: String,
        kota: String,
        kecamatan: String
    ) {
        etLocation.setText("$kecamatan, $kota, $provinsi")
        App.sessions!!.putString(Sessions.store_subdistrict_id, subdiscrict_id)
        App.sessions!!.putString(Sessions.store_district, kecamatan)
        App.sessions!!.putString(Sessions.store_regency, kota)
        App.sessions!!.putString(Sessions.store_province, provinsi)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
