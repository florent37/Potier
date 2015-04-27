package com.github.florent37.xebia.session;

import android.content.Context;

/**
 * Created by florentchampigny on 27/04/15.
 */
public class BookSession {

    public static IBookSession getSession(Context context) {
        return new BookSessionSharedPreferences(context);
    }

}
