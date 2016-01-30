package com.bandonleon.herbalicious.model;

import com.bandonleon.herbalicious.model.HerbCollection.HerbDataStore;
import com.bandonleon.herbalicious.model.HerbForm.Form;
import com.bandonleon.herbalicious.model.HerbForm.Part;
import com.bandonleon.herbalicious.model.HerbForm.Representation;

import java.util.List;

/**
 * Created by dombhuphaibool on 1/30/16.
 */
public class Herb extends PersistentModel {

    private enum State {
        FormsNotLoaded,
        FormsLoaded
    }

    private final HerbDataStore mDataStore;
    private State mState;
    private int mId;
    private String mName;
    private List<HerbForm> mForms;

    Herb(HerbDataStore dataStore, String name) {
        mDataStore = dataStore;
        mId = INVALID_ID;
        mName = name;
        mState = State.FormsNotLoaded;
        mForms = null;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    private void loadForms() {
        mForms = mDataStore.getHerbForms(this);
        mState = State.FormsLoaded;
    }

    public List<HerbForm> getForms() {
        if (mState == State.FormsNotLoaded) {
            loadForms();
        }
        return mForms;
    }

    public HerbForm addForm(Part part, Form form, Representation representation) {
        List<HerbForm> forms = getForms();
        HerbForm herbForm = new HerbForm(part, form, representation);
        forms.add(herbForm);
        return herbForm;
    }

    @Override
    public void save() {
        mId = mDataStore.saveHerb(this);
        if (mForms != null) {
            for (HerbForm form : mForms) {
                form.saveForHerb(mDataStore, this);
            }
        }
    }
}
