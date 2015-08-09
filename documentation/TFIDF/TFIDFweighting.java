package TFIDF;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author bima
 * pembobotn TFIDF dan cosine similarity
 * atribut :
 * 1. dokumen[] array string => jadikan satu kemudian bentuk terms frekuensi
 * 2. 
 * 
 * proses
 * 1. inisialisasi
 * 
 */
public class TFIDFweighting extends DocumentWeighting{
	//
	public ArrayList<TF> terms_all_document;
	public ArrayList<Integer> document_frequency = new ArrayList<Integer>();
	public ArrayList<Double> invers_document_frecuency = new ArrayList<Double>();
	// documents
	public ArrayList<ArrayList<TF>> document_terms = new ArrayList<ArrayList<TF>>(this.docCount);
	public ArrayList<ArrayList<Double>> document_weight = new ArrayList<ArrayList<Double>>(this.docCount);
	// query or keyword
	public ArrayList<Double> keyword_weight = new ArrayList<Double>();
	public ArrayList<TF> keyword_terms;

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
	public void create_terms_documents()
	{
		String allDocs = this.CombineDocs();
		Document a = new Document(allDocs);
		ArrayList<TF> res = a.indexing(allDocs);
		this.terms_all_document = res;
		for(int i=0;i<this.terms_all_document.size();i++)
		{
			document_frequency.add(0);
		}
	}
	public void create_vsm_all_documents()
	{
		for(int i=0;i<this.docCount;i++)
		{
			this.document_terms.add(CreateVSMDoc(this.doc[i]));
		}
	}
	public void set_document_weight(ArrayList<Double> weight)
	{
		this.document_weight.add(weight);
	}
	protected ArrayList<TF> CreateVSMDoc(String docN)
	{
		ArrayList<TF> res = new ArrayList<TF>();
		String[] termDoc = docN.split(" ");
		int len = termDoc.length;
		int i=0;
		for(TF prin : this.terms_all_document)
		{
			TF newTF = new TF();
			newTF.SetTerm(prin.GetTerm());
			for(int j=0;j<len;j++)
			{
				if(termDoc[j].compareTo(newTF.GetTerm())==0)
				{
					newTF.IncrementFrequency();
				}
			}
			if(newTF.GetFrequency()>0)
			{
				document_frequency.set(i,document_frequency.get(i)+1);
			}
			i++;
			res.add(newTF);
		}
		return res;
	}
	public ArrayList<TF> GetAllTerm()
	{
		return this.terms_all_document;
	}
	public void calculate_idf()
	{
		int len = this.terms_all_document.size();
		for(int i=0;i<len;i++)
		{
			this.invers_document_frecuency.add(Math.log10(this.docCount* 1.0 / this.document_frequency.get(i)));
		}
	}
	public ArrayList<Double> calculate_weight(ArrayList<TF> document) // sekalian weight normalisasi
	{
		ArrayList<Double> x = new ArrayList<Double>(this.terms_all_document.size());
		int idx = 0;
		for(TF prin : document)
		{
			x.add((double) (prin.GetFrequency()*this.invers_document_frecuency.get(idx)));
			idx++;
		}
		return x;
	}
	
	public ArrayList<Double> calculate_weight_normalization(ArrayList<Double> weight)
	{
		ArrayList<Double> result = new ArrayList<Double>(this.terms_all_document.size());
		double vector_norm = this.vector_normalization(weight);
		for(Double prin : weight)
		{
			if(vector_norm==0){
				result.add(0.0);
			}else{
				result.add(prin.doubleValue()/vector_norm);
			}
		}
		return result;
	}
	
	public double vector_normalization(List<Double> x)
	{
		double result = 0;
		for(Double val : x)
		{
			result += (val*val);
		}
		return Math.sqrt(result);
	}
	protected ArrayList<TF> create_vector_space_model(String docN)
	{
		ArrayList<TF> res = new ArrayList<TF>();
		String[] termDoc = docN.split(" ");
		int len = termDoc.length;
		for(TF prin : this.terms_all_document)
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
	// comparing area
	public boolean notFound(ArrayList<TF> tfs_compare){
		boolean result = true;
		for(TF prin : tfs_compare)
		{
			if(prin.GetFrequency()>0)
			{
				result = false; break;
			}
		}
		return result;
	}
	public void prepare_keyword_to_compare(String keyword) throws Exception
	{
		if(this.invers_document_frecuency==null){
			System.out.println("You have to preprocessing all document first! :-)");
		}else{
			this.docToCompare = keyword;
			this.PreProcessingDocs(keyword);
			this.keyword_terms = this.CreateVSMDoc(this.docToCompare);
			this.keyword_weight = this.calculate_weight(this.keyword_terms);
		}
	}
	public boolean cosine_similarity(int index_document,ArrayList<Double> doc_weight_norm)
	{
		if(notFound(this.keyword_terms)==false)
		{
			ArrayList<Double> key_weight_norm = this.calculate_weight_normalization(this.keyword_weight);
			double a=0.0;
			int j = 0;
			for(Double wdn : doc_weight_norm)
			{
				a += (wdn.doubleValue() * key_weight_norm.get(j));
				j++;
			}
			this.cos[index_document] = a / Math.sqrt(vector_normalization(doc_weight_norm)*vector_normalization(key_weight_norm));
			return true;
		}else
		{
			return false;
		}
	}
	public void print_result_cosine_similarity()
	{
		for(int i=0;i<this.docCount;i++)
		{
			System.out.println("Document "+(i+1)+" : "+this.cos[i]);
		}
	}
	public void clear_old_keyword()
	{
		this.docToCompare = null;
		this.keyword_terms.clear();
		this.keyword_weight.clear();
	}
}
