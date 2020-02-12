package com.fstravassos.catgallery

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mCatsLiveData = MediatorLiveData<List<String>>()

    init {
        val mainModel = MainModel()

        mainModel.picturesRequest(application)
        mCatsLiveData.addSource(mainModel.mCats) { mCatsLiveData.postValue(it) }
    }

    fun getCatsLiveData() : LiveData<List<String>> {
        return mCatsLiveData
    }
}