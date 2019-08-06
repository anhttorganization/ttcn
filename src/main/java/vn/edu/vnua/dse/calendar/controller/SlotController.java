package vn.edu.vnua.dse.calendar.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.vnua.dse.calendar.model.SlotTime;
import vn.edu.vnua.dse.calendar.repository.SlotTimeRepository;

@Controller
public class SlotController {
	@Autowired
	SlotTimeRepository slotTimeRepository;

	@RequestMapping(value = "admin/slot/index", method = RequestMethod.GET)
	public String index(Model model) {
		List<SlotTime> list = slotTimeRepository.findAll();
		String msg = (String) model.asMap().get("msg");
		String msgSuccess = (String) model.asMap().get("msgSuccess");

		model.addAttribute("listResult", list);
		model.addAttribute("msg", msg);
		model.addAttribute("msgSuccess", msgSuccess);
		return "admin/slot";
	}

	@RequestMapping(value = "admin/slot/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("SlotTime") SlotTime slotTime, Model model,
			final RedirectAttributes redirectAttrs) {
		SlotTime slotTimeFromDB = null;
		slotTimeFromDB = slotTimeRepository.findById(slotTime.getId());

		// TH tiết thêm mới đã tồn tại
		if (slotTimeFromDB != null) {
			redirectAttrs.addFlashAttribute("msg", "Tiết " + slotTime.getId() + " đã tồn tại");
			return "redirect:/admin/slot/index";
		}

		// TH thêm thành công
		try {
			slotTimeRepository.save(slotTime);
			redirectAttrs.addFlashAttribute("msgSuccess", "Thêm thành công");
		} catch (RuntimeException e) {
			// TODO: handle exception
			redirectAttrs.addFlashAttribute("msg",
					"Có lỗi xảy ra, xin mời liên hệ quản trị viên để biết thêm chi tiết");
		}

		return "redirect:/admin/slot/index";
	}

	@RequestMapping(value = "admin/slot/update", method = RequestMethod.POST)
	public String update(@ModelAttribute("SlotTime") SlotTime slotTime, HttpServletRequest reqest,
			final RedirectAttributes redirectAttrs, Model model) {

		// tim tiet hoc theo id
		SlotTime slotTimeFromDB = null;
		slotTimeFromDB = slotTimeRepository.findById(slotTime.getId());

		try {
			// neu co thi update
			if (slotTimeFromDB != null) {
				slotTimeRepository.save(slotTime);
				redirectAttrs.addFlashAttribute("msgSuccess", "Cập nhật thành công");
			}
		} catch (RuntimeException e) {
			redirectAttrs.addFlashAttribute("msg",
					"Có lỗi xảy ra, xin mời liên hệ quản trị viên để biết thêm chi tiết");
		}

		return "redirect:/admin/slot/index";
	}

	@RequestMapping(value = "admin/slot/delete", method = RequestMethod.POST)
	public String delete(HttpServletRequest request, final RedirectAttributes redirectAttrs) {
		// lay tiet hoc theo id
		long id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id").toString()) : 0;
		SlotTime slotTimeFromDB = null;
		slotTimeFromDB = slotTimeRepository.findById(id);

		// xoa tiet neu id ton tai
		if (slotTimeFromDB != null) {
			try {
				slotTimeRepository.delete(id);
				redirectAttrs.addFlashAttribute("msgSuccess", "Xóa thành công");
			} catch (RuntimeException e) {
				redirectAttrs.addFlashAttribute("msg",
						"Có lỗi xảy ra, xin mời liên hệ quản trị viên để biết thêm chi tiết");
			}
		}
		
		return "redirect:/admin/slot/index";
	}
}
