package tsvweilheimapp.globalone.com.tsvweilheim.viewswitcher;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import tsvweilheimapp.globalone.com.tsvweilheim.xmladapter.Downloader;
import tsvweilheimapp.globalone.com.tsvweilheim.xmladapter.SitesAdapter;
import tsvweilheimapp.globalone.com.tsvweilheim.xmladapter.SitesXmlPullParser;
import tsvweilheimapp.globalone.com.tsvweilheim.xmladapter.StackSite;

import java.io.FileNotFoundException;

public class ScoreFragment extends Fragment {

    private static final int TopBottomMargin = 12;

    // Progress Dialog ProgressDialog
    private ProgressDialog pDialog;

    // Gleich Wie MAINXML.java

    private TableLayout tableLayoutLetzteWoche;
    private TableRow tableRowLetzteWoche;

    private TableLayout tableLayout;
    private TableRow tableRow;

    private String UrlLetzteWoche = "";
    private String UrlTabelle = "";

    private SitesAdapter mAdapter;
    private SitesAdapter mAdapterLetzteWoche;
    View rootView;

    static FragmentAdapter m_context;

    public ScoreFragment() {

    }

    public ScoreFragment(FragmentAdapter fmAdapter) {
        m_context = fmAdapter;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("ONCREATEVIEW", "GO");

        rootView = inflater.inflate(R.layout.activity_parse_xml,
                container, false);

        tableLayout = rootView
                .findViewById(R.id.tableLayoutTabelle);
        tableLayoutLetzteWoche = rootView
                .findViewById(R.id.tableLayoutLetzteWoche);

        String receivedMannschaft = getActivity().getIntent().getStringExtra(
                "Mannschaft");

        if (receivedMannschaft.equals("erste")) {
            UrlTabelle = "http://android.handball-weilheim.de/webhandball/hvwcutout.php?site=erste&type=table";
            UrlLetzteWoche = "http://android.handball-weilheim.de/webhandball/hvwcutoutlastweek.php?site=erste&type=table";
        } else if (receivedMannschaft.equals("zweite")) {
            UrlTabelle = "http://android.handball-weilheim.de/webhandball/hvwcutout.php?site=zweite&type=table";
            UrlLetzteWoche = "http://android.handball-weilheim.de/webhandball/hvwcutoutlastweek.php?site=zweite&type=table";
        } else if (receivedMannschaft.equals("damen")) {
            UrlTabelle = "http://android.handball-weilheim.de/webhandball/hvwcutout.php?site=damen&type=table";
            UrlLetzteWoche = "http://android.handball-weilheim.de/webhandball/hvwcutoutlastweek.php?site=damen&type=table";
        } else if (receivedMannschaft.equals("damen2")) {
            UrlTabelle = "http://android.handball-weilheim.de/webhandball/hvwcutout.php?site=damen2&type=table";
            UrlLetzteWoche = "http://android.handball-weilheim.de/webhandball/hvwcutoutlastweek.php?site=damen2&type=table";
        } else if (receivedMannschaft.equals("js")) {
            UrlTabelle = "http://android.handball-weilheim.de/webhandball/hvwcutout.php?site=js&type=table";
            UrlLetzteWoche = "http://android.handball-weilheim.de/webhandball/hvwcutoutlastweek.php?site=js&type=table";
        } else if (receivedMannschaft.equals("ad")) {
            UrlTabelle = "http://android.handball-weilheim.de/webhandball/hvwcutout.php?site=ad&type=table";
            UrlLetzteWoche = "http://android.handball-weilheim.de/webhandball/hvwcutoutlastweek.php?site=ad&type=table";
        }

        if (m_context.getM_item() == 1) {
            if (tableLayout.getChildCount() == 0 || tableLayoutLetzteWoche.getChildCount() == 0) {
                SitesDownloadTask download = new SitesDownloadTask();
                download.execute();
            }
        }

        return rootView;
    }

