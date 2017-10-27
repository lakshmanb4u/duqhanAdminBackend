package com.weavers.duqhan.util;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ProxyJsoup {
	
	public static Document connect(String url) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		HttpConnectionManager conManager = client.getHttpConnectionManager();
		client.getHostConfiguration().setProxy("us-wa.proxymesh.com", 31280);
		HttpState state = new HttpState();
		state.setProxyCredentials(null, null, new UsernamePasswordCredentials("Lakshmanb4u", "Duqhan011"));
		client.setState(state);
		HttpMethod method = new GetMethod("http://www.google.com/");
		client.executeMethod(method);
		int code = method.getStatusCode();
		System.out.println(code);
		if(code == 200) {
			return  Jsoup.parse(method.getResponseBodyAsString());
		}
		return null;
	}
	
	

}
