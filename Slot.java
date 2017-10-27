package src;

public class Slot {
	
	private String cKey, lKey;	//Class and Lesson keys
	
	public Slot() {
		this.cKey = null;
		this.lKey = null;
	}
	
	public void setSlot(String cKey, String lKey) {
		this.cKey = cKey;
		this.lKey = lKey;
	}

	public String getCKey() {
		return this.cKey;
	}
	
	public String getLKey() {
		return this.lKey;
	}
	
}

