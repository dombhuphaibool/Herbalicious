package com.bandonleon.herbalicious.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bandonleon.herbalicious.HerbaliciousApplication;
import com.bandonleon.herbalicious.adapter.TabbedContentPagedAdapter;
import com.bandonleon.herbalicious.R;
import com.bandonleon.herbalicious.model.Herb;
import com.bandonleon.herbalicious.model.HerbCollection;
import com.bandonleon.herbalicious.model.HerbForm;

import java.util.List;

/**
 * Created by dombhuphaibool on 1/31/16.
 */
public class SensesActivity extends AppCompatActivity {

    private static final String EXTRA_HERB_ID = "com.bandonleon.herbalicious.SENSES_EXTRA_HERB_ID";

    public static void start(Context context, Herb herb) {
        start(context, herb.getId());
    }

    public static void start(Context context, int herbId) {
        Intent intent = new Intent(context, SensesActivity.class);
        intent.putExtra(EXTRA_HERB_ID, herbId);
        context.startActivity(intent);
    }

    private ViewPager mViewPager;
    private TabLayout mTabContainer;
    private TabbedContentPagedAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_senses);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabContainer = (TabLayout) findViewById(R.id.tab_container);
        if (mTabContainer != null) {
            mTabContainer.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    mViewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }

        mAdapter = new TabbedContentPagedAdapter(getSupportFragmentManager());

        HerbaliciousApplication app = (HerbaliciousApplication) getApplication();
        HerbCollection herbCollection = app.getHerbCollection();
        int herbId = getIntent().getIntExtra(EXTRA_HERB_ID, Herb.INVALID_ID);
        if (herbId != Herb.INVALID_ID) {
            Herb herb = herbCollection.getHerbById(herbId);
            if (herb != null) {
                // @TODO: Update the action bar title with the appropriate string
                setTitle(herb.getName());
                for (HerbForm herbForm : herb.getForms()) {
                    addTab(herbForm.toString(getResources()));
                }
            }
        }

        if (mViewPager != null) {
            mViewPager.setAdapter(mAdapter);
            mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabContainer));
        }
    }

    private void addTab(String tabTitle) {
        mAdapter.addItem();
        mTabContainer.addTab(mTabContainer.newTab().setText(tabTitle));
    }
}
