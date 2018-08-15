package tsvweilheimapp.globalone.com.tsvweilheim.viewswitcher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import tsvweilheimapp.globalone.com.tsvweilheim.R;


public class TeamFragment extends Fragment {
    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment3, container, false);
        webView = rootView.findViewById(R.id.webView);
        String receivedMannschaft = getActivity().getIntent().getStringExtra(
                "Mannschaft");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String baseUrl = "http://android.handball-weilheim.de/webhandball/team_webview.php?site=";

        if (receivedMannschaft.equals("erste")) {
            webView.loadUrl(baseUrl + "erste");
        } else if (receivedMannschaft.equals("zweite")) {
            webView.loadUrl(baseUrl + "zweite");
        } else if (receivedMannschaft.equals("damen")) {
            webView.loadUrl(baseUrl + "damen");
        } else if (receivedMannschaft.equals("damen2")) {
            webView.loadUrl(baseUrl + "damen2");
        } else if (receivedMannschaft.equals("js")) {
            webView.loadUrl(baseUrl + "js");
        } else if (receivedMannschaft.equals("ad")) {
            webView.loadUrl(baseUrl + "ad");
        }

        return rootView;
    }
}