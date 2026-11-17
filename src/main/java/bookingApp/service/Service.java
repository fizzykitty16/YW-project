package bookingApp.service;

import java.util.*;

import bookingApp.model.Lesson;

public class Service {
	private Map<Integer, Lesson> db;
	
	
	public Service() {
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
	
	
	public List<Lesson> getBookableLessons(String instrument, int level, String day) {
		if (db == null) {
			return null;
		}
		
		List<Lesson> list = new ArrayList<Lesson>();
		//check against booked, instrument, level + day here
		//code for this block found at https://dev.java/learn/language/fp/refactoring-to-functional-style/for-each-with-if/
		
		//google: in java, how to add only some elements of a hashmap, that match a condition, to a list
		
		//then afterwards, may need to edit vvv
		
		list.addAll(db.values());
		return list;
	}
}
