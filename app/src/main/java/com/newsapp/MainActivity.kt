package com.newsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.newsapp.data.remote.model.State
import com.newsapp.databinding.ActivityMainBinding
import com.newsapp.domain.showToast
import com.newsapp.presentation.MainViewModel
import com.newsapp.presentation.NewsAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    private val newsAdapter = NewsAdapter()

    private lateinit var activityViewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        activityViewBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(activityViewBinding.root)

        activityViewBinding.newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = newsAdapter
        }

        val disposable = newsAdapter.getClickObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                openUrl(it)
            }

        initArticles()
    }

    private fun openUrl(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun initArticles() {
        viewModel.newsLiveData.observe(this, Observer { state ->
            when (state) {
                is State.Loading -> showLoading(true)
                is State.Success -> {
                    newsAdapter.setArticles(state.data)
                    showLoading(false)
                }
                is State.Error -> {
                    showToast(state.message)
                    showLoading(false)
                }
            }
        })

        activityViewBinding.swipeRefreshLayout.setOnRefreshListener {
            getNews()
        }

        if (viewModel.newsLiveData.value !is State.Success) {
            getNews()
        }
    }

    private fun getNews() {
        viewModel.getNews()
    }

    private fun showLoading(isLoading: Boolean) {
        activityViewBinding.swipeRefreshLayout.isRefreshing = isLoading
    }
}
