package id.oratakashi.pekalonganstore.ui.storeuser


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.oratakashi.pekalonganstore.R
import id.oratakashi.pekalonganstore.ui.storeuser.create.StoreUserCreateActivity
import kotlinx.android.synthetic.main.fragment_store_user.*

/**
 * A simple [Fragment] subclass.
 */
class StoreUserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRegister.setOnClickListener {
            startActivity(Intent(context, StoreUserCreateActivity::class.java))
        }
    }
}
