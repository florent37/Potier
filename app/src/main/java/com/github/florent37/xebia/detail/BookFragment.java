package com.github.florent37.xebia.detail;

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
import com.github.florent37.xebia.cart.Cart;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by florentchampigny on 27/04/15.
 */
public class BookFragment extends Fragment implements View.OnClickListener {

    public static final String EXTRA_BOOK = "EXTRA_BOOK";
    private Book book;

    @InjectView(R.id.textView) TextView textView;
    @InjectView(R.id.textViewPrice) TextView textViewPrice;
    @InjectView(R.id.imageView) ImageView imageView;
    @InjectView(R.id.commandButton) TextView commander;

    private Cart cart;

    /**
     * Create a new instance of BookFragment
     * @param book
     * @return
     */
    public static Fragment newInstance(Book book) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_BOOK, book);
        Fragment fragment = new BookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Retrieve the book from arguments
     * @param savedInstanceState
     */
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
        cart = new Cart(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.inject(this,view);

        textView.setText(book.getTitle());
        textViewPrice.setText(book.getPrice()+" €");

        //when the image is loaded, start the transition
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

    /**
     * Modify the content of Command Button according to bookSession.isCommanded(book)
     */
    private void loadCommandedButton(){
        if(cart.getCartBook().isCommanded(book)){
            commander.setText(R.string.remove);
        }else{
            commander.setText(R.string.commander);
        }
    }

    @Override
    public void onClick(View v) {
        if(cart.getCartBook().isCommanded(book)){
            cart.getCartBook().removeBookFromCommand(book);
        }else{
            cart.getCartBook().addBookToCommand(book);
        }
        loadCommandedButton();
    }
}
