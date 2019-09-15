
package vn.edu.vnua.dse.calendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {
	@RequestMapping(value = { "/" }, method = { RequestMethod.GET })
	public String login(Model model, String error, String logout, RedirectAttributes ra) {
		return "main";
	}
	
	@RequestMapping(value = { "/guide" }, method = { RequestMethod.GET })
	public String guide(Model model, String error, String logout, RedirectAttributes ra) {
		return "guide";
	}
	
	@RequestMapping(value = { "/contact" }, method = { RequestMethod.GET })
	public String contact(Model model, String error, String logout, RedirectAttributes ra) {
		return "contact";
	}
}
