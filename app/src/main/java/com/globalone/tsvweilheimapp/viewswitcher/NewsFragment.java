package com.globalone.tsvweilheimapp.viewswitcher;

import android.content.Context;
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

import com.globalone.tsvweilheimapp.FullContentActivity;
import com.globalone.tsvweilheimapp.JSONParser;
import com.globalone.tsvweilheimapp.R;
import com.globalone.tsvweilheimapp.listview.MyListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class NewsFragment extends ListFragment implements OnItemClickListener {
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

    public NewsFragment() {

    }

    public NewsFragment(FragmentAdapter fmAdapter) {
        m_context = fmAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.berichte, container, false);
        String receivedMannschaft = getActivity().getIntent().getStringExtra("Mannschaft");

        READ_COMMENTS_URL = "http://android.handball-weilheim.de/webhandball/berichte.php?site=" + receivedMannschaft;

        if (m_context.getM_item() == 1 || m_context.getM_item() == 2 || m_context.getM_item() == 3) {
            if (mCommentList == null) {
                Log.v("NewsFragment", "before load comments");
                tryToLoadComments();
                Log.v("NewsFragment", "after load comments");
            }
        }

        return rootView;
    }

    void tryToLoadComments() {
        if (isNetworkAvailable()) {
            new LoadComments().execute();
        }
    }

    public boolean isNetworkAvailable() {
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

        JSONParser jParser = new JSONParser();
        // Feed the beast our comments url, and it spits us
        // back a JSON object. Boo-yeah Jerome.
        JSONObject json = jParser.getJSONFromUrl(READ_COMMENTS_URL);

        // when parsing JSON stuff, we should probably
        // try to catch any  exceptions:
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

        adapter = new MyListAdapter(getActivity().getApplicationContext(),
                mCommentList, R.layout.single_post, new String[]{TAG_TITLE,
                TAG_INTROTEXT}, new int[]{R.id.title, R.id.message});

        // I shouldn't have to comment on this one:
        setListAdapter(adapter);

        // adapter.setListAdapter(adapter);

        // Optional: when the user clicks a list item we
        // could do something. However, we will choose
        // to do nothing...
        lv = getListView();

        lv.setOnItemClickListener(this);
        // Save ListView state
        state = lv.onSaveInstanceState();
        itemclickListener = lv.getOnItemClickListener();
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        Log.v("INDEX FR4 =", String.valueOf(m_context.getM_item()));

        if (menuVisible && rootView != null && m_context.getM_item() == 3) {
            if (mCommentList == null) {
                // Los gehts ;)
                Log.v("TeamFragment", "vor load comments");
                tryToLoadComments();
                Log.v("TeamFragment", "nach load comments");
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mCommentList != null) {
            // adapter = new
            // MyListAdapter(getActivity().getApplicationContext(),
            // / mCommentList, R.layout.single_post, new String[] { TAG_TITLE,
            // TAG_INTROTEXT }, new int[] { R.id.title, R.id.message });

            // I shouldn't have to comment on this one:
            setListAdapter(adapter);

            // GEHT NUR HIER SONST EXCEPTION OL
            lv = getListView();
            lv.setOnItemClickListener(this);

        }
    }

    public class LoadComments extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... arg0) {
            try {
                updateJSONdata();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            updateList();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Log.v("LOGGED", "CLICKLISTENER");
        Integer index = position;
        String str = index.toString();
        HashMap<String, String> mapActContent = mCommentList.get(position);
        String strActContent = mapActContent.get(TAG_FULLTEXT);
        Intent myIntent = new Intent(view.getContext(),
                FullContentActivity.class);

        myIntent.putExtra("strActContent", strActContent);

        // Titel �bergeben
        String strActTitle = mapActContent.get(TAG_TITLE);
        myIntent.putExtra("strActTitle", strActTitle);

        // Intro �bergeben
        String strActIntro = mapActContent.get(TAG_INTROTEXT);

        myIntent.putExtra("strActIntro", strActIntro);
        startActivityForResult(myIntent, 0);
    }

}
