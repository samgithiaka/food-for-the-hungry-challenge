package com.samgithiaka.apps.foodforthehungry.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.samgithiaka.apps.foodforthehungry.R
import com.samgithiaka.apps.foodforthehungry.data.room.room_model.SaveSponsorRoomModel
import com.samgithiaka.apps.foodforthehungry.databinding.SponsorsListRecyclerBinding

class SponsorsListAdapter(private val clickListener: (SaveSponsorRoomModel) -> Unit) :
    RecyclerView.Adapter<SponsorsListAdapter.SponsorsViewHolder>() {
    private val sponsorsList = ArrayList<SaveSponsorRoomModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SponsorsViewHolder {
        val binding: SponsorsListRecyclerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.sponsors_list_recycler, parent, false
        )
        return SponsorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SponsorsViewHolder, position: Int) {
        holder.bind(sponsorsList[position], clickListener)
        holder.binding.txtCount.text = (position+1).toString()
    }

    fun setList(sponsors: List<SaveSponsorRoomModel>) {
        sponsorsList.clear()
        sponsorsList.addAll(sponsors)
    }

    override fun getItemCount(): Int {
        return sponsorsList.size
    }

    class SponsorsViewHolder(
        sponsorsListRecyclerBinding: SponsorsListRecyclerBinding
    ) : RecyclerView.ViewHolder(sponsorsListRecyclerBinding.root) {
        val binding: SponsorsListRecyclerBinding = sponsorsListRecyclerBinding
        fun bind(sponsor: SaveSponsorRoomModel, clickListener: (SaveSponsorRoomModel) -> Unit) {
            binding.model = sponsor
            binding.listItemLayout.setOnClickListener {
                clickListener(sponsor)
            }
        }
    }
}