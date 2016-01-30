package com.bandonleon.herbalicious.model;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.StringRes;

import com.bandonleon.herbalicious.R;
import com.bandonleon.herbalicious.model.HerbCollection.HerbDataStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dombhuphaibool on 1/30/16.
 */
public class HerbForm {
    public enum Part {
        Root(R.string.part_of_plant_root),
        WholePlant(R.string.part_of_plant_whole);

        private final @StringRes int mResId;

        Part(int resId) {
            mResId = resId;
        }

        public static List<String> toStringList(Context context) {
            Resources res = context.getResources();
            List<String> list = new ArrayList<>();
            for (Part part : Part.values()) {
                list.add(res.getString(part.mResId));
            }
            list.add(res.getString(R.string.part_of_plant_hint));
            return list;
        }
    }

    public enum Form {
        Fresh(R.string.form_of_plant_fresh),
        Dried(R.string.form_of_plant_dried),
        Decoction(R.string.form_of_plant_decoction),
        Boiled(R.string.form_of_plant_boiled);

        private final @StringRes int mResId;

        Form(int resId) {
            mResId = resId;
        }

        public static List<String> toStringList(Context context) {
            Resources res = context.getResources();
            List<String> list = new ArrayList<>();
            for (Form form : Form.values()) {
                list.add(res.getString(form.mResId));
            }
            list.add(res.getString(R.string.form_of_plant_hint));
            return list;
        }
    }

    public enum Representation {
        Actual(R.string.representation_of_plant_actual),
        Picture(R.string.representation_of_plant_picture);

        private final @StringRes int mResId;

        Representation(int resId) {
            mResId = resId;
        }

        public static List<String> toStringList(Context context) {
            Resources res = context.getResources();
            List<String> list = new ArrayList<>();
            for (Representation rep : Representation.values()) {
                list.add(res.getString(rep.mResId));
            }
            list.add(res.getString(R.string.representation_of_plant_hint));
            return list;
        }
    }

    private int mId;
    private final Part mPart;
    private final Form mForm;
    private final Representation mRepresentation;

    HerbForm(Part part, Form form, Representation representation) {
        mId = PersistentModel.INVALID_ID;
        mPart = part;
        mForm = form;
        mRepresentation = representation;
    }

    public int getId() {
        return mId;
    }

    public String toString(Resources res) {
        String wordSeparator = res.getString(R.string.word_separator);
        StringBuilder sb = new StringBuilder();
        if (mRepresentation != Representation.Actual) {
            sb.append(res.getString(mRepresentation.mResId));
            sb.append(wordSeparator).append(res.getString(R.string.of)).append(wordSeparator);
        }
        if (mForm != Form.Fresh) {
            sb.append(res.getString(mForm.mResId));
            sb.append(wordSeparator);
        }
        sb.append(res.getString(mPart.mResId));
        return sb.toString();
    }

    public void saveForHerb(HerbDataStore dataStore, Herb herb) {
        int herbId = herb.getId();
        if (herbId != Herb.INVALID_ID) {
            mId = dataStore.saveHerbForm(herb, this);
        }
    }
}
