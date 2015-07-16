package main.Crawler;

import java.io.*;
import java.net.*;

public class myHTTPManager {
	public final static int HTTP_PORT = 80;
	private Socket socket;
	public myHTTPManager(){
		
	}
	public String downloadPage(URL pageURL){
		String line;
		InputStream pStream = null;
		StringBuffer thePage = new StringBuffer();
		
		try{
			pStream = getPageStream(pageURL);
			if(pStream == null) return "";
		}catch(Exception error){
			System.out.println("get(host,file) failed! "+error);
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(pStream));
		try{
			while((line = br.readLine())!=null){
				thePage.append(line);//read url
			}
			br.close();
		}catch(Exception error){}
		return thePage.toString();
	}
	private InputStream getPageStream(URL url) throws UnknownHostException, IOException {
		if(url.getPort() == -1)
		{
			url = new URL(url.getProtocol(),url.getHost(),HTTP_PORT,url.getFile());
		}
		socket = new Socket(url.getHost(),url.getPort());
		socket.setSoTimeout(12000);
		PrintStream out = new PrintStream(socket.getOutputStream());
		out.print("GET "+ url.getFile() + " HTTP/1.0\n");
		
		out.print("Referer: " + url.getPath() + "\r\n");
		
		out.print("User-Agent : SKRIPSI_myWebCrawler/1.0" +"\r\n");
		
		out.print("From: bimajatiwijaya@gmail.com"+"\r\n");
		out.print("Pragma: no-cache" +"\r\n");
		
		out.print("Host: "+ url.getHost() + ":" + url.getPort() +"\r\n");
		out.print("Acept: */*" +"\r\n");
		out.print("\r\n");
		
		out.flush();
		
		InputStream in = socket.getInputStream();
		return in;
	}
	
}
