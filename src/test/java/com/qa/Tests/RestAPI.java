package com.qa.Tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.RestClient.RestClient;
import com.qa.Testbase.TestBase;
import com.qa.Util.TestUtil;
import com.qa.Util.Users;

public class RestAPI extends TestBase{
	RestClient restclient;
	String serviceurl;
	String endpointurl;
	String URI;
	public RestAPI() {
		super();
	}

	@BeforeMethod
	public void setup() {
		endpointurl=properties.getProperty("endpointurl");
		serviceurl=properties.getProperty("serviceurl");
		URI=endpointurl+serviceurl;
	}
	//GET with out headers
	@Test(priority=1,enabled=false)
	public void GetAPIWithoutHeaders() throws ClientProtocolException, IOException {
		RestClient restclient=new RestClient();
		CloseableHttpResponse httpresponse=restclient.Get(URI);
		
		int statuscode=httpresponse.getStatusLine().getStatusCode();//get the status code
		System.out.println("Status code is---->"+statuscode);
		Assert.assertEquals(statuscode, 200,"Status code 200 is not appearing");
		//Get Entity response
		String entityresponse=EntityUtils.toString(httpresponse.getEntity(), "UTF-8");
		//Get JSON Response 
		JSONObject jsonresponse=new JSONObject(entityresponse);
		System.out.println("JSON response is "+jsonresponse);
		
		
		//Single value
		//page value validation
		String pageval=TestUtil.getValueByJPath(jsonresponse,"/total_pages");
		System.out.println(pageval);
		//total value
		String totalval=TestUtil.getValueByJPath(jsonresponse,"/total");
		System.out.println(totalval);
		
		//multiple values
		//lastname
		String lastname=TestUtil.getValueByJPath(jsonresponse,"/data[0]/last_name");
		System.out.println(lastname);
		//id
		String id=TestUtil.getValueByJPath(jsonresponse,"/data[0]/id");
		System.out.println(id);
		
		//avatar
		String avatar=TestUtil.getValueByJPath(jsonresponse,"/data[0]/avatar");
		System.out.println(avatar);
				
		//Headers array
		Header[] headers=httpresponse.getAllHeaders();
		HashMap<String,String> map=new HashMap<String,String>();
		for(Header header:headers) {
			map.put(header.getName(), header.getValue());
		}
		
		System.out.println("Headers info----> "+map);
	}

//With headers
@Test(priority=2,enabled=false)
public void GetAPIWithHeaders() throws ClientProtocolException, IOException {
	RestClient restclient=new RestClient();
	HashMap<String,String> map=new HashMap<String,String>();
	map.put("content-type", "application/json");
	
	CloseableHttpResponse httpresponse=restclient.Get(URI,map);
	
	int statuscode=httpresponse.getStatusLine().getStatusCode();//get the status code
	System.out.println("Status code is---->"+statuscode);
	Assert.assertEquals(statuscode, 200,"Status code 200 is not appearing");
	//Get Entity response
	String entityresponse=EntityUtils.toString(httpresponse.getEntity(), "UTF-8");
	//Get JSON Response 
	JSONObject jsonresponse=new JSONObject(entityresponse);
	System.out.println("JSON response is "+jsonresponse);
	
	
	//Single value
	//page value validation
	String pageval=TestUtil.getValueByJPath(jsonresponse,"/total_pages");
	System.out.println(pageval);
	//total value
	String totalval=TestUtil.getValueByJPath(jsonresponse,"/total");
	System.out.println(totalval);
	
	//multiple values
	//lastname
	String lastname=TestUtil.getValueByJPath(jsonresponse,"/data[0]/last_name");
	System.out.println(lastname);
	//id
	String id=TestUtil.getValueByJPath(jsonresponse,"/data[0]/id");
	System.out.println(id);
	
	//avatar
	String avatar=TestUtil.getValueByJPath(jsonresponse,"/data[0]/avatar");
	System.out.println(avatar);
			
	//Headers array
	Header[] headers=httpresponse.getAllHeaders();
	HashMap<String,String> map1=new HashMap<String,String>();
	for(Header header:headers) {
		map1.put(header.getName(), header.getValue());
	}
	
	System.out.println("Headers info----> "+map1);
}

//With headers
@Test(priority=2)
public void POSTAPIWithHeaders() throws ClientProtocolException, IOException {
	RestClient restclient=new RestClient();
	HashMap<String,String> map=new HashMap<String,String>();
	map.put("content-type", "application/json");
	//POJO obj
	Users users=new Users("morpheus","leader");//expted obj
	//convert obj to JSON obj is called marshelling
	//Jakson API
	ObjectMapper mapper=new ObjectMapper();
	mapper.writeValue(new File("C:\\Users\\Prasad\\eclipse-workspace\\HttpClientRestAPI\\src\\main\\java\\com\\qa\\Util\\users.json"), users);
	//JSON obj
	String JSONobj=mapper.writeValueAsString(users);
	System.out.println(JSONobj);
	CloseableHttpResponse httpresponse=restclient.Post(URI, JSONobj, map);
	
	//Status code
	int statuscode=httpresponse.getStatusLine().getStatusCode();
	System.out.println("Status code is---->"+statuscode);
	Assert.assertEquals(statuscode, 201,"Status code 201 is not appearing");
	
	//Get entity response
	String entityresponse=EntityUtils.toString(httpresponse.getEntity(), "UTF-8");
	System.out.println("respeonse is---"+entityresponse);
	
	JSONObject jsonresponse=new JSONObject(entityresponse);
	System.out.println("JSON res "+jsonresponse);
	
	//JSON to java obj
	Users userep=mapper.readValue(entityresponse, Users.class);
	Assert.assertEquals(userep.getName(), users.getName());
	Assert.assertEquals(userep.getName(), users.getName());
	System.out.println(userep.getId());
	System.out.println(userep.getCreatedAt());
	}
}
