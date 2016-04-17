package com.expandablenavdrawer;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int LOADER_ID = 1;
    private List<NavigationGroup> mGroupCollection;
    private TabLayout tabLayout;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    public ActionBarDrawerToggle mDrawerToggle = null;
    private ExpandableListView mExpandableListView;
    private TabbedAdapter tabbedAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.extended_toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setElevation(8);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);


        mExpandableListView = (ExpandableListView) findViewById(R.id.navigation_listview);
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (previousGroup != -1 && groupPosition != previousGroup)
                    mExpandableListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }

        });
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                mDrawerLayout.closeDrawers();
                NavigationGroup group = mGroupCollection.get(groupPosition);
                //Click on Navigation Item

                return false;
            }
        });
        mGroupCollection = new ArrayList<NavigationGroup>();

        tabbedAdapter = new TabbedAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(mViewPager);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);
        setTabIcons();


        //Drawer
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                invalidateOptionsMenu();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        displayNavigationCategories();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    private void setupDrawerContent(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(final MenuItem menuItem) {
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void setTabIcons() {
        if (tabLayout != null) {
            tabLayout.getTabAt(0).setIcon(R.drawable.selector_tab_list);
            tabLayout.getTabAt(1).setIcon(R.drawable.selector_tab_grid);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {

        ListFragment stylePackFragment = new ListFragment();
        tabbedAdapter.addFragment(stylePackFragment, "List");
        GridFragment gridFragment = new GridFragment();
        tabbedAdapter.addFragment(gridFragment, "Grid");
        viewPager.setAdapter(tabbedAdapter);
        viewPager.setOffscreenPageLimit(1);


    }


    public static class TabbedAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public TabbedAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
//            return mFragmentTitles.get(position);
        }
    }

    private void setNavigationData() {
        NavigationExpandableAdapter adapter = new NavigationExpandableAdapter(this,
                mExpandableListView, mGroupCollection);
        mExpandableListView.setAdapter(adapter);
    }

    private void displayNavigationCategories() {

        getSupportLoaderManager().restartLoader(LOADER_ID, null, loaderCallbacks).forceLoad();
    }

    private LoaderManager.LoaderCallbacks<ArrayList<Categories>> loaderCallbacks = new LoaderManager.LoaderCallbacks<ArrayList<Categories>>() {
        @Override
        public Loader<ArrayList<Categories>> onCreateLoader(int id, Bundle args) {
            return new NavigationLoader(MainActivity.this);
        }


        @Override
        public void onLoadFinished(Loader<ArrayList<Categories>> loader, ArrayList<Categories> categories) {
            if (categories == null)
                return;
            mGroupCollection.clear();
            //Show Navigation Data
            int faqSize = categories.size();
            for (int i = 0; i < faqSize; i++) {
                NavigationGroup navigationGroup = new NavigationGroup();
                String category = categories.get(i).getName();
                navigationGroup.Name = "" + category;

                List<String> items = categories.get(i).getSubcategories();
                for (String item : items) {
                    navigationGroup.GroupItemCollection.add(item);
                }
                mGroupCollection.add(navigationGroup);
            }

            setNavigationData();
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<Categories>> loader) {
        }
    };


}
