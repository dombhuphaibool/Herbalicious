package com.bandonleon.herbalicious.model;

import java.util.List;

/**
 * Created by dombhuphaibool on 1/30/16.
 */
public class HerbCollection extends PersistentModel {
    public interface HerbDataStore {
        List<Herb> getAllHerbs();
        int saveHerb(Herb herb);
        Herb getHerbById(int id);
        List<HerbForm> getHerbForms(Herb herb);
        // void saveHerbForms(Herb herb);
        int saveHerbForm(Herb herb, HerbForm herbForm);
    }

    private enum State {
        HerbsNotLoaded,
        HerbsLoaded
    }

    private final HerbDataStore mDataStore;
    private List<Herb> mHerbs;
    private State mState;

    public static HerbCollection fromDataStore(HerbDataStore dataStore) {
        HerbCollection herbCollection = dataStore != null ? new HerbCollection(dataStore) : null;
        return herbCollection;
    }

    private HerbCollection(HerbDataStore dataStore) {
        mDataStore = dataStore;
        mState = State.HerbsNotLoaded;
        mHerbs = null;
    }

    private List<Herb> loadHerbs() {
        if (mState == State.HerbsNotLoaded) {
            mHerbs = mDataStore.getAllHerbs();
            mState = State.HerbsLoaded;
        }
        return mHerbs;
    }

    private List<Herb> getAll() {
        return loadHerbs();
    }

    public Herb getHerbById(int id) {
        return mDataStore.getHerbById(id);
    }

    public Herb addHerb(String name) {
        Herb herb = new Herb(mDataStore, name);
        List<Herb> herbs = getAll();
        herbs.add(herb);
        return herb;
    }

    public int size() {
        return getAll().size();
    }

    public Herb get(int index) {
        return getAll().get(index);
    }

    @Override
    public void save() {
        if (mHerbs != null) {
            for (Herb herb : mHerbs) {
                herb.save();
            }
        }
    }
}
