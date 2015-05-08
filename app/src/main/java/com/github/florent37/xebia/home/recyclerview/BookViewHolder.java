package com.github.florent37.xebia.home.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.xebia.R;
import com.github.florent37.xebia.model.Book;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by florentchampigny on 27/04/15.
 */
public class BookViewHolder extends RecyclerView.ViewHolder{

    @InjectView(R.id.imageView) ImageView imageView;
    @InjectView(R.id.textView) TextView textView;

    public BookViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this,itemView);
    }

    public void bindBook(Book book){
        this.textView.setText(book.getTitle());
        Picasso.with(this.itemView.getContext()).load(book.getCover()).fit().centerCrop()
                .into(this.imageView);
    }
}
