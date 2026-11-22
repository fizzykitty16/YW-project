package bookingApp.model;

public class Lesson {
	private String instrument;
	private int level;
	private String tutor;
	private String day;
	private int time;
	private boolean booked;
	private int pupil_mat;
	private String pupil_forename;
	private String pupil_surname;
	
	public Lesson() {
		instrument = "_";
		level = 0;
		tutor = "_";
		day = "_";
		time = 0;
		booked = false;
		pupil_mat = 0;
		pupil_forename = "_";
		pupil_surname = "_";
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
		if (this.pupil_mat != 0) {
			return true;
		}
		else return false;
	}
	
	public void book(int mat_number, String pupil_forename, String pupil_surname) {
		this.pupil_mat = mat_number;
		this.pupil_forename = pupil_forename;
		this.pupil_surname = pupil_surname;
		this.booked = true;
	}
	
	public void cancel() {
		this.booked = false;
		this.pupil_mat = 0;
	}
	
	public String getInstrument() {
		return instrument;
	}
	public int getLevel() {
		return level;
	}
	public String getTutor() {
		return tutor;
	}
	public String getDay() {
		return day;
	}
	public int getTime() {
		return time;
	}
	public int getStudentMat() {
		return pupil_mat;
	}
}