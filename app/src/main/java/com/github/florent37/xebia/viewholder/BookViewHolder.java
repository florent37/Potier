package com.github.florent37.xebia.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.xebia.R;
import com.github.florent37.xebia.adapter.BookAdapter;
import com.github.florent37.xebia.model.Book;
import com.squareup.picasso.Picasso;

/**
 * Created by florentchampigny on 27/04/15.
 */
public class BookViewHolder extends RecyclerView.ViewHolder{

    private ImageView imageView;
    private TextView textView;

    public BookViewHolder(View itemView) {
        super(itemView);
        this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
        this.textView = (TextView) itemView.findViewById(R.id.textView);
    }

    public void bindBook(Book book){
        this.textView.setText(book.getTitle());
        Picasso.with(this.itemView.getContext()).load(book.getCover()).fit().centerCrop()
                .into(this.imageView);
    }
}