    public boolean isNetworkAvailable() {
        // (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        // TODO Auto-generated method stub
        super.setMenuVisibility(menuVisible);

        /*
         * If network is available download the xml from the Internet. If not
         * then try to use the local file from last time.
         */
        if (menuVisible && rootView != null && m_context.getM_item() == 1) {


            if (isNetworkAvailable()) {
                Log.i("StackSites", "starting download Task");

                if (tableLayout.getChildCount() == 0 || tableLayoutLetzteWoche.getChildCount() == 0) {
                    SitesDownloadTask download = new SitesDownloadTask();
                    download.execute();
                }
                Log.i("StackSites", "ended download Task");
            } else {

                showMessage();
                mAdapter = new SitesAdapter(getActivity()
                        .getApplicationContext(), -1,
                        SitesXmlPullParser.getStackSitesFromFile(getActivity(),
                                0));

                mAdapterLetzteWoche = new SitesAdapter(getActivity()
                        .getApplicationContext(), -1,
                        SitesXmlPullParser.getStackSitesFromFile(getActivity(),
                                1));
            }
        }
    }

    void showMessage() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Keine Internetverbindung!")
                .setMessage(
                        "Bitte überprüfen Sie Ihre Internetverbindung.")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        }).show();
    }

    public class SitesDownloadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = ProgressDialog.show(getActivity(), "Lade Tabelle...",
//                    "Bitte warten...", true);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Download the file
            try {
                Downloader
                        .DownloadFromUrl(
                                UrlTabelle,
                                getActivity().openFileOutput("StackSites.xml",
                                        Context.MODE_PRIVATE));

                Downloader.DownloadFromUrl(
                        UrlLetzteWoche,
                        getActivity().openFileOutput(
                                "StackSitesLetzteWoche.xml",
                                Context.MODE_PRIVATE));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
//                pDialog.dismiss();
                // setup our Adapter and set it to the ListView.
                mAdapter = new SitesAdapter(getActivity()
                        .getApplicationContext(), -1,
                        SitesXmlPullParser.getStackSitesFromFile(getActivity(),
                                0));

                mAdapterLetzteWoche = new SitesAdapter(getActivity()
                        .getApplicationContext(), -1,
                        SitesXmlPullParser.getStackSitesFromFile(getActivity(),
                                1));

                LinearLayout lay1 = getActivity().findViewById(
                        R.id.linearLayoutTab1);
                LinearLayout lay2 = getActivity().findViewById(
                        R.id.linearLayoutTab2);
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

            } catch (Exception PostException) {

            }
        }
    }

    void addFirstRow(int laufvar) {
        String receivedMannschaft = getActivity().getIntent().getStringExtra(
                "Mannschaft");
        String text = "";
        int textGroesse = getActivity().getApplicationContext().getResources()
                .getDimensionPixelSize(R.dimen.text_header);

        if (laufvar == 0) {
            if (receivedMannschaft.equals("erste")) {
                text = "Spielergebnisse Herren 1";
            } else if (receivedMannschaft.equals("zweite")) {
                text = "Spielergebnisse Herren 2";
            } else if (receivedMannschaft.equals("damen")) {
                text = "Spielergebnisse Damen 1";
            } else if (receivedMannschaft.equals("damen2")) {
                text = "Spielergebnisse Damen 2";
            } else if (receivedMannschaft.equals("js")) {
                text = "Spielergebnisse Jungsenioren";
            } else if (receivedMannschaft.equals("ad")) {
                text = "Spielergebnisse Attraktive Damen";
            }

            TextView textview2 = getActivity().findViewById(
                    R.id.txtErgebnisOverTable);

            TableRow.LayoutParams llp2 = new TableRow.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
            llp2.setMargins(0, 15, 0, 15); // llp.setMargins(left, top, right,
            // bottom);

            textview2.setLayoutParams(llp2);
            textview2.setGravity(Gravity.CENTER);
            textview2.setText(text);
            textview2.setTextSize(textGroesse);
            // textview2.setBackgroundResource(R.color.LightGrey);

        } else if (laufvar == 1) {

            TableRow.LayoutParams llp = new TableRow.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
            if (receivedMannschaft.equals("erste")) {
                text = "Aktueller Tabellenstand Herren 1";
            } else if (receivedMannschaft.equals("zweite")) {
                text = "Aktueller Tabellenstand Herren 2";
            } else if (receivedMannschaft.equals("damen")) {
                text = "Aktueller Tabellenstand Damen 1";
            } else if (receivedMannschaft.equals("damen2")) {
                text = "Aktueller Tabellenstand Damen 2";
            } else if (receivedMannschaft.equals("js")) {
                text = "Aktueller Tabellenstand Jungsenioren";
            } else if (receivedMannschaft.equals("ad")) {
                text = "Aktueller Tabellenstand Attraktive Damen";
            }

            TextView textview3 = getActivity().findViewById(
                    R.id.txtTabelleOverTable);

            TableRow.LayoutParams llp2 = new TableRow.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
            llp2.setMargins(0, 15, 0, 15); // llp.setMargins(left, top, right,
            // bottom);
            textview3.setGravity(Gravity.CENTER);
            textview3.setLayoutParams(llp2);

            textview3.setText(text);
            textview3.setTextSize(textGroesse);
        }
    }

    void initTableLetzteWoche() {
        Log.v("initTableLetzteWoche", "Beginn");
        tableRowLetzteWoche = new TableRow(getActivity()
                .getApplicationContext());
        Log.v("initTableLetzteWoche", "setBackground:");

        tableRowLetzteWoche.setBackgroundResource(R.color.LightGrey);

        Log.v("initTableLetzteWoche", "Background gesetzt");
        String strLiga = "Liga";
        String strDatum = "Datum";
        strDatum = strDatum.replace(",", "\n");

        String strVereinHeim = "Verein Heim";
        String strVereinGast = "Verein Gast";
        String strSpielstand = "Spielstand";

        int valGroesse = getActivity().getApplicationContext().getResources()
                .getDimensionPixelSize(R.dimen.text_size8);

        // textview1.setTextColor(Color.BLACK);
        TextView textview2 = new TextView(getActivity().getApplicationContext());
        textview2.setText(strDatum);
        textview2.setTextSize(valGroesse);
        TextView textview3 = new TextView(getActivity().getApplicationContext());
        textview3.setText(strVereinHeim);
        textview3.setTextSize(valGroesse);

        TextView textview4 = new TextView(getActivity().getApplicationContext());
        textview4.setText(strVereinGast);
        textview4.setTextSize(valGroesse);

        TextView textview5 = new TextView(getActivity().getApplicationContext());
        textview5.setText(strSpielstand);
        textview5.setTextSize(valGroesse);

        // LayoutParams:

        TableRow.LayoutParams llp = new TableRow.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        llp.setMargins(0, TopBottomMargin, 0, TopBottomMargin); // llp.setMargins(left,
        // top, right, bottom);

        TableRow.LayoutParams llp2 = new TableRow.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        llp2.setMargins(3, TopBottomMargin, 0, TopBottomMargin); // bottom);
        tableRowLetzteWoche.setLayoutParams(llp2);

        textview2.setLayoutParams(llp2);
        textview3.setLayoutParams(llp);
        textview4.setLayoutParams(llp);
        textview5.setLayoutParams(llp);
        textview5.setGravity(Gravity.CENTER);

        textview2.setTextColor(Color.BLACK);
        textview3.setTextColor(Color.BLACK);
        textview4.setTextColor(Color.BLACK);
        textview5.setTextColor(Color.BLACK);

        tableRowLetzteWoche.addView(textview2);
        tableRowLetzteWoche.addView(textview3);
        tableRowLetzteWoche.addView(textview4);
        tableRowLetzteWoche.addView(textview5);

        // Fehler
        tableLayoutLetzteWoche.addView(tableRowLetzteWoche);

    }

    void addRowsLetzteWoche(SitesAdapter mAdapter) {

        for (int i = 0; i < mAdapter.getCount(); i++) {
            StackSite stacks = mAdapter.getItem(i);
            boolean fail = false;

            tableLayout.setStretchAllColumns(true);
            tableLayout.setShrinkAllColumns(true);

            tableRowLetzteWoche = new TableRow(getActivity()
                    .getApplicationContext());
            tableRowLetzteWoche.setGravity(Gravity.CENTER);
            if (i == 0 || i % 2 == 0)
                tableRowLetzteWoche.setBackgroundResource(R.color.White);
            else
                tableRowLetzteWoche.setBackgroundResource(R.color.LightGrey);
            // GEHT
            // txtContent.setText(stackS.getVerein());

            String strLiga = stacks.getLiga();
            String strDatum = stacks.getDatum();
            strDatum = strDatum.replace(", ", "\n");
            strDatum = strDatum.replaceFirst("\n", ", ");
            strDatum = strDatum.trim();
            String strVereinHeim = stacks.getVereinHeim();
            String strVereinGast = stacks.getVereinGast();
            String strSpielstand = stacks.getToreHeim() + ":"
                    + stacks.getToreGast();

            int valGroesse = getActivity().getApplicationContext()
                    .getResources().getDimensionPixelSize(R.dimen.text_size8);

            TextView textview2 = new TextView(getActivity()
                    .getApplicationContext());
            textview2.setText(strDatum);
            textview2.setTextSize(valGroesse);
            TextView textview3 = new TextView(getActivity()
                    .getApplicationContext());
            textview3.setText(strVereinHeim);
            textview3.setTextSize(valGroesse);
            TextView textview4 = new TextView(getActivity()
                    .getApplicationContext());
            textview4.setText(strVereinGast);
            textview4.setTextSize(valGroesse);

            TextView textview5 = new TextView(getActivity()
                    .getApplicationContext());
            textview5.setText(strSpielstand);
            textview5.setTextSize(valGroesse);

            // LayoutParams:

            TableRow.LayoutParams llp = new TableRow.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
            llp.setMargins(0, TopBottomMargin, 0, TopBottomMargin); // llp.setMargins(left,
            // top,
            // right,
            // bottom);

            TableRow.LayoutParams llp2 = new TableRow.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
            llp2.setMargins(5, TopBottomMargin, 0, TopBottomMargin); // bottom);
            tableRowLetzteWoche.setLayoutParams(llp2);

            textview2.setLayoutParams(llp2);
            textview3.setLayoutParams(llp);
            textview4.setLayoutParams(llp);
            textview5.setLayoutParams(llp);
            textview5.setGravity(Gravity.CENTER);

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

    }

    void initTable() {
        tableRow = new TableRow(getActivity().getApplicationContext());

        tableRow.setBackgroundResource(R.color.LightGrey);

        String strPlatz = "Platz";
        String strVerein = "Verein";
        String strSpiele = "Spiele";
        String strSiege = "S:U:N";

        String strTore = "Tore";

        String strPunkte = "Pkt.";

        int valGroesse = getActivity().getApplicationContext().getResources()
                .getDimensionPixelSize(R.dimen.text_size11);

        TextView textview1 = new TextView(getActivity().getApplicationContext());
        textview1.setText(strPlatz);
        textview1.setTextSize(valGroesse);
        textview1.setGravity(Gravity.CENTER);

        TextView textview2 = new TextView(getActivity().getApplicationContext());
        textview2.setText(strVerein);
        textview2.setTextSize(valGroesse);
        TextView textview3 = new TextView(getActivity().getApplicationContext());
        textview3.setText(strSpiele);
        textview3.setTextSize(valGroesse);
        TextView textview4 = new TextView(getActivity().getApplicationContext());
        textview4.setText(strSiege);
        textview4.setTextSize(valGroesse);

        TextView textview7 = new TextView(getActivity().getApplicationContext());
        textview7.setText(strTore);
        textview7.setGravity(Gravity.CENTER);
        textview7.setTextSize(valGroesse);
        TextView textview8 = new TextView(getActivity().getApplicationContext());
        textview8.setText(strPunkte);
        textview8.setTextSize(valGroesse);

        // LayoutParams:

        TableRow.LayoutParams llp = new TableRow.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        llp.setMargins(0, TopBottomMargin, 0, TopBottomMargin);

        TableRow.LayoutParams llp2 = new TableRow.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        llp2.setMargins(3, TopBottomMargin, 0, TopBottomMargin); // llp.setMargins(left,
        // top,
        // right,
        // bottom);

        tableRow.setLayoutParams(llp2);
        textview1.setLayoutParams(llp);
        textview2.setLayoutParams(llp);
        textview3.setLayoutParams(llp);
        textview4.setLayoutParams(llp2);

        textview7.setLayoutParams(llp);
        textview8.setLayoutParams(llp);

        textview1.setGravity(Gravity.CENTER);
        textview3.setGravity(Gravity.CENTER);
        textview7.setGravity(Gravity.CENTER);
        textview8.setGravity(Gravity.CENTER);

        textview1.setTextColor(Color.BLACK);
        textview2.setTextColor(Color.BLACK);
        textview3.setTextColor(Color.BLACK);
        textview4.setTextColor(Color.BLACK);

        textview7.setTextColor(Color.BLACK);
        textview8.setTextColor(Color.BLACK);

        tableRow.addView(textview1);
        tableRow.addView(textview2);
        tableRow.addView(textview3);
        tableRow.addView(textview4);

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

            tableRow = new TableRow(getActivity().getApplicationContext());
            tableRow.setGravity(Gravity.CENTER);
            if (i == 0 || i % 2 == 0)
                tableRow.setBackgroundResource(R.color.White);
            else
                tableRow.setBackgroundResource(R.color.LightGrey);

            String strPlatz = stacks.getPlatz();
            if (strPlatz == null || strPlatz == " ") {
                strPlatz = " ";
            }
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
            String strSiege = stacks.getSiege() + ":" + stacks.getUnentschieden() + ":" + stacks.getNiederlagen();

            String strTore = stacks.getToreGeschossen() + ":"
                    + stacks.getToreErhalten();
            String strPunkte = stacks.getPunktePlus() + ":"
                    + stacks.getPunkteMinus();

            int valGroesse = getActivity().getApplicationContext()
                    .getResources().getDimensionPixelSize(R.dimen.text_size11);

            TextView textview1 = new TextView(getActivity()
                    .getApplicationContext());
            textview1.setText(strPlatz);
            textview1.setTextSize(valGroesse);

            try {
                int Platz = Integer.parseInt(strPlatz);
                if (Platz > 20)
                    fail = true;
            } catch (Exception ex2123) {
                fail = false;
            }
            TextView textview2 = new TextView(getActivity()
                    .getApplicationContext());
            textview2.setText(strVerein);
            textview2.setTextSize(valGroesse);
            TextView textview3 = new TextView(getActivity()
                    .getApplicationContext());
            textview3.setText(strSpiele);
            textview3.setTextSize(valGroesse);
            TextView textview4 = new TextView(getActivity()
                    .getApplicationContext());
            textview4.setText(strSiege);
            textview4.setTextSize(valGroesse);


            TextView textview7 = new TextView(getActivity()
                    .getApplicationContext());
            textview7.setText(strTore);
            textview7.setTextSize(valGroesse);
            TextView textview8 = new TextView(getActivity()
                    .getApplicationContext());
            textview8.setText(strPunkte);
            textview8.setTextSize(valGroesse);

            // LayoutParams:

            TableRow.LayoutParams llp = new TableRow.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
            llp.setMargins(0, TopBottomMargin, 0, TopBottomMargin); // llp.setMargins(left,
            // top,
            // right,
            // bottom);

            TableRow.LayoutParams llp2 = new TableRow.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
            llp2.setMargins(3, TopBottomMargin, 0, TopBottomMargin); // llp.setMargins(left,
            // top,
            // right,
            // bottom);

            tableRow.setLayoutParams(llp2);
            textview1.setLayoutParams(llp);
            textview2.setLayoutParams(llp);
            textview3.setLayoutParams(llp);
            textview4.setLayoutParams(llp2);

            textview7.setLayoutParams(llp);
            textview8.setLayoutParams(llp);

            textview1.setGravity(Gravity.CENTER);
            textview3.setGravity(Gravity.CENTER);
            textview7.setGravity(Gravity.CENTER);
            textview8.setGravity(Gravity.CENTER);

            textview1.setTextColor(Color.BLACK);
            textview2.setTextColor(Color.BLACK);
            textview3.setTextColor(Color.BLACK);
            textview4.setTextColor(Color.BLACK);

            textview7.setTextColor(Color.BLACK);
            textview8.setTextColor(Color.BLACK);

            tableRow.addView(textview1);
            tableRow.addView(textview2);
            tableRow.addView(textview3);
            tableRow.addView(textview4);

            tableRow.addView(textview7);
            tableRow.addView(textview8);
            if (!fail)
                tableLayout.addView(tableRow);

        }

    }

}
