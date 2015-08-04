package main.TFIDF;

public class TFIDFtest {

	public static void main(String[] args) throws Exception {
		String KeyWord = "udinus jawa tengah";
		String[] text = new String[3];
		text[0] = "baik jawa tengah udinus";
		text[1] = "jawa tengah universitas";
		text[2] = "udinus jawa tengah";
		TFIDFweighting a = new TFIDFweighting(text);
		a.buildVSM();
		a.printDF();
		System.out.println("===TERMS DOKUMEN===");
		a.PrintTerms(a.GetAllTerm());
		a.compare(KeyWord);
		System.out.println("==========");
		a.printW();
		a.printWN();
		a.CosSimilarity();
		a.ClearStringCompare();
	}
}