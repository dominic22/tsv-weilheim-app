package com.globalone.tsvweilheimapp.xmladapter;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.globalone.tsvweilheimapp.R;

public class MainXML extends Activity {
	
	//Dies zeigt die Tabelle an!!!

	public static SitesAdapter mAdapterLetzteWoche;

	
	public static SitesAdapter mAdapter;
	// private ListView sitesList;
	// private TextView txtContent;
	private TableLayout tableLayoutLetzteWoche;
	private TableRow tableRowLetzteWoche;

	private TableLayout tableLayout;
	private TableRow tableRow;

	static String strURL = "";
	static String strURLLetzteWoche = "";

	public static void setStrURL(String strURL) {
		MainXML.strURL = strURL;
	}

	public static String getStrURL() {
		return strURL;
	}

	public static void setStrURLLetzteWoche(String strURLLetzteWoche) {
		MainXML.strURLLetzteWoche = strURLLetzteWoche;
	}

	public static String getStrURLLetzteWoche() {
		return strURLLetzteWoche;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("StackSites", "OnCreate()");

		setContentView(R.layout.activity_parse_xml);

		// Get reference to our ListView
		// sitesList = (ListView)findViewById(R.id.sitesList);

		// Set the click listener to launch the browser when a row is clicked.
		/*
		 * sitesList.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View v, int
		 * pos,long id) { String url = mAdapter.getItem(pos).getLink(); Intent i
		 * = new Intent(Intent.ACTION_VIEW); i.setData(Uri.parse(url));
		 * startActivity(i);
		 * 
		 * }
		 * 
		 * });
		 */

		// txtContent = (TextView) findViewById(R.id.txtContentParse);
		tableLayout = (TableLayout) findViewById(R.id.tableLayoutTabelle);
		tableLayoutLetzteWoche = (TableLayout) findViewById(R.id.tableLayoutLetzteWoche);

		String receivedMannschaft = getIntent().getStringExtra("Mannschaft");

		if (receivedMannschaft.equals("erste")) {
			setStrURL("http://android.handball-weilheim.de/webhandball/hvwcutout.php?site=erste&type=table");
			setStrURLLetzteWoche("http://android.handball-weilheim.de/webhandball/hvwcutoutlastweek.php?site=erste&type=table");

		} else if (receivedMannschaft.equals("zweite")) {
			setStrURL("http://android.handball-weilheim.de/webhandball/hvwcutout.php?site=zweite&type=table");
			setStrURLLetzteWoche("http://android.handball-weilheim.de/webhandball/hvwcutoutlastweek.php?site=zweite&type=table");

		} else if (receivedMannschaft.equals("damen")) {
			setStrURL("http://android.handball-weilheim.de/webhandball/hvwcutout.php?site=damen&type=table");
			setStrURLLetzteWoche("http://android.handball-weilheim.de/webhandball/hvwcutoutlastweek.php?site=damen&type=table");

		}// else
			// setStrURL("http://android.handball-weilheim.de/webhandball/hvwcutout.php?site=damen&type=table");

		/*
		 * If network is available download the xml from the Internet. If not
		 * then try to use the local file from last time.
		 */
		if (isNetworkAvailable()) {
			Log.i("StackSites", "starting download Task");
			SitesDownloadTask download = new SitesDownloadTask();
			download.execute();

		} else {
			mAdapter = new SitesAdapter(getApplicationContext(), -1,
					SitesXmlPullParser.getStackSitesFromFile(MainXML.this, 0));

			mAdapterLetzteWoche = new SitesAdapter(getApplicationContext(), -1,
					SitesXmlPullParser.getStackSitesFromFile(MainXML.this, 1));

			// sitesList.setAdapter(mAdapter);
		}

	}

