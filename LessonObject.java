package src;

public class LessonObject
{
    private String lessonName;
    private char lessonSchoolClass;
    private int lessonHours;
    public LessonObject(String name, char schoolClass, int hours)
    {

        lessonName = name;
        lessonSchoolClass = schoolClass;
        lessonHours = hours;
    }
    public String getName()
    {
        return lessonName;
    }

    public char getSchoolClass()
    {
        return lessonSchoolClass;
    }

    public int getHours()
    {
        return lessonHours;
    }
}