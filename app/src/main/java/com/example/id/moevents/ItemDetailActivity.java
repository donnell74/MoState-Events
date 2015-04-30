package com.example.id.moevents;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.text.BoringLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * An activity representing a single Item detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link ItemDetailFragment}.
 */
public class ItemDetailActivity extends ActionBarActivity {

    public static FragmentManager fragmentManager;

    private CalendarEvent mEvent = null;
    private TextView mEventTV;
    private TextView mDescTV;
    private TextView mTimeTV;
    private TextView mLocTV;
    private Button mFavBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        // initialising the object of the FragmentManager. Here I'm passing getSupportFragmentManager(). You can pass getFragmentManager() if you are coding for Android 3.0 or above.
        fragmentManager =  getSupportFragmentManager();

        // Show the Up button in the action bar.
        int id = getIntent().getIntExtra(ItemDetailFragment.ARG_ITEM_ID, 0);
        mEvent = MainActivity.allEvents.get(id);

        mEventTV = (TextView) findViewById(R.id.EventText);
        mDescTV = (TextView) findViewById(R.id.DescriptionText);
        mLocTV = (TextView) findViewById(R.id.LocationText);
        mTimeTV = (TextView) findViewById(R.id.TimeText);
        mFavBtn = (Button) findViewById(R.id.favBtn);

        if ( mEvent != null ) {
            mEventTV.setText(mEvent.getSummary());
            mDescTV.setText(mEvent.getDescription());
            mLocTV.setText(mEvent.getLocation());

            DateFormat m_ISO8601Local = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
            try {
                String time = m_ISO8601Local.parse(mEvent.getStart()).toString();
                time += " - ";
                time += m_ISO8601Local.parse(mEvent.getEnd()).toString();
                mTimeTV.setText(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if ( mEvent.isFavorited() ) {
                mFavBtn.setText(getString(R.string.unfavorite));
            }

            mFavBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Boolean isFav = ! mEvent.isFavorited();
                    mEvent.setFavorite(isFav);
                    if ( isFav ) {
                        mFavBtn.setText(getString(R.string.unfavorite));
                    } else {
                        mFavBtn.setText(getString(R.string.favorite));
                    }
                }
            });
        }

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            ItemDetailFragment fragment = new ItemDetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
