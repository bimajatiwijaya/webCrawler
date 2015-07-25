package main.dbcrawl;

import java.io.Console;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.ConsoleHandler;

import org.apache.jena.atlas.io.NullOutputStream;
import org.apache.jena.atlas.lib.NumberUtils;
import org.apache.jena.atlas.logging.java.ConsoleHandlerStdout;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import main.TFIDF.Document;
import main.TFIDF.TF;
import main.jenahelper.JenaHelper;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
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
	public Double[] GetURLinfo(String url,int flag)
	{
		Double res[] = {0.0,0.0};
		DB db = null;
		try
		{
			relevanCheck tes = new relevanCheck();
			HashMap<String,Document> uniq = tes.GetUniqueWord(tes.Comentar);
			tes.setUniq(uniq);
			db = MONGODB.GetMongoDB();
			DBCollection collCrawlTable = db.getCollection(collCrawl);
			DBObject query = new BasicDBObject();
			query.put("url",java.util.regex.Pattern.compile(url));
			query.put("flag",flag);
			DBCursor cursor = collCrawlTable.find(query);
			int relevan = 0;
			int count = 0;
		    while (cursor.hasNext()) {
		    	int exist = 0;
		    	DBObject currentObj = cursor.next();
		    	StringBuilder sb = new StringBuilder();
		    	org.jsoup.nodes.Document doc = Jsoup.parse(currentObj.get("content").toString());
		    	Document temp = tes.WordUnique.get(currentObj.get("kategori").toString());
		    	Elements Etitle = doc.select("title");
		    	if(Etitle.size()>0){
		    		String title = Jsoup.parse(Etitle.toString()).text();
		    		if(title!=null){
				    	Document Dtitle = new Document(title);
				    	Dtitle.DoPreProcess();
				    	Dtitle.indexing();
				    	if(Dtitle.GetTerms()!=null){
				    	for(TF tdb : Dtitle.GetTerms())
				    	{
				    		for(TF s : temp.GetTerms())
							{
								if(tdb.GetTerm().equals(s.GetTerm())){
									exist=10;
									break;
								}
							}
				    	}}
			    	}
		    	}
		    	if(exist==10){
		    		relevan++;
		    	}
		    	else {
			    	Elements meta = doc.select("meta[name=keywords]");
					if(meta.size()==0){
						meta = doc.select("meta[name=description]");
					}
					if(meta.size()>0){
						for(Element o: meta){
							sb.append(o.attr("content").toString()+",");
						}
					}
					Elements Ebody = doc.select("body");
			    	String body = Jsoup.parse(Ebody.toString()).text();
			    	sb.append(body+" "+currentObj.get("url").toString());
			    	Document Dbody = new Document(sb.toString());
			    	Dbody.DoPreProcess();
			    	Dbody.indexing();
					try {
						for(TF tdb : Dbody.GetTerms())
						{
							for(TF s : temp.GetTerms())
							{
								if(tdb.GetTerm().equals(s.GetTerm())){
									exist=exist+tdb.GetFrequency();
								}
							}
						}
//						System.out.println("cocok "+exist);
						if(exist>=5){
							relevan++;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					count++;
					System.out.println((count*1.0/cursor.size())*100+"%");
			    }
		    }
//		    System.out.println(cursor.size());
//		    System.out.println(relevan);
//		    if(relevan>0){
//		    double prosentase = (relevan*1.0/cursor.size())*100;
//		    System.out.println(prosentase);}
		    res[0] = cursor.size()*1.0;
		    res[1] = relevan*1.0;
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
	public static void main(String[] args) {
//		relevanCheck tes = new relevanCheck();
//		try {
//			HashMap<String,Document> uniq = tes.GetUniqueWord(tes.Comentar);
//			tes.setUniq(uniq);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		ArrayList<String> urls = new ArrayList<String>();
		urls.add("semarangkota.go.id");
		urls.add("kebumenkab.go.id");
		urls.add("banyumaskab.go.id");
		urls.add("pekalongankab.go.id");
		urls.add("semarangkab.go.id");
		urls.add("magelangkab.go.id");
		urls.add("rembangkab.go.id");
		urls.add("patikab.go.id");
		urls.add("tegalkab.go.id");
		urls.add("cilacapkab.go.id");
		crawlColl x = new crawlColl("http://localhost/ta/www.owl","http://www.ta.com/#");
		ArrayList<Double[]> result = new ArrayList<Double[]>(); 
		int i = 0;
//		for(String url : urls)
//		{
//			result.add(x.GetURLinfo(url,2));
//			 try {
//				Thread.sleep(3000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		for(String url : urls)
//		{
//			System.out.println(url.toUpperCase());
//			Double temp[] = result.get(i);
//			System.out.println("Total relevan(original) : "+temp[0]);
//			System.out.println("Total relevan(check) : "+temp[1]);
//			System.out.println("Prosentase : "+String.format("%,2f", (temp[1]/temp[0])*100)+" % (relevan)");
//			i++;
//		}
//		int i = 1;
//		for(String url : urls)
//		{
//			System.out.println(i+". Domain : "+url.toUpperCase());i++;
//			
//		}
		//CEK DETAIL
		i=1;
		System.out.println("\n\n");
		for(String url : urls)
		{
			System.out.println(i+". Domain : "+url.toUpperCase());i++;
			x.SetURL(url);
			//x.cEcCrawl();
			System.out.println("paused : "+x.CountByFlag(x.URL,1));
			System.out.println("tidak terklasifikasi : "+x.CountByFlag(x.URL,3));
			System.out.println("terklasifikasi : "+x.CountByFlag(x.URL,2));
			System.out.println("belum tercrawling : "+x.CountByFlag(x.URL,0));
			System.out.println("total halaman : "+x.getCountCrawlByUrl(x.URL));
			System.out.println("=====================================");
		}
		double f1=x.CountByFlag(1),f2=x.CountByFlag(2),f3=x.CountByFlag(3),f0=x.CountByFlag(0);
		System.out.println("TOTAL :");
		System.out.println("paused : "+f1);
		System.out.println("terklasifikasi : "+f2);
		System.out.println("tidak terklasifikasi : "+f3);
		System.out.println("Belum tercrawling "+f0);
		System.out.println("Keseluruhan "+ (f1+f2+f3+f0));
//		
	}
}
