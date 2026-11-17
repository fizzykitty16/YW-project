package bookingApp.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class Controller {
	private Service service;
	
	public Controller(Service s) {
		this.service = s;
	}
	
	@GetMapping("/bookable_lessons/")
	public List<String> getBookableLessons(String instrument, int level, String day) {
		List<Lesson> lessonList = service.getBookableLessons(instrument, level, day);
		List<String> stringList = new ArrayList<String>(lessonList.size());
		
		lessonList.forEach(l -> stringList.add(l.toString()));
		return stringList;
	}
}
