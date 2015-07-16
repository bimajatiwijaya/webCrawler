package main.TFIDF;

public class TF {
	private String term;
	private int frequency;
	//constructor
	public TF()
	{
		
	}
	public TF(String newTerm)
	{
		SetTerm(newTerm);
		SetFrequency(1);
	}
	// method
	public void SetTerm(String newTerm)
	{
		this.term = newTerm;
	}
	public void SetFrequency(int newFreq)
	{
		this.frequency = newFreq;
	}
	public String GetTerm()
	{
		return this.term;
	}
	public int GetFrequency()
	{
		return this.frequency;
	}
	public void IncrementFrequency()
	{
		this.frequency++;
	}
}
