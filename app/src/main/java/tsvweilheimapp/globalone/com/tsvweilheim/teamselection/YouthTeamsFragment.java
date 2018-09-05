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

public class YouthTeamsFragment extends Fragment implements View.OnClickListener {

    Button btnAM, btnAW, btnBM, btnBW, btnCM, btnCW, btnDM, btnDW;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_teams_youth, container, false);

        btnAM = rootView.findViewById(R.id.btn00);
        btnAW = rootView.findViewById(R.id.btn01);
        btnBM = rootView.findViewById(R.id.btn10);
        btnBW = rootView.findViewById(R.id.btn11);
        btnCM = rootView.findViewById(R.id.btn20);
        btnCW = rootView.findViewById(R.id.btn21);
        btnDM = rootView.findViewById(R.id.btn30);
        btnDW = rootView.findViewById(R.id.btn31);

        btnAM.setOnClickListener(this);
        btnAW.setOnClickListener(this);
        btnBM.setOnClickListener(this);
        btnBW.setOnClickListener(this);
        btnCM.setOnClickListener(this);
        btnCW.setOnClickListener(this);
        btnDM.setOnClickListener(this);
        btnDW.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn00:
                Intent i1 = new Intent(rootView.getContext(), MainSwitcherActivity.class);
                i1.putExtra("Mannschaft", "a_maennlich");
                startActivity(i1);
                break;
            case R.id.btn01:
                Intent i2 = new Intent(rootView.getContext(), MainSwitcherActivity.class);
                i2.putExtra("Mannschaft", "a_weiblich");
                startActivity(i2);
                break;
            case R.id.btn10:
                Intent i3 = new Intent(rootView.getContext(), MainSwitcherActivity.class);
                i3.putExtra("Mannschaft", "b_maennlich");
                startActivity(i3);
                break;
            case R.id.btn11:
                Intent i4 = new Intent(rootView.getContext(), MainSwitcherActivity.class);
                i4.putExtra("Mannschaft", "b_weiblich");
                startActivity(i4);
                break;

            case R.id.btn20:
                Intent i5 = new Intent(rootView.getContext(), MainSwitcherActivity.class);
                i5.putExtra("Mannschaft", "c_maennlich");
                startActivity(i5);
                break;
            case R.id.btn21:
                Intent i6 = new Intent(rootView.getContext(), MainSwitcherActivity.class);
                i6.putExtra("Mannschaft", "c_weiblich");
                startActivity(i6);
                break;
            case R.id.btn30:
                Intent i7 = new Intent(rootView.getContext(), MainSwitcherActivity.class);
                i7.putExtra("Mannschaft", "d_maennlich");
                startActivity(i7);
                break;
            case R.id.btn31:
                Intent i8 = new Intent(rootView.getContext(), MainSwitcherActivity.class);
                i8.putExtra("Mannschaft", "d_weiblich");
                startActivity(i8);
                break;
            default:
                break;
        }
    }
}
