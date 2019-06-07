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

@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	APIWrapper aPIWrapper;

	@Override
	public boolean isExist(ScheduleCreate scheduleCreate) {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public BaseResult<Set<String>> insert(String calenId, ScheduleCreate scheduleCreate) {
		Set<String> eventIds = new HashSet<>();

		try {
			aPIWrapper = new APIWrapper(UserDetailsServiceImpl.getRefreshToken());
			BaseResult<List<GoogleEvent>> events = SubjectEventDetails.getEventsFromSchedule(scheduleCreate.getStudentId(),
					scheduleCreate.getSemester());
			
			if(events.isStatus()) {
				for (GoogleEvent event : events.getResult()) {
					eventIds.add(aPIWrapper.insertEvent(calenId, event).getId());
				}
				
				return new BaseResult<Set<String>>(true, eventIds, events.getMassage());
			}
			
			return new BaseResult<Set<String>>(false, eventIds, events.getMassage());
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

}
