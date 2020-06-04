package id.oratakashi.pekalonganstore.ui.addresses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.oratakashi.pekalonganstore.R
import id.oratakashi.pekalonganstore.data.model.addresses.DataAddresses
import kotlinx.android.synthetic.main.adapter_address.view.*

class AddressAdapter(val data : List<DataAddresses>, val interfaces: AddressInterfaces) :
    RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_address,
            parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvName.text = data[position].name
        holder.itemView.tvPerson.text = data[position].receiver_name
        holder.itemView.tvPhone.text = data[position].phone
        holder.itemView.tvAddress.text = "${data[position].street!!.toUpperCase()} " +
                "${data[position].village!!.village_name}, " +
                "${data[position].village!!.district_name}, " +
                "${data[position].village!!.regency_name}" +
                "${data[position].village!!.province_name}"
        holder.itemView.tvPrimary.setOnClickListener {
            interfaces.onPrimary(data[position].id!!)
        }
        holder.itemView.tvEdit.setOnClickListener {
            interfaces.onUpdate(data[position].id!!)
        }
        holder.itemView.tvDelete.setOnClickListener {
            interfaces.onDelete(data[position].id!!)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}