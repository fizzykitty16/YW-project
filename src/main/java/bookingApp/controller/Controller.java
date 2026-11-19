package bookingApp.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import bookingApp.model.*;
import bookingApp.service.*;

@RestController
@CrossOrigin
@RequestMapping("/bookable_lessons") //<-- learned to put this here via AI
public class Controller {
	private Service service;
	
	public Controller(Service s) {
		this.service = s;
	}
	
	@GetMapping("/view") //<-- learned what goes inside ( ) via AI
	public List<String> getBookableLessons(
			@RequestParam int mat_number,
			@RequestParam int pupil_age,
			@RequestParam String instrument,
			@RequestParam int pupil_level,
			@RequestParam String lesson_day) {
		List<Lesson> lessonList = service.getBookableLessons(mat_number, pupil_age, instrument, pupil_level, lesson_day);
		List<String> stringList = new ArrayList<String>(lessonList.size());
		
		lessonList.forEach(l -> stringList.add(l.toString()));
		return stringList;
	}
	
	@PostMapping("/book")
	public void bookLesson(
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
	}
}
