package TFIDF;

import java.util.ArrayList;

public class Document{
	private String Doc;
	private String DocPreProcess;
	private ArrayList<TF> tFs;
	public Document()
	{}
	public Document(String doc)
	{
		this.tFs = new ArrayList<TF>();
		this.Doc = doc;
	}
	public void DoPreProcess() throws Exception
	{
		textParser tParser = new textParser(this.Doc);
		tParser.Tokenizer();
		tParser.removeStopWords();
		tParser.StemmingKalimat();
		this.DocPreProcess = tParser.GetResult();
	}
	public String GetPreProcess()
	{
		return this.DocPreProcess;
	}
	public void indexing()
	{
		String[] arrTerm = this.DocPreProcess.split(" ");
		int lenArrTerm = arrTerm.length;
		for(int i=0;i<lenArrTerm;i++)
		{
			insPrior(arrTerm[i]);
		}
	}
	public ArrayList<TF> indexing(String text)
	{
		ArrayList<TF> res = new ArrayList<TF>();
		String[] arrTerm = text.split(" ");
		int lenArrTerm = arrTerm.length;
		for(int i=0;i<lenArrTerm;i++)
		{
			insPrior(arrTerm[i],res);
		}
		return res;
	}
	public void insPrior(String newTerm,ArrayList<TF> newTerms)
	{
		boolean added = false;
		if(newTerms.isEmpty())
		{
			newTerms.add(new TF(newTerm));
		}
		else
		{// bad code .. upgrade please
			int i=0;
			for(TF r: newTerms)
			{
				int diff = r.GetTerm().compareTo(newTerm);
				if(diff>0){
					newTerms.add(i,new TF(newTerm));
					added=true;
					break;
				}else if(diff==0)
				{
					newTerms.get(i).IncrementFrequency();
					added=true;
					break;
				}
				i++;
			}
			if(added==false)
			{
				newTerms.add(i,new TF(newTerm));
			}
		}
	}
	
	public void insPrior(String newTerm)
	{
		boolean added = false;
		if(tFs.isEmpty())
		{
			tFs.add(new TF(newTerm));
		}
		else
		{// bad code .. upgrade please
			int i=0;
			for(TF r: tFs)
			{
				int diff = r.GetTerm().compareTo(newTerm);
				if(diff>0){
					tFs.add(i,new TF(newTerm));
					added=true;
					break;
				}else if(diff==0)
				{
					tFs.get(i).IncrementFrequency();
					added=true;
					break;
				}
				i++;
			}
			if(added==false)
			{
				tFs.add(i,new TF(newTerm));
			}
		}
	}
	public void PrintTerms()
	{
		for(TF z: this.tFs)
		{
			System.out.println(z.GetTerm()+","+z.GetFrequency());
		}
	}
	public ArrayList<TF> GetTerms()
	{
		if(this.tFs.isEmpty())
			return null;
		else
			return this.tFs;
	}
}
