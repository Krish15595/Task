package com.d2k.task.ui.carousel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.d2k.task.R

class PageViewModel : ViewModel() {

    private val _index = MutableLiveData<CarouselModel>()
    val text: LiveData<CarouselModel> = Transformations.map(_index) {
        it
    }
    private val array = arrayOf(
            CarouselModel("Intro Screen 1", "Image 1.", R.drawable.ic_image_1, "#e8734d"),
            CarouselModel("Intro Screen 2", "Image 2.", R.drawable.ic_image_2, "#209dce")
    )

    fun setIndex(index: Int) {
        _index.value = this.array[index]
    }

}