package com.song.deviceinfo.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by chensongsong on 2020/5/26.
 */
open class BaseViewModel<T : Any> : ViewModel() {
    private val _items = MutableLiveData<List<T>>()
    val items: LiveData<List<T>> = _items

    fun setItems(items: List<T>) {
        _items.value = items
    }
}
