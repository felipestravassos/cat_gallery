package com.fstravassos.catgallery

import android.app.Application
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainViewModelTest {

    lateinit var mViewModel: MainViewModel

    @Before
    fun setUp() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        mViewModel = MainViewModel(app)
    }

    @Test
    fun getCatsLiveData_notNull() {
        assertNotNull(mViewModel.getCatsLiveData())
    }

    @Test
    fun getCatsLiveData_checkUpdate() {
        val links = MediatorLiveData<List<String>>()
        setPrivateField("mCatsLiveData", links)

        val liveData = mViewModel.getCatsLiveData()
        assertNull(liveData.value)

        liveData.observeForever(Observer {
            assertEquals(2, it.size)
        })

        val list = ArrayList<String>()
        list.add("A")
        list.add("B")

        links.postValue(list)
    }

    private fun setPrivateField(strField: String, newValue: Any) {
        val field = mViewModel::class.java.getDeclaredField(strField)
        field.isAccessible = true
        field.set(mViewModel, newValue)
    }
}
