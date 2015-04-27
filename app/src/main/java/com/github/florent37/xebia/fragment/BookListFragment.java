package com.github.florent37.xebia.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.xebia.DetailActivity;
import com.github.florent37.xebia.R;
import com.github.florent37.xebia.adapter.BookAdapter;
import com.github.florent37.xebia.model.Book;
import com.github.florent37.xebia.parallax.ParallaxHeaderActivity;
import com.github.florent37.xebia.task.GetBooksTask;

import java.util.ArrayList;
import java.util.List;

import static com.github.florent37.xebia.utils.Utils.isLolipop;

/**
 * Created by florentchampigny on 27/04/15.
 */
public class BookListFragment extends Fragment implements GetBooksTask.GetBooksTaskCallBack, BookAdapter.BookClickListener {

    private RecyclerView recyclerView;
    private BookAdapter adapter;

    private List<Book> bookList = new ArrayList<>();

    private ParallaxHeaderActivity parallaxHeaderActivity;

    public static Fragment newInstance() {
        return new BookListFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ParallaxHeaderActivity)
            this.parallaxHeaderActivity = (ParallaxHeaderActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_books, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        final int numberPerLine = isLandscape? 3 : 2;
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), numberPerLine);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (position) {
                    case 0:
                        return numberPerLine;
                    default:
                        return 1;
                }
            }
        });
        recyclerView.setLayoutManager(layoutManager);

        adapter = new BookAdapter(bookList, this);
        recyclerView.setAdapter(adapter);

        if (this.parallaxHeaderActivity != null) {
            parallaxScroll();
        }

        new GetBooksTask(this).execute();
    }

    private void parallaxScroll() {
        this.recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            private int yOffset = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                yOffset += dy;

                if (parallaxHeaderActivity != null) {
                    parallaxHeaderActivity.onParallaxScroll(yOffset);
                }
            }
        });
    }

    @Override
    public void onBookClicked(View view, Book book) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getActivity().getWindow().setExitTransition(null);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity(),
                Pair.create(view.findViewById(R.id.imageView), getString(R.string.transitionNameImage))
        );

        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(BookFragment.EXTRA_BOOK, book);

        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
    }

    //region webservice

    @Override
    public void onBooksReceived(List<Book> bookList) {
        if (bookList != null) {
            this.bookList.addAll(bookList);
            this.adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onNetworkError() {

    }

    //endregion
}