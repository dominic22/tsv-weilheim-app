package com.globalone.tsvweilheimapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Test2 extends Activity{

	static String content ="asd";
	TextView txtTabelle;
	//private Button getImageButton;
	private Button getTextButton;
	private ProgressDialog progressDialog;	
	//private Bitmap bitmap = null;
	private String text = null;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //getImageButton = (Button)findViewById(R.id.Button01);
        getTextButton = (Button)findViewById(R.id.btn123);
        
     //   getImageButton.setOnClickListener( new OnClickListener() {

			/*@Override
			public void onClick(View v) {
				downloadImage("http://www.android.com/media/wallpaper/gif/android_logo.gif");
				
			}
        });
        
        */
        getTextButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			//	downloadText("http://www.edumobile.org/android");
			}
        });
    }
    
/*
	private void downloadImage(String urlStr) {
		progressDialog = ProgressDialog.show(this, "", "Fetching Image...");
		final String url = urlStr;
		
		new Thread() {
			public void run() {
				InputStream in = null;
				Message msg = Message.obtain();
				msg.what = 1;
				try {
				    in = openHttpConnection(url);
				    bitmap = BitmapFactory.decodeStream(in);
				    Bundle b = new Bundle();
				    b.putParcelable("bitmap", bitmap);
				    msg.setData(b);
				    in.close();
				} catch (IOException e1) {
				    e1.printStackTrace();
				}
				messageHandler.sendMessage(msg);	
				
			}
 		}.start();

	}*/
	
	private void downloadText(String urlStr) {
		progressDialog = ProgressDialog.show(this, "", "Fetching Text...");
		final String url = urlStr;
		
		new Thread () {
			@Override
			public void run() {
				int BUFFER_SIZE = 2000;
		        InputStream in = null;
		        Message msg = Message.obtain();
		        msg.what=2;
		        try {
		        	in = openHttpConnection(url);
		            
		            InputStreamReader isr = new InputStreamReader(in);
		            int charRead;
		              text = "";
		              char[] inputBuffer = new char[BUFFER_SIZE];

		                  while ((charRead = isr.read(inputBuffer))>0)
		                  {                    
		                      //---convert the chars to a String---
		                      String readString = 
		                          String.copyValueOf(inputBuffer, 0, charRead);                    
		                      text += readString;
		                      inputBuffer = new char[BUFFER_SIZE];
		                  }
		                 Bundle b = new Bundle();
						    b.putString("text", text);
						    msg.setData(b);
		                  in.close();
	                  
				}catch (IOException e) {
	                e.printStackTrace();
	            }
				messageHandler.sendMessage(msg);
			}
		}.start();
          
	}
	
	private InputStream openHttpConnection(String urlStr) {
		InputStream in = null;
		int resCode = -1;
		
		try {
			URL url = new URL(urlStr);
			URLConnection urlConn = url.openConnection();
			
			if (!(urlConn instanceof HttpURLConnection)) {
				throw new IOException ("URL is not an Http URL");
			}
			
			HttpURLConnection httpConn = (HttpURLConnection)urlConn;
			httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect(); 

            resCode = httpConn.getResponseCode();                 
            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();                                 
            }         
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return in;
	}
	
	private Handler messageHandler = new Handler() {
		
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			
			case 1:
				TextView text = (TextView) findViewById(R.id.txtTabelle);
				text.setText(msg.getData().getString("text"));
				break;
			}
			progressDialog.dismiss();
		}
	};
}