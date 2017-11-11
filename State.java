import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.ArrayList;

public class State implements Comparable<State>{
	
	private Slot[][] schedule;	//school schedule, AxB matrix where A==dayHour, B == TKey
	private int score;
	private HashMap<String, Vector<Lesson>> classLessons; //keys are classes, a1, b1, etc.
	private HashMap<Integer, Teacher> teachers;
	private int[] dayHours = new int[35]; //
	private int slotsFilled;
	private HashMap<String, Integer> classNames;
	private Vector<Integer> lessonKeys;
	
	public State(HashMap<String, Vector<Lesson>> classLessons, HashMap<Integer, Teacher> teachers, Vector<Integer> lessonKeys) {
		this.schedule = new Slot[teachers.size()][this.dayHours.length];
		this.score = -1;
		this.slotsFilled = 0;
		for(String s : classLessons.keySet()) {
			classNames.put(s, 0);
		}
		this.classLessons = classLessons;
		this.teachers = teachers;
		this.lessonKeys = lessonKeys;
	}
	
	public State(Slot[][] schedule, HashMap<String, Vector<Lesson>> classLessons, HashMap<Integer, Teacher> teachers, int slotsFilled, HashMap<String, Integer> classNames, Vector<Integer> lessonKeys) {
		this.schedule = schedule;
		this.score = -1;
		this.slotsFilled = slotsFilled;
		this.classNames = classNames;
		this.classLessons = classLessons;
		this.teachers = teachers;
		this.lessonKeys = lessonKeys;
	}
	
