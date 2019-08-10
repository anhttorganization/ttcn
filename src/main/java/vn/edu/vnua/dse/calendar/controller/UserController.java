package vn.edu.vnua.dse.calendar.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.vnua.dse.calendar.model.User;
import vn.edu.vnua.dse.calendar.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

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