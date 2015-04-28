package com.github.florent37.xebia.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.florent37.xebia.model.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 27/04/15.
 * Implementation of API to manage commanded Books
 * Saving into SharedPReferences with GSON
 */
public class BookSessionSharedPreferences implements IBookSession {

    private static final String PREFS = "PREFS-BookSessionSharedPreferences";
    private static final String PREFS_LIST_BOOKS = "PREFS_LIST_BOOKS";

    private Context context;
    private SharedPreferences prefs;
    private Gson gson;
    private Type classTypeListBook;

    private List<Book> books;

    public BookSessionSharedPreferences(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences(PREFS, 0);
        this.gson = new Gson();
        this.classTypeListBook = new TypeToken<List<Book>>() {
        }.getType();
    }

    private void load() {
        String json = prefs.getString(PREFS_LIST_BOOKS, null);
        if (json != null) {
            this.books = gson.fromJson(json, classTypeListBook);
        } else {
            this.books = new ArrayList<>();
        }
    }

    private void save() {
        String json = gson.toJson(this.books);

        prefs.edit()
                .putString(PREFS_LIST_BOOKS, json)
                .apply();
    }

    @Override
    public List<Book> getCommandedBooks() {
        load();
        return this.books;
    }

    @Override
    public void addBookToCommand(Book book) {
        load();
        if (!this.books.contains(book)) {
            this.books.add(book);
        }
        save();
    }

    @Override
    public void removeBookFromCommand(Book book) {
        load();
        books.remove(book);
        save();
    }

    @Override
    public boolean isCommanded(Book book) {
        load();
        return books.contains(book);
    }
}
