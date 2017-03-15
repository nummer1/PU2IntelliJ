package Algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Erlend on 06.03.2017.
 */
public class StudyPlan {

    private Map<Integer, Semester> semesters = new HashMap<>();
    private String name;
    private String major;
    private int score;

    public StudyPlan(String name) {
        this.name = name;
    }

    public void addSemester(Semester semester, Integer semesterNumber) {
        this.semesters.put(semesterNumber, semester);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMajor() {
        return this.major;
    }

    public ArrayList<Course> getCourses() {
        ArrayList<Course> array = new ArrayList<>();
        for(Semester sem : this.semesters) {
            array.addAll(sem.getCourses());
        }
        return array;
    }

}
