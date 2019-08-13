package vn.edu.vnua.dse.calendar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.vnua.dse.calendar.model.Calendar;
import vn.edu.vnua.dse.calendar.model.Semester;

@Repository("calendarRepository")
public interface CalendarRepository extends JpaRepository<Calendar, Long>{
	Optional<Calendar> findByStudentId(String studentId);
	
	Optional<List<Calendar>>findByStudentIdAndTypeAndUserId(String studentId, boolean type, long userId);
	
	Optional<Calendar> findByCalendarId(String calendarId);
	
	Optional<Calendar> findByCalendarIdAndType(String calendarId, boolean type);
	
}
