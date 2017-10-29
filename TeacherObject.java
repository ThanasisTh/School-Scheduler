package src;

import java.util.Vector;

public class TeacherObject
{
    private int teacherCode;
    private String teacherName;
    Vector lessonsCodes = new Vector();
    private int dayHours;
    private int weekHours;

    public TeacherObject(int code, String name, Vector lessonsCodes, int dayHours, int weekHours)
    {
        teacherCode = code;
        teacherName = name;
        this.lessonsCodes = lessonsCodes;
        this.dayHours = dayHours;
        this.weekHours = weekHours;
    }
    public int getTeacherCode()
    {
        return teacherCode;
    }

    public String getTeacherName()
    {
        return teacherName;
    }

    public Vector getLessonCodes()
    {
        return this.lessonsCodes;
    }

    public int getDayHours()
    {
        return this.dayHours;
    }

    public int getWeekHours()
    {
        return this.weekHours;
    }
}