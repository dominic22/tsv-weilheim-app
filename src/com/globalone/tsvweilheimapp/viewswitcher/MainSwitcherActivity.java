package com.globalone.tsvweilheimapp.viewswitcher;


import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.globalone.tsvweilheimapp.R;


public class MainSwitcherActivity extends android.support.v4.app.FragmentActivity implements
		ActionBar.TabListener {

	
	
	private ViewPager viewPager;
	

	

	private FragmentAdapter mAdapter;
	
	private ActionBar actionBar;
//	http://code.tutsplus.com/tutorials/android-compatibility-working-with-fragments--mobile-5431
	// Tab titles
	private String[] tabs = { "Mannschaft", "Tabelle", "Spiele", "Berichte" };

	private static final int[] ICONS = new int[] { R.drawable.team,
			R.drawable.spiele, R.drawable.spielplan, R.drawable.news, };

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_switcher);

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new FragmentAdapter(getSupportFragmentManager());

		// viewPager.setBackgroundColor(Color.LTGRAY);

		viewPager.setAdapter(mAdapter);

		// actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

		actionBar.setHomeButtonEnabled(false);

		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		// for (String tab_name : tabs) {
		// actionBar.addTab(actionBar.newTab().setText(tab_name)
		// .setTabListener(this));
		//
		// }
		// Actionbar wei�..
		actionBar.setStackedBackgroundDrawable(new ColorDrawable(getResources()
				.getColor(R.color.White)));

		for (int iconid : ICONS) {
			actionBar.addTab(actionBar.newTab().setIcon(iconid)
					.setTabListener(this));

		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				Log.v("SELECTED","NEUU" + String.valueOf(position));
				
				actionBar.setSelectedNavigationItem(position);
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		Log.v("SELECTED","UNTEN");
		mAdapter.setM_item(tab.getPosition());
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

}