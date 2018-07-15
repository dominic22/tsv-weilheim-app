package com.globalone.tsvweilheimapp.listview;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.globalone.tsvweilheimapp.R;

//MIt diesem Adapter l�sst sich der Hintergrund des Layouts des single_posts
//aendern

public class MyListAdapter extends SimpleAdapter{
	Context _context;
	List<? extends Map<String, String>> _data;
	MyListAdapter _listAdapter;
	public MyListAdapter(Context context, List<? extends Map<String, String>> data, int resource, String[] from, int[] to) {
		super(context,data,resource,from,to);
		_context = context;
		_data = data;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
	    if (view == null) {
	        LayoutInflater vi = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        view = vi.inflate(R.layout.single_post, null);
	    }
	    
		LinearLayout lin = (LinearLayout) view.findViewById(R.id.layoutLeftColor);
		LinearLayout box = (LinearLayout) view.findViewById(R.id.box);
		TextView txtHeadline = (TextView) view.findViewById(R.id.title);
		TextView txtShort = (TextView) view.findViewById(R.id.message);
		Map<String, String> map = _data.get(position);
		
		txtHeadline.setText(map.get("title"));
		txtShort.setText(map.get("introtext"));
		
        if (position % 2 == 0) {
        	lin.setBackgroundResource(R.color.EEEE);
        	box.setBackgroundResource(R.color.White);
        }
        else {
        	lin.setBackgroundResource(R.color.White);
        	box.setBackgroundResource(R.color.EEEE);
        }
        return view;
    }
	
	public void setListAdapter(MyListAdapter listAdapter){
		_listAdapter = listAdapter;
	}
	public MyListAdapter getListAdapter(){
		return _listAdapter;
	}
}
