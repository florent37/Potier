package com.github.florent37.xebia.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.xebia.R;
import com.github.florent37.xebia.adapter.BookAdapter;
import com.github.florent37.xebia.model.Book;
import com.github.florent37.xebia.parallax.ParallaxHeaderActivity;
import com.github.florent37.xebia.task.GetBooksTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 27/04/15.
 */
public class BookFragment extends Fragment{

    public static final String EXTRA_BOOK = "EXTRA_BOOK";
    private Book book;

    private TextView textView;
        private ImageView imageView;

    public static Fragment newInstance(Book book) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_BOOK,book);
        Fragment fragment = new BookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null && args.containsKey(EXTRA_BOOK))
            book = (Book) args.getSerializable(EXTRA_BOOK);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = (TextView) view.findViewById(R.id.textView);
        imageView = (ImageView) view.findViewById(R.id.imageView);

        textView.setText(book.getTitle());
        Picasso.with(getActivity()).load(book.getCover()).fit().centerCrop().into(imageView,new Callback() {
            @Override
            public void onSuccess() {
                getActivity().supportStartPostponedEnterTransition();
            }

            @Override
            public void onError() {
                getActivity().supportStartPostponedEnterTransition();
            }
        });

    }

}
