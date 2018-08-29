package tsvweilheimapp.globalone.com.tsvweilheim.viewswitcher;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import tsvweilheimapp.globalone.com.tsvweilheim.R;
import tsvweilheimapp.globalone.com.tsvweilheim.dialog_adapter.DialogHandler;
import tsvweilheimapp.globalone.com.tsvweilheim.xmladapter.Downloader;
import tsvweilheimapp.globalone.com.tsvweilheim.xmladapter.Spielplan;
import tsvweilheimapp.globalone.com.tsvweilheim.xmladapter.SpielplanAdapter;
import tsvweilheimapp.globalone.com.tsvweilheim.xmladapter.SpielplanPullParser;

import java.io.FileNotFoundException;

public class ScheduleFragment extends Fragment {
    private static final int TopBottomMargin = 32;
    private TableLayout tableLayoutLetzteWoche;
    private TableRow tableRowLetzteWoche;
    static private SpielplanAdapter mAdapter;
    String SpielplanURL = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_spielerg, container, false);
        tableLayoutLetzteWoche = rootView.findViewById(R.id.tableSpielergebnis);
        String receivedMannschaft = getActivity().getIntent().getStringExtra("Mannschaft");

        SpielplanURL = "http://android.handball-weilheim.de/webhandball/spielplancutout.php?site=" + receivedMannschaft + "&type=table";

        /*
         * If network is available download the xml from the Internet. If not
         * then try to use the local file from last time.
         */
        Log.v("Log:", "Beginn isNetworkAvailable");

        if (isNetworkAvailable()) {
            Log.i("StackSites", "starting download Task");
            SpielplanDownloadTask download = new SpielplanDownloadTask();
            download.execute();

        } else {
            mAdapter = new SpielplanAdapter(getActivity().getApplicationContext(), -1,
                    SpielplanPullParser.getSpielplanFromFile(getActivity()));
        }

        return rootView;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public class SpielplanDownloadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            // Download the file
            try {

                // strURL=getStrURL();
                Downloader.DownloadFromUrl(SpielplanURL,
                        getActivity().openFileOutput("Spielplan.xml", Context.MODE_PRIVATE));


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // setup our Adapter and set it to the ListView.
            mAdapter = new SpielplanAdapter(getActivity().getApplicationContext(), -1,
                    SpielplanPullParser.getSpielplanFromFile(getActivity()));


            LinearLayout lay1 = getActivity().findViewById(R.id.linearLayoutTabSpielplan);
            lay1.setVisibility(View.VISIBLE);

            // FUER SPIELPLAN
            addFirstRow(0);
            initTableLetzteWoche();
            addRowsLetzteWoche(mAdapter);
        }
    }

    void addFirstRow(int laufvar) {
        String receivedMannschaft = getActivity().getIntent().getStringExtra("Mannschaft");
        String text = "";
        int textGroesse = getActivity().getApplicationContext().getResources().getDimensionPixelSize(R.dimen.text_header);


        if (laufvar == 0) {
            if (receivedMannschaft.equals("erste")) {
                text = "Spielplan Herren 1";
            } else if (receivedMannschaft.equals("zweite")) {
                text = "Spielplan Herren 2";
            } else if (receivedMannschaft.equals("damen")) {
                text = "Spielplan Damen 1";
            } else if (receivedMannschaft.equals("damen2")) {
                text = "Spielplan Damen 2";
            } else if (receivedMannschaft.equals("js")) {
                text = "Spielplan Jungsenioren";
            } else if (receivedMannschaft.equals("ad")) {
                text = "Spielplan Attraktive Damen";
            } else if (receivedMannschaft.equals("a_maennlich")) {
                text = "Spielplan A Jugend Männlich";
            } else if (receivedMannschaft.equals("a_weiblich")) {
                text = "Spielplan A Jugend Weiblich";
            } else if (receivedMannschaft.equals("b_maennlich")) {
                text = "Spielplan B Jugend Männlich";
            } else if (receivedMannschaft.equals("b_weiblich")) {
                text = "Spielplan B Jugend Weiblich";
            } else if (receivedMannschaft.equals("c_maennlich")) {
                text = "Spielplan C Jugend Männlich";
            } else if (receivedMannschaft.equals("c_weiblich")) {
                text = "Spielplan C Jugend Weiblich";
            }


            TextView textview2 = getActivity().findViewById(R.id.txtSpielergebnis);

            TableRow.LayoutParams llp2 = new TableRow.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
            llp2.setMargins(0, 15, 0, 15); // llp.setMargins(left, top, right,
            // bottom);
            textview2.setGravity(Gravity.CENTER);
            textview2.setLayoutParams(llp2);

            textview2.setText(text);
            textview2.setTextSize(textGroesse);
        } else {
            text = "Sorry Fehler :]";
        }


    }

    void initTableLetzteWoche() {
        tableRowLetzteWoche = new TableRow(getActivity().getApplicationContext());
        tableRowLetzteWoche.setBackgroundResource(R.color.LightGrey);

        String strLiga = "Liga";
        String strDatum = "Datum";
        strDatum = strDatum.replace(",", "\n");
        String strUhrzeit = "Uhrzeit";
        String strVereinHeim = "Verein Heim";
        String strVereinGast = "Verein Gast";
        int valGroesse = getActivity().getApplicationContext().getResources().getDimensionPixelSize(R.dimen.text_size8);

        TextView textview2 = new TextView(getActivity().getApplicationContext());
        textview2.setText(strDatum);
        textview2.setTextSize(valGroesse);
        TextView textview3 = new TextView(getActivity().getApplicationContext());
        textview3.setText(strUhrzeit);
        textview3.setTextSize(valGroesse);

        TextView textview4 = new TextView(getActivity().getApplicationContext());
        textview4.setText(strVereinHeim);
        textview4.setTextSize(valGroesse);

        TextView textview5 = new TextView(getActivity().getApplicationContext());
        textview5.setText(strVereinGast);
        textview5.setTextSize(valGroesse);

        // LayoutParams:

        TableRow.LayoutParams llp = new TableRow.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        llp.setMargins(15, TopBottomMargin, 0, TopBottomMargin); // llp.setMargins(left, top, right,
        // bottom);
        TableRow.LayoutParams llp2 = new TableRow.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        llp2.setMargins(0, TopBottomMargin, 0, TopBottomMargin);        // bottom);

        tableRowLetzteWoche.setLayoutParams(llp2);

        textview2.setLayoutParams(llp);
        textview3.setLayoutParams(llp);
        textview4.setLayoutParams(llp2);
        textview5.setLayoutParams(llp);


        textview2.setTextColor(Color.BLACK);
        textview3.setTextColor(Color.BLACK);
        textview4.setTextColor(Color.BLACK);
        textview5.setTextColor(Color.BLACK);

        tableRowLetzteWoche.addView(textview2);
        tableRowLetzteWoche.addView(textview3);
        tableRowLetzteWoche.addView(textview4);
        tableRowLetzteWoche.addView(textview5);
        tableLayoutLetzteWoche.addView(tableRowLetzteWoche);
    }

    String splitString(String str) {
        if (str.length() > 15) {
            if (str.contains("-/")) {
                str = str.replace("-/", "-/\n");
            } else if (str.contains("/")) {
                str = str.replace("/", "-\n");
            } else if (str.contains("-")) {
                str = str.replace("-", "/\n");
            }
        }
        return str;
    }


    void addRowsLetzteWoche(SpielplanAdapter mAdapter) {

        for (int i = 0; i < mAdapter.getCount(); i++) {
            Spielplan stacks = mAdapter.getItem(i);
            boolean fail = false;

            tableRowLetzteWoche = new TableRow(getActivity().getApplicationContext());
            tableRowLetzteWoche.setGravity(Gravity.CENTER);
            if (i == 0 || i % 2 == 0)
                tableRowLetzteWoche.setBackgroundResource(R.color.White);
            else
                tableRowLetzteWoche.setBackgroundResource(R.color.LightGrey);

            String strDatum = stacks.getDatum();
            String strUhrzeit = stacks.getUhrzeit();
            String strVereinHeim = stacks.getHeim();
            String strVereinGast = stacks.getGast();

            strVereinHeim = splitString(strVereinHeim);
            strVereinGast = splitString(strVereinGast);

            int valGroesse = getActivity().getApplicationContext().getResources().getDimensionPixelSize(R.dimen.text_size8);

            TextView textview1 = new TextView(getActivity().getApplicationContext());
            textview1.setText(strDatum);
            textview1.setTextSize(valGroesse);

            TextView textview2 = new TextView(getActivity().getApplicationContext());
            textview2.setText(strUhrzeit);
            textview2.setTextSize(valGroesse);
            TextView textview3 = new TextView(getActivity().getApplicationContext());
            textview3.setText(strVereinHeim);
            textview3.setTextSize(valGroesse);
            TextView textview4 = new TextView(getActivity().getApplicationContext());
            textview4.setText(strVereinGast);
            textview4.setTextSize(valGroesse);

            // LayoutParams:

            TableRow.LayoutParams llp = new TableRow.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
            llp.setMargins(15, TopBottomMargin, 4, TopBottomMargin); // llp.setMargins(left, top, right,
            // bottom);

            TableRow.LayoutParams llp2 = new TableRow.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
            llp2.setMargins(0, TopBottomMargin, 0, TopBottomMargin); // llp.setMargins(left, top, right,
            // bottom);

            tableRowLetzteWoche.setLayoutParams(llp2);

            textview1.setLayoutParams(llp);
            textview2.setLayoutParams(llp);
            textview3.setLayoutParams(llp2);
            textview4.setLayoutParams(llp);

            textview1.setTextColor(Color.BLACK);
            textview2.setTextColor(Color.BLACK);
            textview3.setTextColor(Color.BLACK);
            textview4.setTextColor(Color.BLACK);

            tableRowLetzteWoche.addView(textview1);
            tableRowLetzteWoche.addView(textview2);
            tableRowLetzteWoche.addView(textview3);
            tableRowLetzteWoche.addView(textview4);

            tableLayoutLetzteWoche.addView(tableRowLetzteWoche);

        }
    }

}
