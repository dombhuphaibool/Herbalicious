package com.bandonleon.herbalicious.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bandonleon.herbalicious.adapter.HerbsListAdapter;
import com.bandonleon.herbalicious.R;

public class MainActivity extends AppCompatActivity {
    private static final int ADD_HERB_REQUEST = 1;

    private RecyclerView mHerbsList;
    private HerbsListAdapter mHerbsListAdapter;
    private ImageView mEmptyImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Adding herb...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                */
                onActionPerformAddHerb();
            }
        });

        mEmptyImage = (ImageView) findViewById(R.id.empty_image);
        if (mEmptyImage != null) {
            mEmptyImage.setAlpha(0.4f);
        }

        mHerbsListAdapter = new HerbsListAdapter();
        mHerbsList = (RecyclerView) findViewById(R.id.herbs_list);
        if (mHerbsList != null) {
            mHerbsList.setHasFixedSize(true);
            mHerbsList.setLayoutManager(new LinearLayoutManager(this));
            mHerbsList.setAdapter(mHerbsListAdapter);
        }
        updateViewVisibility();
    }

    private void onActionPerformAddHerb() {
        Intent addHerbIntent = new Intent(this, AddHerbActivity.class);
        startActivityForResult(addHerbIntent, ADD_HERB_REQUEST);
    }

    private void updateViewVisibility() {
        if (mHerbsListAdapter.getItemCount() > 0) {
            mEmptyImage.setVisibility(View.GONE);
            mHerbsList.setVisibility(View.VISIBLE);
        } else {
            mEmptyImage.setVisibility(View.VISIBLE);
            mHerbsList.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_HERB_REQUEST && resultCode == RESULT_OK) {
            String herbName = data.getStringExtra(AddHerbActivity.ADD_HERB_EXTRA_NAME);
            mHerbsListAdapter.addItem(herbName);
            updateViewVisibility();
        }
    }

    /**********************************************************************************************
     *                                     Options Menu
     *********************************************************************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
