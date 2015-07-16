package main.dbcrawl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import main.setting;
import main.jenahelper.JenaHelper;

public class relevanCheck extends setting {
	public HashMap<String,String> Comentar = new HashMap<String,String>();
	public HashMap<String,String> WordUnique = new HashMap<String,String>();
	Iterator<Entry<String, String>> ComentarIterator;
	public relevanCheck()
	{
		JenaHelper temp = new JenaHelper(address,URI);
		this.Comentar = temp.getDetailClass();
		Set<Entry<String, String>> setTemp = this.Comentar.entrySet();
		ComentarIterator = setTemp.iterator();
	}
	/**
	 * 
	 * @param value
	 * cari kata unik tiap kelas
	 * @return
	 */
	public HashMap<String,String> GetUniqueWord(HashMap<String,String> value)
	{
		HashMap<String,String> result = new HashMap<String,String>();
		while(this.ComentarIterator.hasNext())
		{
			Map.Entry<String,String> dataComentar = (Map.Entry<String,String>)this.ComentarIterator.next();
			String compare = dataComentar.getValue();
			Set<Entry<String, String>> setTemp = value.entrySet();
			Iterator<Entry<String, String>> itTemp = setTemp.iterator();
			while(itTemp.hasNext()){
				Map.Entry<String,String> data = (Map.Entry<String,String>)itTemp.next();
				if(data.getKey()!=dataComentar.getKey()){
					String token[] = data.getValue().split(" ");
					for(int i=0;i<token.length;i++){
						if(compare.contains(token[i])){
							compare = compare.replace(token[i], "");// wrong
						}
					}
				}
			}
			result.put(dataComentar.getKey(),compare.trim());
		}
		return result;
	}
	public void readHashMap(HashMap<String,String> value)
	{
		Set<Entry<String, String>> setTemp = value.entrySet();
		Iterator<Entry<String, String>> itTemp = setTemp.iterator();
		while(itTemp.hasNext()){
			Map.Entry<String,String> data = (Map.Entry<String,String>)itTemp.next();
			System.out.println(data.getKey()+" = "+data.getValue());
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		relevanCheck tes = new relevanCheck();
		HashMap<String,String> uniqs = tes.GetUniqueWord(tes.Comentar);
		tes.readHashMap(uniqs);
	}

}
