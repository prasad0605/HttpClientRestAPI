package com.qa.RestClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class RestClient {
	
	public CloseableHttpResponse Get(String url) throws ClientProtocolException, IOException{
		CloseableHttpClient httpcleint=HttpClients.createDefault();//create one Http Cleint connection
		HttpGet httpget=new HttpGet(url);//crate Http get URL
		CloseableHttpResponse httpresponse=httpcleint.execute(httpget);//hit the GET url
		return httpresponse;
	}
	
	public CloseableHttpResponse Get(String url,HashMap<String,String> hashmap) throws ClientProtocolException, IOException{
		CloseableHttpClient httpcleint=HttpClients.createDefault();//create one Http Cleint connection
		HttpGet httpget=new HttpGet(url);//crate Http get URL
		for(Map.Entry<String, String> m:hashmap.entrySet()) {
			httpget.addHeader(m.getKey(), m.getValue());
		}
		
		CloseableHttpResponse httpresponse=httpcleint.execute(httpget);//hit the GET url
		return httpresponse;
	}
	
	public CloseableHttpResponse Post(String url,String entitystring,HashMap<String,String> hashmap) throws ClientProtocolException, IOException{
		CloseableHttpClient httpcleint=HttpClients.createDefault();//create one Http Cleint connection
		HttpPost httppost=new HttpPost(url);//create Http post URL
		for(Map.Entry<String, String> m:hashmap.entrySet()) {
			httppost.addHeader(m.getKey(), m.getValue());
		}
		httppost.setEntity(new StringEntity(entitystring));//for payload
		
		CloseableHttpResponse httpresponse=httpcleint.execute(httppost);//hit the POST url
		return httpresponse;
	}
}
