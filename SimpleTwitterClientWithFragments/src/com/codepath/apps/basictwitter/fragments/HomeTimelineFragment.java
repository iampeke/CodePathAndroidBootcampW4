package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;
import org.json.JSONObject;

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

public class HomeTimelineFragment extends tweetsListFragment {
	private TwitterClient client;
	private ListView lvTweets;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
		populateTimeline();
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
		    		customLoadMoreDataFromApi(maxId);
		    	} 
		    }
	        });
	        
		
		// Return the layout view
		return v;
	}

	public void customLoadMoreDataFromApi(String offset) {
        populateTimeline(offset);
    }

	public void populateTimeline() {
		client.getHomeTimeline(new JsonHttpResponseHandler() {
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
		});
	}
	
	public void populateTimeline(String maxid) {
		client.getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				addAll(Tweet.fromJSONArray(json));
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}, maxid);
	}
	
	public void postNewTweet(String newTweetText) {
		client.postNewTweet(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject resp) {
				add(Tweet.fromJson(resp));
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Toast.makeText(getActivity(), "Tweet not posted", Toast.LENGTH_SHORT).show();
			}
		}, newTweetText);
	}
}
