package com.globalone.tsvweilheimapp.viewswitcher;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {
	static int m_item;
	
	public int getM_item(){
		return m_item;
	}
	public void setM_item(int item) {
		FragmentAdapter.m_item = item;
	}
	
	FragmentAdapter fmAdapter;
	ActionBar actionB;
	
	
    public ActionBar getActionB() {
		return actionB;
	}

	public void setActionB(ActionBar actionB) {
		this.actionB = actionB;
	}

	public FragmentAdapter(FragmentManager fm ) {
        super(fm);        
        fmAdapter = this;
    }

	public FragmentAdapter(FragmentManager supportFragmentManager,
			ActionBar actionBar) {
		 super(supportFragmentManager);
	      fmAdapter = this;
	      actionB= actionBar;
	}

	@Override
    public Fragment getItem(int index) {
        switch (index) {
        case 0:
            return new ScoreFragment(fmAdapter);
        case 1:
            return new ScheduleFragment();
        case 2:
            return new NewsFragment(fmAdapter);
        case 3:
			return new TeamFragment();
	    }
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 4;
    }
}