package vn.edu.vnua.dse.calendar.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

@Controller
public class SecurityGoogleController {
	@RequestMapping(value = "/privacy.html", method = RequestMethod.GET)
	public String getPrivacy(Model model) throws IOException, ParserConfigurationException, SAXException {
		File file = ResourceUtils.getFile("classpath:xml/privacy.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder buider = factory.newDocumentBuilder();
		Document doc = buider.parse(file);
		
		Element privacy = doc.getDocumentElement();
		model.addAttribute("title", privacy.getElementsByTagName("title").item(0).getTextContent());
		model.addAttribute("content", privacy.getElementsByTagName("content").item(0).getTextContent());
		
		return "privacy";
	}

	@RequestMapping(value = "/terms.html", method = RequestMethod.GET)
	public String getTerms(Model model) throws SAXException, IOException, ParserConfigurationException {
		File file = ResourceUtils.getFile("classpath:xml/terms.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder buider = factory.newDocumentBuilder();
		Document doc = buider.parse(file);
		
		Element terms = doc.getDocumentElement();
		model.addAttribute("title", terms.getElementsByTagName("title").item(0).getTextContent());
		model.addAttribute("content", terms.getElementsByTagName("content").item(0).getTextContent());

		return "terms";
	}
}
