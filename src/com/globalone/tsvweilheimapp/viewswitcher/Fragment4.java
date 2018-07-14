package com.globalone.tsvweilheimapp.viewswitcher;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.globalone.tsvweilheimapp.FullContentActivity;
import com.globalone.tsvweilheimapp.JSONParser;
import com.globalone.tsvweilheimapp.R;
import com.globalone.tsvweilheimapp.listview.MyListAdapter;


public class Fragment4 extends ListFragment implements OnItemClickListener {
	// Tablelayout in Fragment 3 hinzufügen..
	// Dann dort die daten eintragen..

	// Progress Dialog ProgressDialog
	private ProgressDialog pDialog;

	// php read comments script

	// localhost :
	// testing on your device
	// put your local ip instead, on windows, run CMD > ipconfig
	// or in mac's terminal type ifconfig and look for the ip under en0 or en1
	// private static final String READ_COMMENTS_URL =
	// "http://xxx.xxx.x.x:1234/webservice/comments.php";

	// testing on Emulator:
	private static String READ_COMMENTS_URL = "http://android.handball-weilheim.de/webhandball/berichte.php?site=erste"; // "http://globalone331.bplaced.net/webservice/comments.php";

	// testing from a real server:
	// private static final String READ_COMMENTS_URL =
	// "http://www.mybringback.com/webservice/comments.php";

	TextView txtComments;
	// JSON IDS:

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_TITLE = "title";
	private static final String TAG_POSTS = "posts";
	private static final String TAG_INTROTEXT = "introtext";
	private static final String TAG_FULLTEXT = "fulltext";

	// private static final String TAG_USERNAME = "username";
	// private static final String TAG_MESSAGE = "message";
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
	// List
	MyListAdapter adapter;
	ListView lv;
	static Parcelable state;
	OnItemClickListener itemclickListener;
	View rootView;

	
	static FragmentAdapter m_context;

	public Fragment4() {

	}
	int index;

	public Fragment4(FragmentAdapter fmAdapter) {
		// TODO Auto-generated constructor stub
		m_context= fmAdapter;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log.v("Fragment3", "beginn");
		rootView = inflater.inflate(R.layout.berichte, container, false);

		// lv = (ListView) rootView.findViewById(R.id.list);

		String receivedMannschaft = getActivity().getIntent().getStringExtra(
				"Mannschaft");

		if (receivedMannschaft.equals("erste")) {
			READ_COMMENTS_URL = "http://android.handball-weilheim.de/webhandball/berichte.php?site=erste";
		} else if (receivedMannschaft.equals("zweite")) {
			READ_COMMENTS_URL = "http://android.handball-weilheim.de/webhandball/berichte.php?site=zweite";
		} else if (receivedMannschaft.equals("damen")) {
			READ_COMMENTS_URL = "http://android.handball-weilheim.de/webhandball/berichte.php?site=damen";
		} else if (receivedMannschaft.equals("damen2")) {
			READ_COMMENTS_URL = "http://android.handball-weilheim.de/webhandball/berichte.php?site=damen2";
		} else if (receivedMannschaft.equals("js")) {
			READ_COMMENTS_URL = "http://android.handball-weilheim.de/webhandball/berichte.php?site=js";
		} else if (receivedMannschaft.equals("ad")) {
			READ_COMMENTS_URL = "http://android.handball-weilheim.de/webhandball/berichte.php?site=ad";
		}

	
		
		if (m_context.getM_item() == 3) {
			if (mCommentList == null) {
				// Los gehts ;)
				Log.v("Fragment3", "vor load comments");
				tryToLoadComments();
//				new LoadComments().execute();
				Log.v("Fragment3", "nach load comments");
			}
		}

		return rootView;
	}

