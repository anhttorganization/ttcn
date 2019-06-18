package vn.edu.vnua.dse.calendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SecurityGoogleController {
	@RequestMapping(value = "/privacy.html", method = RequestMethod.GET)
	public String getPrivacy() {
		return "privacy";
	}
	
	@RequestMapping(value = "/terms.html", method = RequestMethod.GET)
	public String getTerms() {
		return "terms";
	}
}
