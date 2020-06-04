package id.oratakashi.pekalonganstore.ui.region.villages

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import butterknife.OnClick
import id.oratakashi.pekalonganstore.R
import id.oratakashi.pekalonganstore.data.model.region.villages.DataSearchVillage
import kotlinx.android.synthetic.main.fragment_villages.*


class VillagesFragment : BottomSheetDialogFragment(),
    VillagesInterface.Dialog{

    lateinit var interfaces : VillagesInterface.Activity
    lateinit var viewModel : VillagesViewModel
    lateinit var adapter : VillagesAdapter

    var data : MutableList<DataSearchVillage> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_villages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ButterKnife.bind(this, view)

        viewModel = ViewModelProviders.of(this).get(VillagesViewModel::class.java)
        adapter = VillagesAdapter(data, interfaces, this)

        rvRegion.layoutManager = LinearLayoutManager(context)
        rvRegion.adapter = adapter

        viewModel.setupInstantSearch(etSearch)

        setupViewModel()
    }

    fun setupViewModel(){
        viewModel.showMessage.observe(this, Observer { message ->
            message?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.errorSearch.observe(this, Observer { error ->
            error?.let{
                shRegion.visibility = View.GONE
                llError.visibility = View.VISIBLE
                rvRegion.visibility = View.GONE
                llEmpty.visibility = View.GONE
                llBlank.visibility = View.GONE
            }
        })
        viewModel.progressSearch.observe(this, Observer { progress ->
            progress?.let {
                when(it){
                    true -> {
                        shRegion.visibility = View.VISIBLE
                        shRegion.startShimmerAnimation()
                        llEmpty.visibility = View.GONE
                        llBlank.visibility = View.GONE
                        rvRegion.visibility = View.GONE
                    }
                    false -> {
                        shRegion.visibility = View.GONE
                        shRegion.stopShimmerAnimation()
                        llEmpty.visibility = View.GONE
                        llBlank.visibility = View.GONE
                        rvRegion.visibility = View.VISIBLE
                    }
                }
            }
        })
        viewModel.emptySearch.observe(this, Observer { empty ->
            empty?.let {
                if(it){
                    llBlank.visibility = View.VISIBLE
                    shRegion.visibility = View.GONE
                    llEmpty.visibility = View.GONE
                    rvRegion.visibility = View.GONE
                    llError.visibility = View.GONE
                }
            }
        })
        viewModel.responseSearch.observe(this, Observer { response ->
            response?.let {
                when(it.status){
                    true -> {
                        data.clear()
                        if(it.data!!.size < 1){
                            rvRegion.visibility = View.GONE
                            llEmpty.visibility = View.VISIBLE
                            llError.visibility = View.GONE

                        }else{
                            rvRegion.visibility = View.VISIBLE
                            llEmpty.visibility = View.GONE
                            llError.visibility = View.GONE
                            it.data.forEach {
                                data.add(it)
                            }
                            adapter.notifyDataSetChanged()
                        }
                    }
                    false -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    override fun onDismis() {
        dismiss()
    }

    @OnClick(R.id.btnRetry) fun onRetry(){
        viewModel.getSearch(etSearch.text.toString())
    }

    companion object {
        fun newInstance(activity : VillagesInterface.Activity): VillagesFragment =
            VillagesFragment().apply {
                interfaces = activity
            }

    }
}
