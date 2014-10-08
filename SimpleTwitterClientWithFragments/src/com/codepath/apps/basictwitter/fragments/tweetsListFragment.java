package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.Tweet;

public class tweetsListFragment extends Fragment {
	private ArrayList<Tweet> tweets;
	private TweetArrayAdapter aTweets;
	//private PullToRefreshListView lvTweets;
	private ListView lvTweets;
	private View v;
	private String maxId;
	
	//layout.fragment_tweets_list.xml created; List View moved there from layout.activity_timeline
	
	// Hook up some of the lifecycle methods: (there is a guide for lifecycle of a fragment)

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
		// getActivity() should be used sparingly...
	}
	
	public ListView getListView() {
		return lvTweets;
	}
	
	public TweetArrayAdapter getATweets() {
		return aTweets;
	}
	
	/*
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout:
		v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		// Assign our view references (lvItems findViewById, etc...)
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		
		// Return the layout view
		return v;
	}
*/
	
	// Delegate adding to the internal adapter:	
	public void addAll(ArrayList<Tweet> tweets) {
		Tweet lastTweet = tweets.get(tweets.size()-1);
		String lastTweetId = Long.toString(lastTweet.getUid());
		this.maxId = lastTweetId;
		aTweets.addAll(tweets);
	}
	
	public void removeAll() {
		aTweets.clear();
		aTweets.notifyDataSetChanged();
	}
	
	public String getMaxId() {
		return this.maxId;
	}
	
	public void add(Tweet tweet) {
		tweets.add(0, tweet);
		aTweets.notifyDataSetChanged();
	}

	
	
	

}
