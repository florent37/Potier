package com.github.florent37.xebia;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by florentchampigny on 28/04/15.
 */
public class BookFragment extends Fragment {

    private static final String TITLE = "TITLE";

    private String title;
    private TextView textView;

    public static BookFragment newInstance(String title){
        BookFragment fragment = new BookFragment();
        Bundle args = new Bundle();
        args.putString(TITLE,title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            this.title = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.textView = (TextView) view.findViewById(R.id.textView);

        this.textView.setText(this.title);
    }
}
