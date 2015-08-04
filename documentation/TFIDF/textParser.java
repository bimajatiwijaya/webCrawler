package TFIDF;

import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.id.*;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;


import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
/**
 * 
 * @author bima
 * 
 */
public class textParser {
	private static String[] SWords = new String[] {"a","b","c","d","mari","semoga","assalamu’alaikum","abad","acara","ada","adalah","adanya","adapun","agak","agaknya","agama","agar","agustus","air","akan","akankah","akhir","akhiri","akhirnya","akibat","aku","akulah","alam","album","amat","amatlah","amerika","anak","and","anda","andalah","anggota","antar","antara","antarabangsa","antaranya","apa","apaan","apabila","apakah","apalagi","apatah","api","april","artikel","artinya","as","asal","asalkan","asas","asia","asing","atas","atau","ataukah","ataupun","australia","awal","awalnya","awam","badan","bagai","bagaikan","bagaimana","bagaimanakah","bagaimanapun","bagainamakah","bagi","bagian","bahagian","bahan","baharu","bahasa","bahawa","bahkan","bahwa","bahwasannya","bahwasanya","baiknya","bakal","bakalan","balik","bandar","bangsa","bank","banyak","bapak","barang","barangan","barat","baru","baru-baru","bawah","beberapa","begini","beginian","beginikah","beginilah","begitu","begitukah","begitulah","begitupun","bekas","bekerja","belakang","belakangan","belanda","beli","beliau","belum","belumlah","benar","benarkah","benarlah","bentuk","berada","berakhir","berakhirlah","berakhirnya","berapa","berapakah","berapalah","berapapun","berarti","berasal","berat","berawal","berbagai","berbanding","berbeda","berdasarkan","berdatangan","berharap","berhasil","beri","berikan","berikut","berikutan","berikutnya","berita","berjalan","berjaya","berjumlah","berkaitan","berkali","berkali-kali","berkata","berkehendak","berkeinginan","berkenaan","berlainan","berlaku","berlalu","berlangsung","berlebihan","bermacam","bermacam-macam","bermain","bermaksud","bermula","bernama","bernilai","bersama","bersama-sama","bersiap","bertanya","bertemu","berturut","bertutur","berubah","berujar","berupa","besar","besok","betul","betulkah","bhd","biasa","biasanya","bidang","bila","bilakah","bilion","bintang","bisa","bisakah","blog","bn","bola","boleh","bolehkah","bolehlah","buat","bukan","bukankah","bukanlah","bukannya","buku","bulan","bumi","bung","bursa","cadangan","cara","caranya","catch","china","click","code","copyright","cukup","cukupkah","cukuplah","cuma","daerah","dagangan","dahulu","dalam","dan","dana","dapat","dari","daripada","dasar","datang","datuk","dekat","demi","demikian","demikianlah","dengan","depan","derivatives","desa","desember","detik","dewan","di","dia","diadakan","diakhiri","diakhirinya","dialah","dianggap","diantara","diantaranya","diberi","diberikan","diberikannya","dibuat","dibuatnya","dibuka","dicatatkan","didapat","didatangkan","didirikan","diduga","digunakan","diibaratkan","diibaratkannya","diingat","diingatkan","diinginkan","dijangka","dijawab","dijelaskan","dijelaskannya","dikarenakan","dikatakan","dikatakannya","dikenal","dikerjakan","diketahui","diketahuinya","dikira","dilakukan","dilalui","dilihat","dimaksud","dimaksudkan","dimaksudkannya","dimaksudnya","dimana","diminta","dimintai","dimisalkan","dimulai","dimulailah","dimulainya","dimungkinkan","dini","diniagakan","dipastikan","diperbuat","diperbuatnya","dipergunakan","diperkirakan","diperlihatkan","diperlukan","diperlukannya","dipersoalkan","dipertanyakan","dipunyai","diri","dirilis","dirinya","dis","disampaikan","disebut","disebutkan","disebutkannya","disember","disini","disinilah","distrik","ditambahkan","ditandaskan","ditanya","ditanyai","ditanyakan","ditegaskan","ditemukan","ditujukan","ditunjuk","ditunjuki","ditunjukkan","ditunjukkannya","ditunjuknya","ditutup","dituturkan","dituturkannya","diucapkan","diucapkannya","diungkapkan","document.write","dolar","dong","dr","dua","dulu","dunia","effective","ekonomi","eksekutif","eksport","empat","enam","enggak","enggaknya","entah","entahlah","era","eropa","err","faedah","feb","film","gat","gedung","gelar","gettracker","global","grup","guna","gunakan","gunung","hadap","hadapan","hal","hampir","hanya","hanyalah","harga","hari","harian","harus","haruslah","harusnya","hasil","hendak","hendaklah","hendaknya","hidup","hingga","https","hubungan","hukum","hutan","ia","iaitu","ialah","ibarat","ibaratkan","ibaratnya","ibu","ii","iklan","ikut","ilmu","indeks","india","indonesia","industri","informasi","ingat","inggris","ingin","inginkah","inginkan","ini","inikah","inilah","internasional","islam","isnin","isu","italia","itu","itukah","itulah","jabatan","jadi","jadilah","jadinya","jakarta","jalan","jalur","jaman","jan","jangan","jangankan","janganlah","januari","jauh","jawab","jawaban","jawabnya","jawatan","jawatankuasa","jelas","jelaskan","jelaslah","jelasnya","jenis","jepang","jepun","jerman","jika","jikalau","jiwa","jual","jualan","juga","julai","jumaat","jumat","jumlah","jumlahnya","jun","juni","justru","juta","kabar","kabupaten","kadar","kala","kalangan","kalau","kalaulah","kalaupun","kali","kalian","kalimantan","kami","kamilah","kamis","kamu","kamulah","kan","kantor","kapal","kapan","kapankah","kapanpun","karena","karenanya","karya","kasus","kata","katakan","katakanlah","katanya","kaunter","kawasan","ke","keadaan","kebetulan","kebutuhan","kecamatan","kecil","kedua","kedua-dua","keduanya","kedudukan","kegiatan","kehidupan","keinginan","kejadian","kekal","kelamaan","kelihatan","kelihatannya","kelima","kelompok","keluar","keluarga","kelurahan","kembali","kementerian","kemudahan","kemudian","kemungkinan","kemungkinannya","kenaikan","kenapa","kenyataan","kepada","kepadanya","kepala","kepentingan","keputusan","kerajaan","kerana","kereta","kerja","kerjasama","kes","kesampaian","keselamatan","keseluruhan","keseluruhannya","kesempatan","kesihatan","keterangan","keterlaluan","ketiga","ketika","ketua","keuntungan","kewangan","khamis","khusus","khususnya","kini","kinilah","kira","kira-kira","kiranya","kita","kitalah","klci","klibor","klik","km","kok","komentar","kompas","komposit","kondisi","kontrak","korban","korea","kos","kota","kuala","kuasa","kukuh","kumpulan","kurang","kurangnya","lagi","lagian","lagu","lah","lain","lainnya","lakukan","laku","lalu","lama","lamanya","langkah","langsung","lanjut","lanjutnya","laporan","laut","lebih","lembaga","lepas","lewat","lima","lingkungan","login","lokasi","lot","luar","luas","lumpur","mac","macam","mahkamah","mahu","majlis","maka","makanan","makanya","makin","maklumat","malah","malahan","malam","malaysia","mampu","mampukah","mana","manakala","manalagi","mantan","manusia","masa","masalah","masalahnya","masih","masihkah","masing","masing-masing","masuk","masyarakat","mata","mau","maupun","measure","media","mei","melainkan","melakukan","melalui","melawan","melihat","melihatnya","memandangkan","memang","memastikan","membantu","membawa","memberi","memberikan","membolehkan","membuat","memerlukan","memihak","memiliki","meminta","memintakan","memisalkan","memperbuat","mempergunakan","memperkirakan","memperlihatkan","mempersiapkan","mempersoalkan","mempertanyakan","mempunyai","memulai","memungkinkan","menaiki","menambah","menambahkan","menandaskan","menanti","menantikan","menanya","menanyai","menanyakan","menarik","menawarkan","mencapai","mencari","mencatatkan","mendapat","mendapatkan","mendatang","mendatangi","mendatangkan","menegaskan","menerima","menerusi","mengadakan","mengakhiri","mengaku","mengalami","mengambil","mengapa","mengatakan","mengatakannya","mengenai","mengerjakan","mengetahui","menggalakkan","menggunakan","menghadapi","menghendaki","mengibaratkan","mengibaratkannya","mengikut","mengingat","mengingatkan","menginginkan","mengira","mengucapkan","mengucapkannya","mengumumkan","mengungkapkan","mengurangkan","meninggal","meningkat","meningkatkan","menjadi","menjalani","menjawab","menjelang","menjelaskan","menokok","menteri","menuju","menunjuk","menunjuki","menunjukkan","menunjuknya","menurut","menuturkan","menyaksikan","menyampaikan","menyangkut","menyatakan","menyebabkan","menyebutkan","menyediakan","menyeluruh","menyiapkan","merasa","mereka","merekalah","merosot","merupakan","meski","meskipun","mesyuarat","meyakini","meyakinkan","milik","militer","minat","minggu","minta","minyak","mirip","misal","misalkan","misalnya","mobil","modal","mohd","mudah","mula","mulai","mulailah","mulanya","muncul","mungkin","mungkinkah","musik","musim","nah","naik","nama","namun","nanti","nantinya","nasional","negara","negara-negara","negeri","new","niaga","nilai","nomor","noun","nov","november","numeral","numeralia","nya","nyaris","nyatanya","of","ogos","okt","oktober","olah","oleh","olehnya","operasi","orang","organisasi","pada","padahal","padanya","pagetracker","pagi","pak","paling","pameran","panjang","pantas","papan","para","paras","parlimen","partai","parti","particle","pasar","pasaran","password","pasti","pastilah","pasukan","paticle","pegawai","pejabat","pekan","pekerja","pelabur","pelaburan","pelancongan","pelanggan","pelbagai","peluang","pemain","pembangunan","pemberita","pembinaan","pemerintah","pemerintahan","pemimpin","pendapatan","penduduk","penerbangan","pengarah","pengeluaran","pengerusi","pengguna","penggunaan","pengurusan","peniaga","peningkatan","penting","pentingnya","per","perancis","perang","peratus","percuma","perdagangan","perdana","peringkat","perjanjian","perkara","perkhidmatan","perladangan","perlu","perlukah","perlunya","permintaan","pernah","perniagaan","persekutuan","persen","persidangan","persoalan","pertama","pertandingan","pertanyaan","pertanyakan","pertubuhan","pertumbuhan","perubahan","perusahaan","pesawat","peserta","petang","pihak","pihaknya","pilihan","pinjaman","polis","polisi","politik","pos","posisi","presiden","prestasi","produk","program","projek","pronomia","pronoun","proses","proton","provinsi","pt","pubdate","pukul","pula","pulau","pun","punya","pusat","rabu","radio","raja","rakan","rakyat","ramai","rantau","rasa","rasanya","rata","raya","rendah","republik","resmi","ribu","ringgit","root","ruang","rumah","rupa","rupanya","saat","saatnya","sabah","sabtu","sahaja","saham","saja","sajalah","sakit","salah","saling","sama","sama-sama","sambil","sampai","sampaikan","sana","sangat","sangatlah","sarawak","satu","sawit","saya","sayalah","sdn","se","sebab","sebabnya","sebagai","sebagaimana","sebagainya","sebagian","sebahagian","sebaik","sebaiknya","sebaliknya","sebanyak","sebarang","sebegini","sebegitu","sebelah","sebelum","sebelumnya","sebenarnya","seberapa","sebesar","sebetulnya","sebisanya","sebuah","sebut","sebutlah","sebutnya","secara","secukupnya","sedang","sedangkan","sedemikian","sedikit","sedikitnya","seenaknya","segala","segalanya","segera","segi","seharusnya","sehingga","seingat","sejak","sejarah","sejauh","sejenak","sejumlah","sekadar","sekadarnya","sekali","sekali-kali","sekalian","sekaligus","sekalipun","sekarang","sekaranglah","sekecil","seketika","sekiranya","sekitar","sekitarnya","sekolah","sektor","sekurang","sekurangnya","sekuriti","sela","selagi","selain","selaku","selalu","selama","selama-lamanya","selamanya","selanjutnya","selasa","selatan","selepas","seluruh","seluruhnya","semacam","semakin","semalam","semampu","semampunya","semasa","semasih","semata","semaunya","sementara","semisal","semisalnya","sempat","semua","semuanya","semula","sen","sendiri","sendirian","sendirinya","senin","seolah","seolah-olah","seorang","sepak","sepanjang","sepantasnya","sepantasnyalah","seperlunya","seperti","sepertinya","sepihak","sept","september","serangan","serantau","seri","serikat","sering","seringnya","serta","serupa","sesaat","sesama","sesampai","sesegera","sesekali","seseorang","sesi","sesuai","sesuatu","sesuatunya","sesudah","sesudahnya","setelah","setempat","setengah","seterusnya","setiap","setiausaha","setiba","setibanya","setidak","setidaknya","setinggi","seusai","sewaktu","siap","siapa","siapakah","siapapun","siaran","sidang","singapura","sini","sinilah","sistem","soal","soalnya","sokongan","sri","stasiun","suara","suatu","sudah","sudahkah","sudahlah","sukan","suku","sumber","sungai","supaya","surat","susut","syarikat","syed","tadi","tadinya","tahap","tahu","tahun","tak","tama","tambah","tambahnya","tampak","tampaknya","tampil","tan","tanah","tandas","tandasnya","tanggal","tanpa","tanya","tanyakan","tanyanya","tapi","tawaran","tegas","tegasnya","teknologi","telah","televisi","teman","tempat","tempatan","tempo","tempoh","tenaga","tentang","tentara","tentu","tentulah","tentunya","tepat","terakhir","terasa","terbang","terbanyak","terbesar","terbuka","terdahulu","terdapat","terdiri","terhadap","terhadapnya","teringat","terjadi","terjadilah","terjadinya","terkait","terkenal","terkira","terlalu","terlebih","terletak","terlihat","termasuk","ternyata","tersampaikan","tersebut","tersebutlah","tertentu","tertuju","terus","terutama","testimoni","testimony","tetap","tetapi","the","tiada","tiap","tiba","tidak","tidakkah","tidaklah","tidaknya","tiga","tim","timbalan","timur","tindakan","tinggal","tinggi","tingkat","toh","tokoh","try","tun","tunai","tunjuk","turun","turut","tutur","tuturnya","tv","uang","ucap","ucapnya","udara","ujar","ujarnya","umum","umumnya","unescape","ungkap","ungkapnya","unit","untuk","untung","upaya","urus","usah","usaha","usai","user","utama","utara","var","versi","waduh","wah","wahai","wakil","waktu","waktunya","walau","walaupun","wang","wanita","warga","warta","wib","wilayah","wong","word","ya","yaitu","yakin","yakni","yang","zaman"};
	private static int lenSWords;
	private CharArraySet stopWords = IndonesianAnalyzer.getDefaultStopSet();
	private String rawText;
	public String StopWordResult;
	public String TokenResult;
	private String Result;
	/*constructor*/
	public textParser()
	{
		lenSWords = SWords.length;
		for(int i=0;i<lenSWords;i++)
	    {
	    	stopWords.add(SWords[i]);
	    }
	}
	/*constructor 2*/
	public textParser(String text)
	{
		rawText = text;
		lenSWords = SWords.length;
		for(int i=0;i<lenSWords;i++)
	    {
	    	stopWords.add(SWords[i]);
	    }
	}
	/*constructor*/
	/* method 1 */
	public String GetTokenizer(String text)
	{
		String words = text.replaceAll("[^a-zA-Z ]", "").toLowerCase();
		return words;
	}
	public String StemmingKalimat(String text)
	{
		String[] arrText = text.split(" ");
		int len = arrText.length;
		StringBuilder sb = new StringBuilder();
		IndonesianStemmer stemmer = new IndonesianStemmer();
		for(int i=0; i<len; i++)
		{
			char[] chars = arrText[i].toCharArray();
			int newlen = stemmer.stem(chars, chars.length, true);
			String stem = new String(chars, 0, newlen);
			sb.append(stem + " ");
		}
		return sb.toString();
	}
	@SuppressWarnings({ "deprecation", "resource" })
	public String removeStopWords(String textFile) throws Exception {
	    TokenStream tokenStream = new StandardTokenizer(Version.LUCENE_48, new StringReader(textFile.trim()));
	    tokenStream = new StopFilter(Version.LUCENE_48, tokenStream, stopWords);
	    StringBuilder sb = new StringBuilder();
	    CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
	    tokenStream.reset();
	    while (tokenStream.incrementToken()) {
	        String term = charTermAttribute.toString();
	        sb.append(term + " ");
	    }
	    return sb.toString();
	}
	/* method 1 */
	/* method 2 */
	public void Tokenizer()
	{
		this.TokenResult = rawText.replaceAll("[^a-zA-Z ]", " ").toLowerCase();
	}
	@SuppressWarnings({ "deprecation", "resource" })
	public void removeStopWords() throws Exception {
	    TokenStream tokenStream = new StandardTokenizer(Version.LUCENE_48, new StringReader(TokenResult.trim()));
	    tokenStream = new StopFilter(Version.LUCENE_48, tokenStream, stopWords);
	    StringBuilder sb = new StringBuilder();
	    CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
	    tokenStream.reset();
	    while (tokenStream.incrementToken()) {
	        String term = charTermAttribute.toString();
	        sb.append(term + " ");
	    }
	    this.StopWordResult = sb.toString();
	}
	public void StemmingKalimat()
	{
		String[] arrText = StopWordResult.split(" ");
		int len = arrText.length;
		StringBuilder sb = new StringBuilder();
		IndonesianStemmer stemmer = new IndonesianStemmer();
		for(int i=0; i<len; i++)
		{
			char[] chars = arrText[i].toCharArray();
			int newlen = stemmer.stem(chars, chars.length, true);
			String stem = new String(chars, 0, newlen);
			sb.append(stem + " ");
		}
		this.Result = sb.toString();
	}
	
