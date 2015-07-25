package testing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import main.Crawler.myWebCrawler;
// https://github.com/bimajatiwijaya/webCrawler.git
public class Main {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		String startURL = null;
	    Scanner in = new Scanner(System.in);
	    startURL = in.nextLine();
		myWebCrawler agent = new myWebCrawler(startURL);
		ExecutorService threadPool;
		int t = 50;
		threadPool = Executors.newFixedThreadPool(t);
		   // submit jobs to be executing by the pool
		   for (int i = 0; i < t; i++) {
			   if(agent.getStatus())
			   {
				   threadPool.submit(new Runnable() {
			           public void run() {
			        	   try {
								agent.crawl();   // Let the thread sleep for a while.
			      	            Thread.sleep(50);
						} catch (Exception e) {
							e.printStackTrace(); // TODO Auto-generated catch block
						}
			           }
			       });
			   }else
			   {
					
			   }
		   }
		   while(true)
		   {
			   if(threadPool.isTerminated())
			   {
				  System.out.println("finish"); 
			   }
		   }
	}

}
