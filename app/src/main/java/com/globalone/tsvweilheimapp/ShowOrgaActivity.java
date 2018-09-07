package com.globalone.tsvweilheimapp;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowOrgaActivity extends Activity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_orga);
        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String receivedParameter = getIntent().getStringExtra("Parameter");
        String baseUrl = "http://android.handball-weilheim.de/webhandball/team_webview.php?site=";

        if (receivedParameter.equals("vorstand")) {
            webView.loadUrl(baseUrl + "vs");
            this.getActionBar().setTitle("TSV Weilheim - Vorstand");
        } else if (receivedParameter.equals("jugendleitung")) {
            webView.loadUrl(baseUrl + "jl");
            this.getActionBar().setTitle("TSV Weilheim - Jugendleitung");
        } else if (receivedParameter.equals("wirtschaftsausschuss")) {
            webView.loadUrl(baseUrl + "wa");
            this.getActionBar().setTitle("TSV Weilheim - Wirtschaftsasschuss");
        } else if (receivedParameter.equals("foerderverein")) {
            webView.loadUrl(baseUrl + "fv");
            this.getActionBar().setTitle("TSV Weilheim - F�rderverein");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
