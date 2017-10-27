package src;

public class Lesson {
	
	private String LKey, LName, LClass;
	private int LHours;
	
	public Lesson() {
		this.LKey = null;
		this.LName = null;
		this.LClass = null;
		this.LHours = 0;
	}
	
	public Lesson(String LKey, String LName, String LClass, int LHours) {
		this.LKey = LKey;
		this.LName = LName;
		this.LClass = LClass;
		this.LHours = LHours;
	}
	
	public void setKey(String LKey) {
		this.LKey = LKey;
	}
	
	public void setName(String LName) {
		this.LName = LName;
	}
	
	public void setLClass(String LClass) {
		this.LClass = LClass;
	}
	
	public void setHours(int LHours) {
		this.LHours = LHours;
	}
	
	public void decreaseHours() {
		this.LHours--;
	}

	public String getKey() {
		return this.LKey;
	}
	
	public String getName() {
		return this.LName;
	}
	
	public String getLClass() {
		return this.LClass;
	}

	public int getHours() {
		return this.LHours;
	}

}
