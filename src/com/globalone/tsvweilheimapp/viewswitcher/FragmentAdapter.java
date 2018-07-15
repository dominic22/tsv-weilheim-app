package com.globalone.tsvweilheimapp.viewswitcher;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;





public class FragmentAdapter extends FragmentPagerAdapter {
 
	static Fragment f1,f2,f3,f4;
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
        	Log.v("erstellt Fragment 3","asd");
        	f3= new Fragment3();
        	return f3;
        case 1:
        	Log.v("erstellt Fragment 1","asd");
        	return new Fragment1(fmAdapter);
        case 2:
        	Log.v("erstellt Fragment 2","asd");
        	return new Fragment2();
        case 3:
	    	Log.v("erstellt Fragment 4","asd");
	    	return new Fragment4(fmAdapter);
	    }

        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 4;
    }
 
}