package com.song.deviceinfo.ui.about

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.song.deviceinfo.R
import com.song.deviceinfo.utils.Constants

class AboutViewModel : ViewModel() {
    private val mText = MutableLiveData<String?>()
    
    val text: LiveData<String?>
        get() = mText
    
    fun setData(context: Context) {
        mText.value = String.format(context.getString(R.string.about_version), Constants.VERSION)
    }
}
