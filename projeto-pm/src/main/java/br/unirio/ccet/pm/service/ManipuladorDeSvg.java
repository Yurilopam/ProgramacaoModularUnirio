package br.unirio.ccet.pm.service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import br.unirio.ccet.pm.model.Disciplina;

public class ManipuladorDeSvg {
	
	private static final String GRADE_CURRICULAR_SVG = "grade_curricular.svg";
	private static final String GRADE_CURRICULAR_PINTADA_SVG = "grade_curricular_pintada.svg";
	private static final Path GRADE_CURRICULAR_PATH = Paths.get(System.getProperty("user.dir"), GRADE_CURRICULAR_SVG);

	
	public void manipularSvg(HashMap<String, Disciplina> hashMap) throws IOException, DocumentException {
		imprimirNovoSvg(manipularStyleSvg(hashMap));
	}
	
	public void imprimirNovoSvg(Document doc) throws IOException {
		try(FileWriter fileOut = new FileWriter(GRADE_CURRICULAR_PINTADA_SVG)){
			OutputFormat format = new OutputFormat();
			XMLWriter writer = new XMLWriter(fileOut, format);
			writer.write(doc);
			writer.close();
		}
	}

	@SuppressWarnings("unchecked")
	public Document manipularStyleSvg(HashMap<String, Disciplina> informacaoesDeDisciplinas) throws DocumentException {
		Document doc = new SAXReader().read(GRADE_CURRICULAR_PATH.toFile());
		
		Element root = doc.getRootElement();
		Element g = root.element("g");
		
		Disciplina disciplina;
		
		for(Iterator<Element> it = g.elementIterator("path"); it.hasNext();) {
			
			Element path = it.next();
			
			String codigo = path.attribute("id").getStringValue();
			
			if(informacaoesDeDisciplinas.keySet().contains(codigo)) {
				
				String style = path.attributeValue("style");
				int index = style.indexOf("fill:");
				String substring = style.substring(index+5, index+12);
				
				disciplina = informacaoesDeDisciplinas.get(codigo);
				
				if (disciplina.getSituacao().equals("Aprovado") || disciplina.getSituacao().equals("nota")) {
					style = style.replace(substring, "#00ff00");
				} else if (disciplina.getSituacao().equals("falta") || disciplina.getSituacao().equals("por")) {
					style = style.replace(substring, "#ff0000");
				} 
				
				path.addAttribute("style", style);
			}
		}
		return doc;
	}
}
