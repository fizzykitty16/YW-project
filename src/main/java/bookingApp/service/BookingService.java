package bookingApp.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import bookingApp.model.Lesson;

@Component
public class BookingService {
	private Map<Integer, Lesson> db;
	private List<Integer> mat_numbers;
	private List<String> tutors;
	
	public BookingService() {
		mat_numbers = new ArrayList<Integer>();
		mat_numbers.add(101);
		mat_numbers.add(102);
		mat_numbers.add(103);
		mat_numbers.add(104);
		mat_numbers.add(105);
		mat_numbers.add(106);
		mat_numbers.add(107);
		
		tutors = new ArrayList<String>();
		tutors.add("JP");
		tutors.add("JS");
		
		db = new HashMap<>();
		
		Lesson piano3JPmon1600 = new Lesson("piano", 3, "JP", "mon", 1600, false, 0);
		Lesson piano7JPmon1630 = new Lesson("piano", 7, "JP", "mon", 1630, false, 0);
		Lesson guitar5JPmon1600 = new Lesson("guitar", 5, "JP", "mon", 1600, false, 0);
		Lesson guitar3JPtue1700 = new Lesson("guitar", 3, "JP", "tue", 1700, false, 0);
		Lesson defaultBookedviolin1JPwed1830 = new Lesson("violin", 1, "JP", "wed", 1830, true, 102);
		Lesson defaultBookedpiano6JPmon1900 = new Lesson("piano", 6, "JP", "mon", 1900, true, 101);
		Lesson violin4JPsat1000 = new Lesson("violin", 4, "JP", "sat", 1000, false, 0);
		db.put(1, piano3JPmon1600);
		db.put(2, piano7JPmon1630);
		db.put(3, guitar5JPmon1600);
		db.put(4, guitar3JPtue1700);
		db.put(5, defaultBookedviolin1JPwed1830);
		db.put(6, defaultBookedpiano6JPmon1900);
		db.put(7, violin4JPsat1000);
	}
	
	
	public List<Lesson> getBookableLessons(int mat_number, int age, String instrument, int level, String day) {
		if (!mat_numbers.contains(mat_number)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid matriculation number.");
		}
		
		if (db == null) {
			return null;
		}
		
		List<Lesson> bookableLessons = new ArrayList<Lesson>();
		//AI-assisted code block
		db.values().stream()
				.filter(lesson -> lesson.isBooked() == false && lesson.getInstrument().equals(instrument) && lesson.getLevel() == level && lesson.getDay().equals(day))
				.forEach(bookableLessons::add);
		//end of AI-assisted code block
		
		return bookableLessons;
	}

	
	
	public List<Lesson> getBookedLessons(String tutor_name, String specified_day) {
		if (!tutors.contains(tutor_name)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tutor does not exist.");
		}
		
		List<Lesson> bookedLessons = new ArrayList<Lesson>();
		db.values().stream()
				.filter(lesson -> lesson.isBooked() == true && lesson.getTutor().equals(tutor_name) && lesson.getDay().equals(specified_day))
				.forEach(bookedLessons::add);
		
		return bookedLessons;
	}
	
	
	
	public List<Lesson> getBookedLessons(int mat_number) {
		if(!mat_numbers.contains(mat_number)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid matriculation number.");
		}
		
		if (db == null) {
			return null;
		}
		
		List<Lesson> bookedLessons = new ArrayList<Lesson>();
		db.values().stream()
			.filter(lesson -> lesson.getStudentMat() == mat_number && lesson.isBooked() == true)
			.forEach(bookedLessons::add);
		
		return bookedLessons;
	}
	
	
	
	
	public void bookLesson(int mat_number, String pupil_forename, String pupil_surname, int pupil_age, String pupils_instrument, int pupils_level, String lesson_day, int lesson_time, String lesson_tutor, String lesson_instrument, int lesson_level) {
		if (!mat_numbers.contains(mat_number)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid matriculation number.");
		}
		
		if(lesson_time <= 899 || lesson_time >= 1931) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid lesson time.");
		}
		
		if (lesson_time <= 1599 && lesson_day != "sat") {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input: earliest possible lesson time on weekdays is 16:00.");
		}
		
		if(lesson_time >= 1800 && pupil_age <= 15) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pupils must be aged 16 or over to book a lesson after 18:00.");
		}
		
		if (!(pupils_instrument == lesson_instrument && pupils_level == lesson_level)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect instrument or level selected. Please book a lesson for an instrument you are learning, at your current level.");
		}
		
		//AI-assisted code block:
		boolean lesson_exists = db.values().stream().anyMatch(
				lesson -> lesson_instrument.equals(lesson.getInstrument()) && lesson_day.equals(lesson.getDay()) && lesson_time == lesson.getTime() && lesson_tutor.equals(lesson.getTutor()));				
		//end of AI-assisted code block
		if(lesson_exists == false) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input: " + lesson_tutor + " does not teach " + lesson_instrument + " at this time.");
		}
		
		boolean pupil_booked_day_already = db.values().stream().anyMatch(
				lesson -> lesson.getStudentMat() == mat_number && lesson.getDay().equals(lesson_day));
		if (pupil_booked_day_already == true) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input: you have alreadby booked a lesson for " + lesson_day + ".");
		}
		
		//AI-assisted code block:
		Lesson lessonToBook = db.values().stream()
				.filter(lesson -> lesson_day.equals(lesson.getDay()) && lesson_time == lesson.getTime() && lesson_tutor.equals(lesson.getTutor()))
				.findFirst()
				.orElse(null);
		//end of AI-assisted code block
		if (lessonToBook.isBooked()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This lesson is already booked.");
		}
		
		
		
		lessonToBook.book(mat_number, pupil_forename, pupil_surname);
	}

	
	
	
	public void cancelLesson(int mat_number, String day, int time, String tutor) {
		boolean lesson_exists = db.values().stream().anyMatch(
				lesson ->  day.equals(lesson.getDay()) && time == lesson.getTime() && tutor.equals(lesson.getTutor()) && lesson.isBooked() == true);				
		if(lesson_exists == false) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input: " + tutor + " does not have a lesson at this time.");
		}
		
		Lesson lessonToCancel = db.values().stream()
				.filter(lesson -> day.equals(lesson.getDay()) && time == lesson.getTime() && tutor.equals(lesson.getTutor()) && lesson.isBooked() == true && lesson.getStudentMat() == mat_number)
				.findFirst()
				.orElse(null);
		lessonToCancel.cancel();
	}
}