package TFIDF;

public class DocumentWeighting {
	protected String[] doc;
	protected int maxDocuments = 1000;
	public Double[] cos = new Double[this.maxDocuments];
	protected int docCount;
	protected String docToCompare;//query
	public DocumentWeighting()
	{
		
	}
	public DocumentWeighting(String[] newDoc,String compare) {
		this.docCount = newDoc.length;
		doc = new String[this.docCount];
		for(int i=0;i<this.docCount;i++)
		{
			this.doc[i] = newDoc[i].trim();
		}
		this.docToCompare = compare;
	}
	protected String CombineDocs()
	{
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<this.docCount;i++)
		{
			sb.append(doc[i]);
		}
		return sb.toString();
	}
	protected void PreProcessingDocs() throws Exception
	{
		for(int i=0;i<this.docCount;i++)
		{
			Document a = new Document(this.doc[i]);
			a.DoPreProcess();
			this.doc[i] = a.GetPreProcess();
		}
	}
	protected void PreProcessingDocs(String docToCom) throws Exception
	{
		Document a = new Document(docToCom.trim());
		a.DoPreProcess();
		docToCompare = a.GetPreProcess();
	}
	public String GetDoc(int idx)
	{
		return this.doc[idx];
	}
	public double CosSimilarity(int idx)
	{
		return this.cos[idx];
	}
}
