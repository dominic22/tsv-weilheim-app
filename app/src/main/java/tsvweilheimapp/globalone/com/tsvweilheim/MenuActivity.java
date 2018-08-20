package tsvweilheimapp.globalone.com.tsvweilheim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import tsvweilheimapp.globalone.com.tsvweilheim.teamselection.TeamSelectionActivity;

public class MenuActivity extends Activity implements OnClickListener{

	Button btnNews,btnTeams,btnImpressum,btnOrga;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		btnNews = findViewById(R.id.btnNews);
		btnTeams = findViewById(R.id.btnTeams);
		btnImpressum = findViewById(R.id.btnImpressum);
		btnOrga = findViewById(R.id.btnOrga);
		btnOrga.setOnClickListener(this);
		btnNews.setOnClickListener(this);
		btnTeams.setOnClickListener(this);
		btnImpressum.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnNews:
			Intent i1 = new Intent(this, MainActivity.class);
			i1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(i1);
			break;
		case R.id.btnTeams:
			Intent i2 = new Intent(this, TeamSelectionActivity.class);
			i2.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(i2);
			break;
			
		case R.id.btnImpressum:
			Intent i3 = new Intent(this, ImpressumActivity.class);
			i3.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(i3);
			break;

		case R.id.btnOrga:
			Intent i4 = new Intent(this, OrganisationMenu.class);
			i4.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(i4);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
