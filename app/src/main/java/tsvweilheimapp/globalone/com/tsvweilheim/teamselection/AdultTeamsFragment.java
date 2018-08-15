package tsvweilheimapp.globalone.com.tsvweilheim.teamselection;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import tsvweilheimapp.globalone.com.tsvweilheim.R;
import tsvweilheimapp.globalone.com.tsvweilheim.viewswitcher.MainSwitcherActivity;

public class AdultTeamsFragment extends Fragment implements View.OnClickListener {

    Button btnH1, btnH2, btnD1, btnD2, btnJS, btnAD;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_teams, container, false);

        btnH1 = rootView.findViewById(R.id.btnHerren1);
        btnH2 = rootView.findViewById(R.id.btnHerren2);
        btnD1 = rootView.findViewById(R.id.btnDamen1);
        btnD2 = rootView.findViewById(R.id.btnDamen2);
        btnJS = rootView.findViewById(R.id.btnJS);
        btnAD = rootView.findViewById(R.id.btnAD);

        btnH1.setOnClickListener(this);
        btnH2.setOnClickListener(this);
        btnD1.setOnClickListener(this);
        btnD2.setOnClickListener(this);
        btnJS.setOnClickListener(this);
        btnAD.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnHerren1:
                Intent i1 = new Intent(rootView.getContext(), MainSwitcherActivity.class);
                i1.putExtra("Mannschaft", "erste");
                startActivity(i1);
                break;
            case R.id.btnHerren2:
                Intent i2 = new Intent(rootView.getContext(), MainSwitcherActivity.class);
                i2.putExtra("Mannschaft", "zweite");
                startActivity(i2);
                break;
            case R.id.btnDamen1:
                Intent i3 = new Intent(rootView.getContext(), MainSwitcherActivity.class);
                i3.putExtra("Mannschaft", "damen");
                startActivity(i3);
                break;
            case R.id.btnDamen2:
                Intent i4 = new Intent(rootView.getContext(), MainSwitcherActivity.class);
                i4.putExtra("Mannschaft", "damen2");
                startActivity(i4);
                break;

            case R.id.btnJS:
                Intent i5 = new Intent(rootView.getContext(), MainSwitcherActivity.class);
                i5.putExtra("Mannschaft", "js");
                startActivity(i5);
                break;
            case R.id.btnAD:
                Intent i6 = new Intent(rootView.getContext(), MainSwitcherActivity.class);
                i6.putExtra("Mannschaft", "ad");
                startActivity(i6);
                break;

            default:
                break;
        }
    }
}
