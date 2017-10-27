import java.util.Vector;

public class Teacher {
	
	private String tKey, tName;
	private Vector<String> lKeys;
	private int[] tDayHours = {0, 0, 0, 0, 0};
	private int tWeekHours;
	
	public Teacher() {
		this.tKey = null;
		this.tName = null;
		this.lKeys = null;
		this.tWeekHours = 0;
	}
	
	public Teacher(String TKey, String TName, Vector<String> LKeys, int[] THours, int TWeekHours) {
		this.tKey = TKey;
		this.tName = TName;
		this.lKeys = LKeys;
		this.tDayHours = THours;
		this.tWeekHours = TWeekHours;
	}
	
	public void setKey(String TKey) {
		this.tKey = TKey; 	
	}
	
	public void setName(String TName) {
		this.tName = TName;
	}
	
	public void setTClass(Vector<String> LKeys) {
		this.lKeys = LKeys;
	}
	
	public void decreaseDayHours(int i) {
		this.tDayHours[i]--;
	}
	
	public void decreaseWeekHours(int THours) {
		this.tWeekHours--;
	}

	public String getKey() {
		return this.tKey;
	}
	
	public String getName() {
		return this.tName;
	}
	
	public Vector<String> getLKeys() {
		return this.lKeys;
	}

	public int[] getDayHours() {
		return this.tDayHours;
	}
	
	public int getWeekHours() {
		return this.tWeekHours;
	}

}