	public boolean assignSlot(String cKey, int lKey, int tKey, int dayHour) {
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
	public ArrayList<State> getChildren() {
		ArrayList<State> children = new ArrayList<State>();
		State child = new State(this.schedule, this.classLessons, this.teachers, this.slotsFilled, this.classNames, this.lessonKeys);
		
		for(Map.Entry<String, Vector<Lesson>> c : this.classLessons.entrySet()) {
			for(int d=0; d<dayHours.length; d++) {
				for(Lesson l : c.getValue()) {
					for(Map.Entry<Integer, Teacher> t : teachers.entrySet()) {	
						if(t.getValue().getLKeys().contains(l.getKey())) {
							if(teachers.get(t.getKey()).getWeekHours()!=0 && l.getHours()!=0) {
								if(teachers.get(t.getKey()).getDayHours()[dayHours[d]/10-1]!=0) {
									if(child.assignSlot(c.getKey(), l.getKey(), t.getKey(), d)) {
										child.evaluate();
										l.decreaseHours();
										teachers.get(t.getKey()).decreaseDayHours(dayHours[d]/10-1);
										this.slotsFilled++;
										this.classNames.put(c.getKey(), this.classNames.get(c.getKey())+1);
										children.add(child);
										child = new State(this.schedule, this.classLessons, this.teachers, this.slotsFilled, this.classNames, this.lessonKeys);
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
	
	/***
	 * Various checks (limitations 1-5) and increases score
	 * (limitation 1) uses emptyPeriods, class names are added here if they have a lesson on the specified hours of the day
	 * (limitation 2) uses intensiveCounter, increases every time the same teacher works for one hour, resets to 0 if the teacher hasn't worked for more than 2 consecutive hours
	 * (limitation 3) uses this.classNames, increases every time a class is taught
	 * (limitation 4) uses lessonsTaught, class names are added here if they were taught the specified lesson on one day
	 * (limitation 5) uses fairnessCounters, increases for each hour a teacher works
	 */
	public void evaluate() {
		//HashSet<Position> positions = new HashSet<Position>(); //store position of "problematic" slots here (used with different algorithm)
		HashMap<Integer, Vector<String>> emptyPeriods = new HashMap<Integer, Vector<String>>();		
		emptyPeriods.put(0, null);
		emptyPeriods.put(1, null);
		emptyPeriods.put(2, null);
		emptyPeriods.put(3, null);
		emptyPeriods.put(4, null);
		emptyPeriods.put(5, null);
		emptyPeriods.put(6, null);
		HashMap<Integer, Vector<String>> lessonsTaught = new HashMap<Integer, Vector<String>>();	
		for(int key: this.lessonKeys) {
			lessonsTaught.put(key, null);	
		}
		int intensiveCounter = 0;																	
		int[] fairnessCounters = new int[this.schedule.length];										
																									
		
		//loop through teachers
		for(int i=0; i<this.schedule.length; i++) {
			//loop through days
			for(int j=0; j<5; j++) {
				//loop through hours of day
				for(int k=0; k<7; k++) {	
					Slot slot = this.schedule[i][j*5+k];
					//update counters
					if(slot!=null) {
						intensiveCounter++;
						fairnessCounters[i]++;
						if(intensiveCounter>2) {
							this.score++;
						}
						if(!emptyPeriods.get(k).contains(slot.getCKey())) {
							emptyPeriods.get(k).add(slot.getCKey());
						}
						lessonsTaught.get(slot.getLKey()).add(slot.getCKey());
					}
					else {
						intensiveCounter = 0;
					}
				}
				intensiveCounter = 0;
				//this loop checks if any classes have not been added to emptyPeriods (for the k-th day), and therefore have empty periods
				for(int n=0; n<emptyPeriods.size(); n++) {
					for(String s : this.classNames.keySet()) {
						if(!emptyPeriods.get(n).contains(s)) {
							this.score++;
						}
					}
					emptyPeriods.replace(n, null);
				}
				//this loop checks if any classes have been added to lessonsTaught more than once (in the same day) 
				int smallCounter = 0;
				for(int n=0; n<lessonsTaught.size(); n++) {
					for(String s : this.classNames.keySet()) {
						while(lessonsTaught.get(n).contains(s)) {
							smallCounter++;
							lessonsTaught.get(n).remove(s);
							if(smallCounter>1) {
								this.score++;
							}
						}
					}
					smallCounter = 0;
					lessonsTaught.replace(n, null);
				}
			}
			intensiveCounter = 0;
		}
		int average = 0;
		//calculate sum and then average working hours of all teachers, and check if any of them worked for more hours than the average
		for(int i=0; i<fairnessCounters.length; i++) {
			average += fairnessCounters[i];
		}
		average /= fairnessCounters.length;
		for(int i=0; i<fairnessCounters.length; i++) {
			if(fairnessCounters[i]>average) {
				this.score++;
				for(int j=0; j<this.dayHours.length; j++) {
					if(this.schedule[i][j]!=null) {
					}
				}
			}
		}
		//this loop checks if any classes have been taught for less than 32 hours, which is the total for all subjects of each class
		for(Map.Entry<String, Integer> c : this.classNames.entrySet()) {
			if(c.getValue() < 32) {
				this.score += 32 - c.getValue();
			}
		}
	}
	
	public boolean isTerminal() {
		if(this.classLessons.isEmpty() || this.teachers.isEmpty()) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this.slotsFilled != ((State)obj).slotsFilled) {
			return false;
		}
		for(int i=0; i<this.schedule.length; i++) {
			for(int j=0; j<this.dayHours.length; i++) {
				if(this.schedule[i][j] != ((State)obj).schedule[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		return this.slotsFilled;
	}
	
	@Override
    //We override the compareTo function of this class so only the heuristic scores are compared
	public int compareTo(State s) {
		return Double.compare(this.score, s.score);
	}
	
	public void print()
	{
		System.out.println("------------------------------------");
		for(int i=0; i<this.schedule.length; i++)
		{
			for(int j=0; j<this.schedule[0].length; j++)
			{
				if(this.schedule[i][j].isEmpty())
				{
					System.out.print(' ');
				}
				else
				{
					System.out.print(this.schedule[i][j].toString());
				}
				if(j < this.schedule[0].length - 1)
				{
					System.out.print('\t');
				}
			}
			System.out.println(this.slotsFilled);
		}
		System.out.println("------------------------------------");
	}
	
	
	/*swaps the slot at position teacher,dayHour with the slot at t,d (used for different algorithm)
	public boolean changeSlot(int teacher, int dayHour, int t, int d) {
		Slot slot = this.schedule[teacher][dayHour];
		if(t==teacher && d==dayHour) {
			return false;
		}
		if(!this.teachers.get(t+1).getLKeys().contains(slot.getLKey())) {
			return false;
		}
		this.schedule[teacher][dayHour] = this.schedule[t][d];
		this.schedule[t][d] = slot;		
		return true;
	}
	
	public ArrayList<State> getChildren(int a) {
		ArrayList<State> children = new ArrayList<State>();
		State child = new State(this.schedule, this.classLessons, this.teachers, this.slotsFilled, this.classNames, this.lessonKeys);
		HashSet<Position> positions = child.heuristics();
		child.evaluate();
		//for every slot in the schedule that violates a limitation, create a child
		for(Position p : positions) {
			for(int d=0; d<dayHours.length; d++) {
				for(int t=0; t<this.schedule.length; t++) {
					if(child.changeSlot(p.getI(), p.getJ(), t, d)) {
						children.add(child);
						child = new State(this.schedule, this.classLessons, this.teachers, this.slotsFilled, this.classNames, this.lessonKeys);
					}
				}
			}
		}
		return children;
	}
	*/
	
}
