package com.codepath.apps.basictwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.Tweet;

public class NewTweetActivity extends Activity {
	private TwitterClient client;
	private Tweet newTweet;
	private TextView tvCharacterCount;
	private EditText etNewTweet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_tweet);
		client = TwitterApplication.getRestClient();
		newTweet = new Tweet();
		etNewTweet = (EditText) findViewById(R.id.etNewTweet);
		tvCharacterCount = (TextView) findViewById(R.id.tvCharacterCount);
		tvCharacterCount.setText("140");
		etNewTweet.addTextChangedListener(tweetTextEditorWatcher);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
        if (id == R.id.miSubmitTweet) {
            return true;
        }
        return super.onOptionsItemSelected(item);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.submit_tweet, menu);
        return true;
	}
	/*
	public void postNewTweet(String newTweetText) {
		client.postNewTweet(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject resp) {
				newTweet = Tweet.fromJson(resp);
				Toast.makeText(NewTweetActivity.this, "Post Success: " + newTweet.toString(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Toast.makeText(NewTweetActivity.this, "New Tweet Post Failure", Toast.LENGTH_SHORT).show();
			}
		}, newTweetText);
	}
	*/
	
	public void onSubmitTweetMenuItem(MenuItem mi) {
		// Get the text from the text field for the new tweet:
		String newTweetText = etNewTweet.getText().toString();
		//postNewTweet(newTweetText);
		Intent i = new Intent();
        i.putExtra("newTweetText", newTweetText);
        //i.putExtra(SearchActivity.COLOR_FILTER, spColorFilterValue.getSelectedItem().toString());
        //i.putExtra(SearchActivity.TYPE_FILTER, spTypeFilterValue.getSelectedItem().toString());
        //i.putExtra(SearchActivity.SITE_FILTER, etSiteFilterValue.getText().toString());
        setResult(RESULT_OK, i);
        finish();
	}
	
	public void onSubmitTweet(View v) {
		// Get the text from the text field for the new tweet:
		String newTweetText = etNewTweet.getText().toString();
		Intent i = new Intent();
        i.putExtra("newTweetText", newTweetText);
        setResult(RESULT_OK, i);
        finish();
	}
	
	private final TextWatcher tweetTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
           //This sets a textview to the current length
        	tvCharacterCount.setText(String.valueOf(140-s.length()));
        }

        public void afterTextChanged(Editable s) {
        }
};
}
