package id.oratakashi.pekalonganstore.ui.dialog.upload_profile

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.ButterKnife
import butterknife.OnClick
import id.oratakashi.pekalonganstore.R


class UploadProfileFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_uploadprofile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ButterKnife.bind(this, view)
    }

    @OnClick(R.id.llGalery) fun onGalery(){
        interfaces!!.onSelect("galery")
        dismiss()
    }

    @OnClick(R.id.llCamera) fun onCamera(){
        interfaces!!.onSelect("camera")
        dismiss()
    }

    @OnClick(R.id.llDelete) fun onDelete(){
        interfaces!!.onSelect("delete")
        dismiss()
    }

    companion object {
        var interfaces : UploadProfileInterface?= null
        fun newInstance(activity : UploadProfileInterface): UploadProfileFragment =
            UploadProfileFragment().apply {
                interfaces = activity
            }

    }
}
