package shehab.task.com.jakewhartonrepos.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home.*
import shehab.task.com.jakewhartonrepos.R
import shehab.task.com.jakewhartonrepos.data.model.ReposResponse
import shehab.task.com.jakewhartonrepos.utils.EndlessRecyclerViewScrollListener
import shehab.task.com.jakewhartonrepos.utils.NetworkingUtils
import javax.inject.Inject

const val PAGE_SIZE = 15

class HomeFragment : DaggerFragment(), ReposListAdapter.OnItemClickListener {

    private val TAG: String = HomeFragment::class.java.simpleName
    private var pageIndex = 1
    val reposResponse = ReposResponse()
    private var isTypeAdded = false
    private var reposList = ArrayList<ReposResponse>()
    private var reposListAdapter: ReposListAdapter? = null
    private var scrollListener: EndlessRecyclerViewScrollListener? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var layoutManager: LinearLayoutManager

    private val viewModel: HomeFragmentViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(HomeFragmentViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reposResponse.type = 2
        if (isAdded)
            initReposList()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getAllRepos(pageIndex, PAGE_SIZE)
        observeReposLiveData()
    }


    private fun getAllRepos(pageIndex: Int, pageSize: Int) {
        viewModel.getRepos(pageIndex, pageSize)
    }

    private fun observeReposLiveData() {
        viewModel.getReposLiveData().observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            Log.d(TAG, "Data loaded")
            if (reposList.size > 0) {
                reposList.removeAt(reposList.size - 1)
                reposListAdapter!!.notifyItemRemoved(reposList.size)
                isTypeAdded = false
            }
            reposList.addAll(it)
            reposListAdapter!!.notifyDataSetChanged()
        })
    }


    private fun initReposList() {
        rvReposList.layoutManager = layoutManager
        rvReposList.setHasFixedSize(true)
        reposListAdapter = ReposListAdapter(activity!!, reposList, this)
        rvReposList.adapter = reposListAdapter

        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                if (NetworkingUtils(activity!!).isNetworkConnected) {
                    Log.d(TAG, "Load More")
                    loadMoreData()
                }
            }
        }
        rvReposList.addOnScrollListener(scrollListener!!)

    }

    override fun onItemClick(repoItem: ReposResponse) {

    }

    private fun loadMoreData() {
        if (!isTypeAdded) {
            reposList.add(reposResponse)
            reposListAdapter!!.notifyItemInserted(reposList.size - 1)
        }
        isTypeAdded = true
        pageIndex++
        getAllRepos(pageIndex, PAGE_SIZE)
    }
}
