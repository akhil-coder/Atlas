package com.example.atlas.presentation.main.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.atlas.business.domain.models.ResultsEntity
import com.example.atlas.databinding.RecyclerListItemBinding

class MovieListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResultsEntity>() {

        override fun areItemsTheSame(oldItem: ResultsEntity, newItem: ResultsEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResultsEntity, newItem: ResultsEntity): Boolean {
            return oldItem == newItem
        }
    }

    class MovieViewHolder
    constructor(
        private val binding: RecyclerListItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ResultsEntity) {
            binding.tvTitle.text = item.title
            binding.tvReleaseDate.text = item.releaseDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder(
            RecyclerListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = differ.currentList.size

    fun submitList(movieList: List<ResultsEntity>?) {
        val newList = movieList?.toMutableList()
        differ.submitList(newList)
    }

    private val differ =
        AsyncListDiffer(
            RecyclerChangeCallback(this),
            AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
        )

    internal inner class RecyclerChangeCallback(
        private val adapter: MovieListAdapter
    ) : ListUpdateCallback {

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            adapter.notifyItemRangeChanged(position, count, payload)
        }

        override fun onInserted(position: Int, count: Int) {
            adapter.notifyItemRangeChanged(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapter.notifyDataSetChanged()
        }

        override fun onRemoved(position: Int, count: Int) {
            adapter.notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }
}