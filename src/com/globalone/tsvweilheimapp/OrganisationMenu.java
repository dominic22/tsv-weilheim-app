package com.globalone.tsvweilheimapp;

import com.globalone.tsvweilheimapp.viewswitcher.MainSwitcherActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class OrganisationMenu extends Activity implements OnClickListener{

	Button btnH1, btnH2, btnD1, btnD2;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_organisation_menu);
		
		btnH1 = (Button) findViewById(R.id.btnVorstand);
		btnH2 = (Button) findViewById(R.id.btnJugendleitung);
		btnD1 = (Button) findViewById(R.id.btnWirtschaftsausschuss);
		btnD2 = (Button) findViewById(R.id.btnFoerderverein);
		
		btnH1.setOnClickListener(this);
		btnH2.setOnClickListener(this);
		btnD1.setOnClickListener(this);
		btnD2.setOnClickListener(this);
		
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
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnVorstand:
			Intent i1 = new Intent(this, ShowOrgaActivity.class);
			i1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			i1.putExtra("Parameter","vorstand");
			startActivity(i1);
			break;
		case R.id.btnJugendleitung:
			Intent i2 = new Intent(this, ShowOrgaActivity.class);
			i2.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			i2.putExtra("Parameter","jugendleitung");
			startActivity(i2);
			break;
		case R.id.btnWirtschaftsausschuss:
			Intent i3 = new Intent(this, ShowOrgaActivity.class);
i3.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			i3.putExtra("Parameter","wirtschaftsausschuss");
			startActivity(i3);
			break;
		case R.id.btnFoerderverein:
			Intent i4 = new Intent(this, ShowOrgaActivity.class);
			
			i4.putExtra("Parameter","foerderverein");
			startActivity(i4);
			break;

		default:
			break;
		}
	}

	
}
