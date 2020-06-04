package id.oratakashi.pekalonganstore.ui.addresses

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.material.snackbar.Snackbar
import id.oratakashi.pekalonganstore.R
import id.oratakashi.pekalonganstore.data.model.addresses.DataAddresses
import kotlinx.android.synthetic.main.activity_address.*

class AddressActivity : AppCompatActivity(), AddressInterfaces {

    lateinit var adapter: AddressAdapter
    lateinit var viewModel: AddressViewModel

    val data : MutableList<DataAddresses> = ArrayList()

    var id : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        ButterKnife.bind(this)

        supportActionBar!!.title = "Daftar Alamat"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white)
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }

        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }

        adapter = AddressAdapter(data, this)
        viewModel = ViewModelProviders.of(this).get(AddressViewModel::class.java)

        rvAddress.adapter = adapter
        rvAddress.layoutManager = LinearLayoutManager(this)

        srAddress.setOnRefreshListener {
            srAddress.isRefreshing = false
            viewModel.getAddress()
        }

        setupViewModel()
    }

    fun setupViewModel(){
        viewModel.showMessage.observe(this, Observer { message ->
            message?.let{
                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.errorAddress.observe(this, Observer { error ->
            error?.let {
                if(it) Snackbar.make(srAddress, "Gagal memuat data!", Snackbar.LENGTH_SHORT)
                    .setAction("Coba Lagi"){
                        viewModel.getAddress()
                    }.show()
            }
        })
        viewModel.progressAddress.observe(this, Observer { progress ->
            progress?.let {
                when(it){
                    true -> {
                        shAddress.visibility = View.VISIBLE
                        llPrimary.visibility = View.GONE
                        rvAddress.visibility = View.GONE
                        llEmpty.visibility = View.GONE
                        shAddress.startShimmerAnimation()
                    }
                    false -> {
                        shAddress.stopShimmerAnimation()
                        shAddress.visibility = View.GONE
                        llPrimary.visibility = View.VISIBLE
                        rvAddress.visibility = View.VISIBLE
                        llEmpty.visibility = View.GONE
                    }
                }
            }
        })
        viewModel.responseAddress.observe(this, Observer { response ->
            response?.let {
                when(it.status!!){
                    true -> {
                        if(it.data == null){
                            llEmpty.visibility = View.VISIBLE
                            rvAddress.visibility = View.GONE
                            llPrimary.visibility = View.GONE
                        }else{
                            data.clear()
                            it.data.forEach {
                                data.add(it)
                            }
                            /**
                             * Mengambil Alamat utama, lalu menghapus dari list alamat
                             */
                            val dataPrimary : DataAddresses = data[0]
                            data.removeAt(0)

                            /**
                             * Seting Alamat Utama
                             */
                            tvName.text = dataPrimary.name
                            tvPerson.text = dataPrimary.receiver_name
                            tvAddress.text = "${dataPrimary.street!!.toUpperCase()} " +
                                    "${dataPrimary.village!!.village_name}, " +
                                    "${dataPrimary.village.district_name}, " +
                                    "${dataPrimary.village.regency_name}, " +
                                    "${dataPrimary.village.province_name}"
                            tvPhone.text = dataPrimary.phone
                            id = dataPrimary.id!!

                            adapter.notifyDataSetChanged()
                        }
                    }
                    false -> {
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        viewModel.progressSetPrimary.observe(this, Observer { progress ->
            progress?.let{
                when(it){
                    true -> {
                        Toast.makeText(applicationContext, "Mengubah alamat utama...",
                            Toast.LENGTH_SHORT).show()
                    }
                    false -> {

                    }
                }
            }
        })
        viewModel.errorSetPrimary.observe(this, Observer { error ->
            error?.let{
                if(it) Toast.makeText(applicationContext, "Gagal mengubah alamat utama!",
                    Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.responseSetPrimary.observe(this, Observer { response ->
            response?.let{
                when(it.status){
                    true -> {
                        viewModel.getAddress()
                    }
                    false -> {
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        viewModel.progressDelete.observe(this, Observer { progress ->
            progress?.let {
                when(it){
                    true -> {
                        Toast.makeText(applicationContext, "Menghapus alamat...", Toast.LENGTH_SHORT).show()
                    }
                    false -> {

                    }
                }
            }
        })
        viewModel.errorDelete.observe(this, Observer { error ->
            error?.let{
                if(it) Toast.makeText(applicationContext, "Gagal menghapus alamat!", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.responseDelete.observe(this, Observer { response ->
            response?.let{
                when(it.status){
                    true -> {
                        viewModel.getAddress()
                    }
                    false -> {
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAddress()
    }

    override fun onUpdate(id: String) {

    }

    override fun onDelete(id: String) {
        viewModel.deleteAddress(id)
    }

    override fun onPrimary(id: String) {
        viewModel.putPrimary(id)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    @OnClick(R.id.tvDelete) fun onDelete(){
        viewModel.deleteAddress(id)
    }
}