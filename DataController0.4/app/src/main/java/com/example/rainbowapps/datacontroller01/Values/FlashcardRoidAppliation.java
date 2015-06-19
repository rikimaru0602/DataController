package com.example.rainbowapps.datacontroller01.Values;

import android.app.Application;

import com.dropbox.client2.session.Session;

public class FlashcardRoidAppliation extends Application{
    // app キー
    public static final String DROPBOX_APP_KEY = "adttr9ats1zypbz";
    // app シークレットキー
    public static final String DROPBOX_APP_SCECRET = "t4d5tu2j1qy5pnw";
    public static final String DROPBOX_APP_FOLDER_NAME = "FlashcardRoid";
    public static final Session.AccessType DROPBOX_ACCESS_TYPE = Session.AccessType.DROPBOX;
}