	// Helper method to determine if Internet connection is available.
	public  boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	/*
	 * AsyncTask that will download the xml file for us and store it locally.
	 * After the download is done we'll parse the local file.
	 */
	public class SitesDownloadTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			// Download the file
			try {

				// strURL=getStrURL();
				Downloader.DownloadFromUrl(strURL,
						openFileOutput("StackSites.xml", Context.MODE_PRIVATE));

				Downloader.DownloadFromUrl(
						strURLLetzteWoche,
						openFileOutput("StackSitesLetzteWoche.xml",
								Context.MODE_PRIVATE));

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// setup our Adapter and set it to the ListView.
			mAdapter = new SitesAdapter(MainXML.this, -1,
					SitesXmlPullParser.getStackSitesFromFile(MainXML.this, 0));

			mAdapterLetzteWoche = new SitesAdapter(getApplicationContext(), -1,
					SitesXmlPullParser.getStackSitesFromFile(MainXML.this, 1));

			LinearLayout lay1 = (LinearLayout)findViewById(R.id.linearLayoutTab1);
			LinearLayout lay2 = (LinearLayout)findViewById(R.id.linearLayoutTab2);
			lay1.setVisibility(View.VISIBLE);
			lay2.setVisibility(View.VISIBLE);
			// sitesList.setAdapter(mAdapter);

			// FUER LETZTE WOCHE
			 addFirstRow(0);
			initTableLetzteWoche();
			addRowsLetzteWoche(mAdapterLetzteWoche);

			// Hier in for schleife alle durchgehen..
			// FUER TABELLE
			addFirstRow(1);
			initTable();
			addRows(mAdapter);
			// Log.i("StackSites", "adapter size = "+ mAdapter.getCount());

		}

	}

	void addFirstRow(int laufvar) {
		String receivedMannschaft = getIntent().getStringExtra("Mannschaft");
		String text = "";
		int textGroesse=getApplicationContext().getResources().getDimensionPixelSize(R.dimen.text_header);
		
		

		if (laufvar == 0) {
			if (receivedMannschaft.equals("erste")) {
				text = "Spielergebnisse Herren 1";
			} else if (receivedMannschaft.equals("zweite")) {
				text = "Spielergebnisse Herren 2";
			} else if (receivedMannschaft.equals("damen")) {
				text = "Spielergebnisse Damen 1";
			}
			
		
			TextView textview2 =  (TextView) findViewById(R.id.txtErgebnisOverTable);
			textview2.setText(text);
			textview2.setTextSize(textGroesse);
		//	textview2.setBackgroundResource(R.color.LightGrey);
			
			
		}
		else if(laufvar == 1){
			
			TableRow.LayoutParams llp = new TableRow.LayoutParams(
					android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			if (receivedMannschaft.equals("erste")) {
				text = "Aktueller Tabellenstand Herren 1";
			} else if (receivedMannschaft.equals("zweite")) {
				text = "Aktueller Tabellenstand Herren 2";
			} else if (receivedMannschaft.equals("damen")) {
				text = "Aktueller Tabellenstand Damen 1";
			}
					
			//
			  // 
			   
			
			TextView textview3 =  (TextView) findViewById(R.id.txtTabelleOverTable);
			textview3.setText(text);
			textview3.setTextSize(textGroesse);
		//	textview3.setBackgroundResource(R.color.LightGrey);
		
		}
		
		


	}

	void initTableLetzteWoche() {
		tableRowLetzteWoche = new TableRow(getApplicationContext());

		tableRowLetzteWoche.setBackgroundResource(R.color.LightGrey);

		String strLiga = "Liga";
		String strDatum = "Datum";
		strDatum = strDatum.replace(",", "\n");

		String strVereinHeim = "Verein Heim";
		String strVereinGast = "Verein Gast";
		String strSpielstand = "Spielstand";

		int valGroesse = getApplicationContext().getResources().getDimensionPixelSize(R.dimen.text_size8);
		/*
		 * TextView textview1 = new TextView(getApplicationContext());
		 * textview1.setText(strLiga); textview1.setTextSize(valGroesse);
		 */
		// int Platz = Integer.parseInt(strPlatz);
		// if(Platz >20)
		// fail = true;

		// textview1.setTextColor(Color.BLACK);
		TextView textview2 = new TextView(getApplicationContext());
		textview2.setText(strDatum);
		textview2.setTextSize(valGroesse);
		TextView textview3 = new TextView(getApplicationContext());
		textview3.setText(strVereinHeim);
		textview3.setTextSize(valGroesse);
		
		TextView textview4 = new TextView(getApplicationContext());
		textview4.setText(strVereinGast);
		textview4.setTextSize(valGroesse);

		TextView textview5 = new TextView(getApplicationContext());
		textview5.setText(strSpielstand);
		textview5.setTextSize(valGroesse);

		// LayoutParams:

		TableRow.LayoutParams llp = new TableRow.LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		llp.setMargins(15, 3, 0, 3); // llp.setMargins(left, top, right,
										// bottom);

		TableRow.LayoutParams llp2 = new TableRow.LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		llp2.setMargins(0, 3, 0, 3); // llp.setMargins(left, top, right,
										// bottom);

		tableRowLetzteWoche.setLayoutParams(llp2);

		// textview1.setLayoutParams(llp2);
		textview2.setLayoutParams(llp);
		textview3.setLayoutParams(llp2);
		textview4.setLayoutParams(llp);
		textview5.setLayoutParams(llp);

		// textview1.setTextColor(Color.BLACK);
		textview2.setTextColor(Color.BLACK);
		textview3.setTextColor(Color.BLACK);
		textview4.setTextColor(Color.BLACK);
		textview5.setTextColor(Color.BLACK);

		// textview.getTextColors(R.color.)
		// textview2.setTextColor(Color.BLACK);
		// tableRowLetzteWoche.addView(textview1);
		tableRowLetzteWoche.addView(textview2);
		tableRowLetzteWoche.addView(textview3);
		tableRowLetzteWoche.addView(textview4);
		tableRowLetzteWoche.addView(textview5);
		
		tableLayoutLetzteWoche.addView(tableRowLetzteWoche);

	}

	void addRowsLetzteWoche(SitesAdapter mAdapter) {

		for (int i = 0; i < mAdapter.getCount(); i++) {
			StackSite stacks = mAdapter.getItem(i);
			boolean fail = false;

			tableLayout.setStretchAllColumns(true);
			tableLayout.setShrinkAllColumns(true);

			tableRowLetzteWoche = new TableRow(getApplicationContext());
			tableRowLetzteWoche.setGravity(Gravity.CENTER);
			if (i == 0 || i % 2 == 0)
				tableRowLetzteWoche.setBackgroundResource(R.color.White);
			else
				tableRowLetzteWoche.setBackgroundResource(R.color.LightGrey);
			// GEHT
			// txtContent.setText(stackS.getVerein());

			String strLiga = stacks.getLiga();
			String strDatum = stacks.getDatum();
			String strVereinHeim = stacks.getVereinHeim();
			String strVereinGast = stacks.getVereinGast();
			String strSpielstand = stacks.getToreHeim() + ":"
					+ stacks.getToreGast();

			/*
			 * if (strVerein.length() > 15) { if (strVerein.contains("-/")) {
			 * strVerein = strVerein.replace("-/", "-/\n"); } else if
			 * (strVerein.contains("/")) { strVerein = strVerein.replace("/",
			 * "-\n"); } else if (strVerein.contains("-")) { strVerein =
			 * strVerein.replace("-", "/\n"); } }
			 */

			// strGetAll += stackS.getVerein() + stackS.getSpiele();
			// txtContent.setText(strGetAll);

			int valGroesse =  getApplicationContext().getResources().getDimensionPixelSize(R.dimen.text_size8);
			

			/*
			 * TextView textview1 = new TextView(getApplicationContext());
			 * textview1.setText(strLiga); textview1.setTextSize(valGroesse);
			 */

			// textview1.setTextColor(Color.BLACK);
			TextView textview2 = new TextView(getApplicationContext());
			textview2.setText(strDatum);
			textview2.setTextSize(valGroesse);
			TextView textview3 = new TextView(getApplicationContext());
			textview3.setText(strVereinHeim);
			textview3.setTextSize(valGroesse);
			TextView textview4 = new TextView(getApplicationContext());
			textview4.setText(strVereinGast);
			textview4.setTextSize(valGroesse);

			TextView textview5 = new TextView(getApplicationContext());
			textview5.setText(strSpielstand);
			textview5.setTextSize(valGroesse);
			//textview5.setGravity(Gravity.CENTER);

			// LayoutParams:

			TableRow.LayoutParams llp = new TableRow.LayoutParams(
					android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			llp.setMargins(15, 3, 0, 3); // llp.setMargins(left, top, right,
											// bottom);

			TableRow.LayoutParams llp2 = new TableRow.LayoutParams(
					android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			llp2.setMargins(0, 3, 0, 3); // llp.setMargins(left, top, right,
											// bottom);

			tableRowLetzteWoche.setLayoutParams(llp2);

			// textview1.setLayoutParams(llp2);
			textview2.setLayoutParams(llp);
			textview3.setLayoutParams(llp2);
			textview4.setLayoutParams(llp);
			llp.setMargins(20, 3, 0, 3);
			textview5.setLayoutParams(llp);

			// textview1.setGravity(Gravity.CENTER);

			// textview3.setGravity(Gravity.CENTER);

			// textview1.setTextColor(Color.BLACK);
			textview2.setTextColor(Color.BLACK);
			textview3.setTextColor(Color.BLACK);
			textview4.setTextColor(Color.BLACK);
			textview5.setTextColor(Color.BLACK);

			// textview.getTextColors(R.color.)
			// textview2.setTextColor(Color.BLACK);
			// tableRowLetzteWoche.addView(textview1);
			tableRowLetzteWoche.addView(textview2);
			tableRowLetzteWoche.addView(textview3);
			tableRowLetzteWoche.addView(textview4);
			tableRowLetzteWoche.addView(textview5);

			tableLayoutLetzteWoche.addView(tableRowLetzteWoche);

		}

	}

	void initTable() {
		tableRow = new TableRow(getApplicationContext());

		tableRow.setBackgroundResource(R.color.LightGrey);

		String strPlatz = "Platz";
		String strVerein = "Verein";
		String strSpiele = "Spiele";
		String strSiege = "S";
		String strUnentschieden = "U";
		String strNiederlagen = "N";
		String strTore = "Tore";

		String strPunkte = "Pkt.";

		int valGroesse = getApplicationContext().getResources().getDimensionPixelSize(R.dimen.text_size11);
		
		

		TextView textview1 = new TextView(getApplicationContext());
		textview1.setText(strPlatz);
		textview1.setTextSize(valGroesse);
		textview1.setGravity(Gravity.CENTER);

		// int Platz = Integer.parseInt(strPlatz);
		// if(Platz >20)
		// fail = true;

		// textview1.setTextColor(Color.BLACK);
		TextView textview2 = new TextView(getApplicationContext());
		textview2.setText(strVerein);
		textview2.setTextSize(valGroesse);
		//textview2.setGravity(Gravity.CENTER);
		TextView textview3 = new TextView(getApplicationContext());
		textview3.setText(strSpiele);
		textview3.setTextSize(valGroesse);
		TextView textview4 = new TextView(getApplicationContext());
		textview4.setText(strSiege);
		textview4.setTextSize(valGroesse);

		TextView textview5 = new TextView(getApplicationContext());
		textview5.setText(strUnentschieden);
		textview5.setTextSize(valGroesse);
		TextView textview6 = new TextView(getApplicationContext());
		textview6.setText(strNiederlagen);
		textview6.setTextSize(valGroesse);
		TextView textview7 = new TextView(getApplicationContext());
		textview7.setText(strTore);
		textview7.setGravity(Gravity.CENTER);
		textview7.setTextSize(valGroesse);
		TextView textview8 = new TextView(getApplicationContext());
		textview8.setText(strPunkte);
		textview8.setTextSize(valGroesse);

		// LayoutParams:

		TableRow.LayoutParams llp = new TableRow.LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		llp.setMargins(15, 3, 0, 3); // llp.setMargins(left, top, right,
										// bottom);

		TableRow.LayoutParams llp2 = new TableRow.LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		llp2.setMargins(0, 3, 0, 3); // llp.setMargins(left, top, right,
										// bottom);

		tableRow.setLayoutParams(llp2);
		textview1.setLayoutParams(llp2);
		textview2.setLayoutParams(llp);
		textview3.setLayoutParams(llp);
		textview4.setLayoutParams(llp);
		textview5.setLayoutParams(llp);
		textview6.setLayoutParams(llp);
		textview7.setLayoutParams(llp);
		textview8.setLayoutParams(llp);

		textview1.setTextColor(Color.BLACK);
		textview2.setTextColor(Color.BLACK);
		textview3.setTextColor(Color.BLACK);
		textview4.setTextColor(Color.BLACK);
		textview5.setTextColor(Color.BLACK);
		textview6.setTextColor(Color.BLACK);
		textview7.setTextColor(Color.BLACK);
		textview8.setTextColor(Color.BLACK);

		// textview.getTextColors(R.color.)
		// textview2.setTextColor(Color.BLACK);
		tableRow.addView(textview1);
		tableRow.addView(textview2);
		tableRow.addView(textview3);
		tableRow.addView(textview4);
		tableRow.addView(textview5);
		tableRow.addView(textview6);
		tableRow.addView(textview7);
		tableRow.addView(textview8);

		tableLayout.addView(tableRow);

	}

	void addRows(SitesAdapter mAdapter) {

		for (int i = 0; i < mAdapter.getCount(); i++) {
			StackSite stacks = mAdapter.getItem(i);
			boolean fail = false;

			tableLayout.setStretchAllColumns(true);
			tableLayout.setShrinkAllColumns(true);

			tableRow = new TableRow(getApplicationContext());
			tableRow.setGravity(Gravity.CENTER);
			if (i == 0 || i % 2 == 0)
				tableRow.setBackgroundResource(R.color.White);
			else
				tableRow.setBackgroundResource(R.color.LightGrey);
			// GEHT
			// txtContent.setText(stackS.getVerein());
			String strPlatz = stacks.getPlatz();
			if (strPlatz == null || strPlatz == "")
				strPlatz = "";
			String strVerein = stacks.getVerein();
			if (strVerein.length() > 15) {
				if (strVerein.contains("-/")) {
					strVerein = strVerein.replace("-/", "-/\n");
				} else if (strVerein.contains("/")) {
					strVerein = strVerein.replace("/", "-\n");
				} else if (strVerein.contains("-")) {
					strVerein = strVerein.replace("-", "/\n");
				}
			}

			String strSpiele = stacks.getSpiele();
			String strSiege = stacks.getSiege();
			String strUnentschieden = stacks.getUnentschieden();
			String strNiederlagen = stacks.getNiederlagen();
			String strTore = stacks.getToreGeschossen() + ":"
					+ stacks.getToreErhalten();
			String strPunkte = stacks.getPunktePlus() + ":"
					+ stacks.getPunkteMinus();
			// strGetAll += stackS.getVerein() + stackS.getSpiele();
			// txtContent.setText(strGetAll);

			int valGroesse = getApplicationContext().getResources().getDimensionPixelSize(R.dimen.text_size11);
			
			TextView textview1 = new TextView(getApplicationContext());
			textview1.setText(strPlatz);
			textview1.setTextSize(valGroesse);

			int Platz = Integer.parseInt(strPlatz);
			if (Platz > 20)
				fail = true;

			// textview1.setTextColor(Color.BLACK);
			TextView textview2 = new TextView(getApplicationContext());
			textview2.setText(strVerein);
			textview2.setTextSize(valGroesse);
			//textview2.setGravity(Gravity.CENTER);
			TextView textview3 = new TextView(getApplicationContext());
			textview3.setText(strSpiele);
			textview3.setTextSize(valGroesse);
			TextView textview4 = new TextView(getApplicationContext());
			textview4.setText(strSiege);
			textview4.setTextSize(valGroesse);

			TextView textview5 = new TextView(getApplicationContext());
			textview5.setText(strUnentschieden);
			textview5.setTextSize(valGroesse);
			TextView textview6 = new TextView(getApplicationContext());
			textview6.setText(strNiederlagen);
			textview6.setTextSize(valGroesse);
			TextView textview7 = new TextView(getApplicationContext());
			textview7.setText(strTore);
			textview7.setTextSize(valGroesse);
			TextView textview8 = new TextView(getApplicationContext());
			textview8.setText(strPunkte);
			textview8.setTextSize(valGroesse);

			// LayoutParams:

			TableRow.LayoutParams llp = new TableRow.LayoutParams(
					android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			llp.setMargins(15, 3, 0, 3); // llp.setMargins(left, top, right,
											// bottom);

			TableRow.LayoutParams llp2 = new TableRow.LayoutParams(
					android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			llp2.setMargins(0, 3, 0, 3); // llp.setMargins(left, top, right,
											// bottom);

			tableRow.setLayoutParams(llp2);
			textview1.setLayoutParams(llp2);
			textview2.setLayoutParams(llp);
			textview3.setLayoutParams(llp2);
			textview4.setLayoutParams(llp);
			textview5.setLayoutParams(llp);
			textview6.setLayoutParams(llp);
			textview7.setLayoutParams(llp);
			textview8.setLayoutParams(llp);

			textview1.setGravity(Gravity.CENTER);

			textview3.setGravity(Gravity.CENTER);

			textview1.setTextColor(Color.BLACK);
			textview2.setTextColor(Color.BLACK);
			textview3.setTextColor(Color.BLACK);
			textview4.setTextColor(Color.BLACK);
			textview5.setTextColor(Color.BLACK);
			textview6.setTextColor(Color.BLACK);
			textview7.setTextColor(Color.BLACK);
			textview8.setTextColor(Color.BLACK);

			// textview.getTextColors(R.color.)
			// textview2.setTextColor(Color.BLACK);
			tableRow.addView(textview1);
			tableRow.addView(textview2);
			tableRow.addView(textview3);
			tableRow.addView(textview4);
			tableRow.addView(textview5);
			tableRow.addView(textview6);
			tableRow.addView(textview7);
			tableRow.addView(textview8);
			if (!fail)
				tableLayout.addView(tableRow);

		}

	}

}
