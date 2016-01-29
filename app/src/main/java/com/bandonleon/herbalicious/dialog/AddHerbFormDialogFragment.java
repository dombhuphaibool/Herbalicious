package com.bandonleon.herbalicious.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.bandonleon.herbalicious.R;
import com.bandonleon.herbalicious.adapter.SpinnerHintAdapter;

/**
 * Created by dombhuphaibool on 1/23/16.
 */
public class AddHerbFormDialogFragment extends DialogFragment {
    public static final String TAG = "AddHerbFormDialogFragment";

    public interface AddHerbFormListener {
        void onHerbFormAdded(String herbForm);
    }

    private static AddHerbFormListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (AddHerbFormListener) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException("Activity must implement AddHerbFormListener");
        }
    }

    private ArrayAdapter<String> createSpinnerAdapter(@ArrayRes int arrayResId) {
        final String[] spinnerContent = getResources().getStringArray(arrayResId);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, spinnerContent) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                if (position == getCount()) {
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                    textView.setText("");
                    textView.setHint(spinnerContent[getCount()]);
                } else {
                    // textView.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
                }
                return textView;
            }

            @Override
            public int getCount() {
                // Disregard the hint
                return super.getCount() - 1;
            }
        };

        /*
        ArrayAdapter<String> adapter = new SpinnerHintAdapter<>(getContext(),
                R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(arrayResId));

        */
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        return adapter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_herb_form, null);
        final Spinner plantPartSpinner = (Spinner) dialogView.findViewById(R.id.plant_part_spinner);
        final Spinner plantFormSpinner = (Spinner) dialogView.findViewById(R.id.plant_form_spinner);
        final Spinner plantRepresentationSpinner = (Spinner) dialogView.findViewById(R.id.plant_representation_spinner);
        final ArrayAdapter<String> partAdapter = createSpinnerAdapter(R.array.part_of_plant);
        plantPartSpinner.setAdapter(partAdapter);
        plantPartSpinner.setSelection(partAdapter.getCount());

        final ArrayAdapter<String> formAdapter = createSpinnerAdapter(R.array.form_of_plant);
        plantFormSpinner.setAdapter(formAdapter);
        plantFormSpinner.setSelection(formAdapter.getCount());

        final ArrayAdapter<String> representationAdapter = createSpinnerAdapter(R.array.representation_of_plant);
        plantRepresentationSpinner.setAdapter(representationAdapter);
        plantRepresentationSpinner.setSelection(representationAdapter.getCount());

        builder.setView(dialogView)
                // .setTitle(R.string.add_herb_form_title)
                .setPositiveButton(R.string.add_herb_form, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Resources res = getResources();
                        StringBuilder sb = new StringBuilder();
                        String wordSeparator = getString(R.string.word_separator);
                        int herbPartIdx = plantPartSpinner.getSelectedItemPosition();
                        int herbFormIdx = plantFormSpinner.getSelectedItemPosition();
                        int herbRepresentationIdx = plantRepresentationSpinner.getSelectedItemPosition();
                        if (herbRepresentationIdx >= 1) {
                            sb.append(res.getStringArray(R.array.representation_of_plant)[herbRepresentationIdx]);
                            sb.append(wordSeparator).append(getString(R.string.of)).append(wordSeparator);
                        }
                        if (herbFormIdx >= 1) {
                            sb.append(res.getStringArray(R.array.form_of_plant)[herbFormIdx]);
                            sb.append(wordSeparator);
                        }
                        if (herbPartIdx >= 0) {
                            sb.append(res.getStringArray(R.array.part_of_plant)[herbPartIdx]);
                        }
                        mListener.onHerbFormAdded(sb.toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Nothing to do
                    }
                });

        // Note: we must pass in the dialog to the annonymous OnItemSelectedListener since
        // dialog.getButton() will return null until dialog.show() is called. So, we can't assign
        // final Button positiveButton = dialog.getButton() here.
        final AlertDialog dialog = builder.create();
        AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Button addButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                addButton.setEnabled(plantPartSpinner.getSelectedItemPosition() < partAdapter.getCount() &&
                        plantFormSpinner.getSelectedItemPosition() < formAdapter.getCount() &&
                        plantRepresentationSpinner.getSelectedItemPosition() < representationAdapter.getCount());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nothing to do
            }
        };

        plantPartSpinner.setOnItemSelectedListener(spinnerSelectedListener);
        plantFormSpinner.setOnItemSelectedListener(spinnerSelectedListener);
        plantRepresentationSpinner.setOnItemSelectedListener(spinnerSelectedListener);

        return dialog;
    }
}
