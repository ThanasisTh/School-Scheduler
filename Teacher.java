import java.util.Vector;

public class Teacher {
	
	private String tName;
	private Vector<Integer> lKeys;
	private int[] tDayHours = {0, 0, 0, 0, 0};
	private int tWeekHours, tKey;
	
	public Teacher() {
		this.tKey = 0;
		this.tName = null;
		this.lKeys = null;
		this.tWeekHours = 0;
	}
	
	public Teacher(int TKey, String TName, Vector<Integer> LKeys, int[] THours, int TWeekHours) {
		this.tKey = TKey;
		this.tName = TName;
		this.lKeys = LKeys;
		this.tDayHours = THours;
		this.tWeekHours = TWeekHours;
	}
	
	public void setKey(int TKey) {
		this.tKey = TKey; 	
	}
	
	public void setName(String TName) {
		this.tName = TName;
	}
	
	public void setTClass(Vector<Integer> LKeys) {
		this.lKeys = LKeys;
	}
	
	public void decreaseDayHours(int i) {
		this.tDayHours[i]--;
	}
	
	public void decreaseWeekHours(int THours) {
		this.tWeekHours--;
	}

	public int getKey() {
		return this.tKey;
	}
	
	public String getName() {
		return this.tName;
	}
	
	public Vector<Integer> getLKeys() {
		return this.lKeys;
	}

	public int[] getDayHours() {
		return this.tDayHours;
	}
	
	public int getWeekHours() {
		return this.tWeekHours;
	}

}
