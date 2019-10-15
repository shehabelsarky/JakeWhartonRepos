package shehab.task.com.jakewhartonrepos.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import shehab.task.com.jakewhartonrepos.data.model.ReposResponse
import javax.inject.Inject

class HomeFragmentViewModel @Inject constructor(private var homeFragmentRepo: HomeFragmentRepo) : ViewModel() {

    fun getRepos(pageIndex: Int,pageSize: Int){
        homeFragmentRepo.getRepos(pageIndex,pageSize)
    }

    fun getReposLiveData() :LiveData<List<ReposResponse>> = homeFragmentRepo.reposMutableLiveData

    override fun onCleared() {
        super.onCleared()
        homeFragmentRepo.clearDisposables()
    }
}