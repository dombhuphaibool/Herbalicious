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

import com.bandonleon.herbalicious.R;
import com.bandonleon.herbalicious.adapter.HerbFormsAdapter;
import com.bandonleon.herbalicious.dialog.AddHerbFormDialogFragment;
import com.bandonleon.herbalicious.dialog.AddHerbFormDialogFragment.AddHerbFormListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dombhuphaibool on 1/23/16.
 */
public class AddHerbActivity extends FragmentActivity implements AddHerbFormListener {

    private static final String ADD_HERB_RESULT = "com.bandonleon.herbalicious.ACTIVITY_RESULT";
    public static final String ADD_HERB_EXTRA_NAME = "com.bandonleon.herbalicious.ADD_HERB_EXTRA_NAME";

    private HerbFormsAdapter mHerbFormsAdapter;
    private FloatingActionButton mFabDone;

    private List<String> mHerbForms;
    private List<String> initModel() {
        mHerbForms = new ArrayList<>();
        return mHerbForms;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_herb);

        FloatingActionButton fabAddForm = (FloatingActionButton) findViewById(R.id.fab_add_form);
        fabAddForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPerformActionAddHerbForm();
            }
        });

        final EditText nameTxt = (EditText) findViewById(R.id.herb_name_txt);
        nameTxt.setSingleLine(true);
        nameTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (v == nameTxt && actionId == EditorInfo.IME_ACTION_DONE) {
                    // Clear the input focus
                    nameTxt.clearFocus();
                    // Hide the soft keyboard
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(nameTxt.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        mFabDone = (FloatingActionButton) findViewById(R.id.fab_done);
        mFabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String herbName = "New herb";
                if (nameTxt != null) {
                    String textInput = nameTxt.getText().toString();
                    if (!TextUtils.isEmpty(textInput)) {
                        herbName = textInput;
                    }
                }
                onPerformActionDone(herbName);
            }
        });
        mFabDone.setVisibility(View.GONE);

        mHerbFormsAdapter = new HerbFormsAdapter(initModel());
        RecyclerView herbFormsList = (RecyclerView) findViewById(R.id.herb_forms_list);
        if (herbFormsList != null) {
            herbFormsList.setHasFixedSize(true);
            herbFormsList.setLayoutManager(new LinearLayoutManager(this));
            herbFormsList.setAdapter(mHerbFormsAdapter);
        }
    }

    @Override
    public void onHerbFormAdded(String herbForm) {
        mHerbForms.add(herbForm);
        mHerbFormsAdapter.notifyItemInserted(mHerbForms.size() - 1);

        mFabDone.setVisibility(mHerbForms.size() > 0 ? View.VISIBLE : View.GONE);
    }

    private void onPerformActionAddHerbForm() {
        DialogFragment dialog = new AddHerbFormDialogFragment();
        dialog.show(getSupportFragmentManager(), AddHerbFormDialogFragment.TAG);
    }

    private void onPerformActionDone(String herbName) {
        Intent result = new Intent(ADD_HERB_RESULT);
        result.putExtra(ADD_HERB_EXTRA_NAME, herbName);
        setResult(RESULT_OK, result);
        finish();
    }
}
