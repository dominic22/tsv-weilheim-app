package com.globalone.tsvweilheimapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FullContentActivity extends Activity {

	private ProgressDialog pDialog;

	// php read comments script

	// localhost :
	// testing on your device
	// put your local ip instead, on windows, run CMD > ipconfig
	// or in mac's terminal type ifconfig and look for the ip under en0 or en1
	// private static final String READ_COMMENTS_URL =
	// "http://xxx.xxx.x.x:1234/webservice/comments.php";

	TextView tvFull;
	
	// testing on Emulator:
	private static final String READ_COMMENTS_URL ="http://android.handball-weilheim.de/webhandball/comments.php"; //"http://globalone331.bplaced.net/webservice/comments.php";

	// testing from a real server:
	// private static final String READ_COMMENTS_URL =
	// "http://www.mybringback.com/webservice/comments.php";

	TextView txtComments;
	// JSON IDS:
	
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_TITLE = "title";
	private static final String TAG_POSTS = "posts";
	private static final String TAG_FULLTEXT = "fulltext";
	
	private static String fulltext="";
//	private static final String TAG_USERNAME = "username";
//	private static final String TAG_MESSAGE = "message";
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

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// note that use read_comments.xml instead of our single_post.xml
		setContentView(R.layout.fullcontent);
		this.getActionBar().setTitle("TSV Weilheim - Aktuelles");
		tvFull = findViewById(R.id.contentFullContent);
		TextView title = findViewById(R.id.titleFullContent);
		TextView intro = findViewById(R.id.introFullContent);
		
		//txtComments = (TextView) findViewById(R.id.message);
		
		String receivedTitle = getIntent().getStringExtra("strActTitle");
		String receivedContent = getIntent().getStringExtra("strActContent");
		String receivedIntro = getIntent().getStringExtra("strActIntro");
		//tvFull = (TextView) findViewById(R.id.contentFullContent);
		intro.setText("\n"+receivedIntro);
		title.setText(receivedTitle);
		tvFull.setText(receivedContent);
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// loading the comments via AsyncTask
	//	new LoadComments().execute();
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
			
			// looping through all posts according to the json object returned
			for (int i = 0; i < 2/*mComments.length()*/; i++) {
				JSONObject c = mComments.getJSONObject(i);

				// gets the content of each tag
				String title = c.getString(TAG_TITLE);
				 fulltext = c.getString(TAG_FULLTEXT);
				
				//Parse Content... >.<
				
				 
				 //Wurde schon replaced in MainActivity..
			//	 fulltext = fulltext.replaceAll("<br />","\n");
				 
				//fulltext = fulltext.replaceAll("<"+".*?"+">", "");//("(&<=)[^&]*(&>=)", "$1foo$2");
				
				
				//Ansonsten str.replaceAll("(?<=<p>).*?(?=</p>)", "replacement");
				
				
			//	String username = c.getString(TAG_USERNAME);

				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();

				map.put(TAG_TITLE, title);
				map.put(TAG_FULLTEXT, fulltext);
		//		map.put(TAG_USERNAME, username);

				// adding HashList to ArrayList
				mCommentList.add(map);
				
				/*String str = "";
				for(int xy = 0 ; xy< mCommentList.size();xy++){
					 str += mCommentList.get(xy).toString(); 
					
				}
				txtComments.setText(str);
				
			*/
				
				// annndddd, our JSON data is up to date same with our array
				// list
				
			}
			

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	
	private void updateList() {
		// For a ListActivity we need to set the List Adapter, and in order to do
		//that, we need to create a ListAdapter.  This SimpleAdapter,
		//will utilize our updated Hashmapped ArrayList, 
		//use our single_post xml template for each item in our list,
		//and place the appropriate info from the list to the
		//correct GUI id.  Order is important here.
	
		
		
		/*ListAdapter adapter = new SimpleAdapter(this, mCommentList,
				R.layout.single_post, new String[] { TAG_TITLE , TAG_INTROTEXT
						 }, new int[] { R.id.title ,R.id.message
						});
*/
		// I shouldn't have to comment on this one:
		//setListAdapter(adapter);
		
		
	}

	public class LoadComments extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(FullContentActivity.this);
			pDialog.setMessage("Loading Comments...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
			/* pDialog = ProgressDialog.show(FullContentActivity.this, "Loading...", "Please wait...", true);
			*/
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
			
			tvFull.setText(fulltext);
			
		}
	}
	
	
	
}
