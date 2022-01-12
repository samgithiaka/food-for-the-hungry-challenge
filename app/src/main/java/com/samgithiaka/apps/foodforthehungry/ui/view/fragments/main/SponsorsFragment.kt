package com.samgithiaka.apps.foodforthehungry.ui.view.fragments.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.samgithiaka.apps.foodforthehungry.R
import com.samgithiaka.apps.foodforthehungry.data.repository.SponsorsRepository
import com.samgithiaka.apps.foodforthehungry.data.room.dbs.SponsorsDatabase
import com.samgithiaka.apps.foodforthehungry.data.room.room_model.SaveSponsorRoomModel
import com.samgithiaka.apps.foodforthehungry.databinding.FragmentSponsorsBinding
import com.samgithiaka.apps.foodforthehungry.ui.base.BaseFragment
import com.samgithiaka.apps.foodforthehungry.ui.base.SponsorsModelFactory
import com.samgithiaka.apps.foodforthehungry.ui.view.adapters.SponsorsListAdapter
import com.samgithiaka.apps.foodforthehungry.ui.view.sponsor.AddSponsorActivity
import com.samgithiaka.apps.foodforthehungry.ui.view.sponsor.SponsorsProfileActivity
import com.samgithiaka.apps.foodforthehungry.ui.viewmodel.SponsorDetailsViewModel

class SponsorsFragment : BaseFragment() {

    private lateinit var viewModel: SponsorDetailsViewModel
    private lateinit var adapter: SponsorsListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sponsors, container, false)
        setUpOnclickListener()
        setUpInits()
        initRecyclerView()
        return binding.root
    }

    private fun setUpInits() {
        try{
            val dao = SponsorsDatabase.getInstance(activityContext.application)?.sponsorStorageDao()
            val repository = dao?.let { SponsorsRepository(it) }
            val factory = repository?.let { SponsorsModelFactory(it) }
            viewModel = factory?.let {
                activityContext?.let { it1 ->
                    ViewModelProvider(
                        it1,
                        it
                    )
                }?.get(SponsorDetailsViewModel::class.java)
            }!!
            binding.lifecycleOwner = activityContext

        }catch (e : Exception){
            Log.e(TAG, "setUpInits: ", e)
        }
    }

    private fun setUpOnclickListener() {
        binding.btnAddSponsor.setOnClickListener { proceedToAddSponsor() }
        binding.searchName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(s.length >2) {
                    searchSponsor(s.toString())
                }
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun searchSponsor(searchParam: String) {
        viewModel.getSearchedSponsor(searchParam).observe(activityContext, {
            Log.e(TAG, "searchSponsor: $it" )
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }
    private fun proceedToAddSponsor() {
        try{
            activityContext.startActivity(Intent(activityContext, AddSponsorActivity::class.java))
        }catch (e: Exception){
            Log.e(TAG, "proceedToAddChild: ",e )
        }
    }

    private fun initRecyclerView() {
        try {
            binding.includedRecyclerview.recycler.layoutManager =
                LinearLayoutManager(BaseFragment.activityContext)
            adapter = SponsorsListAdapter { selectedItem: SaveSponsorRoomModel ->
                listItemClicked(
                    selectedItem
                )
            }
            binding.includedRecyclerview.recycler.adapter = adapter
            displayChildrenList()
        }catch (e : Exception) {
            Log.e(TAG, "initRecyclerView: ",e )
        }
    }

    private fun displayChildrenList() {
        viewModel.getSavedSponsors().observe(activityContext, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(sponsor: SaveSponsorRoomModel) {
        val i = Intent(activityContext, SponsorsProfileActivity::class.java)
        i.putExtra("sponsorObject", sponsor)
        startActivity(i)
    }

    companion object {
        private const val TAG = "SponsorsFragment"
        lateinit var binding: FragmentSponsorsBinding
    }
}