package main.TFIDF;

import java.util.ArrayList;
import java.util.List;

public class TFIDFweighting extends DocumentWeighting{
	private ArrayList<TF> termsAllDocument;
	private ArrayList<ArrayList<TF>> termsDoc = new ArrayList<ArrayList<TF>>(this.docCount);
	private ArrayList<ArrayList<Double>> w = new ArrayList<ArrayList<Double>>(this.docCount);
	private ArrayList<ArrayList<Double>> wNormal = new ArrayList<ArrayList<Double>>(this.docCount);
	public ArrayList<Double> vectorNorm = new ArrayList<Double>(this.docCount);
	public ArrayList<Double> devNorm = new ArrayList<Double>(this.docCount);
	public ArrayList<Double> IDF = new ArrayList<Double>();
	// query
	private ArrayList<Double> wQ = new ArrayList<Double>();
	private ArrayList<Double> wNQ = new ArrayList<Double>();
	public Double DividerQ;
	public Double DividerQN;
	private ArrayList<TF> termsDocToCompare;
	//
	private ArrayList<Integer> df = new ArrayList<Integer>();
	public TFIDFweighting(){}
	public TFIDFweighting(String documents[]) throws Exception
	{
		this.docCount = documents.length;
		this.doc = new String[this.docCount];
		for(int i=0;i<this.docCount;i++)
		{
			this.doc[i] = documents[i].trim();
		}
		this.PreProcessingDocs();
	}
	public void buildVSM()
	{
		String allDocs = this.CombineDocs();
		Document a = new Document(allDocs);
		ArrayList<TF> res = a.indexing(allDocs);
		this.termsAllDocument = res;
		this.initialDF();
		this.VSMdocs();
		this.CalIDF();
		this.CalcWeight();
	}
	private void VSMdocs()
	{
		for(int i=0;i<this.docCount;i++)
		{
			this.termsDoc.add(CreateVSMDoc(this.doc[i]));
		}
	}
	private void initialDF()
	{
		for(int i=0;i<this.termsAllDocument.size();i++)
		{
			df.add(0);
		}
	}
	protected ArrayList<TF> CreateVSMDoc(String docN)
	{
		ArrayList<TF> res = new ArrayList<TF>();
		String[] termDoc = docN.split(" ");
		int len = termDoc.length;
		int i=0;
		for(TF prin : this.termsAllDocument)
		{
			TF newT = new TF();
			newT.SetTerm(prin.GetTerm());
			for(int j=0;j<len;j++)
			{
				if(termDoc[j].compareTo(newT.GetTerm())==0)
				{
					newT.IncrementFrequency();
				}
			}
			if(newT.GetFrequency()>0)
			{
				df.set(i,df.get(i)+1);
			}
			i++;
			res.add(newT);
		}
		return res;
	}
	public ArrayList<TF> GetAllTerm()
	{
		return this.termsAllDocument;
	}
	public String termsString()
	{
		StringBuilder sb = new StringBuilder();
		for(TF prin : this.termsAllDocument)
		{
			sb.append(prin.GetTerm()+",");
		}
		return sb.toString();
	}
	
	private void CalIDF()
	{
		int len = this.termsAllDocument.size();
		//System.out.println("total terms : "+len);
		for(int i=0;i<len;i++)
		{
			//this.termsAllDocument.get(i).SetIDF(this.docCount*1.0,this.df.get(i));
			this.IDF.add(Math.log10(this.docCount / this.df.get(i) * 1.0));
		}
	}
	public void PrintTermsDoc(int i)
	{
		System.out.println("Document "+(i+1)+" :");
		for(TF prin : this.termsDoc.get(i))
		{
			//System.out.println("{"+prin.GetFrequency()+","+String.format("%.3f", prin.GetIDF())+","+prin.GetTerm()+"}");
			System.out.println(prin.GetTerm());
		}
	}
	protected void CalcWeight() // sekalian weight normalisasi
	{
		for(int i=0;i<this.docCount;i++)
		{
			ArrayList<Double> x = new ArrayList<Double>(this.termsAllDocument.size());
			int v=0;
			for(TF prin : this.termsDoc.get(i))
			{
				//x.add((double) (prin.GetFrequency()*this.termsAllDocument.get(v).GetIDF()));
				x.add((double) (prin.GetFrequency()*this.IDF.get(v)));
				v++;
			}
			this.w.add(x);
			this.vectorNorm.add(VectorNorm(x));
		}
		this.WeightNormalization();
	}
	
