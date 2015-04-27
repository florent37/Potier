package com.github.florent37.xebia.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.xebia.R;
import com.github.florent37.xebia.model.Book;
import com.squareup.picasso.Picasso;

/**
 * Created by florentchampigny on 27/04/15.
 */
public class BookCommandViewHolder extends RecyclerView.ViewHolder{

    private ImageView imageView;
    private TextView textView;
    private TextView textViewPrice;

    public BookCommandViewHolder(View itemView) {
        super(itemView);
        this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
        this.textView = (TextView) itemView.findViewById(R.id.textView);
        this.textViewPrice = (TextView) itemView.findViewById(R.id.textViewPrice);
    }

    public void bindBook(Book book){
        this.textView.setText(book.getTitle());
        this.textViewPrice.setText(book.getPrice()+" €");
        Picasso.with(this.itemView.getContext()).load(book.getCover())
                .into(this.imageView);
    }
}
