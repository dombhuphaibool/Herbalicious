package com.bandonleon.herbalicious;

import android.app.Application;

import com.bandonleon.herbalicious.model.HerbCollection;
import com.bandonleon.herbalicious.model.persistence.MemoryDataStore;

/**
 * Created by dombhuphaibool on 1/30/16.
 */
public class HerbaliciousApplication extends Application {

    private HerbCollection mHerbCollection = null;

    @Override
    public void onCreate() {
        super.onCreate();

        // @TODO: Move this out to LaunchActivity
        initForNormalAppLaunch();
    }

    public HerbCollection getHerbCollection() {
        return mHerbCollection;
    }

    public void initForNormalAppLaunch() {
        mHerbCollection = HerbCollection.fromDataStore(new MemoryDataStore());
    }
}
