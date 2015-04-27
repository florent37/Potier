package com.github.florent37.xebia.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.xebia.R;
import com.github.florent37.xebia.model.Book;
import com.github.florent37.xebia.session.BookSession;
import com.github.florent37.xebia.session.IBookSession;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by florentchampigny on 27/04/15.
 */
public class BookFragment extends Fragment implements View.OnClickListener {

    public static final String EXTRA_BOOK = "EXTRA_BOOK";
    private Book book;

    private TextView textView;
    private TextView textViewPrice;
    private ImageView imageView;
    private Button commander;

    private IBookSession bookSession;

    public static Fragment newInstance(Book book) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_BOOK, book);
        Fragment fragment = new BookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null && args.containsKey(EXTRA_BOOK))
            book = (Book) args.getSerializable(EXTRA_BOOK);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        bookSession = BookSession.getSession(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = (TextView) view.findViewById(R.id.textView);
        textViewPrice = (TextView) view.findViewById(R.id.textViewPrice);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        commander = (Button) view.findViewById(R.id.commandButton);

        textView.setText(book.getTitle());
        textViewPrice.setText(book.getPrice()+" €");
        Picasso.with(getActivity()).load(book.getCover()).fit().centerCrop().into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                getActivity().supportStartPostponedEnterTransition();
            }

            @Override
            public void onError() {
                getActivity().supportStartPostponedEnterTransition();
            }
        });

        commander.setOnClickListener(this);

        loadCommandedButton();
    }

    private void loadCommandedButton(){
        if(bookSession.isCommanded(book)){
            commander.setText(R.string.remove);
        }else{
            commander.setText(R.string.commander);
        }
    }

    @Override
    public void onClick(View v) {
        if(bookSession.isCommanded(book)){
            bookSession.removeBookFromCommand(book);
        }else{
            bookSession.addBookToCommand(book);
        }
        loadCommandedButton();
    }
}
