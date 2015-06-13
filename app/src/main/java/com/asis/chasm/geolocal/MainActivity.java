package com.asis.chasm.geolocal;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements
        NavigationDrawerFragment.NavigationDrawerCallbacks,
        LocalPointsListFragment.OnFragmentInteractionListener,
        GeoPointsListFragment.OnFragmentInteractionListener {

    // Use for logging and debugging
    private static final String TAG = "MainActivity";

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Fragment displaying the local points list
     */
    private LocalPointsListFragment mLocalPointsListFragment;

    /**
     * Fragment displaying the geographic point list
     */
    private GeoPointsListFragment mGeoPointsListFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position + 1) {
            case 1:
                mTitle = getString(R.string.title_section1);
                if (mLocalPointsListFragment == null) {
                    mLocalPointsListFragment = new LocalPointsListFragment();
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.container, mLocalPointsListFragment)
                        .commit();
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                        .commit();
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                if (mGeoPointsListFragment == null) {
                    mGeoPointsListFragment = new GeoPointsListFragment();
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.container, mGeoPointsListFragment)
                        .commit();
                break;
        }
    }


    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment_main, container, false);
            TextView text = (TextView) root.findViewById(R.id.section_label);
            if (text != null) {
                text.setText("Section number: " + getArguments().getInt(ARG_SECTION_NUMBER));
            }
            return root;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public void onLocalPointsFragmentInteraction(String id) {

        Uri uri;
        switch (id) {
            case "1":
                uri = Uri.parse(PointsContract.Points.CONTENT_URI);
                break;
            case "2":
                uri = Uri.parse(PointsContract.Projections.CONTENT_URI);
                break;
            case "3":
            default:
                uri = Uri.parse(PointsContract.Transforms.CONTENT_URI);
                break;

        }
        Log.d(TAG, "Calling ContentResolver.getType: " + uri);

        String type = getContentResolver().getType(uri);

        Toast.makeText(this, "Content type: " + type, Toast.LENGTH_LONG).show();
    }

    public void onGeoPointsFragmentInteraction(String id) {
        Uri uri;
        switch (id) {
            case "1":
                uri = Uri.parse(PointsContract.Points.CONTENT_URI + "/1");
                break;
            case "2":
                uri = Uri.parse(PointsContract.Projections.CONTENT_URI + "/1");
                break;
            case "3":
            default:
                uri = Uri.parse(PointsContract.Transforms.CONTENT_URI + "/1");
                break;

        }
        Log.d(TAG, "Calling ContentResolver.getType: " + uri);

        String type = getContentResolver().getType(uri);

        Toast.makeText(this, "Content type: " + type, Toast.LENGTH_LONG).show();

    }

}
