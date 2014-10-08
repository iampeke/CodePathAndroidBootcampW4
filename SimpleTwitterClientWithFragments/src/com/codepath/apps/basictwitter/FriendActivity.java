package com.codepath.apps.basictwitter;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.fragments.FriendTimelineFragment;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FriendActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend);
		Intent i = getIntent();
		String message = i.getStringExtra("ScreenName");
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		FriendTimelineFragment fragmentDemo = FriendTimelineFragment.newInstance(message);
		ft.replace(R.id.friendFragmentHolder, fragmentDemo);
		ft.commit();
		loadProfileInfo(message);
	}
	
	private void loadProfileInfo(String screenName) {
		TwitterApplication.getRestClient().getMyInfo(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray json) {
						try {
							JSONObject user = json.getJSONObject(0);
							User u = User.fromJSON(user);
							getActionBar().setTitle("@" + u.getScreenName());
							populateProfileHeader(u);
						} catch (Exception e) {
							//User not found.
							e.printStackTrace();
						}
						//populateProfileHeader(u);
					}
				}, screenName);
	}
	
	private void populateProfileHeader(User u) {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagLine);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		TextView tvTweetsCount = (TextView) findViewById(R.id.tvTweetsCount);
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		tvName.setText(u.getName());
		tvTagline.setText(u.getTagLine());
		tvFollowers.setText(u.getFollowersCount() + " Followers");
		tvFollowing.setText(u.getFriendsCount() + " Following");
		tvTweetsCount.setText(u.getTweetsCount() + " Tweets");
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivProfileImage);
	}
}
