package tsvweilheimapp.globalone.com.tsvweilheim;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HoleStringVonWebsite {

	
	    //Thread to stop network calls on the UI thread
	    private int TIMEOUT_CONNECTION =200;

		public String stringQuery( String url) {
			
				// TODO Auto-generated method stub
				try {
		            HttpParams httpParameters = new BasicHttpParams();
		            HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT_CONNECTION);
		            int TIMEOUT_SOCKET = 200;
					HttpConnectionParams.setSoTimeout(httpParameters, TIMEOUT_SOCKET);

		            HttpClient client = new DefaultHttpClient(httpParameters);
		            //String url = "http://www.google.com";
		            HttpParams params = new BasicHttpParams();
		            params.setParameter("geo", "{\"$circle\":{\"$center\":[\" + x + \",\" + y + \"],\"$meters\":5000}}");
		            HttpGet request = new HttpGet(url);
		            
		            Log.v("fuck u", "geht nocht");
		            HttpResponse response = client.execute(request);
		            Log.v("fuck u", "execution complete");
		            HttpEntity entity = response.getEntity();
		            Log.v("fuck u", "habe entity");
		            
		            //Do something with the response
		            if(entity != null){
		                String strEntity =  EntityUtils.toString(entity);
						Log.v("fuck u", strEntity);
						return strEntity;
		            }
		            else{
		            	Log.v("fehler entity","tehle2");
		            }
		            
		            
		        }
		        catch (IOException e) {
		            Log.e("Tag", "Could not get HTML: " + e.getMessage());
		        }
				
				return null;
			
			
	    
		}
	
	
}
