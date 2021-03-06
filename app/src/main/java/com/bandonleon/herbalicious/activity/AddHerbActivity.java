package com.bandonleon.herbalicious.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bandonleon.herbalicious.HerbaliciousApplication;
import com.bandonleon.herbalicious.R;
import com.bandonleon.herbalicious.adapter.HerbFormsAdapter;
import com.bandonleon.herbalicious.dialog.AddHerbFormDialogFragment;
import com.bandonleon.herbalicious.dialog.AddHerbFormDialogFragment.AddHerbFormListener;
import com.bandonleon.herbalicious.model.Herb;
import com.bandonleon.herbalicious.model.HerbCollection;
import com.bandonleon.herbalicious.model.HerbForm;

/**
 * Created by dombhuphaibool on 1/23/16.
 */
public class AddHerbActivity extends FragmentActivity implements AddHerbFormListener {

    private static final String ADD_HERB_EXTRA_ID = "com.bandonleon.herbalicious.ADD_HERB_EXTRA_ID";

    private static final String ADD_HERB_RESULT = "com.bandonleon.herbalicious.ACTIVITY_RESULT";
    public static final String ADD_HERB_EXTRA_HERB_ID = "com.bandonleon.herbalicious.ADD_HERB_EXTRA_HERB_ID";

    private static final String DEFAULT_INPUT_HERB_NAME = "New herb";

    private HerbFormsAdapter mHerbFormsAdapter;
    private FloatingActionButton mFabDone;
    private EditText mHerbNameTxt;

    private HerbCollection mHerbCollection;
    private Herb mHerb;

    private void clearFocusAndHideInput(EditText editText) {
        // Clear the input focus
        editText.clearFocus();
        // Hide the soft keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HerbaliciousApplication app = (HerbaliciousApplication) getApplication();
        mHerbCollection = app.getHerbCollection();
        mHerb = null;
        if (savedInstanceState != null) {
            int herbId = savedInstanceState.getInt(ADD_HERB_EXTRA_ID, -1);
            if (herbId != -1) {
                mHerb = mHerbCollection.getHerbById(herbId);
            }
        }

        setContentView(R.layout.activity_add_herb);

        mHerbNameTxt = (EditText) findViewById(R.id.herb_name_txt);
        mHerbNameTxt.setSingleLine(true);
        mHerbNameTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (v == mHerbNameTxt && actionId == EditorInfo.IME_ACTION_DONE) {
                    clearFocusAndHideInput(mHerbNameTxt);
                    return true;
                }
                return false;
            }
        });

        FloatingActionButton fabAddForm = (FloatingActionButton) findViewById(R.id.fab_add_form);
        fabAddForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFocusAndHideInput(mHerbNameTxt);
                onPerformActionAddHerbForm();
            }
        });

        mHerbFormsAdapter = new HerbFormsAdapter(getResources(), mHerb);
        RecyclerView herbFormsList = (RecyclerView) findViewById(R.id.herb_forms_list);
        if (herbFormsList != null) {
            herbFormsList.setHasFixedSize(true);
            herbFormsList.setLayoutManager(new LinearLayoutManager(this));
            herbFormsList.setAdapter(mHerbFormsAdapter);
        }

        mFabDone = (FloatingActionButton) findViewById(R.id.fab_done);
        mFabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHerb.setName(getInputHerbName());
                mHerb.save();
                onPerformActionDone(mHerb.getId());
            }
        });
        mFabDone.setVisibility(mHerbFormsAdapter.getItemCount() > 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mHerb != null) {
            outState.putInt(ADD_HERB_EXTRA_ID, mHerb.getId());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        if (mHerb != null) {
            mHerb.save();
        }
        super.onPause();
    }

    @Override
    public void onHerbFormAdded(HerbForm.Part part, HerbForm.Form form, HerbForm.Representation rep) {

        if (mHerb == null) {
            mHerb = mHerbCollection.addHerb(getInputHerbName());
            mHerbFormsAdapter.load(mHerb);
        }
        mHerb.addForm(part, form, rep);
        mHerbFormsAdapter.notifyItemInserted(mHerb.getForms().size() - 1);

        mFabDone.setVisibility(mHerbFormsAdapter.getItemCount() > 0 ? View.VISIBLE : View.GONE);
    }

    private String getInputHerbName() {
        String herbName = DEFAULT_INPUT_HERB_NAME;
        if (mHerbNameTxt != null) {
            String textInput = mHerbNameTxt.getText().toString();
            if (!TextUtils.isEmpty(textInput)) {
                herbName = textInput;
            }
        }
        return herbName;
    }

    private void onPerformActionAddHerbForm() {
        DialogFragment dialog = new AddHerbFormDialogFragment();
        dialog.show(getSupportFragmentManager(), AddHerbFormDialogFragment.TAG);
    }

    private void onPerformActionDone(int herbId) {
        Intent result = new Intent(ADD_HERB_RESULT);
        result.putExtra(ADD_HERB_EXTRA_HERB_ID, herbId);
        setResult(RESULT_OK, result);
        finish();
    }

}
