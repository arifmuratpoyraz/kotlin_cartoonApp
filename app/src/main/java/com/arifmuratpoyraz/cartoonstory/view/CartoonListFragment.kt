package com.arifmuratpoyraz.cartoonstory.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arifmuratpoyraz.cartoonstory.R
import com.arifmuratpoyraz.cartoonstory.adapter.CartoonRecyclerAdapter
import com.arifmuratpoyraz.cartoonstory.viewmodel.CartoonListViewModel

class CartoonListFragment : Fragment() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var errorMessageText : TextView
    private lateinit var loadingProgress : ProgressBar
    private lateinit var viewModel : CartoonListViewModel
    private lateinit var swiperefreshlayout : SwipeRefreshLayout
    private val recyclerCartoonAdapter = CartoonRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this).get(CartoonListViewModel::class.java)
        viewModel.refreshData()

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cartoon_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        errorMessageText = view.findViewById(R.id.errorMessageText)
        loadingProgress = view.findViewById(R.id.loadingProgress)
        swiperefreshlayout = view.findViewById(R.id.swiperefreshlayout)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = recyclerCartoonAdapter

        swiperefreshlayout.setOnRefreshListener {
            errorMessageText.visibility = View.GONE
            loadingProgress.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            viewModel.refreshData()
            swiperefreshlayout.isRefreshing = false
        }
        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.cartoons.observe(viewLifecycleOwner, Observer {
            it?.let{
                recyclerCartoonAdapter.refreshList(it)
                recyclerView.visibility = View.VISIBLE
        }
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    recyclerView.visibility = View.GONE
                    errorMessageText.visibility = View.VISIBLE
                }else{
                    errorMessageText.visibility = View.GONE
                }
            }
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                recyclerView.visibility = View.GONE
                errorMessageText.visibility = View.GONE
                loadingProgress.visibility = View.VISIBLE
                }else {
                loadingProgress.visibility = View.GONE
            }
            }
        })
    }
}