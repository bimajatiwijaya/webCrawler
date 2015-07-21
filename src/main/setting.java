package main;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class setting {
	public static final String collCrawl = "crawlcoll";
//	public static final String collCrawl = "testcrawl";
	public static final double minRel = 0.27;
	public static final String address = "http://localhost/ta/www.owl";
	public static final String URI = "http://www.ta.com/#";
	public URL StringToURL(String url)
	{
		URI uri = null;
		URL rest = null;
		try
		{
			uri = new URI(url);
		}catch(URISyntaxException e)
		{
			System.out.println("error in URI format: "+ url);
		}
		
		try
		{
			if(uri!=null) rest = uri.toURL();
		}catch(IllegalArgumentException e)
		{
			System.out.println(url +": invalid URL.. will not starting thread for this one!");
		} catch (MalformedURLException badURL) {
			System.out.println(url +": invalid URL.. will not starting thread for this one!");
		}
		return rest;
	}
	public static void main(String[] args) {
		setting t = new setting();
		System.out.println(t.StringToURL("http://www.facebook.com/share=www.banyumaskab.go.id/sdf/we/qw").getHost());
		System.out.println(t.StringToURL("https://www.banyumaskab.go.id/sdfsdf/sdf/ew").getHost());
		System.out.println("http://www.facebook.com/share=www.banyumaskab.go.id/sdf/we/qw".replaceAll("[^a-zA-Z ]", " ").toLowerCase());
	}
}
