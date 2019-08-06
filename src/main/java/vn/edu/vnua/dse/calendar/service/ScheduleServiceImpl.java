package vn.edu.vnua.dse.calendar.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.vnua.dse.calendar.co.BaseResult;
import vn.edu.vnua.dse.calendar.co.ScheduleCreate;
import vn.edu.vnua.dse.calendar.crawling.SubjectEventDetails;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleEvent;
import vn.edu.vnua.dse.calendar.ggcalendar.wrapperapi.APIWrapper;
import vn.edu.vnua.dse.calendar.model.Semester;
import vn.edu.vnua.dse.calendar.repository.SemesterRepository;

@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	APIWrapper aPIWrapper;

	@Autowired
	SemesterRepository semesterRepository;
	
	@Override
	public boolean isExist(ScheduleCreate scheduleCreate) {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public BaseResult<Set<String>> insert(String calenId, ScheduleCreate scheduleCreate) {
		Set<String> eventIds = new HashSet<>();
		Semester semester = semesterRepository.findById(scheduleCreate.getSemester());
		try {
			aPIWrapper = new APIWrapper(UserDetailsServiceImpl.getRefreshToken());
			BaseResult<List<GoogleEvent>> events = SubjectEventDetails.getEventsFromSchedule(scheduleCreate.getStudentId(), semester);
			
			if(events.isStatus()) {
				for (GoogleEvent event : events.getResult()) {
					eventIds.add(aPIWrapper.insertEvent(calenId, event).getId());
				}
				
				return new BaseResult<Set<String>>(true, eventIds, events.getMessage(), events.getWeekEvents());
			}
			
			return new BaseResult<Set<String>>(false, eventIds, events.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return null;
	}

	@Override
	public Set<String> insert(String calenId, List<GoogleEvent> events) throws IOException {
		Set<String> eventIds = new HashSet<>();

		aPIWrapper = new APIWrapper(UserDetailsServiceImpl.getRefreshToken());
		
		for (GoogleEvent event : events) {
			eventIds.add(aPIWrapper.insertEvent(calenId, event).getId());
		}

		return eventIds;
	}

	
	/**
	 * 
	 */
	@Override
	public void insert1(String calenId, List<String> events) {
		try {
			if (events.size() > 0) {
				for (String eventJson : events) {
					aPIWrapper.insertEvent(calenId, eventJson);
				}
			}
		}catch (Exception e) {
			System.out.println("thêm tuần không thành công!");
		}
	}

}
