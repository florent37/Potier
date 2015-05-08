package com.github.florent37.xebia.cart;

import com.github.florent37.xebia.model.Book;

import java.util.List;

/**
 * Created by florentchampigny on 27/04/15.
 * Abstract API to manage commanded Books
 */
public interface CartBookFacade {

    List<Book> getCommandedBooks();
    void addBookToCommand(Book book);
    void removeBookFromCommand(Book book);
    boolean isCommanded(Book book);

}