	private void WeightNormalization()//Unit Vectors - Normalizing
	{
		for(int i=0;i<this.docCount;i++)
		{
			ArrayList<Double> x = new ArrayList<Double>(this.termsAllDocument.size());
			for(Double prin : this.w.get(i))
			{
				x.add(prin.doubleValue()/this.vectorNorm.get(i));
			}
			this.wNormal.add(x);
		}
	}
	
	private double VectorNorm(List<Double> x)
	{
		double result = 0;
		for(Double val : x)
		{
			result += (val*val);
		}
		return Math.sqrt(result);
	}
	public void printW()
	{
		for(int i=0;i<this.docCount;i++)
		{
			System.out.println("\ndocument weight : "+(i+1));
			for(Double prin : this.w.get(i))
			{
				System.out.println(String.format("%,5f", prin));
			}
		}
	}
	public void printWN()
	{
		for(int i=0;i<this.docCount;i++)
		{
			System.out.println("\ndocument weight normalitation: "+(i+1));
			for(Double prin : this.wNormal.get(i))
			{
				System.out.println(String.format("%,5f", prin));
			}
		}
	}
	public void PrintTerms(ArrayList<TF> t)
	{
		int i=1;
		for(TF prin : t)
		{
			System.out.println(i+". {"+prin.GetTerm()+","+this.IDF.get(i-1)+","+prin.GetFrequency()+"}");i++;
//			System.out.print(prin.GetTerm()+" ");
		}
	}
	public void printDF()
	{
		for(Integer prin : this.df)
		{
			System.out.println(prin);
		}
	}
	// compare docs with query //////////////////////////////////////////////////////////////////////////////
	public void printVsmQ()
	{
		for(TF prin : this.termsDocToCompare)
		{
			System.out.println(prin.GetFrequency());
		}
		//System.out.println("total doc. "+this.docCount);
	}
	public void printWQ()
	{
		for(Double prin : this.wQ)
		{
			System.out.println(String.format("%,5f", prin));
		}
	}
	public void printWNQ()
	{
		for(Double prin : this.wNQ)
		{
			System.out.println(String.format("%,5f", prin));
		}
	}
	private void WeightNQuery()//Unit Vectors - Normalizing
	{
		for(Double prin : this.wQ)
		{
			this.wNQ.add(prin.doubleValue()/this.DividerQ);
		}
	}
	protected void CalcWeightQ() // sekalian weight normalisasi
	{
		int i = 0;
		for(TF prin : this.termsDocToCompare)
		{
			this.wQ.add((double) (prin.GetFrequency()*this.IDF.get(i)));i++;
		}
		this.DividerQ = this.VectorNorm(wQ);
		this.WeightNQuery();
	}
	protected ArrayList<TF> CreateVSMQuery(String docN)
	{
		ArrayList<TF> res = new ArrayList<TF>();
		String[] termDoc = docN.split(" ");
		int len = termDoc.length;
		for(TF prin : this.termsAllDocument)
		{
			TF newT = new TF();
			newT.SetTerm(prin.GetTerm());
			for(int j=0;j<len;j++)
			{
				if(termDoc[j].compareTo(newT.GetTerm())==0)
				{
					newT.IncrementFrequency();
				}
			}
			res.add(newT);
		}
		return res;
	}
	private boolean notFound(){
		boolean result = true;
		for(TF prin : this.termsDocToCompare)
		{
			if(prin.GetFrequency()>0)
			{
				result = false; break;
			}
		}
		return result;
	}
	public boolean compare(String query) throws Exception
	{
		this.docToCompare = query;
		this.PreProcessingDocs(query);
		this.termsDocToCompare = this.CreateVSMQuery(this.docToCompare);
		if(notFound()==false){
			this.CalcWeightQ();
			this.DividerQN = VectorNorm(this.wNQ);
			for(int i=0; i<this.docCount;i++) // cosine similarity formula
			{
				double a=0.0;
				int j = 0;
				ArrayList<Double> wn = this.wNormal.get(i);
				this.devNorm.add(VectorNorm(wn));
				for(Double wdn : wn)
				{
					a += (wdn.doubleValue() * this.wNQ.get(j));
					j++;
				}
				this.cos[i] = a / Math.sqrt(this.devNorm.get(i)*this.DividerQN);
			}
			return true;
		}else
		{
			return false;
		}
	}
	public void CosSimilarity()
	{
		for(int i=0;i<this.docCount;i++)
		{
			System.out.println("Document "+(i+1)+" : "+this.cos[i]);
		}
	}
	public void ClearStringCompare()
	{
		this.docToCompare = null;
		this.termsDocToCompare.clear();
		this.wQ.clear();
		this.wNQ.clear();
		this.DividerQ=0.0;
		this.DividerQN=0.0;
	}
}
