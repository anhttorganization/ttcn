package vn.edu.vnua.dse.calendar.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import vn.edu.vnua.dse.calendar.co.ImportCreate;
import vn.edu.vnua.dse.calendar.common.AppUtils;
import vn.edu.vnua.dse.calendar.service.ImportServiceImpl;

@Controller
@RequestMapping("/import/")
public class ImportController {
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String Create(Model model) {
		
		model.addAttribute("ImportCreate", new ImportCreate());
		return "import/create";
	}

@RequestMapping(value = "create", method = RequestMethod.POST)
public String Create(Model model, ImportCreate importCreate, HttpServletRequest request) throws IOException {
	String calendarId = importCreate.getCalendarId();

    String uploadRootPath = request.getServletContext().getRealPath("upload");
    System.out.println("uploadRootPath=" + uploadRootPath);

    File uploadRootDir = new File(uploadRootPath);
    //
    // Create directory if it not exists.
    if (!uploadRootDir.exists()) {
        uploadRootDir.mkdirs();
    }
    
    try {
      MultipartFile multipartFile = importCreate.getMultipartFile();
//      String fileName = multipartFile.getOriginalFilename();
      File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + "event.xlsx");

      multipartFile.transferTo(serverFile);
      //code
      
      
      serverFile.delete();
      
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("message", "upload failed");
    }

    return "import/create";
}
}


