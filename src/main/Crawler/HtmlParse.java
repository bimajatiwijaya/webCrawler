package main.Crawler;

import java.io.File;
import java.net.URL;
import java.util.Scanner;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParse {
	protected Vector<String> extractLinks(String page,String PrimaryDomain,URL DomainCrawl)
	{
		Document doc = Jsoup.parse(page);
		Elements links = doc.select("a[href]");
		Vector<String> bagOfLinks = new Vector<String>();
		for(Element link: links){
			String s = link.toString();
			s = s.substring(s.indexOf("\"") + 1);
			s = s.substring(0, s.indexOf("\"")); // mengambil link antara <a href="[link]">text<>
			if(s.length()>0)
			{
				if(s.contains(PrimaryDomain))
				{
					bagOfLinks.addElement(s);	
				}else if(s.charAt(0)=='/'){
					bagOfLinks.addElement(DomainCrawl+s.replaceFirst("/", ""));
				}
			}
		}
		return bagOfLinks;
	}
	public String getMetaContent(String page)
	{
		Document doc = Jsoup.parse(page);
		Elements body = doc.select("meta[name=keywords]");
		if(body.size()==0){ return null; }
		body = doc.select("meta[name=description]");
		if(body.size()==0){ return null; }
		StringBuilder sb = new StringBuilder();
		for(Element o: body){
			sb.append(o.attr("content").toString()+" ");
		}
		return sb.toString();
	}
	protected String getBody(String page)
	{
		Document doc = Jsoup.parse(page);
		Elements links = doc.select("body");
		return getText(links.toString());
	}
	public String getText(String html)
	{
		return Jsoup.parse(html).text();
	}
	public static void main(String[] args) throws Exception {
		StringBuilder sb = new StringBuilder();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(new File("C:/bima data/semester 6/kp/crawl_pekalongan_finished/id_crawl_84.html"));
        scanner.useDelimiter("\r");
        while(scanner.hasNext()){
        	String value = scanner.next();
            sb.append(value);
        }
		String a = sb.toString();
		HtmlParse x = new HtmlParse();
		System.out.println(x.getMetaContent(a));
		//System.out.println(x.getText(x.getBody(a)));
	}
}
