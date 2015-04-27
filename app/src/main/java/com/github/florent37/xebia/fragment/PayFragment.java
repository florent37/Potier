package com.github.florent37.xebia.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.xebia.DetailActivity;
import com.github.florent37.xebia.R;
import com.github.florent37.xebia.adapter.BookAdapter;
import com.github.florent37.xebia.adapter.BookCommandAdapter;
import com.github.florent37.xebia.model.Book;
import com.github.florent37.xebia.model.CommercialOffer;
import com.github.florent37.xebia.model.Offer;
import com.github.florent37.xebia.session.BookSession;
import com.github.florent37.xebia.session.IBookSession;
import com.github.florent37.xebia.task.GetCommercialOffersTask;
import com.github.florent37.xebia.utils.CommercialUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 27/04/15.
 */
public class PayFragment extends Fragment implements BookCommandAdapter.BookClickListener, GetCommercialOffersTask.GetCommercialOfferTaskCallBack {

    private static final String TAG = PayFragment.class.getSimpleName();
    private IBookSession bookSession;
    private RecyclerView recyclerView;
    private BookCommandAdapter adapter;

    private List<Book> bookList = new ArrayList<>();

    public static Fragment newInstance() {
        Fragment fragment = new PayFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        bookSession = BookSession.getSession(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pay, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().supportStartPostponedEnterTransition();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        final int numberPerLine = 1;
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), numberPerLine);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new BookCommandAdapter(bookList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter != null) {
            bookList.clear();
            bookList.addAll(bookSession.getCommandedBooks());
            adapter.notifyDataSetChanged();

            if(adapter.getHeaderViewHolder() != null)
                adapter.getHeaderViewHolder().setInitialPrice(CommercialUtils.getPrice(this.bookList));
        }

        new GetCommercialOffersTask(this).execute(this.bookList);
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
    public void onCommercialOfferReceived(List<Offer> offers) {
        Log.d(TAG,offers.toString());

        CommercialOffer bestOffer = CommercialUtils.getBestOffer(bookList,offers);

        if(adapter.getHeaderViewHolder() != null)
            adapter.getHeaderViewHolder().setFinalPrice(bestOffer);
    }

    @Override
    public void onNetworkError() {

    }

    //endregion
}
