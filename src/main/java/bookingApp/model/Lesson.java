package bookingApp.model;

public class Lesson {
	private String instrument;
	private int level;
	private String tutor;
	private String day; //formatted mon
	private int time; //formatted 1400
	private boolean booked;
	private int pupil_mat;
	
	public Lesson() {
		instrument = "_";
		level = 0;
		tutor = "_";
		day = "_";
		time = 0;
		booked = false;
		pupil_mat = 0;
	}
	
	public Lesson(String instrument, int level, String tutor, String day, int time, boolean booked, int pupil_mat) {
		this.instrument = instrument;
		this.level = level;
		this.tutor = tutor;
		this.day = day;
		this.time = time;
		this.booked = booked;
		this.pupil_mat = pupil_mat;
	}
	
	public boolean isBooked() {
		if (this.booked == true) {
			return true;
		}
		else return false;
	}
}
