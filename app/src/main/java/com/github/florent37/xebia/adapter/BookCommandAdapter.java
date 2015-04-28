package com.github.florent37.xebia.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.xebia.R;
import com.github.florent37.xebia.model.Book;
import com.github.florent37.xebia.utils.CommercialUtils;
import com.github.florent37.xebia.viewholder.BookCommandViewHolder;
import com.github.florent37.xebia.viewholder.BookViewHolder;
import com.github.florent37.xebia.viewholder.PayHeaderViewHolder;

import java.util.List;

/**
 * Created by florentchampigny on 27/04/15.
 *
 * An adapter to display books, used in PayActivity,
 * so I keep the instance of header to access it from the fragment
 */
public class BookCommandAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface BookClickListener {
        public void onBookClicked(View view, Book book);
    }

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_DEFAULT = 1;

    private static final int HEADER_SIZE = 1;

    List<Book> bookList;
    BookClickListener bookClickListener;

    private View header;
    private PayHeaderViewHolder headerViewHolder;

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_DEFAULT;
        }
    }

    public BookCommandAdapter(List<Book> bookList, BookClickListener bookClickListener) {
        this.bookList = bookList;
        this.bookClickListener = bookClickListener;
    }

    private Book getItem(int position){
        return bookList.get(position-HEADER_SIZE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case TYPE_HEADER:
                if(header == null) {
                    header = LayoutInflater.from(parent.getContext()).inflate(R.layout.pay_fragment_header, parent, false);
                    headerViewHolder = new PayHeaderViewHolder(header);
                    headerViewHolder.setInitialPrice(CommercialUtils.getPrice(this.bookList));
                    return headerViewHolder;
                }else
                    return headerViewHolder;
            case TYPE_DEFAULT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_command_book, parent, false);
                return new BookCommandViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                break;
            case TYPE_DEFAULT:

                BookCommandViewHolder.class.cast(holder).bindBook(getItem(position));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bookClickListener != null) {
                            bookClickListener.onBookClicked(holder.itemView, getItem(holder.getAdapterPosition()));
                        }
                    }
                });
        }
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public PayHeaderViewHolder getHeaderViewHolder() {
        return headerViewHolder;
    }
}
