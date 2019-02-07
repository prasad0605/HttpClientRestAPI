package com.qa.RestClient;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class RestClient {
	
	public void Get(String url) throws ClientProtocolException, IOException{
		CloseableHttpClient httpcleint=HttpClients.createDefault();//create one Http Cleint connection
		HttpGet httpget=new HttpGet(url);//crate Http get URL
		CloseableHttpResponse httpresponse=httpcleint.execute(httpget);//hit the GET url
		int statuscode=httpresponse.getStatusLine().getStatusCode();//get the status code
		System.out.println("Status code is---->"+statuscode);
		//Get Entity response
		String entityresponse=EntityUtils.toString(httpresponse.getEntity(), "UTF-8");
		//Get JSON Response 
		JSONObject jsonresponse=new JSONObject(entityresponse);
		System.out.println("JSON response is "+jsonresponse);
		//Headers array
		Header[] headers=httpresponse.getAllHeaders();
		HashMap<String,String> map=new HashMap<String,String>();
		for(Header header:headers) {
			map.put(header.getName(), header.getValue());
		}
		
		System.out.println("Headers info "+map);
	}
}
