package com.bandonleon.herbalicious.model.persistence;

import com.bandonleon.herbalicious.model.Herb;
import com.bandonleon.herbalicious.model.HerbCollection.HerbDataStore;
import com.bandonleon.herbalicious.model.HerbForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dombhuphaibool on 1/30/16.
 */
public class MemoryDataStore implements HerbDataStore {
    private List<Herb> mHerbs;
    // private Map<Herb, List<HerbForm>> mHerbForms;
    private Map<Herb, Set<HerbForm>> mHerbForms;

    public MemoryDataStore() {
        mHerbs = new ArrayList<>();
        mHerbForms = new HashMap<>();
    }

    @Override
    public List<Herb> getAllHerbs() {
        return new ArrayList<>(mHerbs);
    }

    @Override
    public int saveHerb(Herb herb) {
        int herbId = herb.getId();
        if (herbId == Herb.INVALID_ID) {
            herbId = mHerbs.size();
            mHerbs.add(herb);
        } else {
            mHerbs.set(herbId, herb);
        }
        return herbId;
    }

    @Override
    public Herb getHerbById(int id) {
        return mHerbs.get(id);
    }

    @Override
    public List<HerbForm> getHerbForms(Herb herb) {
        Set<HerbForm> herbForms = mHerbForms.get(herb);
        List<HerbForm> formsList = new ArrayList<>();
        if (herbForms != null) {
            formsList.addAll(herbForms);
        }
        return formsList;
    }

    @Override
    public int saveHerbForm(Herb herb, HerbForm herbForm) {
        int herbFormId;
        Set<HerbForm> herbForms = mHerbForms.get(herb);
        if (herbForms == null) {
            herbForms = new LinkedHashSet<>();
            herbForms.add(herbForm);
            mHerbForms.put(herb, herbForms);
            herbFormId = 0;
        } else {
            if (!herbForms.contains(herbForm)) {
                herbFormId = herbForms.size();
                herbForms.add(herbForm);
            } else {
                herbFormId = herbForm.getId();
            }
        }
        return herbFormId;
    }

    /*
    @Override
    public void saveHerbForms(Herb herb) {
        mHerbForms.put(herb, herb.getForms());
    }
    */
}
