package com.example.id.moevents;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Favorites extends ListFragment {
    int fragNum;
    public ArrayList<CalendarEvent> upcomingEvents = new ArrayList<>();
    public ArrayList<String> upcomingSummaries = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapter;

    static Favorites init(int val) {
        Favorites truitonList = new Favorites();

        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        truitonList.setArguments(args);

        return truitonList;
    }

    /**
     * Retrieving this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragNum = getArguments() != null ? getArguments().getInt("val") : 1;
    }

    // handler for received Intents for the "allEventsNotify" event
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            initListElements();
        }
    };

    /**
     * The Fragment's UI is a simple text view showing its instance number and
     * an associated list.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.suggest,
                container, false);

        // Register mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter("allEventsNotify"));

        return layoutView;
    }

    public void initListElements() {
        if (MainActivity.allEvents.size() > 1) {
            for (CalendarEvent eachEvent : MainActivity.allEvents) {
                if ( eachEvent.isFavorited() ) {
                    upcomingSummaries.add(eachEvent.getSummary());
                    upcomingEvents.add(eachEvent);
                }
            }

            arrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, upcomingSummaries);
        setListAdapter(arrayAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("favorites", upcomingEvents.get((int) id).toString());
    }
}