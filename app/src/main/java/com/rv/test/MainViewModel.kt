package com.rv.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val max = 500
    val data = MutableLiveData<List<TestItem>>()
    val itmEvent = MutableLiveData<Unit>()
    val flow = MutableStateFlow<Int?>(null)

    var currentId = 0
    var job: Job? = null

    init {
        val d = (1..max).filter { it % 2 == 0 }.map {
            TestItem(it, it.toString(), false)
        }
        data.postValue(d)

        viewModelScope.launch {
            flow.collect { id ->
                if (id != null) {
                    val newData = data.value?.toMutableList()?.also {
                        val itm = it.find { it.isItm }
                        if (itm != null) {
                            it.remove(itm)
                        }
                        it.add(TestItem(id, id.toString(), true))
                        it.sortBy { it.id }
                    }
                    newData?.let {
                        data.postValue(it)
                    }
                    itmEvent.postValue(Unit)
                }
            }
        }
    }

    fun addItem() {
        viewModelScope.launch {
            currentId++
            flow.emit(currentId)
        }
    }

    fun startMonkeyTest() {
        job?.cancel()
        job = viewModelScope.launch {
            delay(100)
            (1..max).forEach {
                flow.emit(it)
                delay(50)
            }
        }
    }

    fun stopMonkeyTest() {
        job?.cancel()
        currentId = 0
    }
}