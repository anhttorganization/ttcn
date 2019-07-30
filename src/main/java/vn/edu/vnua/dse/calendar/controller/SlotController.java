package vn.edu.vnua.dse.calendar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.edu.vnua.dse.calendar.model.SlotTime;
import vn.edu.vnua.dse.calendar.repository.SlotTimeRepository;

@Controller
public class SlotController {
	@Autowired
	SlotTimeRepository slotTimeRepository;
	
	@RequestMapping(value = "admin/slot/index", method = RequestMethod.GET)
	public String index(Model model) {
		List<SlotTime> list = slotTimeRepository.findAll();
		
		model.addAttribute("listResult", list);

		return "admin/slot";
	}
	
	@RequestMapping(value = "admin/slot/slot/add", method = RequestMethod.GET)
	public String add(Model model) {
//		model.addAttribute("user", new User());

		return "register";
	}
	
	@RequestMapping(value = "admin/slot/slot/update", method = RequestMethod.GET)
	public String update(Model model) {
//		model.addAttribute("user", new User());

		return "register";
	}
	
	@RequestMapping(value = "admin/slot/slot/delete", method = RequestMethod.GET)
	public String delete(Model model) {
//		model.addAttribute("user", new User());

		return "register";
	}
}
