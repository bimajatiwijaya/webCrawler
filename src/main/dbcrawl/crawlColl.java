package main.dbcrawl;

import java.util.ArrayList;

import main.jenahelper.JenaHelper;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import connector.MONGODB;
public class crawlColl extends main.setting{
	public ArrayList<String> Classes;
	public String URL;
	public crawlColl(String path,String URI,String urlCrawl)
	{
		JenaHelper jh = new JenaHelper(path, URI);
		Classes = jh.getAllListClass();
		URL = urlCrawl;
	}
	public crawlColl(String path,String URI)
	{
		JenaHelper jh = new JenaHelper(path, URI);
		Classes = jh.getAllListClass();
	}
	public void SetURL(String url)
	{
		this.URL = url;
	}
	public double getCountCrawlByUrl(String url)
	{
		double res = 0;
		DB db = null;
		try
		{
			db = MONGODB.GetMongoDB();
			DBCollection collCrawlTable = db.getCollection(collCrawl);
			DBObject query = new BasicDBObject("url",java.util.regex.Pattern.compile(url));
			res = collCrawlTable.count(query);
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
		return res;
	}
	public double getCountCrawlByUrlAndClass(String url,String classes)
	{
		double res = 0;
		DB db = null;
		try
		{
			db = MONGODB.GetMongoDB();
			DBCollection collCrawlTable = db.getCollection(collCrawl);
			DBObject query = new BasicDBObject();
			query.put("url",java.util.regex.Pattern.compile(url));
			query.put("kategori", classes);
			res = collCrawlTable.count(query);
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
		return res;
	}
	public double CountByFlag(String url,int flag)
	{
		double res = 0;
		DB db = null;
		try
		{
			db = MONGODB.GetMongoDB();
			DBCollection collCrawlTable = db.getCollection(collCrawl);
			DBObject query = new BasicDBObject();
			query.put("url",java.util.regex.Pattern.compile(url));
			query.put("flag",flag);
			res = collCrawlTable.count(query);
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
		return res;
	}
	public double CountByFlag(int flag)
	{
		double res = 0;
		DB db = null;
		try
		{
			db = MONGODB.GetMongoDB();
			DBCollection collCrawlTable = db.getCollection(collCrawl);
			DBObject query = new BasicDBObject();
			query.put("flag",flag);
			res = collCrawlTable.count(query);
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
		return res;
	}
	/**
	 * count each url class 
	 */
	public void cEcCrawl()
	{
		for(String cl : this.Classes)
		{
			double cc = getCountCrawlByUrlAndClass(this.URL, cl);
			System.out.println(cl+" : "+cc);
		}
	}
	public static void main(String[] args) throws Exception {
		ArrayList<String> urls = new ArrayList<String>();
		urls.add("semarangkota.go.id");
		urls.add("kebumenkab.go.id");
		urls.add("banyumaskab.go.id");
		urls.add("pekalongankab.go.id");
		crawlColl x = new crawlColl("http://localhost/ta/www.owl","http://www.ta.com/#");
		for(String url : urls)
		{
			x.SetURL(url);
			x.cEcCrawl();
			System.out.println(url);
			System.out.println(x.CountByFlag(x.URL,3));
			System.out.println(x.CountByFlag(x.URL,2));
			System.out.println(x.CountByFlag(x.URL,1));
			System.out.println(x.getCountCrawlByUrl(x.URL));
			System.out.println("=====================================");
		}
		double f1=x.CountByFlag(1),f2=x.CountByFlag(2),f3=x.CountByFlag(3);
		System.out.println(f1);
		System.out.println(f2);
		System.out.println(f3);
		System.out.println(f1+f2+f3);
	}
}
