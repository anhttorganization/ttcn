package vn.edu.vnua.dse.calendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.edu.vnua.dse.calendar.model.User;

@Controller
public class UserController {
	@RequestMapping(value = "/user/index", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("user", new User());

		return "register";
	}
	
	@RequestMapping(value = "/user/add", method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("user", new User());

		return "register";
	}
	
	@RequestMapping(value = "/user/update", method = RequestMethod.GET)
	public String update(Model model) {
		model.addAttribute("user", new User());

		return "register";
	}
	
	@RequestMapping(value = "/user/delete", method = RequestMethod.GET)
	public String delete(Model model) {
		model.addAttribute("user", new User());

		return "register";
	}
	
}
