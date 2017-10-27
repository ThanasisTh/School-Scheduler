import java.util.HashMap;
import java.util.Iterator;
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
	
	public State() {
		this.schedule = null;
		this.score = 0;
	}
	
	public State(Slot[][] schedule) {
		this.schedule = schedule;
		//TODO: read data from files
	}
	
	public boolean assignSlot(String cKey, String lKey, int tKey, int dayHour) {
		if(this.schedule[dayHour][tKey] != null) {
			return false;
		}
		this.schedule[dayHour][tKey].setSlot(cKey, lKey);
		return true;
	}
	
	 /***
     * Generates the children-states of this state
     * Each child-state is created by assigning one lesson from one class to one teacher on a specific hour of the week
     */
	public ArrayList<State> getChildren(int heuristic) {
		ArrayList<State> children = new ArrayList<State>();
		State child = new State(this.schedule);
		
		Iterator<Lesson> iterator;
		
		//TODO check generating process
		for(Map.Entry<Integer, Teacher> t : teachers.entrySet()) {
			for(Map.Entry<String, Vector<Lesson>> c : classLessons.entrySet()) {
				iterator = c.getValue().iterator();
				for(int d=0; d<dayHours.length; d++) {
					
					//should be ok now	
					Lesson next = iterator.next();
					if(t.getValue().getLKeys().contains(next.getKey())) {
						if(teachers.get(t.getKey()).getWeekHours()!=0 && next.getHours()!=0) {
							if(teachers.get(t.getKey()).getDayHours()[dayHours[d]/10-1]!=0) {
								if(child.assignSlot(c.getKey(), next.getKey(), t.getKey(), d)) {
									child.evaluate(); //use heuristic functions
									children.add(child);
									child = new State(this.schedule);
									next.decreaseHours();
									teachers.get(t.getKey()).decreaseDayHours(dayHours[d]/10-1);
								}
							}
						}
						if(next.getHours()==0) {
							c.getValue().remove(next);
							if(c.getValue().isEmpty()) {
								classLessons.remove(c.getKey());
							}
						}
						if(teachers.get(t.getKey()).getWeekHours()==0) {
							teachers.remove(t.getKey());
						}
					}
				}
			}
		}
		return children;
	}
	
	//Checks if a teacher gets more than two consecutive hours, or if any teachers get more hours than others
	public void heuristicA() {
		int intensiveCounter = 0;
		int[] fairnessCounters = new int[this.schedule[1].length];
		for(int i=0; i<this.schedule[1].length; i++) {
			for(int j=0; j<4; j++) {
				for(int k=0; k<6; k++) {	
					if(this.schedule[j*10+k][i]!=null) {
						intensiveCounter++;
						fairnessCounters[i]++;
						if(intensiveCounter>2) {
							this.score++;
						}
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
	}
	
	//TODO checks if the classes' lessons are distributed uniformly in the week
	public boolean isUniform() {
		return true;
	}
	
	//TODO checks if empty periods exist for classes
	public boolean isSparse() {
		return true;
	}
	
	private void evaluate() {
		this.heuristicA();
		if(this.isSparse()) {
			this.score++;
		}
		if(!this.isUniform()) {
			this.score++;
		}
	}
	
	//TODO state is terminal if all classes and lessons have been assigned
	public boolean isTerminal() {
		if(this.classLessons.isEmpty() || this.teachers.isEmpty()) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return 1;
	}
	
	@Override
    //We override the compareTo function of this class so only the heuristic scores are compared
	public int compareTo(State s)
	{
		return Double.compare(this.score, s.score);
	}
		
	
}
