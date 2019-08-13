package vn.edu.vnua.dse.calendar.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.edu.vnua.dse.calendar.co.BaseResult;
import vn.edu.vnua.dse.calendar.co.MySqlDate;
import vn.edu.vnua.dse.calendar.model.Semester;
import vn.edu.vnua.dse.calendar.repository.SemesterRepository;

@Controller
public class SemesterController {
	@Autowired
	SemesterRepository semesterRepository;

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/admin/semester/index", method = RequestMethod.GET)
	public String index(Model model) {
		List<Semester> list = semesterRepository.findAll();
		
		for(int i = 0; i < list.size(); i++) {
			Date startDate = list.get(i).getStartDate();
			
			list.get(i).setStartDate(new MySqlDate(startDate.getYear(), startDate.getMonth(), startDate.getDate()));
			System.out.println(list.get(i).getStartDate());
		}
		
		model.addAttribute("listResult", list);
		return "admin/semester";
	}

	@RequestMapping(value = "/admin/semester/add", method = RequestMethod.POST)
	public @ResponseBody BaseResult<Semester> add(@ModelAttribute("Semester") Semester semester) {
		// find in data base
		if (semesterRepository.findById(semester.getId()) != null) {
			return new BaseResult<Semester>(false, semester, "Id học kỳ đã tồn tại!");
		}

		semesterRepository.save(semester);

		return new BaseResult<Semester>(true, semester, "Thêm học kỳ thành công!");
	}

	@RequestMapping(value = "/admin/semester/update", method = RequestMethod.POST)
	public @ResponseBody BaseResult<Semester> update(@ModelAttribute("Semester") Semester semester) {
		Semester entity = semesterRepository.findById(semester.getId());

		if (entity != null) {
			entity.setName(semester.getName());
			entity.setStartDate(semester.getStartDate());
			entity.setCalendarDetails(semester.getCalendarDetails());
			semesterRepository.save(entity);
			return new BaseResult<Semester>(true, entity, "Cập nhật học kỳ thành công!");
		} else {
			return new BaseResult<Semester>(true, null, "Không tìm thấy học kỳ!");
		}
	}

	@RequestMapping(value = "/admin/semester/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody BaseResult<Semester> delete(@PathVariable String id) {
		try {
			semesterRepository.delete(id);
		} catch (Exception e) {
			return new BaseResult<Semester>(false, null, "Có lỗi xảy ra học kỳ chưa được xóa!");
		}

		return new BaseResult<Semester>(true, null, "Xóa học kỳ thành công!");
	}
}
