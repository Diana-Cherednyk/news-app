package com.newsapp.presentation

import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.newsapp.R
import com.newsapp.data.local.model.Article
import com.newsapp.databinding.ItemArticleBinding
import io.reactivex.rxjava3.subjects.PublishSubject

class ArticleViewHolder(private val binding: ItemArticleBinding, private val publishSubject: PublishSubject<String>)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(article: Article) {
        binding.articleTitle.text = article.title
        binding.articleSource.text = article.source
        binding.articleImage.load(article.imageUrl) {
            placeholder(R.drawable.ic_photo)
            error(R.drawable.ic_broken_image)
        }
        binding.articleItemLayout.setOnClickListener {
            publishSubject.onNext(article.url)
        }
    }
}
