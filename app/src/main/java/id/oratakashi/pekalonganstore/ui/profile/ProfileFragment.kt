package id.oratakashi.pekalonganstore.ui.profile


import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.OnClick
import id.oratakashi.pekalonganstore.R
import id.oratakashi.pekalonganstore.data.db.Sessions
import id.oratakashi.pekalonganstore.root.App
import id.oratakashi.pekalonganstore.ui.login.LoginActivity
import id.oratakashi.pekalonganstore.ui.storeuser.StoreUserActivity
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ButterKnife.bind(this, view)

        tvName.text = App.sessions!!.getString(Sessions.name)
        tvEmail.text = App.sessions!!.getString(Sessions.email)
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
