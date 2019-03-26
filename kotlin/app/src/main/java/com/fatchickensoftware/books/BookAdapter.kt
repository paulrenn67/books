package com.fatchickensoftware.books

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.fatchickensoftware.books.databinding.BookListItemBinding

class BookAdapter(private val layoutInflater: LayoutInflater) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    var content: List<Volume> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder =
        BookViewHolder(BookListItemBinding.inflate(layoutInflater, parent, false))

    override fun getItemId(position: Int): Long =
        content[position].id.hashCode().toLong()

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        content[position].volumeInfo?.let { volumeInfo ->
            val context = holder.binding.thumbnail.context

            holder.binding.volumeInfo = volumeInfo
            holder.binding.root.setOnClickListener {
                context.startActivity(BookDetailActivity.createInstance(context, content[position]))
            }
            holder.binding.backgroundColor =
                    if (position % 2 != 0) ContextCompat.getColor(context, R.color.colorRow1)
                    else ContextCompat.getColor(context, R.color.colorRow2)
            holder.binding.textColor = context?.let { ContextCompat.getColor(it, R.color.black) }
        }
    }

    override fun getItemCount() = content.size

    class BookViewHolder(internal val binding: BookListItemBinding) : RecyclerView.ViewHolder(binding.root)
}
