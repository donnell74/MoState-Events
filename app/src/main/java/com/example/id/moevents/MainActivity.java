package com.example.id.moevents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;

import org.apache.http.Header;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.IncompleteAnnotationException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Today","Upcoming", "Favorites"};
    int Numboftabs =3;
    static public List<CalendarEvent> allEvents = new ArrayList<CalendarEvent>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://missouristate.info/feeds/calendar.aspx?recurrences=none&timespan=future&campus=s&alt=ical",
                new FileAsyncHttpResponseHandler(this) {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                        // Failed to download
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, File response) {
                        // Do something with the file `response`
                        //Log.i("get success", mFile.toString());

                        FileInputStream fin = null;
                        try {
                            fin = new FileInputStream(mFile);
                            CalendarBuilder builder = new CalendarBuilder();

                            Calendar calendar = builder.build(fin);

                            List<Component> components = calendar.getComponents("VEVENT");

                            for (int j = 0; j < components.size(); ++j) {
                                MainActivity.allEvents.add(new CalendarEvent(j, components.get(j)));
                            }

                            Intent intent = new Intent("allEventsNotify");
                            // add data
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (ParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });


        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        tabs.setViewPager(pager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
