package com.samgithiaka.apps.foodforthehungry.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.samgithiaka.apps.foodforthehungry.R
import com.samgithiaka.apps.foodforthehungry.data.room.room_model.SaveChildRoomModel
import com.samgithiaka.apps.foodforthehungry.databinding.SponsoredChildrenRecyclerBinding

class SponsoredChildrenListAdapter(private val clickListener: (SaveChildRoomModel) -> Unit) :
    RecyclerView.Adapter<SponsoredChildrenListAdapter.SponsoredChildrenViewHolder>() {
    private val childrenList = ArrayList<SaveChildRoomModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SponsoredChildrenViewHolder {
        val binding: SponsoredChildrenRecyclerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.sponsored_children_recycler, parent, false
        )
        return SponsoredChildrenViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SponsoredChildrenViewHolder, position: Int) {
        holder.bind(childrenList[position], clickListener)
    }

    fun setList(children: List<SaveChildRoomModel>) {
        childrenList.clear()
        childrenList.addAll(children)
    }

    override fun getItemCount(): Int {
        return childrenList.size
    }

    class SponsoredChildrenViewHolder(
        sponsoredChildrenRecyclerBinding: SponsoredChildrenRecyclerBinding
    ) : RecyclerView.ViewHolder(sponsoredChildrenRecyclerBinding.root) {
        val binding: SponsoredChildrenRecyclerBinding = sponsoredChildrenRecyclerBinding
        fun bind(child: SaveChildRoomModel, clickListener: (SaveChildRoomModel) -> Unit) {
            binding.model = child
            binding.listItemLayout.setOnClickListener {
                clickListener(child)
            }
            binding.btnViewProfile.setOnClickListener {
                clickListener(child)
            }
        }
    }
}