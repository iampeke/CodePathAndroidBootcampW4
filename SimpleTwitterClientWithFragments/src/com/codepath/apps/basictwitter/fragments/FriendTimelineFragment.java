package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class FriendTimelineFragment extends tweetsListFragment {
private TwitterClient client;
private ListView lvTweets;
private String screenName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
		screenName = getArguments().getString("screenName", "");
		populateTimeline(screenName);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout:
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		// Assign our view references (lvItems findViewById, etc...)
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(getATweets());
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
	                // Triggered only when new data needs to be appended to the list
	                // Add whatever code is needed to append new items to your AdapterView
		    	String maxId = getMaxId();
		    	if (maxId != null) {
		    		customLoadMoreDataFromApi(screenName, maxId);
		    	} 
		    }
	        });
		
		// Return the layout view
		return v;
	}

	
	public void customLoadMoreDataFromApi(String screenName, String maxid) {
		populateTimeline(screenName, maxid);
    }
	
	public static FriendTimelineFragment newInstance(String screenName) {
		FriendTimelineFragment fragmentDemo = new FriendTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screenName", screenName);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }
	
	// Add in the pull to refresh code / infinite scroll code when you figure it out again.

	public void populateTimeline(String screenName) {
		client.getFriendTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				addAll(Tweet.fromJSONArray(json));
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}, screenName);
	}
	
	public void populateTimeline(String screenName, String maxid) {
		client.getFriendTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				addAll(Tweet.fromJSONArray(json));
				//lvTweets.onRefreshComplete();
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}, screenName, maxid);
	}
}
