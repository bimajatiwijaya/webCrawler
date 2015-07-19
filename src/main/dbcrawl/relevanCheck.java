package main.dbcrawl;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import main.setting;
import main.TFIDF.Document;
import main.TFIDF.TF;
import main.jenahelper.JenaHelper;

public class relevanCheck extends setting {
	public HashMap<String,String> Comentar = new HashMap<String,String>();
	public HashMap<String,String> WordUnique = new HashMap<String,String>();
	Iterator<Entry<String, String>> ComentarIterator;
	public relevanCheck()
	{
		JenaHelper temp = new JenaHelper(address,URI);
		this.Comentar = temp.getDetailClass();
	}
	/**
	 * 
	 * @param value
	 * cari kata unik tiap kelas
	 * @return
	 */
	public HashMap<String,String> GetUniqueWord(HashMap<String,String> value)
	{
		return null;
	}
	public HashMap<String,Document> GetUniqueWord_2(HashMap<String,Document> value) throws Exception
	{
		HashMap<String,Document> result = new HashMap<String,Document>();
		Set<Entry<String, Document>> setTemp = value.entrySet();
		Iterator<Entry<String, Document>> itTemp = setTemp.iterator();
		Set<Entry<String, String>> setTempCom = this.Comentar.entrySet();
		this.ComentarIterator = setTempCom.iterator();
		while(this.ComentarIterator.hasNext())
		{
			if(itTemp.hasNext()){
				Map.Entry<String,String> dataComentar = (Map.Entry<String,String>)this.ComentarIterator.next();
				Map.Entry<String,Document> data = (Map.Entry<String,Document>)itTemp.next();
				String compare = dataComentar.getValue();
				Document CompareDoc = new Document(compare);
				if(data.getKey()==dataComentar.getKey()){
					CompareDoc.DoPreProcess();
					CompareDoc.indexing();
					ArrayList<TF> tfc = CompareDoc.GetTerms();
					ArrayList<TF> all = data.getValue().GetTerms();
					System.out.println(tfc.size());
					for(TF allt : all)
					{
						int i=0;
						String Sallt = allt.GetTerm();
						for(TF tc: tfc)
						{
							if(tc.GetTerm()==Sallt)
							{
								System.out.println("PODO");
								tfc.remove(i);
								break;
							}
							i++;
						}
					}
					System.out.println("after "+tfc.size());
					
				}else{
					System.out.println("bla");
				}
			}
		}
		return null;
	}
	public void readHM(HashMap<String,String> value)
	{
		Set<Entry<String, String>> setTemp = value.entrySet();
		Iterator<Entry<String, String>> itTemp = setTemp.iterator();
		while(itTemp.hasNext()){
			Map.Entry<String,String> data = (Map.Entry<String,String>)itTemp.next();
			System.out.println(data.getKey()+" = "+data.getValue());
		}
	}
	public void readHM_SD(HashMap<String,Document> value)
	{
		Set<Entry<String, Document>> setTemp = value.entrySet();
		Iterator<Entry<String, Document>> itTemp = setTemp.iterator();
		while(itTemp.hasNext()){
			Map.Entry<String,Document> data = (Map.Entry<String,Document>)itTemp.next();
			System.out.println(data.getKey()+" = "+data.getValue().GetTerms().size());
//			for(TF temp : data.getValue().GetTerms())
//			{
//				System.out.print(temp.GetTerm()+" ");
//			}
		}
	}
	public HashMap<String,Document> ComentExcKey(HashMap<String,String> value) throws Exception
	{
		HashMap<String,Document> result = new HashMap<String,Document>();
		Set<Entry<String, String>> setTempCom = this.Comentar.entrySet();
		this.ComentarIterator = setTempCom.iterator();
		while(this.ComentarIterator.hasNext())
		{
			StringBuilder text = new StringBuilder();
			Map.Entry<String,String> dataComentar = (Map.Entry<String,String>)this.ComentarIterator.next();
			Set<Entry<String, String>> setTemp = value.entrySet();
			Iterator<Entry<String, String>> itTemp = setTemp.iterator();
			while(itTemp.hasNext()){
				Map.Entry<String,String> data = (Map.Entry<String,String>)itTemp.next();
				if(dataComentar.getKey()!=data.getKey())
				{
					text.append(data.getValue()+" ");
				}
			}
			Document temp = new Document(text.toString());
			temp.DoPreProcess();
			temp.indexing();
			result.put(dataComentar.getKey(),temp);
		}
		return result;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		relevanCheck tes = new relevanCheck();
		//HashMap<String,String> uniqs = tes.GetUniqueWord(tes.Comentar);
		try {
			HashMap<String,Document> cek = tes.ComentExcKey(tes.Comentar);
			//tes.readHM_SD(cek);
			tes.GetUniqueWord_2(cek);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
