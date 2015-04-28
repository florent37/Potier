package com.github.florent37.xebia.session;

import android.content.Context;

/**
 * Created by florentchampigny on 27/04/15.
 * BookSession is a Wrapper of Book API, it defines where the datas will be saved
 * In my app, into SharedPreferences (didn't want to create a database for 8 books)
 */
public class BookSession {

    public static IBookSession getSession(Context context) {
        return new BookSessionSharedPreferences(context);
    }

}
