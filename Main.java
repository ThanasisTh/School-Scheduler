package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Main {
    public static void main(String[] args) {

        try {
            BufferedReader inLessons = new BufferedReader(new FileReader("C:\\tmp\\lessons.txt"));
            BufferedReader inTeachers = new BufferedReader(new FileReader("C:\\tmp\\teachers.txt"));

            HashMap<Integer, Vector> lessons= new HashMap<Integer, Vector>();
            HashMap<Integer, Vector> teachers= new HashMap<Integer, Vector>();
            Vector classA = new Vector();
            Vector classB = new Vector();
            Vector classC = new Vector();
            String str;
            char schoolClass;
            int schoolClassHours;

            inLessons.readLine();
            while ((str = inLessons.readLine()) != null) {
                String[] line = str.split("-");
                if (line[2].contains("a")){
                    schoolClass = 'a';
                    schoolClassHours = Character.getNumericValue(line[2].charAt(line[2].indexOf("a")+1));
                    LessonObject lesson = new LessonObject(line[1], schoolClass, schoolClassHours);
                    classA.addElement(lesson);
                }
                if(line[2].contains("b")){
                    schoolClass = 'b';
                    schoolClassHours = Character.getNumericValue(line[2].charAt(line[2].indexOf("b")+1));
                    LessonObject lesson = new LessonObject(line[1], schoolClass, schoolClassHours);
                    classB.addElement(lesson);
                }
                if(line[2].contains("c")){
                    schoolClass = 'c';
                    schoolClassHours = Character.getNumericValue(line[2].charAt(line[2].indexOf("c")+1));
                    LessonObject lesson = new LessonObject(line[1], schoolClass, schoolClassHours);
                    classC.addElement(lesson);
                }
            }

            inTeachers.readLine();
            while ((str = inTeachers.readLine()) != null) {
                String[] line = str.split("-");
                Vector classes = new Vector();

                String[] classesHolder = line[2].split(",");
                Collections.addAll(classes, classesHolder);

                TeacherObject teacher = new TeacherObject(Integer.parseInt(line[0]), line[1], classes, Integer.parseInt(line[3]), Integer.parseInt(line[4]));
//                System.out.print(teacher.getTeacherCode());
//                System.out.print(teacher.getTeacherName());
//                System.out.print(teacher.getLessonCodes());
//                System.out.print(teacher.getDayHours());
//                System.out.println(teacher.getWeekHours());
            }

        } catch (IOException e) {
        }
    }
}