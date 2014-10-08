package com.codepath.apps.basictwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	private String name;
	private long uid;
	private String screenName;
	private String profileImageUrl;
	private String tagLine;
	private int followersCount;
	private int friendsCount;
	private int tweetsCount;

	//User.fromJSON(...)
	public static User fromJSON(JSONObject jsonObject) {
		User u = new User();
		try {
			u.name = jsonObject.getString("name");
			u.uid = jsonObject.getLong("id");
			u.screenName = jsonObject.getString("screen_name");
			u.profileImageUrl = jsonObject.getString("profile_image_url");
			u.tagLine = jsonObject.getString("description");
			u.followersCount = jsonObject.getInt("followers_count");
			u.friendsCount = jsonObject.getInt("friends_count");
			u.tweetsCount = jsonObject.getInt("statuses_count");
		} catch(JSONException e) {
			e.printStackTrace();
		}
		return u;
	}
	
	public int getTweetsCount() {
		return tweetsCount;
	}
	
	public String getTagLine() {
		return tagLine;
	}
	
	public int getFollowersCount() {
		return followersCount;
	}
	
	public int getFriendsCount() {
		return friendsCount;
	}

	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

}
