package com.xhr.configserver.util;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpUtil {

	public void simpleGetRequest(String url) {
		try {
			CloseableHttpClient client = HttpClientBuilder.create().build();
			HttpGet get = new HttpGet(url);
			client.execute(get);
			client.close();
			System.out.println("simply pay " + url + " a get visit");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void simplePostRequest(String url) {
		try {
			CloseableHttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);
			client.execute(post);
			client.close();
			System.out.println("simply pay " + url + " a post visit");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
