package TFIDF;

import java.util.ArrayList;

public class TFIDFtest {

	public static void main(String[] args) throws Exception {
		/**
		 * contoh penggunaan :D
		 */
		String KeyWord = "udinus jawa tengah";
		String[] text = new String[3];
		text[0] = "baik jawa tengah udinus";
		text[1] = "jawa tengah universitas";
		text[2] = "udinus jawa tengah";
		TFIDFweighting a = new TFIDFweighting(text);
		a.create_terms_documents();
		a.create_vsm_all_documents();
		a.calculate_idf();
		ArrayList<ArrayList<Double>> doc_weight_norm = new ArrayList<ArrayList<Double>>(); 
		for(int i=0;i<text.length;i++)
		{
			a.set_document_weight(a.calculate_weight(a.document_terms.get(i)));
			ArrayList<Double> temp_weight_norm = a.calculate_weight_normalization(a.document_weight.get(i));
			doc_weight_norm.add(temp_weight_norm);
		}
		a.prepare_keyword_to_compare(KeyWord);
		for(int i=0;i<text.length;i++)
		{
			a.cosine_similarity(i, doc_weight_norm.get(i));
		}
		a.print_result_cosine_similarity();
		a.clear_old_keyword();
		KeyWord = "universitas jawa tengah";
		System.out.println();
		a.prepare_keyword_to_compare(KeyWord);
		for(int i=0;i<text.length;i++)
		{
			a.cosine_similarity(i, doc_weight_norm.get(i));
		}
		a.print_result_cosine_similarity();
	}
}