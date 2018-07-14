package com.globalone.tsvweilheimapp.viewswitcher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalone.tsvweilheimapp.R;


//TEAMVIEW
public class Fragment3 extends Fragment {
	ImageView imageView;
	TextView txtBericht;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment3, container, false);

		imageView = (ImageView) rootView.findViewById(R.id.imageTeam);
		txtBericht = (TextView) rootView.findViewById(R.id.txtTeamBericht);

		String receivedMannschaft = getActivity().getIntent().getStringExtra(
				"Mannschaft");
		// imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.WRAP_CONTENT));
	


		if (receivedMannschaft.equals("erste")) {
			imageView.setBackgroundResource(R.drawable.herren1);
			txtBericht.setText(R.string.herren1text);

		} else if (receivedMannschaft.equals("zweite")) {
			imageView.setBackgroundResource(R.drawable.herren2);
			txtBericht.setText(R.string.herren2text);

		} else if (receivedMannschaft.equals("damen")) {
			imageView.setBackgroundResource(R.drawable.damen1);
			txtBericht.setText(R.string.damen1text);
		} else if (receivedMannschaft.equals("damen2")) {
			imageView.setBackgroundResource(R.drawable.damen2);
			txtBericht.setText(R.string.damen2text);
		}else if (receivedMannschaft.equals("js")) {
			imageView.setBackgroundResource(R.drawable.js);
			txtBericht.setText(R.string.jstext);
		}else if (receivedMannschaft.equals("ad")) {
			imageView.setBackgroundResource(R.drawable.ad);
			txtBericht.setText(R.string.adtext);
		}

		return rootView;
	}
}