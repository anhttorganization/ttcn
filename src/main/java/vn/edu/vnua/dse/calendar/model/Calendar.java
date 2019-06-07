package vn.edu.vnua.dse.calendar.model;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "calendar")
public class Calendar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "studentId", length = 6)
	private String studentId;

	@Column(name = "calendarId")
	private String calendarId;
	
	@Column(name = "type")//0: thời khóa biểu; 1: lịch thi
	private boolean type;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false)
	private User user;

	
	@OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<CalendarDetail> calendarDetails;

	
	
	public Calendar() {

	}

	public Calendar(User user, String studentId, String calendarId, boolean type) {
		this.user = user;
		this.studentId = studentId;
		this.calendarId = calendarId;
		this.type = type;
	}
	
	public Calendar(User user, String studentId, String calendarId, boolean type, CalendarDetail... calendarDetails) {
		this.user = user;
		this.studentId = studentId;
		this.calendarId = calendarId;
		this.type = type;
		for (CalendarDetail calendarDetail : calendarDetails)
			calendarDetail.setCalendar(this);
		this.calendarDetails = Stream.of(calendarDetails).collect(Collectors.toSet());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<CalendarDetail> getCalendarDetails() {
		return calendarDetails;
	}

	public void setCalendarDetails(Set<CalendarDetail> calendarDetails) {
		this.calendarDetails = calendarDetails;
	}
	
	public void addCalendarDetails(CalendarDetail calendarDetail) {
		calendarDetail.setCalendar(this);
		this.calendarDetails.add(calendarDetail);
	}
	
	public void removeCalendarDetails(CalendarDetail calendarDetail)
	{
		this.calendarDetails.remove(calendarDetail);
	}

}
