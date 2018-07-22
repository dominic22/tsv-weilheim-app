package tsvweilheimapp.globalone.com.tsvweilheim;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Test extends Activity{
	
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
        setContentView(R.layout.test);
        
        //getImageButton = (Button)findViewById(R.id.Button01);
        getTextButton = findViewById(R.id.btn123);
        
       // getImageButton.setOnClickListener( new OnClickListener() {

		/*	@Override
			public void onClick(View v) {
				downloadImage("http://www.android.com/media/wallpaper/gif/android_logo.gif");
				
			}
        });*/
        
       // downloadText("http://www.hvw-online.org/?A=g_class&id=39&orgID=8&score=16114");
        downloadText("http://android.handball-weilheim.de/webhandball/hvwcutout.php?site=erste&type=table");
        
        getTextButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
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
			
			case 2:
				TextView text = findViewById(R.id.txtTabelle);
				String messageText = msg.getData().toString();
				
				
			/*	messageText = messageText.replace("<!--", "");
				messageText = messageText.replace("-->", "");
				messageText = messageText.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
				
				messageText = messageText.replace("<body>", "");
				//messageText = messageText.replace("Aktueller Tabellenstand</h1>", "");
				messageText = messageText.replace("</div> * ScoreTableFoot()", "");
				messageText = messageText.replace("</html>", "");
				
				//messageText= messageText.replace("Aktueller Tabellen", "�");
				*/
				
				
				Jsoup.parse(messageText);
				Document doc = Jsoup.parse(messageText);
                Elements paragaph= doc.getAllElements();
                org.jsoup.nodes.Element el = paragaph.get(1);
                messageText = el.toString();
                
                String[] arrMessage = new String[22];
				Integer anzahl = messageText.split("Aktueller Tabellen").length;
				Log.v("anzahl",anzahl.toString());
                arrMessage = messageText.split("Aktueller Tabellen");
							
				if(anzahl >=2){
					messageText = arrMessage[1];
					Log.v("anzahl",arrMessage[1]);
				//messageText.split(regularExpression)
				messageText = messageText.replace("</td>", "�");
				messageText=messageText.replaceAll("<"+".*?"+">", "");//("(&<=)[^&]*(&>=)", "$1foo$2");
				//messageText.replace("\n", "�");
				
				
				messageText = messageText.replace("\n", "").replace("\r", "");
				//messageText = messageText.replaceAll("\\s+", " ");
				//messageText=messageText.replace("�", "\n\r");
				messageText = messageText.replaceAll("\\s+", " ");
				
				arrMessage = new String[messageText.split("1").length];
				arrMessage = messageText.split("Punkte");
				messageText = arrMessage[1];
				
				arrMessage = new String[messageText.split("1").length];
				arrMessage = messageText.split("Diese Tabell");
				messageText = arrMessage[0];
				
				messageText=messageText.trim();
				
				arrMessage = new String[messageText.split(" ").length];
				arrMessage = messageText.split("Diese Tabell");
				messageText = arrMessage[0];
				
				text.setText(messageText);
				}
				else{
					text.setText("Fehler beim Parsen!");
				}
				
				break;
			}
			progressDialog.dismiss();
		}
	};
}