package com.bandonleon.herbalicious.model;

/**
 * Created by dombhuphaibool on 1/30/16.
 */
public abstract class PersistentModel {
    public static final int INVALID_ID = -1;

    public abstract void save();
}
