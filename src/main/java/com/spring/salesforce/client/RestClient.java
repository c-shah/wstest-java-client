package com.spring.salesforce.client;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by rr245546 on 6/8/2017.
 */
public class RestClient {
    public static void main(String args[]) throws Exception {
        new RestClient().executeService();
    }

    public void executeService() throws Exception {
        System.out.println("Working Directory = " +  System.getProperty("user.dir"));
        Properties properties = new Properties();
        properties.load(  this.getClass().getResourceAsStream("/config.properties") );

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost( (String) properties.get("sf.oauth.url") );

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("grant_type", "password") );
        urlParameters.add(new BasicNameValuePair("client_id", (String) properties.get("sf.oauth.client.id")));
        urlParameters.add(new BasicNameValuePair("client_secret", (String) properties.get("sf.oauth.client.secret")));
        urlParameters.add(new BasicNameValuePair("username", (String) properties.get("sf.username")));
        urlParameters.add(new BasicNameValuePair("password", (String) properties.get("sf.password")));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(" result " + result.toString() );
    }
}
