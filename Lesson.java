
public class Lesson {
	
	private String LName;
	private char LClass;
	private int LKey, LHours;
	
	public Lesson() {
		this.LKey = 0;
		this.LName = null;
		this.LClass = '\u0000';
		this.LHours = 0;
	}
	
	public Lesson(int LKey, String LName, char LClass, int LHours) {
		this.LKey = LKey;
		this.LName = LName;
		this.LClass = LClass;
		this.LHours = LHours;
	}
	
	public void setKey(int LKey) {
		this.LKey = LKey;
	}
	
	public void setName(String LName) {
		this.LName = LName;
	}
	
	public void setLClass(char LClass) {
		this.LClass = LClass;
	}
	
	public void setHours(int LHours) {
		this.LHours = LHours;
	}
	
	public void decreaseHours() {
		this.LHours--;
	}

	public int getKey() {
		return this.LKey;
	}
	
	public String getName() {
		return this.LName;
	}
	
	public char getLClass() {
		return this.LClass;
	}

	public int getHours() {
		return this.LHours;
	}

}
