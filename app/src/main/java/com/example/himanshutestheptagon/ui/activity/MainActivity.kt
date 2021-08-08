package com.example.himanshutestheptagon.ui.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.himanshutestheptagon.R
import com.example.himanshutestheptagon.data.model.ItemList
import com.example.himanshutestheptagon.data.model.Question
import com.example.himanshutestheptagon.databinding.ActivityMainBinding
import com.example.himanshutestheptagon.ui.adapter.TestRecyclerAdapter
import com.example.himanshutestheptagon.ui.viewmodel.MainViewModel
import com.example.himanshutestheptagon.util.showNormalToast
import dagger.hilt.android.AndroidEntryPoint
import life.alhilal.utils.Status

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var listItemData: ArrayList<Question> = ArrayList()
    private val viewModel by viewModels<MainViewModel>()
    
    private val adapter: TestRecyclerAdapter by lazy {
        TestRecyclerAdapter { mOrders: Question -> onSelect(mOrders) }
    }

    private fun onSelect(mOrders: Question) {
        Log.d("TAG", "onSelect: $mOrders")
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        adapter.context = this
        binding.viewModel = viewModel
        viewModel.getList()
        iniObser()
        setView()


    }

    private fun iniObser() {
        viewModel.getResponse().observe(
            this
        ) { value ->
            when (value.status) {
                Status.SUCCESS -> {
                    val list = value?.data?.questions
                    if (list != null) {
                        listItemData.addAll(list)
                    }
                    adapter.replaceItems(listItemData)
                    Log.d("data", "iniObser: ${value.data}")

                    binding.progressBar.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR, Status.NO_INTERNET -> {
                    binding.progressBar.visibility = View.GONE
                    value.message?.let { it1 ->
                        showNormalToast(this,it1)
                    }
                }
            }
        }
    }


    private fun setView() {
        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter = adapter
    }
}