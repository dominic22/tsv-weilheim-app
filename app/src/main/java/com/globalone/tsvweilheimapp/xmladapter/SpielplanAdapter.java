package com.globalone.tsvweilheimapp.xmladapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.globalone.tsvweilheimapp.R;

import java.util.List;

public class SpielplanAdapter extends ArrayAdapter<Spielplan> {

//	ImageLoader imageLoader;
//	DisplayImageOptions options;
	
	
	public SpielplanAdapter(Context ctx, int textViewResourceId, List<Spielplan> sites) {
		
		super(ctx, textViewResourceId, sites);
		
		//Setup the ImageLoader, we'll use this to display our images
        /*ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ctx).build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
        
        //Setup options for ImageLoader so it will handle caching for us.
        options = new DisplayImageOptions.Builder()
		.cacheInMemory()
		.cacheOnDisc()
		.build();
*/

	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 * 
	 * This method is responsible for creating row views out of a StackSite object that can be put
	 * into our ListView
	 */
	@Override
	public View getView(int pos, View convertView, ViewGroup parent){
		RelativeLayout row = (RelativeLayout)convertView;
		Log.i("StackSites", "getView pos = " + pos);
		if(null == row){
			//No recycled View, we have to inflate one.
			LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = (RelativeLayout)inflater.inflate(R.layout.row_site, null);
		}
		
		//Get our View References
		final ImageView iconImg = row.findViewById(R.id.iconImg);
		TextView nameTxt = row.findViewById(R.id.nameTxt);
		TextView aboutTxt = row.findViewById(R.id.aboutTxt);
		final ProgressBar indicator = row.findViewById(R.id.progress);
		
		//Initially we want the progress indicator visible, and the image invisible
		indicator.setVisibility(View.VISIBLE);
		iconImg.setVisibility(View.INVISIBLE);

		//Setup a listener we can use to swtich from the loading indicator to the Image once it's ready
	
		
		//Load the image and use our options so caching is handled.
		///imageLoader.displayImage(getItem(pos).getImgUrl(), iconImg,options, listener);
		
		//Set the relavent text in our TextViews
		
//	nameTxt.setText(getItem(pos).getVerein());
//		aboutTxt.setText(getItem(pos).getSpiele());
		
		
		
		return row;
				
				
	}

}