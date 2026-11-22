package bookingApp.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import bookingApp.model.*;
import bookingApp.service.*;

@RestController
@CrossOrigin
public class BookingController {
	private BookingService service;
	
	public BookingController(BookingService s) {
		this.service = s;
	}
	
	@GetMapping("/bookable_lessons/view")
	public List<Lesson> getBookableLessons(
			@RequestParam int mat_number,
			@RequestParam int pupil_age,
			@RequestParam String instrument,
			@RequestParam int pupil_level,
			@RequestParam String lesson_day) {
		List<Lesson> lessonList = service.getBookableLessons(mat_number, pupil_age, instrument, pupil_level, lesson_day);
		return lessonList;
	}
	
	@GetMapping("/bookable_lessons/tutorView")
	public List<Lesson> getTutorsLessons(@RequestParam String tutor_name, @RequestParam String specified_day) {
		List<Lesson> lessonList = service.getBookedLessons(tutor_name, specified_day);
		return lessonList;
	}
	
	@GetMapping("/bookable_lessons/view_booked")
	public List<Lesson> getPupilsBookedLessons(@RequestParam int mat_number) {
		List<Lesson> bookedLessons = service.getBookedLessons(mat_number);
		return bookedLessons;
	}
	
	@PostMapping("/bookable_lessons/book")
	public ResponseEntity<Void> bookLesson(
			@RequestParam int mat_number,
			@RequestParam String pupil_forename,
			@RequestParam String pupil_surname,
			@RequestParam String pupils_instrument,
			@RequestParam int pupil_age,
			@RequestParam int pupils_level,
			@RequestParam String lesson_day,
			@RequestParam int lesson_time,
			@RequestParam String lesson_tutor,
			@RequestParam String lesson_instrument,
			@RequestParam int lesson_level) {
		service.bookLesson(mat_number, pupil_forename, pupil_surname, pupil_age, pupils_instrument, pupils_level, lesson_day, lesson_time, lesson_tutor, lesson_instrument, lesson_level);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/bookable_lessons/cancel")
	public ResponseEntity<Void> cancelLesson(
			@RequestParam int mat_number,
			@RequestParam String day_to_cancel,
			@RequestParam int time_to_cancel,
			@RequestParam String tutor_to_cancel) {
		service.cancelLesson(mat_number, day_to_cancel, time_to_cancel, tutor_to_cancel);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}