	void tryToLoadComments(){
		if(isNetworkAvailable())
		{
			new LoadComments().execute();
		}
		else{
		/*	new AlertDialog.Builder(getActivity()).setTitle("Keine Internetverbindung!").setMessage("Bitte überprüfen Sie Ihre Internetverbindung.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			}).show();
		*/}
	}
	public boolean isNetworkAvailable() {
		// (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
		ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
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
			if (mComments.length() > 30)
				maxNews = 30;
			else
				maxNews = mComments.length();

			// looping through all posts according to the json object returned

			for (int i = 0; i < maxNews/* mComments.length() */; i++) {
				JSONObject c = mComments.getJSONObject(i);

				// gets the content of each tag
				String title = c.getString(TAG_TITLE);
				String introtext = c.getString(TAG_INTROTEXT);
				String fulltext = c.getString(TAG_FULLTEXT);

				// TODO

				if (fulltext != "") {

					fulltext = fulltext.replaceAll("<br />", "\n\n");

					fulltext = fulltext.replaceAll("<p>", "\n");
					fulltext = fulltext.replaceAll("<" + ".*?" + ">", "");
					if (fulltext.startsWith("\r\n\n")) {
						fulltext = fulltext.replaceFirst("\r\n\n", "");
					}
				}
				// Parse Content... >.<
				introtext = introtext.replaceAll("<" + ".*?" + ">", "");// ("(&<=)[^&]*(&>=)",
																		// "$1foo$2");

				// Ansonsten str.replaceAll("(?<=<p>).*?(?=</p>)",
				// "replacement");

				// String username = c.getString(TAG_USERNAME);

				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();

				HashMap<String, String> mapFullContent = new HashMap<String, String>();

				map.put(TAG_TITLE, title);
				map.put(TAG_INTROTEXT, introtext);
				if (fulltext != "")
					map.put(TAG_FULLTEXT, fulltext);

				// adding HashList to ArrayList
				mCommentList.add(map);

				// mapFullContent.put(TAG_FULLTEXT, strFullText);

				// arrFullContent.add(mapFullContent);

				// arrFullContent.add(strFullText);

				/*
				 * String str = ""; for(int xy = 0 ; xy<
				 * mCommentList.size();xy++){ str +=
				 * mCommentList.get(xy).toString();
				 * 
				 * } txtComments.setText(str);
				 */

				// annndddd, our JSON data is up to date same with our array
				// list

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

		adapter = new MyListAdapter(getActivity().getApplicationContext(),
				mCommentList, R.layout.single_post, new String[] { TAG_TITLE,
						TAG_INTROTEXT }, new int[] { R.id.title, R.id.message });

		// I shouldn't have to comment on this one:
		setListAdapter(adapter);

		// adapter.setListAdapter(adapter);

		// Optional: when the user clicks a list item we
		// could do something. However, we will choose
		// to do nothing...
		// getListView();
		lv = getListView();

		lv.setOnItemClickListener(this);
		// Save ListView state
		state = lv.onSaveInstanceState();
		itemclickListener = lv.getOnItemClickListener();
	}

	@Override
	public void setMenuVisibility(boolean menuVisible) {
		// TODO Auto-generated method stub
		super.setMenuVisibility(menuVisible);
		
		
//		ActionBar pager = mainSwitch.getActionBar();
		
		// MainSwitcherActivity mainSwitch= (MainSwitcherActivity) m_context;
		// ActionBar action = mainSwitch.getActionBar();

		 Log.v("INDEX FR4 =",String.valueOf(m_context.getM_item()));

		if (menuVisible && rootView != null && m_context.getM_item() ==3) {
			if (mCommentList == null) {
				// Los gehts ;)
				Log.v("Fragment3", "vor load comments");
				tryToLoadComments();
//				new LoadComments().execute();
				Log.v("Fragment3", "nach load comments");
			}
		}

		else {
			
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	
		
		if (mCommentList == null) {

		} else {

			// HashMap<String, String> act_map=(HashMap<String, String>)
			// lv.getItemAtPosition(0);
			// String strTitle= act_map.get("title");
			// Log.v("asd",strTitle );
			// lv.onRestoreInstanceState(state);

			// lv.setOnItemClickListener(this);
		}

		if (mCommentList != null) {
			// adapter = new
			// MyListAdapter(getActivity().getApplicationContext(),
			// / mCommentList, R.layout.single_post, new String[] { TAG_TITLE,
			// TAG_INTROTEXT }, new int[] { R.id.title, R.id.message });

			// I shouldn't have to comment on this one:
			setListAdapter(adapter);
			Log.v("asd", "Holitem");

			// GEHT NUR HIER SONST EXCEPTION OLÉ
			lv = getListView();
			lv.setOnItemClickListener(this);

		}
	}

	public class LoadComments extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = ProgressDialog.show(getActivity(), "Lade Berichte...",
					"Bitte warten...", true);
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			try {
				updateJSONdata();
			} catch (Exception EX2) {

			}
			return null;

		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pDialog.dismiss();
			updateList();
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Log.v("LOGGED", "CLICKLISTENER");
		Integer index = position;
		String str = index.toString();

		// Toast.makeText(getApplicationContext() , str,
		// Toast.LENGTH_LONG).show();

		/*
		 * for(HashMap<String, String> map: mylist) { for(Entry<String, String>
		 * mapEntry: map.entrySet()) { String key = mapEntry.getKey(); String
		 * value = mapEntry.getValue(); } }
		 */

		// HashMap<String, String> mapActContent=
		// arrFullContent.get(position);
		HashMap<String, String> mapActContent = mCommentList.get(position);

		String strActContent = mapActContent.get(TAG_FULLTEXT);

		Intent myIntent = new Intent(view.getContext(),
				FullContentActivity.class);
		myIntent.putExtra("strActContent", strActContent);

		// startActivityForResult(myIntent, 0); // Nur einmal starten..

		// Titel übergeben
		String strActTitle = mapActContent.get(TAG_TITLE);

		// Intent myIntent2 = new Intent(view.getContext(),
		// FullContentActivity.class);
		myIntent.putExtra("strActTitle", strActTitle);
		// startActivityForResult(myIntent, 0);// Nur einmal starten..

		// Intro übergeben
		String strActIntro = mapActContent.get(TAG_INTROTEXT);

		// Intent myIntent2 = new Intent(view.getContext(),
		// FullContentActivity.class);
		myIntent.putExtra("strActIntro", strActIntro);

		startActivityForResult(myIntent, 0);

	}

}
