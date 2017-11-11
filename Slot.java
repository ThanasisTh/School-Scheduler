public class Slot {
	
	private String cKey;
	private int lKey;	//Class and Lesson keys
	
	public Slot() {
		this.cKey = null;
		this.lKey = 0;
	}
	
	public void setSlot(String cKey, int lKey) {
		this.cKey = cKey;
		this.lKey = lKey;
	}

	public String getCKey() {
		return this.cKey;
	}
	
	public int getLKey() {
		return this.lKey;
	}
	
	public boolean isEmpty() {
		if(this.cKey==null || this.lKey==0) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		return this.cKey + "-" + this.lKey;
	}
	
}

