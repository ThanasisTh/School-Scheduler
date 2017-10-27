package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Main {
    public static void main(String[] args) {

        try {
            BufferedReader in = new BufferedReader(new FileReader("C:\\tmp\\lessons.txt"));
            HashMap<Integer, Vector> lessons= new HashMap<Integer, Vector>();
            Vector classA = new Vector();
            Vector classB = new Vector();
            Vector classC = new Vector();
            String str;
            char schoolClass;
            int schoolClassHours;

            in.readLine();
            while ((str = in.readLine()) != null) {
                String[] line = str.split("-");
                if (line[2].contains("a")){
                    schoolClass = 'a';
                    schoolClassHours = Character.getNumericValue(line[2].charAt(line[2].indexOf("a")+1));
                    LessonObject lesson = new LessonObject(line[1], schoolClass, schoolClassHours);
                    System.out.println("class and hours a= "+lesson.getSchoolClass()+" "+lesson.getHours() + " " + lesson.getName());
                    classA.addElement(lesson);
                }
                if(line[2].contains("b")){
                    schoolClass = 'b';
                    schoolClassHours = Character.getNumericValue(line[2].charAt(line[2].indexOf("b")+1));
                    LessonObject lesson = new LessonObject(line[1], schoolClass, schoolClassHours);
                    System.out.println("class and hours b= "+lesson.getSchoolClass()+" "+lesson.getHours() + " " + lesson.getName());
                    classB.addElement(lesson);
                }
                if(line[2].contains("c")){
                    schoolClass = 'c';
                    schoolClassHours = Character.getNumericValue(line[2].charAt(line[2].indexOf("c")+1));
                    LessonObject lesson = new LessonObject(line[1], schoolClass, schoolClassHours);
                    classC.addElement(lesson);
                }
            }

        } catch (IOException e) {
        }
    }
}