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

        webView.loadUrl(baseUrl + receivedMannschaft);
        return rootView;
    }
}