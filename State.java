import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.ArrayList;

public class State implements Comparable<State>{
	
	private Slot[][] schedule;	//school schedule, AxB matrix where A==dayHour, B == TKey, this should prevent sparse final matrix
	private int score;
	private HashMap<String, Vector<Lesson>> classLessons; //keys are classes, a1, b1, etc.
	private HashMap<Integer, Teacher> teachers;
	private int[] dayHours = { 11, 21, 31, 41, 51, 
								12, 22, 32, 42, 52,
								13, 23, 33, 43, 53,
								14, 24, 34, 44, 54,
								15, 25 ,35, 45, 55,
								16, 26, 36, 46, 56,
								17, 27, 37, 47, 57 }; //11 == Monday - 1st hour, 21 == Tuesday - 1st hour, etc ;
	private int slotsFilled;
	private Vector<String> classNames;
	
	public State(HashMap<String, Vector<Lesson>> classLessons, HashMap<Integer, Teacher> teachers) {
		this.schedule = new Slot[teachers.size()][this.dayHours.length];
		this.score = -1;
		this.slotsFilled = 0;
		for(String s : classLessons.keySet()) {
			classNames.add(s);
		}
		this.classLessons = classLessons;
		this.teachers = teachers;
		
	}
	
	public State(Slot[][] schedule, HashMap<String, Vector<Lesson>> classLessons, HashMap<Integer, Teacher> teachers, int slotsFilled, Vector<String> classNames) {
		this.schedule = schedule;
		this.score = -1;
		this.slotsFilled = slotsFilled;
		this.classNames = classNames;
		this.classLessons = classLessons;
		this.teachers = teachers;
	}
	
	public boolean assignSlot(String cKey, String lKey, int tKey, int dayHour) {
		if(this.schedule[tKey][dayHour] != null) {
			return false;
		}
		this.schedule[tKey][dayHour].setSlot(cKey, lKey);
		return true;
	}
	
	 /***
     * Generates the children-states of this state
     * Each child-state is created by assigning one lesson from one class to one teacher on a specific hour of the week
     */
	public ArrayList<State> getChildren(int heuristic) {
		ArrayList<State> children = new ArrayList<State>();
		State child = new State(this.schedule, this.classLessons, this.teachers, this.slotsFilled, this.classNames);
		
		//generating process should be 100% complete
		for(Map.Entry<String, Vector<Lesson>> c : this.classLessons.entrySet()) {
			for(int d=0; d<dayHours.length; d++) {
				for(Lesson l : c.getValue()) {
					for(Map.Entry<Integer, Teacher> t : teachers.entrySet()) {
						
						//should be ok now	
						if(t.getValue().getLKeys().contains(l.getKey())) {
							if(teachers.get(t.getKey()).getWeekHours()!=0 && l.getHours()!=0) {
								if(teachers.get(t.getKey()).getDayHours()[dayHours[d]/10-1]!=0) {
									if(child.assignSlot(c.getKey(), l.getKey(), t.getKey(), d)) {
										child.evaluate();
										l.decreaseHours();
										teachers.get(t.getKey()).decreaseDayHours(dayHours[d]/10-1);
										this.slotsFilled++;
										children.add(child);
										child = new State(this.schedule, this.classLessons, this.teachers, this.slotsFilled, this.classNames);
									}
								}
							}
							if(l.getHours()==0) {
								c.getValue().remove(l);
								if(c.getValue().isEmpty()) {
									this.classLessons.remove(c.getKey());
								}
							}
							if(teachers.get(t.getKey()).getWeekHours()==0) {
								teachers.remove(t.getKey());
							}
						}
						
					}
				}
			}
		}
		return children;
	}
	
	//Checks if a teacher gets more than two consecutive hours, if any teachers get more hours than others, or if any classes have empty periods
	public void heuristicA() {
		HashMap<Integer, Vector<String>> classHours = new HashMap<Integer, Vector<String>>();
		classHours.put(0, null);
		classHours.put(1, null);
		classHours.put(2, null);
		classHours.put(3, null);
		classHours.put(4, null);
		classHours.put(5, null);
		classHours.put(6, null);
		
		int intensiveCounter = 0;
		int[] fairnessCounters = new int[this.schedule.length];
		for(int i=0; i<this.schedule.length; i++) {
			for(int j=0; j<4; j++) {
				for(int k=0; k<6; k++) {	
					if(this.schedule[i][j*10+k]!=null) {
						intensiveCounter++;
						fairnessCounters[i]++;
						if(intensiveCounter>2) {
							this.score++;
						}
						classHours.get(k).add(this.schedule[i][j*10+k].getCKey());
					}
					else {
						intensiveCounter = 0;
					}
				}
				intensiveCounter = 0;
			}
			intensiveCounter = 0;
		}
		int average = 0;
		for(int i=0; i<fairnessCounters.length; i++) {
			average += fairnessCounters[i];
		}
		average /= fairnessCounters.length;
		for(int i=0; i<fairnessCounters.length; i++) {
			if(fairnessCounters[i]>average) {
				this.score++;
			}
		}
		for(int i=0; i<classHours.size(); i++) {
			for(String s : classNames) {
				if(!classHours.get(i).contains(s)) {
					this.score++;
				}
			}
		}
	}
	
	//TODO checks if the classes' lessons are distributed uniformly in the week
	public boolean isUniform() {
		
		return true;
	}
		
	private void evaluate() {
		this.heuristicA();
		if(!this.isUniform()) {
			this.score++;
		}
	}
	
	//state is terminal if all classes and lessons have been assigned
	public boolean isTerminal() {
		if(this.classLessons.isEmpty() || this.teachers.isEmpty()) {
			return true;
		}
		return false;
	}
	
	//TODO
	@Override
	public boolean equals(Object obj) {
		return true;
	}
	
	@Override
	public int hashCode() {
		return this.slotsFilled;
	}
	
	@Override
    //We override the compareTo function of this class so only the heuristic scores are compared
	public int compareTo(State s)
	{
		return Double.compare(this.score, s.score);
	}
		
	
}
