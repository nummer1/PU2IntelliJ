package Algorithm;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Erlend on 06.03.2017.
 */
public class StudyPlan {

    private Collection<Semester> semesters = new ArrayList<>();
    private String name;
    private String major;
    private int score;

    public StudyPlan(String name) {
        this.name = name;
    }

    public void addSemester(Semester semester) {
        this.semesters.add(semester);
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

}
