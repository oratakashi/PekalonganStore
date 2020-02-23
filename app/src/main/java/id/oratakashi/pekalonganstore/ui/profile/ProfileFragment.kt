package id.oratakashi.pekalonganstore.ui.profile


import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import butterknife.OnClick
import id.oratakashi.pekalonganstore.R
import id.oratakashi.pekalonganstore.data.db.Sessions
import id.oratakashi.pekalonganstore.root.App
import id.oratakashi.pekalonganstore.ui.login.LoginActivity
import id.oratakashi.pekalonganstore.ui.profile.edit_profile.EditProfileActivity
import id.oratakashi.pekalonganstore.ui.storeuser.StoreUserActivity
import id.oratakashi.pekalonganstore.utils.ImageHelper
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * Fragment untuk UI Profile
 */
class ProfileFragment : Fragment() {

    lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ButterKnife.bind(this, view)

        tvName.text = App.sessions!!.getString(Sessions.name)
        tvEmail.text = App.sessions!!.getString(Sessions.email)

        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)

        setupViewModel()
    }

    private fun setupViewModel(){

        /**
         * Show Message Observer
         */

        viewModel.showMessage.observe(this, Observer { message ->
            message?.let{
                Toast.makeText(context!!, it, Toast.LENGTH_SHORT).show()
            }
        })

        /**
         * Profile Observer
         */

        viewModel.progressProfile.observe(this, Observer { progress ->
            progress?.let{
                when(it){
                    true -> {
                        llProfile.visibility = View.GONE
                        shProfile.visibility = View.VISIBLE
                        shProfile.startShimmerAnimation()
                    }
                    false -> {
                        shProfile.stopShimmerAnimation()
                        shProfile.visibility = View.GONE
                        llProfile.visibility = View.VISIBLE
                    }
                }
            }
        })
        viewModel.errorProfile.observe(this, Observer { error ->
            error?.let{
                if(it) Toast.makeText(context, "Gagal mendapatkan profile terbaru!",
                    Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.responseProfile.observe(this, Observer { response ->
            response?.let{
                when(it.status){
                    true -> {
                        tvName.text = it.data!!.name
                        tvEmail.text = it.data.email

                        if(it.data.photo != null){
                            ImageHelper.getPicassoWithoutCache(ivPhoto, it.data.photo)
                        }
                    }
                    false -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.getProfile()
        if(App.sessions!!.getString(Sessions.store_id).isNotEmpty()){
            llOpenStore.visibility = View.GONE
            llStorePanel.visibility = View.VISIBLE
        }else{
            llOpenStore.visibility = View.VISIBLE
            llStorePanel.visibility = View.GONE
        }
    }

    @OnClick(R.id.llMenu_Profile) fun onProfile(){
        startActivity(Intent(context, EditProfileActivity::class.java))
    }

    @OnClick(R.id.llLogout) fun onLogout(){
        AlertDialog.Builder(context!!)
            .setTitle("Konfirmasi")
            .setMessage("Apakah kamu yakin ingin keluar?")
            .setPositiveButton("Ya"
            ) { dialog: DialogInterface?, which: Int ->
                App.sessions!!.Logout()
                startActivity(Intent(context, LoginActivity::class.java))
                activity!!.finish()
            }
            .setNegativeButton("Tidak"){ dialog: DialogInterface?, which: Int ->
                  dialog!!.dismiss()
            }
            .show()
    }

    @OnClick(R.id.llOpenStore) fun onOpenStore(){
        startActivity(Intent(context, StoreUserActivity::class.java))
    }
}
