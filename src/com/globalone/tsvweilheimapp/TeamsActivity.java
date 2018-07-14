package com.globalone.tsvweilheimapp;

import com.globalone.tsvweilheimapp.viewswitcher.MainSwitcherActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TeamsActivity extends Activity implements OnClickListener{

	Button btnH1, btnH2, btnD1, btnD2,btnJS,btnAD;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teams);
		
		btnH1 = (Button) findViewById(R.id.btnHerren1);
		btnH2 = (Button) findViewById(R.id.btnHerren2);
		btnD1 = (Button) findViewById(R.id.btnDamen1);
		btnD2 = (Button) findViewById(R.id.btnDamen2);
		btnJS = (Button) findViewById(R.id.btnJS);
		btnAD = (Button) findViewById(R.id.btnAD);
		
		
//		btnH1.setHeight(btnH1.getWidth());
//		btnH2.setHeight(btnH1.getWidth());
//		btnD1.setHeight(btnH1.getWidth());
//		btnD2.setHeight(btnH1.getWidth());
		
		btnH1.setOnClickListener(this);
		btnH2.setOnClickListener(this);
		btnD1.setOnClickListener(this);
		btnD2.setOnClickListener(this);
		btnJS.setOnClickListener(this);
		btnAD.setOnClickListener(this);
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnHerren1:
			Intent i1 = new Intent(this, MainSwitcherActivity.class);
			i1.putExtra("Mannschaft","erste");
			startActivity(i1);
			break;
		case R.id.btnHerren2:
			Intent i2 = new Intent(this, MainSwitcherActivity.class);
			i2.putExtra("Mannschaft","zweite");
			startActivity(i2);
			break;
		case R.id.btnDamen1:
			Intent i3 = new Intent(this, MainSwitcherActivity.class);
			i3.putExtra("Mannschaft","damen");
			startActivity(i3);
			break;
		case R.id.btnDamen2:
			Intent i4 = new Intent(this, MainSwitcherActivity.class);
			i4.putExtra("Mannschaft","damen2");
			startActivity(i4);
			break;
			
		case R.id.btnJS:
			Intent i5 = new Intent(this, MainSwitcherActivity.class);
			i5.putExtra("Mannschaft","js");
			startActivity(i5);
			break;
		case R.id.btnAD:
			Intent i6 = new Intent(this, MainSwitcherActivity.class);
			i6.putExtra("Mannschaft","ad");
			startActivity(i6);
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
