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
		tutors.add("John Piano");
		tutors.add("Jane Strings");
		
		db = new HashMap<>();
		
		Lesson piano4JPmon1600 = new Lesson("piano", 4, "JP", "mon", 1600, false, 0);
		Lesson piano8JPmon1630 = new Lesson("piano", 8, "JP", "mon", 1630, false, 0);
		Lesson guitar5JSmon1600 = new Lesson("guitar", 5, "JS", "mon", 1600, false, 0);
		Lesson guitar3JStue1700 = new Lesson("guitar", 3, "JS", "tue", 1700, false, 0);
		Lesson violin1JSwed1830 = new Lesson("violin", 1, "JS", "wed", 1830, false, 0);
		Lesson defaultBookedpiano6JPmon1900 = new Lesson("piano", 6, "JP", "mon", 1900, true, 1);
		Lesson violin4JSsat1000 = new Lesson("violin", 4, "JS", "sat", 1000, false, 0);
		db.put(1, piano4JPmon1600);
		db.put(2, piano8JPmon1630);
		db.put(3, guitar5JSmon1600);
		db.put(4, guitar3JStue1700);
		db.put(5, violin1JSwed1830);
		db.put(6, defaultBookedpiano6JPmon1900);
		db.put(7, violin4JSsat1000);
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

	
	
	public List<Lesson> getBookedLessons(String tutor_name) {
		if (!tutors.contains(tutor_name)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid matriculation number.");
		}
		
		List<Lesson> bookedLessons = new ArrayList<Lesson>();
		db.values().stream()
				.filter(lesson -> lesson.isBooked() == false && lesson.getTutor().equals(tutor_name))
				.forEach(bookedLessons::add);
		
		return bookedLessons;
	}
	
	
	
	
	public void bookLesson(int mat_number, String pupil_forename, String pupil_surname, int pupil_age, String pupils_instrument, int pupils_level, String lesson_day, int lesson_time, String lesson_tutor, String lesson_instrument, int lesson_level) {
		if (!mat_numbers.contains(mat_number)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid matriculation number.");
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
}