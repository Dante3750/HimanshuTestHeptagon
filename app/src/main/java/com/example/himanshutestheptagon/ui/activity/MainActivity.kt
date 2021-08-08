package com.example.himanshutestheptagon.ui.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.himanshutestheptagon.R
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
        swipe()
        retry()


    }

    private fun retry() {
        binding.llRetry.setOnClickListener {
            listItemData.clear()
            adapter.notifyDataSetChanged()
            setItems()
        }
    }

    private fun swipe() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            setItems()
        }
    }

    private fun setItems() {
        listItemData.clear()
        adapter.notifyDataSetChanged()
       viewModel.getList()
    }

    private fun iniObser() {
        viewModel.getResponse().observe(
            this
        ) { value ->
            when (value.status) {
                Status.SUCCESS -> {
                    val list = value?.data?.questions
                    Log.d("data", "iniObser: $list")
                    if (list != null) {
                        listItemData.clear()
                        listItemData.addAll(list)
                    }
                    adapter.replaceItems(listItemData)


                    binding.progressBar.visibility = View.GONE
                    binding.llRetry.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.llRetry.visibility = View.GONE
                }
                Status.ERROR, Status.NO_INTERNET -> {
                    binding.progressBar.visibility = View.GONE
                    binding.llRetry.visibility = View.VISIBLE
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