	public String GetResult()
	{
		return this.Result;
	}
	public static void main(String[] args) throws Exception {
		String text = "Penyelenggaraan survailans epidemiologi, penyelidikan kejadian luar biasa skala kabupaten. 2. Penyelenggaraan pencegahan dan penanggulangan penyakit menular skala kabupaten. 3. Penyelenggaraan pencegahan dan penanggulangan penyakit tidak menular tertentu skala kabupaten. 4. Penyelenggaraan operasional penanggulangan masalah kesehatan akibat bencana dan wabah  skala kabupaten. 5. Pencegahan dan pemberantasan penyakit dalam lingkungan kabupaten 6. Perencanaan dan pengadaan obat pelayanan kesehatan dasar esensial 7. Pencegahan dan penanggulangan penyalahgunaan obat, narkotika, psikotropika, zat adiktif dan bahan berbahaya lingkup kabupaten 8. Penyelenggaraan kewaspadaan pangan dan gizi lingkup kabupaten 9. Pengembangan kerjasama lintas sektor lingkup kabupaten dna kerjasama antar daerah Penyelenggaraan pencegahan dan penanggulangan pencemaran lingkungan skala  kabupaten. 2. Penyehatan lingkungan 3. Penyelenggaraan upaya kesehatan lingkungan dan pemantauan dampak pembangunan terhadap kesehatan lingkup kabupaten 4. Penyelenggaraan akuntabilitas instansi kesehatan di wilayah kabupaten 5. Penyelenggaraan akuntabilitas instansi kesehatan di wilayah kabupaten Penyelenggaraan survailans gizi buruk skala kabupaten. 2. Penyelenggaraan penanggulangan gizi buruk skala kabupaten. 3. Perbaikan gizi keluarga dan masyarakat Penyelenggaraan pelayanan  kesehatan gaji skala  kabupaten. 2. Pengelolaan pelayanan kesehatan dasar dan rujukan sekunder skala kabupaten. 3. Penyelenggaraan upaya kesehatan pada daerah perbatasan, terpencil, rawan dan kepulauan skala kabupaten. 4. Registrasi, akreditasi, sertifikasi sarana kesehatan sesuai peraturan perundang-undangan. 5. Pemberian rekomendasi izin sarana kesehatan tertentu yang diberikan oleh pemerintah dan provinsi. 6. Pemberian izin sarana kesehatan meliputi rumah sakit pemerintah Kelas C, Kelas D, rumah sakit swasta yang setara, praktik berkelompok, klinik umum/spesialis, rumah bersalin, klinik dokter keluarga/dokter gigi keluarga, kedokteran komplementer, dan pengobatan tradisional,  serta sarana penunjang yang setara Pengelolaan/penyelenggaraan, jaminan pemeliharaan kesehatan sesuai kondisi lokal. 2. Penyelenggaraan jaminan pemeliharaan kesehatan nasional (Tugas Pembantuan 3. Implementasi sistem pembiayaan kesehatan melalui jaminan pemeliharaan kesehatan masyarakat dan atau sistem lain di kabupaten Pemanfaatan tenaga kesehatan strategis. 2. Pendayagunaan tenaga       kesehatan skala kabupaten. 3. Pelatihan teknis skala  kabupaten 4. Registrasi, akreditasi, sertifikasi tenaga kesehatan tertentu skala       kabupaten sesuai peraturan perundang-undangan. 5. Pemberian izin praktik tenaga kesehatan tertentu  Penyediaan dan pengelolaan obat pelayanan kesehatan dasar, alat kesehatan, reagensia dan vaksin skala kabupaten 2. Pengambilan sampling/contoh sediaan farmasi di lapangan. 3. Pemeriksaan setempat sarana produksi dan distribusi sediaan farmasi. 4. Pengawasan dan registrasi  makanan minuman produksi rumah tangga. 5. Sertifikasi alat kesehatan dan PKRT Kelas I.( Perbekalan  kesehatan rumah tangga ) 6. Pemberian rekomendasi izin PBF Cabang, PBAK dan Industri Kecil Obat Tradisional (IKOT). 7. Pemberian izin apotik, toko obat. 8. Sertifikasi keamanan industri rumah tangga.  9. penapisan dan pengembangan iptek kesehatan/kedokteran canggih Mengamankan kebijakan pengawasan dan pengendalian 10. Pelaksanaan kegiatan pengawasan program kesehatan 11. Pencatatan dan pelaporan obat pelayanan kesehatan dasar Penyelenggaraan promosi kesehatan skala kabupaten 2. Penyelenggaraan upaya/sarana kesehatan kabupaten  3. Penyelenggaraan upaya dan promosi kesehatan masyarakat 4. Menyelenggarakan program pelatihan kesehatan di wilayah kabupaten Penyelenggaraan, bimbingan dan pengendalian operasionalisasi bidang kesehatan. 2. Perencanaan pembangunan kesehatan wilayah kabupaten 3. Pengaturan dan pengorganisasian sistem kesehatan kabupaten 4. Perizinan kerja/praktek kerja tenaga kesehatan  5. Perizinan sarana kesehatan  6. Perizinan distribusi pelayanan obat skala kabupaten (apotik dan toko obat) 7. Pendayagunaan tenaga kesehatan 8. Penetapan tarif pelayanan kesehatan lingkup kabupaten 9. Bimbingan dan pengendalian kegiatan pengobatan tradisonal  10. Bimbingan dan pengendalian upaya/sarana kesehatan   skala kabupaten 11. Bimbingan dan pengendalian upaya kesehatan lingkup kabupaten  Penyelenggaraan penelitian dan pengembangan kesehatan yang mendukung perumusan kebijakan kabupaten 2. Pengelolaan surkesda skala kabupaten. 3. Implementasi penapisan Iptek di bidang pelayanan kesehatan skala kabupaten 4. Penelitian dan pengembangan kesehatan kabupaten  sistem informasi kesehatan ";
		textParser x = new textParser(text);
		x.Tokenizer();
		x.removeStopWords();
		x.StemmingKalimat();
		System.out.println(x.GetResult());
		
	}
}
