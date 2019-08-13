package vn.edu.vnua.dse.calendar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.edu.vnua.dse.calendar.model.Calendar;
import vn.edu.vnua.dse.calendar.model.CalendarDetail;
import vn.edu.vnua.dse.calendar.model.Semester;

@Repository("calendarDetailRepository")
public interface CalendarDetailRepository extends JpaRepository<CalendarDetail, Long>{
	Optional<CalendarDetail> findByCalendarAndSemester(Calendar calendar, Semester semester);
	Optional<CalendarDetail> findByCalendar(Calendar calendar);
	
//	Optional<CalendarDetail> findByCalenIdAndSemesId(long calendId, String semesId);
	
	@Modifying
	@Query(value="DELETE FROM calendar_detail WHERE id = ?1 ", nativeQuery = true)
	void delete(Long id);
	

}
