package testing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import main.Crawler.myWebCrawler;

public class Main {
	protected String executeCommand(String command) {
		 
		StringBuffer output = new StringBuffer();
 
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = 
                           new BufferedReader(new InputStreamReader(p.getInputStream()));
 
			String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output.toString();
	}
	@SuppressWarnings("resource")
	public static void main(String[] args) {
//		String startURL = null;
//	    Scanner in = new Scanner(System.in);
//	    startURL = in.nextLine();
		/**
		 *  http://semarangkota.go.id/  =====
		 *  kebumenkab.go.id =======
			http://www.banyumaskab.go.id/ ======
			http://pekalongankab.go.id/ =====
			
			13. http://www.semarangkab.go.id/ ====
			7. http://magelangkab.go.id/ === 72
			11. http://rembangkab.go.id/ 503
			2. http://patikab.go.id/  ==paused==
			10. http://tegalkab.go.id/ ==paused==
			http://cilacapkab.go.id/v2/ ==53
			http://batangkab.go.id/ XXX g iso  
			3. http://grobogan.go.id/ XXX
			6. http://www.karanganyarkab.go.id/  XXX
			
			
			 
			
			12. http://sukoharjokab.go.id/  XXX
			
			
		 */
		myWebCrawler agent = new myWebCrawler("http://cilacapkab.go.id/v2/");
		ExecutorService threadPool;
		int t = 1;
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
