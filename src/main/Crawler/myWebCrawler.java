package main.Crawler;
//{content,meta,url,term,title,crawled,depth,datecrawl}
// flag 0 seed baru, 1 sedang dicrawl, 2 sukses/relevan, 3 tidak relevan
import java.util.*;
import java.util.regex.Pattern;
import java.net.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import connector.MONGODB;
import main.TFIDF.*;
import main.jenahelper.JenaHelper;
public class myWebCrawler extends main.setting{
	private URL DomainCrawl;
	public String PrimaryDomain;
	public boolean statCrawl;
	public static URL pageToFetch;
	public static ArrayList<String> kelas;
	public static ArrayList<String> coment;
	public static int countclass;
	public myWebCrawler(){}
	public myWebCrawler(String pageName)
	{
		boolean uniq = UniqueDomain(pageName);
		if(uniq)
		{
			DomainCrawl = StringToURL(pageName);
			statCrawl = true;
			PrimaryDomain = DomainCrawl.getHost();//AddDomain(DomainCrawl.toString(),false);
			AddSeed(DomainCrawl.toString(),0);
		}		
		else if(!uniq || NotFinished(pageName))
		{
			DomainCrawl = StringToURL(pageName);
			statCrawl = true;
			PrimaryDomain = DomainCrawl.getHost();
		}else
		{
			System.out.println("this domain finished");
			DomainCrawl = null;
			statCrawl = false;
		}
		JenaHelper jH = new JenaHelper(address,URI);
		jH.initOWL();
		kelas = jH.clas;
		coment = jH.coment;
		countclass = jH.clas.size();
	}
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
	public String GetNextUrl() {
		DBObject info = new BasicDBObject();
		try
		{
			DB db = MONGODB.GetMongoDB();
			DBCollection collCrawlTable = db.getCollection(collCrawl);
			
			BasicDBObject where_query;
			DBObject find_next_seed;
			where_query = new BasicDBObject();
			where_query.put("crawled",0);
			where_query.put("flag",0);
			where_query.put("url", Pattern.compile(PrimaryDomain.substring(4, PrimaryDomain.length())));
			find_next_seed = collCrawlTable.findOne(where_query);
			
			if (find_next_seed != null)
			{
				info.put("url",find_next_seed.get("url").toString());
				info.put("depth",find_next_seed.get("depth").toString());
				info.put("_id",find_next_seed.get("_id").toString());
				info.put("crawled",find_next_seed.get("crawled").toString());
				// menandai coll sedang dalam proses crawling
				// agar thread tidak mengcrawl url yang sama
				DBObject query = new BasicDBObject("url",find_next_seed.get("url").toString());
				DBObject updateset = new BasicDBObject();
				updateset.put("flag", 1);
				DBObject findOne = collCrawlTable.findOne(query);
				if(findOne != null){
					updateCrawl(collCrawlTable, query, updateset);
				}
			}else
			{/*System.out.println("tidak "+where_query);*/
				info = null;
			}
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
		if(info==null){return null;}
		return info.toString();
	}

	@SuppressWarnings("rawtypes")
	public void crawl() throws Exception
	{
		String[] doc = new String[3];
		HtmlParse phhelp = new HtmlParse();
		String seedToCrawl = GetNextUrl();
		if(seedToCrawl==null){Thread.sleep(10000); if(GetNextUrl()!=null){crawl();}else{System.out.println("finished");}}
		else{
			JSONObject parse = (JSONObject) JSONValue.parse(seedToCrawl);
			//info webtocrawl
			String url = parse.get("url").toString();
			Integer depth = new Integer(parse.get("depth").toString());depth++;
			Integer crawled = new Integer(parse.get("crawled").toString());crawled++;
			//
			//System.out.println(url);
			Vector<String> pageLinks;
			pageToFetch = StringToURL(url);
			if(pageToFetch!=null)
			{
				String webPage = downloadPage(pageToFetch);
				if(webPage!=null)
				{
					String tempURL = null;
					String tempBody = null;
					String tempMeta = null;
					if(url.length()<=DomainCrawl.toString().length()){
						tempURL = "none";
					}else{
						tempURL = url.substring(DomainCrawl.toString().length(),url.length());
					}
					tempBody = phhelp.getBody(webPage);
					tempMeta = phhelp.getMetaContent(webPage);
					if(tempMeta==null)
					{
						doc = new String[2];
						doc[0] = tempURL;
						doc[1] = tempBody;
					}else{
						doc[0] = tempURL; doc[1]=tempBody;doc[2]=tempMeta;
					}
					pageLinks = phhelp.extractLinks(webPage,PrimaryDomain,DomainCrawl); //#parselink
					// tambah get meta
					Enumeration allLinks = pageLinks.elements();
					DB db = null;
					try
					{
						db = MONGODB.GetMongoDB();
						DBCollection collCrawlTable = db.getCollection(collCrawl);
						while(allLinks.hasMoreElements())
						{					
							String newSeed = (String)allLinks.nextElement();
							//if(isText(newSeed)){
								AddSeed(newSeed,depth,collCrawlTable); // #addseed
							//}
						}
						// mulai pembobotan
						TFIDFweighting calW = new TFIDFweighting(doc);
						calW.buildVSM();	
						boolean relevan = false;
						String kategori = null;
						double[] res = new double[countclass]; 
						for(int i=0;i<countclass;i++)
						{
							boolean match = calW.compare(coment.get(i));
							if(match){
								if(doc.length==2){
									res[i] = Math.max(calW.cos[0],calW.cos[1]);
									if(res[i]>=minRel)
									{
										relevan = true; 
									}
								}
								else
								{
									res[i] = Math.max(calW.cos[2],Math.max(calW.cos[0],calW.cos[1]));
									if(res[i]>=minRel)
									{
										relevan = true;
									}
								}
									
							}else{
								res[i]=0;
							}
							calW.ClearStringCompare();
						}
						int ont = this.maxRelClass(res);
						if(ont!=-1)
						{
							kategori = kelas.get(ont);
							if(res[ont]<minRel)
							{
								//System.out.println("[T] class "+kategori+" relevansi : "+res[ont]);
								relevan = false;
							}
						}
						else
						{
							relevan = false;
							//System.out.println("kategori tidak ada yang cocok");
						}
						
						// end
						// update url yang sudah dicrawl
						DBObject query = new BasicDBObject("url",parse.get("url").toString());
						DBObject updateset;
						if(relevan)
						{
							//System.out.println(url+" : "+kategori);
							updateset = this.setUpdateCrawl(webPage, crawled, 2, calW.termsString(),kategori,res[ont]);
						}
						else
						{
							//System.out.println("Tidak relevan");
							updateset = this.setUpdateCrawl(null, crawled, 3,null,null,0);
						}
						DBObject findOne = collCrawlTable.findOne(query);
						if(findOne != null)
						{
							updateCrawl(collCrawlTable, query, updateset);
							Delete(url);
						}
						else
						{
							
						}
						crawl();
					}
					catch (Exception ex)
					{
						System.out.println(ex);
					}
				}else
				{
					crawl();
				}
			}
			else
			{
				crawl();
			}
		}
	}
	private DBObject setUpdateCrawl(String content,int crawled,int flag,String terms,String kategori,double sim)
	{
		DBObject updateset = new BasicDBObject();
		updateset.put("rel", sim);
		updateset.put("terms",terms);
		updateset.put("content", content);
		updateset.put("crawled", crawled);
		updateset.put("flag", flag);
		updateset.put("kategori", kategori);
		updateset.put("datecrawl", new Date());
		return updateset;
	}
	public boolean getStatus()
	{
		return statCrawl;
	}
	protected String downloadPage(URL page)
	{
		myHTTPManager http = new myHTTPManager();
		String res = null;
		try{
			res = http.downloadPage(page);
		}catch(Exception ex){ res = null; }
		return res;
	}
	@SuppressWarnings("unused")
	protected boolean uniqueURL(DBCollection collCrawl,String url)
	{
		boolean res = false;
		DB db = null;
		try
		{
			db = MONGODB.GetMongoDB();
			
			DBObject query = new BasicDBObject("url",url);
			DBObject findOne = collCrawl.findOne(query);
			if(findOne==null)
			{
				res = true;
			}
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
		return res;
	}
	protected boolean uniqueURL(String url)
	{
		boolean res = false;
		DB db = null;
		try
		{
			db = MONGODB.GetMongoDB();
			DBCollection collCrawlTable = db.getCollection(collCrawl);
			DBObject query = new BasicDBObject("url",url);
			DBObject findOne = collCrawlTable.findOne(query);
			if(findOne==null)
			{
				res = true;
			}
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
		return res;
	}
	protected void updateCrawl(DBCollection collCrawl, DBObject query,DBObject updateset)
	{	
		BasicDBObject ObjectQuery = new BasicDBObject();
		ObjectQuery.put("$set", updateset);
		collCrawl.update(query, ObjectQuery);
	}
	protected void AddSeed(String url,int depth) {
		DB db = null;
		try
		{
			db = MONGODB.GetMongoDB();
			DBCollection collCrawlTable = db.getCollection(collCrawl);
			DBObject newField = new BasicDBObject();
			newField.put("url", url);
			newField.put("depth", depth);
			newField.put("crawled", 0);
			newField.put("flag", 0);
			collCrawlTable.insert(newField);
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
	}
	protected void AddDomain(String url,boolean status) {
		DB db = null;
		try
		{
			db = MONGODB.GetMongoDB();
			DBCollection collCrawlTable = db.getCollection("crawltarget");
			DBObject newField = new BasicDBObject();
			newField.put("url", url);
			newField.put("status", 0);
			collCrawlTable.insert(newField);
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
	}
	protected void AddSeed(String url,int depth,DBCollection collCrawlTable) {
		if(uniqueURL(collCrawlTable,url))
		{
			DBObject newField = new BasicDBObject();
			newField.put("url", url);
			newField.put("depth", depth);
			newField.put("crawled", 0);
			newField.put("flag", 0);
			collCrawlTable.insert(newField);
		}
	}
	

	private boolean UniqueDomain(String domain)
	{
		boolean res = false;
		DB db = null;
		try
		{
			db = MONGODB.GetMongoDB();
			DBCollection collCrawlTable = db.getCollection(collCrawl);
			DBObject query = new BasicDBObject("url",domain);
			DBObject findOne = collCrawlTable.findOne(query);
			if(findOne==null)
			{
				res = true;
			}
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
		return res;
	}
	public boolean NotFinished(String domain) {
		boolean finish=true;
		long a = 0;
		try
		{
			DB db = MONGODB.GetMongoDB();
			DBCollection collC = db.getCollection(collCrawl);
			
			BasicDBObject where_query;
			where_query = new BasicDBObject("crawled",1);
			a = collC.count(where_query);
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
		if(a==0){ finish = false; }
		return finish;
	}
	private int maxRelClass(double[] a)
	{
		int i=-1;
		double rel = a[0];
		for(int j=0;j<a.length;j++)
		{
			if(rel<a[j])
			{rel=a[j]; i = j;}
		}
		return i;
	}
	public void Delete(String domain) {
		try
		{
			long a = 0;
			DB db = MONGODB.GetMongoDB();
			DBCollection collC = db.getCollection(collCrawl);
			
			BasicDBObject where_query;
			where_query = new BasicDBObject("crawled",1);
			a = collC.count(where_query);
			if(a>1){
			BasicDBObject query = new BasicDBObject();
			query.put("url", domain);
			query.put("flag", 0);
			collC.remove(query);
			}
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
	}
	public boolean isText(String urlToCrawl) {
		int len = urlToCrawl.length();
		urlToCrawl = urlToCrawl.toLowerCase();
		String ext = urlToCrawl.substring(len-5, len);
		if(ext.contains("jpg")||ext.contains("png")||ext.contains("pdf")||ext.contains("jpeg")||ext.contains("gif"))
		{
			return false;
		}else
		{
			return true;
		}
	}
	public void updateNotCrawledComplete()
	{
		try
		{
			
			DB db = MONGODB.GetMongoDB();
			DBCollection collC = db.getCollection(collCrawl);
			
			BasicDBObject where_query;
			where_query = new BasicDBObject();
			where_query.put("flag", 1);
			where_query.put("url", java.util.regex.Pattern.compile(PrimaryDomain));
			DBCursor a = collC.find(where_query);
			if(a!=null)
			{
				while(a.hasNext())
				{
					DBObject q = new BasicDBObject();
					q.put("url", a.next().get("url"));
					if(!isText(a.next().get("url").toString()))
					{//System.out.println("not text");
						collC.remove(q);
					}else
					{
						//System.out.println("text");
						DBObject o = new BasicDBObject();
						o.put("url", a.next().get("url").toString());
						o.put("depth", new Integer(a.next().get("depth").toString()));
						o.put("crawled", new Integer( a.next().get("crawled").toString()));
						o.put("flag", 0);
						collC.update(q, o);
					}
				}
			}
		}catch (Exception ex)
		{
			System.out.println(ex);
		}
	}
}
