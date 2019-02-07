package com.qa.Tests;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.RestClient.RestClient;
import com.qa.Testbase.TestBase;

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
	
	@Test
	public void GetAPI() throws ClientProtocolException, IOException {
		RestClient restclient=new RestClient();
		restclient.Get(URI);
	}
}
