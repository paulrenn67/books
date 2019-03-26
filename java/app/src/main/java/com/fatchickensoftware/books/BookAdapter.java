package com.fatchickensoftware.books;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fatchickensoftware.books.databinding.BookListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private LayoutInflater layoutInflater;
    List<Volume> content = new ArrayList<>();

    BookAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BookViewHolder(BookListItemBinding.inflate(layoutInflater, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        VolumeInfo volumeInfo = content.get(position).volumeInfo;
        if (volumeInfo != null) {
            Context context = holder.binding.thumbnail.getContext();
            holder.binding.setVolumeInfo(volumeInfo);
            holder.binding.getRoot().setOnClickListener((View v) -> {
                context.startActivity(BookDetailActivity.createInstance(context, content.get(position)));
            });
            holder.binding.setBackgroundColor((position % 2 != 0) ? ContextCompat.getColor(context, R.color.colorRow1) : ContextCompat.getColor(context, R.color.colorRow2));
            holder.binding.setTextColor(ContextCompat.getColor(context, R.color.black));
        }
    }

    @Override
    public long getItemId(int position) {
        return content.get(position).id.hashCode();
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        BookListItemBinding binding;
        BookViewHolder(BookListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
