package com.peopleHere.people_here.AddPicture

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peopleHere.people_here.Data.AddPicturLocationData
import com.peopleHere.people_here.databinding.ItemAddpictureLocationNameBinding

class AddPictureLocationAdapter(var addlocationlist: ArrayList<AddPicturLocationData>) :
    RecyclerView.Adapter<AddPictureLocationAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return addlocationlist.size
    }

    override fun onCreateViewHolder(//이거 다시
        parent: ViewGroup,
        viewType: Int
    ): AddPictureLocationAdapter.ViewHolder {

        val view =
            ItemAddpictureLocationNameBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddPictureLocationAdapter.ViewHolder, position: Int) {
        holder.bind(addlocationlist[position])
    }

    inner class ViewHolder(private val binding: ItemAddpictureLocationNameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(addlocation: AddPicturLocationData) {
        }


    }


}
