package vn.edu.vnua.dse.calendar.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.vnua.dse.calendar.co.ScheduleResult;
import vn.edu.vnua.dse.calendar.crawling.ExamEventDetails;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleEvent;
import vn.edu.vnua.dse.calendar.ggcalendar.wrapperapi.APIWrapper;

@Service("testScheduleService")
public class TestScheduleServiceImpl implements TestScheduleService{

	@Autowired
	APIWrapper aPIWrapper;
	
	@Override
	public ScheduleResult<Set<String>> insert(String calenId, String studentId) throws IOException, ParseException, NoSuchAlgorithmException {
		Set<String> eventIds = new HashSet<>();
		aPIWrapper = new APIWrapper(UserDetailsServiceImpl.getRefreshToken());
		ScheduleResult<List<GoogleEvent>> events = ExamEventDetails.getEventsFromSchedule(studentId);
		
		if(events.isStatus()) {
			for (GoogleEvent event : events.getResult()) {
				eventIds.add(aPIWrapper.insertEvent(calenId, event).getId());
			}
			
			return new ScheduleResult<Set<String>>(true, eventIds, events.getMessage(), events.getScheduleHash());
		}
		
		return new ScheduleResult<Set<String>>(false, eventIds, events.getMessage(), events.getScheduleHash());
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
