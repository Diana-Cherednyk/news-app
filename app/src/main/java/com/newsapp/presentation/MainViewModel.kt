package com.newsapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.newsapp.data.remote.NewsRepository
import com.newsapp.domain.ArticleListState



class MainViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    private val _newsLiveData = MutableLiveData<ArticleListState>()

    val newsLiveData: LiveData<ArticleListState>
        get() = _newsLiveData

    fun getNews() {
        viewModelScope.launch {
            newsRepository.getNews().collect {
                _newsLiveData.value = it
            }
        }
    }
}
