import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    public static void main(String[] args) {

        HashMap<String, Vector<Lesson>> lessons= new HashMap<String, Vector<Lesson>>();
        HashMap<Integer, Teacher> teachers= new HashMap<Integer, Teacher>();
        Vector<Lesson> classA = new Vector<Lesson>();
        Vector<Lesson> classB = new Vector<Lesson>();
        Vector<Lesson> classC = new Vector<Lesson>();
        String str;
        char schoolClass;
        int schoolClassHours;
        int lKey = 0;
        int numberOfClassesA = 1;
        int numberOfClassesB = 1;
        int numberOfClassesC = 1;
        Vector<Integer> lessonKeys = new Vector<Integer>();
        
        try {
            BufferedReader inLessons = new BufferedReader(new FileReader("C:\\tmp\\lessons.txt"));
            BufferedReader inTeachers = new BufferedReader(new FileReader("C:\\tmp\\teachers.txt"));
	        try {   
	            inLessons.readLine();
	            while ((str = inLessons.readLine()) != null) {
	                String[] line = str.split("-");
	                Pattern p = Pattern.compile("(\\d+)");
	                Matcher m = p.matcher(line[0]);
	                lKey = Integer.valueOf(m.group(1));
	                lessonKeys.add(lKey);
	                
	                if (line[2].contains("a")){
	                    schoolClass = 'a';
	                    schoolClassHours = Character.getNumericValue(line[2].charAt(line[2].indexOf("a")+1));
	                    Lesson lesson = new Lesson(lKey, line[1], schoolClass, schoolClassHours);
	                    classA.addElement(lesson);               
	                }
	                if(line[2].contains("b")){
	                    schoolClass = 'b';
	                    schoolClassHours = Character.getNumericValue(line[2].charAt(line[2].indexOf("b")+1));
	                    Lesson lesson = new Lesson(lKey, line[1], schoolClass, schoolClassHours);
	                    classB.addElement(lesson);
	                }
	                if(line[2].contains("c")){
	                    schoolClass = 'c';
	                    schoolClassHours = Character.getNumericValue(line[2].charAt(line[2].indexOf("c")+1));
	                    Lesson lesson = new Lesson(lKey, line[1], schoolClass, schoolClassHours);
	                    classC.addElement(lesson);
	                }
	            }
	        }
	        finally {
            	inLessons.close();
            }
            for(int i=0; i<numberOfClassesA-1; i++) {
            	lessons.put("a"+(i+1), classA);
            }
            for(int i=0; i<numberOfClassesB-1; i++) {
            	lessons.put("b"+(i+1), classB);
            }
            for(int i=0; i<numberOfClassesC-1; i++) {
            	lessons.put("c"+(i+1), classC);
            }
            try {
	            inTeachers.readLine();
	            while ((str = inTeachers.readLine()) != null) {
	                String[] line = str.split("-");
	                Vector<Integer> lKeys = new Vector<Integer>();
	                
	                String[] lessonsHolder = line[2].split(",");
	                for(int i=0; i<lessonsHolder.length; i++) {
	                	lKeys.add(Integer.parseInt(lessonsHolder[i]));
	                }
	                int[] tDayHours = new int[5];
	                for(int i=0; i<tDayHours.length; i++) {
	                	tDayHours[i] = Integer.parseInt(line[3]);
	                }
	                
	                Teacher teacher = new Teacher(Integer.parseInt(line[0]), line[1], lKeys, tDayHours, Integer.parseInt(line[4]));
	                teachers.put(teacher.getKey(), teacher);
	            }
            }
            finally {
            	inTeachers.close();
            }
        }
        catch (IOException e) {
        }
        
        State initialState = new State(lessons, teachers, lessonKeys);
        SpaceSearcher spacesearcher = new SpaceSearcher();
        State terminalState = null;
        
        long start = System.currentTimeMillis();
        terminalState = spacesearcher.bestFSClosedSet(initialState);
        long end = System.currentTimeMillis();
        if(terminalState == null) {
			System.out.println("Could not find solution");
		}
        else {
			terminalState.print();
		}
        System.out.println("BestFS with closed set search time: " + (double)(end - start) / 1000 + " sec.");
        
    }
}