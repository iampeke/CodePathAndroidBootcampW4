package com.codepath.apps.basictwitter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet>{

	
	public TweetArrayAdapter(Context context, ArrayList<Tweet> tweets) {
		super(context, 0, tweets);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Convert tweet data into the actual tweet template view:
		// Get data item for position:
		Tweet tweet = getItem(position);
		// Find or inflate template:
		View v;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			v = inflater.inflate(R.layout.tweet_item, parent, false);
		} else {
			v = convertView;
		}
		
		ImageView  ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
		TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
		TextView tvName = (TextView) v.findViewById(R.id.tvName);
		TextView tvTimeSince = (TextView) v.findViewById(R.id.tvTimeSince);
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
		ivProfileImage.setTag(tweet.getUser().getScreenName());
		ivProfileImage.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		        //Toast.makeText(TweetArrayAdapter.this.getContext(), v.findViewById(R.id.ivProfileImage).getTag().toString(),Toast.LENGTH_SHORT).show();
		    	Intent i = new Intent(v.getContext(), FriendActivity.class);
		    	i.putExtra("ScreenName", v.findViewById(R.id.ivProfileImage).getTag().toString());
		    	v.getContext().startActivity(i);
		    	
		    }
		});
		tvUserName.setText("@" + tweet.getUser().getScreenName());
		tvName.setText(tweet.getUser().getName());
		tvBody.setText(tweet.getBody());
		tvTimeSince.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
		return v;
	}
	

	public String getRelativeTimeAgo(String rawJsonDate) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
		 
		String relativeDate = "";
		try {
		long dateMillis = sf.parse(rawJsonDate).getTime();
		relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
		System.currentTimeMillis(), 0, DateUtils.FORMAT_ABBREV_ALL).toString();
		} catch (ParseException e) {
		e.printStackTrace();
		}
		 
		return relativeDate;
		}
}
