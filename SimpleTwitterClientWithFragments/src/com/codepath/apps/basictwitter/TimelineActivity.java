package com.codepath.apps.basictwitter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.basictwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.basictwitter.fragments.UserTimelineFragment;
import com.codepath.apps.basictwitter.listeners.FragmentTabListener;

public class TimelineActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupTabs();
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
	}
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
			.setText("Home")
			//.setIcon(R.drawable.ic_home)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "home",
								HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Mentions")
			//.setIcon(R.drawable.ic_mentions)
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "mentions",
								MentionsTimelineFragment.class));

		actionBar.addTab(tab2);
	}
	
	
	
	public void postNewTweet(String newTweetText) {
		
		HomeTimelineFragment fragmentDemo = (HomeTimelineFragment) 
	            getSupportFragmentManager().findFragmentById(R.id.flContainer);
        fragmentDemo.postNewTweet(newTweetText);
		/*
		client.postNewTweet(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject resp) {
				tweets.add(0, Tweet.fromJson(resp));
				aTweets.notifyDataSetChanged();
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Toast.makeText(TimelineActivity.this, "Tweet not posted", Toast.LENGTH_SHORT).show();
			}
		}, newTweetText);
		*/
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.miCreateTweet) {
			return true;
		}
		if (id == R.id.miProfileView) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.create_tweet, menu);
		//getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
	
	public void onProfileView(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
		startActivity(i);
	}
	
	public void onCreateTweetMenuItem(MenuItem mi) {
		//Toast.makeText(this, "Create Tweet Clicked", Toast.LENGTH_SHORT).show();
		
		// Create the intent:
        Intent i = new Intent(this, NewTweetActivity.class); // explicit intent

        //Define the parameters ("extras" encoded into a bundle):
        //i.putExtra(SIZE_FILTER, sizeFilterValue);
        //i.putExtra(COLOR_FILTER, colorFilterValue);
        //i.putExtra(TYPE_FILTER, typeFilterValue);
        //i.putExtra(SITE_FILTER, siteFilterValue);

        // Execute the intent:
        startActivityForResult(i, 50); //Last number could be a constant that describes that actual payload type
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 50) { // Check for the correct payload
            if (resultCode == RESULT_OK) { // Checks if the request was correct
                //Toast.makeText(this, "Filter Changed", Toast.LENGTH_SHORT).show();
                String newTweetText = data.getStringExtra("newTweetText");
                postNewTweet(newTweetText);
                //Toast.makeText(this, tweets.get(tweets.size()).toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
