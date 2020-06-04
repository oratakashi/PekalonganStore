package id.oratakashi.pekalonganstore.ui.region.villages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.oratakashi.pekalonganstore.R
import id.oratakashi.pekalonganstore.data.model.region.villages.DataSearchVillage
import kotlinx.android.synthetic.main.adapter_village.view.*

class VillagesAdapter(data : List<DataSearchVillage>,
                      interfaces : VillagesInterface.Activity, dialog : VillagesInterface.Dialog) : RecyclerView.Adapter<VillagesAdapter.ViewHolder>() {

    val data = data
    val interfaces = interfaces
    val dialog = dialog

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_village,
            parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvVillage.text = data[position].village_name
        holder.itemView.tvDistrict.text = data[position].district_name
        holder.itemView.tvProvince.text = data[position].province_name
        holder.itemView.tvRegency.text = data[position].regency_name
        holder.itemView.llAdapter.setOnClickListener {
            interfaces.onSelect(data[position])
            dialog.onDismis()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}