package com.github.florent37.xebia.session;

import com.github.florent37.xebia.model.Book;

import java.util.List;

/**
 * Created by florentchampigny on 27/04/15.
 */
public interface IBookSession {

    public List<Book> getCommandedBooks();
    public void addBookToCommand(Book book);
    public void removeBookFromCommand(Book book);
    public boolean isCommanded(Book book);

}
