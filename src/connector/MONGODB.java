package connector;

import java.io.IOException;
import java.util.Arrays;

import org.json.simple.JSONObject;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MONGODB {
	private static MongoCredential credential;
	private static MongoClient mongoClient;
	private static JSONObject jsonProp;
	
	public static DB GetMongoDB() throws Exception{
		try {
			if(jsonProp == null)
				jsonProp = getPropValues();
			
			if(credential == null)
				credential = MongoCredential.createMongoCRCredential(
						jsonProp.get("db_user").toString(), 
						jsonProp.get("db_name").toString(), 
						jsonProp.get("db_pass").toString().toCharArray());
			
			if(mongoClient == null)
				mongoClient = new MongoClient(new ServerAddress(
						jsonProp.get("db_host").toString(),
						Integer.parseInt(jsonProp.get("db_port").toString())), Arrays.asList(credential));
			
			return mongoClient.getDB(jsonProp.get("db_name").toString());
		} catch (Throwable e) {
			e.printStackTrace();
			throw new Exception(e.toString());
		}
	}
	
	@SuppressWarnings("unchecked")
	private static JSONObject getPropValues() throws IOException {
		JSONObject resultObj = new JSONObject();
		resultObj.put("db_host", "localhost");
		resultObj.put("db_port", "27017");
		resultObj.put("db_user", "crawluser");
		resultObj.put("db_pass", "crawluser");
		resultObj.put("db_name", "crawlta");
		return resultObj;
	}
}
