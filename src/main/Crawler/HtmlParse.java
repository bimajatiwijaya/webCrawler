package main.Crawler;

import java.net.URL;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParse extends main.setting{
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
					URL temp = StringToURL(s);
					if(DomainCrawl.toString()==temp.getHost().toString())
					{
						bagOfLinks.addElement(s);
					}// to avoid url direction from sosial media like twitter.com/share=batangkab.go.id?dst
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
}
