package main.jenahelper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class JenaHelper {
	public static String uri;
	public String owlPath;
	public OntModel ontology=null;
	public ArrayList<String> clas = new ArrayList<String>();
	public ArrayList<String> coment = new ArrayList<String>();
    public JenaHelper(String owlPath,String URI) {
    	uri = URI;
    	InputStream is = FileManager.get().open(owlPath);
		ontology = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF,null);
		ontology.read(is,"");
		this.owlPath = owlPath;
    }
    public ArrayList<String> getAllListClass() // membaca semua class dari file .owl
	{
		ArrayList<String> ListClass = new ArrayList<String>();
		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF,null);

    	ontModel.read(this.owlPath,"");
    	
		ExtendedIterator<OntClass> classes = ontModel.listClasses();

		while(classes.hasNext()) {
			OntClass ontClass = classes.next();
			if(ontClass.getURI().contains(uri))
			{
				String temp = ontClass.getLocalName();
				ListClass.add(temp);
			}
		}
		return ListClass;
	}
    public ArrayList<String> getAllListComentar() // membaca semua class dari file .owl
	{
		ArrayList<String> ListClass = new ArrayList<String>();
		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF,null);

    	ontModel.read(this.owlPath,"");
    	
		ExtendedIterator<OntClass> classes = ontModel.listClasses();

		while(classes.hasNext()) {
			OntClass ontClass = classes.next();
			if(ontClass.getURI().contains(uri))
			{
				String temp = ontClass.getComment(null);
				ListClass.add(temp);
			}
		}
		return ListClass;
	}
    public HashMap<String,String> getDetailClass() // membaca semua class dari file .owl
	{
    	HashMap<String,String> ListClass = new HashMap<String,String>();
		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF,null);

    	ontModel.read(this.owlPath,"");
    	
		ExtendedIterator<OntClass> classes = ontModel.listClasses();

		while(classes.hasNext()) {
			OntClass ontClass = classes.next();
			if(ontClass.getURI().contains(uri))
			{
				String coment = ontClass.getComment(null);
				String classname = ontClass.getLocalName();
				ListClass.put(classname,coment);
			}
		}
		return ListClass;
	}
    public ArrayList<String> initOWL() // membaca semua class dari file .owl
	{
		ArrayList<String> ListClass = new ArrayList<String>();
		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF,null);

    	ontModel.read(this.owlPath,"");
    	
		ExtendedIterator<OntClass> classes = ontModel.listClasses();

		while(classes.hasNext()){
			OntClass ontClass = classes.next();
			if(ontClass.getURI().contains(uri))
			{
				clas.add(ontClass.getLocalName());
				coment.add(ontClass.getComment(null));
			}
		}
		return ListClass;
	}
    @SuppressWarnings("rawtypes")
	public void getAllSubClass() // membaca semua class dari file .owl
	{
		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF,null);

    	ontModel.read(this.owlPath,"");
    	OntClass camera = this.ontology.getOntClass( uri + "Kesehatan" );
    	for (Iterator i = camera.listSubClasses(); i.hasNext(); ) {
    	OntClass c = (OntClass) i.next();
    	System.out.println( c.getLocalName() + " " );
    	}
	}
	public static void main(String[] args) throws Exception {
		JenaHelper x = new JenaHelper("http://localhost/ta/www.owl","http://www.ta.com/#");
		ArrayList<String> list = new ArrayList<String>();
		
		list = x.getAllListComentar();
		for (int i = 0; i < list.size(); i++) 
		{
			System.out.println(list.get(i));
		}
	}
}
