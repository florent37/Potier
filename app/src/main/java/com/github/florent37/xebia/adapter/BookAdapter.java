package com.github.florent37.xebia.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.xebia.R;
import com.github.florent37.xebia.model.Book;
import com.github.florent37.xebia.viewholder.BookViewHolder;

import java.util.List;

/**
 * Created by florentchampigny on 27/04/15.
 */
public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_PLACEHOLDER = 0;
    private static final int TYPE_DEFAULT = 1;

    private static final int PLACEHOLDER_SIZE = 1;

    List<Book> bookList;

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_PLACEHOLDER;
            default:
                return TYPE_DEFAULT;
        }
    }

    private Book getItem(int position){
        return bookList.get(position-PLACEHOLDER_SIZE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case TYPE_PLACEHOLDER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_placeholder,parent,false);
                return new RecyclerView.ViewHolder(view) {
                };
            case TYPE_DEFAULT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_book,parent,false);
                return new BookViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case TYPE_PLACEHOLDER:break;
            case TYPE_DEFAULT:
                BookViewHolder.class.cast(holder).bindBook(getItem(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return bookList.size()+PLACEHOLDER_SIZE;
    }
}
