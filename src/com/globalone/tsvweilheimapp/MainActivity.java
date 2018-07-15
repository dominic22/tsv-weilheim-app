package com.globalone.tsvweilheimapp;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.globalone.tsvweilheimapp.listview.MyListAdapter;

public class MainActivity extends ListActivity {
	// Progress Dialog
	private ProgressDialog pDialog;

	// testing on your device
	// put your local ip instead, on windows, run CMD > ipconfig
	// or in mac's terminal type ifconfig and look for the ip under en0 or en1
	// private static final String READ_COMMENTS_URL =
	// "http://xxx.xxx.x.x:1234/webservice/comments.php";

	TextView txtComments;
	private static final String READ_COMMENTS_URL = "http://android.handball-weilheim.de/webhandball/comments.php"; // "http://globalone331.bplaced.net/webservice/comments.php";
	// JSON IDS:
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_TITLE = "title";
	private static final String TAG_POSTS = "posts";
	private static final String TAG_INTROTEXT = "introtext";
	private static final String TAG_FULLTEXT = "fulltext";

	// it's important to note that the message is both in the parent branch of
	// our JSON tree that displays a "Post Available" or a "No Post Available"
	// message,
	// and there is also a message for each individual post, listed under the
	// "posts"
	// category, that displays what the user typed as their message.

	// An array of all of our comments
	private JSONArray mComments = null;
	// manages all of our comments in a list.
	private ArrayList<HashMap<String, String>> mCommentList;
	private ArrayList<HashMap<String, String>> arrFullContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// note that use read_comments.xml instead of our single_post.xml
		setContentView(R.layout.main);
		this.getActionBar().setTitle("TSV Weilheim - Aktuelles");
		// txtComments = (TextView) findViewById(R.id.message);
		if (isNetworkAvailable()) {
			new LoadComments().execute();
		}
		else{
			new AlertDialog.Builder(this).setTitle("Keine Internetverbindung!").setMessage("Bitte �berpr�fen Sie Ihre Internetverbindung.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			}).show();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// loading the comments via AsyncTask
		// new LoadComments().execute();
	}

	public boolean isNetworkAvailable() {
		// (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
		ConnectivityManager connectivityManager = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	/**
	 * Retrieves recent post data from the server.
	 */

	public void updateJSONdata() {

		// Instantiate the arraylist to contain all the JSON data.
		// we are going to use a bunch of key-value pairs, referring
		// to the json element name, and the content, for example,
		// message it the tag, and "I'm awesome" as the content..

		mCommentList = new ArrayList<HashMap<String, String>>();

		// Bro, it's time to power up the J parser
		JSONParser jParser = new JSONParser();
		// Feed the beast our comments url, and it spits us
		// back a JSON object. Boo-yeah Jerome.
		JSONObject json = jParser.getJSONFromUrl(READ_COMMENTS_URL);

		// when parsing JSON stuff, we should probably
		// try to catch any exceptions:
		try {

			// I know I said we would check if "Posts were Avail." (success==1)
			// before we tried to read the individual posts, but I lied...
			// mComments will tell us how many "posts" or comments are
			// available
			mComments = json.getJSONArray(TAG_POSTS);

			int maxNews;
			if (mComments.length() > 20)
				maxNews = 20;
			else
				maxNews = mComments.length();

			// looping through all posts according to the json object returned
			for (int i = 0; i < maxNews/* mComments.length() */; i++) {
				JSONObject c = mComments.getJSONObject(i);

				// gets the content of each tag
				String title = c.getString(TAG_TITLE);
				String introtext = c.getString(TAG_INTROTEXT);
				String fulltext = c.getString(TAG_FULLTEXT);

				if (fulltext != "") {

					fulltext = fulltext.replaceAll("<br />", "\n\n");

					fulltext = fulltext.replaceAll("<p>", "\n");
					fulltext = fulltext.replaceAll("<" + ".*?" + ">", "");
					if (fulltext.startsWith("\r\n\n")) {
						fulltext = fulltext.replaceFirst("\r\n\n", "");
					}
				}
				// Parse Content... >.<
				introtext = introtext.replaceAll("<" + ".*?" + ">", "");
				HashMap<String, String> map = new HashMap<String, String>();
				HashMap<String, String> mapFullContent = new HashMap<String, String>();

				map.put(TAG_TITLE, title);
				map.put(TAG_INTROTEXT, introtext);
				if (fulltext != "")
					map.put(TAG_FULLTEXT, fulltext);
				// adding HashList to ArrayList
				mCommentList.add(map);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inserts the parsed data into the listview.
	 */
	private void updateList() {
		// For a ListActivity we need to set the List Adapter, and in order to
		// do
		// that, we need to create a ListAdapter. This SimpleAdapter,
		// will utilize our updated Hashmapped ArrayList,
		// use our single_post xml template for each item in our list,
		// and place the appropriate info from the list to the
		// correct GUI id. Order is important here.

		ListAdapter adapter = new MyListAdapter(this, mCommentList,
				R.layout.single_post,
				new String[] { TAG_TITLE, TAG_INTROTEXT }, new int[] {
						R.id.title, R.id.message });

		// I shouldn't have to comment on this one:
		setListAdapter(adapter);

		// Optional: when the user clicks a list item we
		// could do something. However, we will choose
		// to do nothing...
		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// This method is triggered if an item is click within our
				// list. For our example we won't be using this, but
				// it is useful to know in real life applications.
				// String item = ((TextView)view).getText().toString();
				Integer index = position;
				String str = index.toString();
				HashMap<String, String> mapActContent = mCommentList
						.get(position);
				String strActContent = mapActContent.get(TAG_FULLTEXT);
				Intent myIntent = new Intent(view.getContext(),
						FullContentActivity.class);
				myIntent.putExtra("strActContent", strActContent);

				// Titel uebergeben
				String strActTitle = mapActContent.get(TAG_TITLE);
				myIntent.putExtra("strActTitle", strActTitle);

				// Intro uebergeben
				String strActIntro = mapActContent.get(TAG_INTROTEXT);

				// Intent myIntent2 = new Intent(view.getContext(),
				// FullContentActivity.class);
				myIntent.putExtra("strActIntro", strActIntro);

				startActivityForResult(myIntent, 0);
			}
		});
	}

	public class LoadComments extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = ProgressDialog.show(MainActivity.this,
					"Lade Nachrichten...", "Bitte warten...", true);
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			updateJSONdata();
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pDialog.dismiss();
			updateList();
		}
	}
}
