package id.oratakashi.pekalonganstore.ui.region

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.oratakashi.pekalonganstore.R
import id.oratakashi.pekalonganstore.data.model.region.DataSearchSubdistrict
import kotlinx.android.synthetic.main.adapter_subdistrict.view.*

class SubdistrictAdapter(data : List<DataSearchSubdistrict>, interfaces : SubdistrictInterface.Activity,
                         dialog : SubdistrictInterface.Dialog) : RecyclerView.Adapter<SubdistrictAdapter.ViewHolder>() {

    val data = data
    val interfaces = interfaces
    val dialog = dialog

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_subdistrict,
            parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvDistrict.text = data[position].subdistrict_name
        holder.itemView.tvRegency.text = data[position].type+" "+data[position].city
        holder.itemView.tvProvince.text = data[position].province
        holder.itemView.llAdapter.setOnClickListener {
            interfaces.onSelect(
                data[position].subdistrict_id!!,
                data[position].province!!,
                data[position].type+" "+data[position].city!!,
                data[position].subdistrict_name!!
            )
            dialog.onDismis()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}