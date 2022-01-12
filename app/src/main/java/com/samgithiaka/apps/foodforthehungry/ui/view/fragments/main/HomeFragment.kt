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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.samgithiaka.apps.foodforthehungry.R
import com.samgithiaka.apps.foodforthehungry.data.repository.ChildRepository
import com.samgithiaka.apps.foodforthehungry.data.room.dbs.ChildDatabase
import com.samgithiaka.apps.foodforthehungry.data.room.room_model.SaveChildRoomModel
import com.samgithiaka.apps.foodforthehungry.databinding.FragmentHomeBinding
import com.samgithiaka.apps.foodforthehungry.ui.base.BaseActivity
import com.samgithiaka.apps.foodforthehungry.ui.base.BaseFragment
import com.samgithiaka.apps.foodforthehungry.ui.base.ChildModelFactory
import com.samgithiaka.apps.foodforthehungry.ui.view.adapters.SponsoredChildrenListAdapter
import com.samgithiaka.apps.foodforthehungry.ui.view.child.AddChildActivity
import com.samgithiaka.apps.foodforthehungry.ui.view.child.ChildProfileActivity
import com.samgithiaka.apps.foodforthehungry.ui.viewmodel.ChildDetailsViewModel

class HomeFragment : BaseFragment() {

    private lateinit var viewModel: ChildDetailsViewModel
    private lateinit var adapter: SponsoredChildrenListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false)
        setUpOnclickListener()
        setUpInits()
        initRecyclerView()
        return binding.root
    }

    private fun setUpInits() {
        try{
            val dao = ChildDatabase.getInstance(activityContext.application)?.childStorageDao()
            val repository = dao?.let { ChildRepository(it) }
            val factory = repository?.let { ChildModelFactory(it) }
            viewModel = factory?.let {
                BaseActivity.activityContext?.let { it1 ->
                    ViewModelProvider(
                        it1,
                        it
                    )
                }?.get(ChildDetailsViewModel::class.java)
            }!!
            binding.lifecycleOwner = BaseActivity.activityContext
            binding.twGreetings.text = "Welcome, ${Firebase.auth.currentUser?.displayName}"

        }catch (e : Exception){
            Log.e(TAG, "setUpInits: ", e)
        }
    }

    private fun setUpOnclickListener() {
        binding.btnAddChild.setOnClickListener { proceedToAddChild() }
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
                    searchChild(s.toString())
                }
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun searchChild(searchParam: String) {
        viewModel.getSearchedChildren(searchParam).observe(activityContext, {
            Log.e(TAG, "searchChild: $it" )
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun proceedToAddChild() {
        try{
            activityContext.startActivity(Intent(activityContext, AddChildActivity::class.java))
        }catch (e: Exception){
            Log.e(TAG, "proceedToAddChild: ",e )
        }
    }

    private fun initRecyclerView() {
        try {
            binding.includedRecyclerview.recycler.layoutManager =
                LinearLayoutManager(activityContext)
            adapter = SponsoredChildrenListAdapter { selectedItem: SaveChildRoomModel ->
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
        viewModel.getSavedChildren().observe(activityContext, {
           adapter.setList(it)
           adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(child: SaveChildRoomModel) {
        try {
            val i = Intent(activityContext, ChildProfileActivity::class.java)
            i.putExtra("childObject", child)
            startActivity(i)
        }catch (e : Exception) {
            Log.e(TAG, "listItemClicked: ",e )
        }
    }

    companion object {
        private const val TAG = "HomeFragment"
        lateinit var binding: FragmentHomeBinding
    }
}