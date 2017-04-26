package Algorithm;

import java.util.ArrayList;
import java.util.Collection;
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

    public Semester getSemester(int i) { return semesters.get(i); }

    public Collection<Semester> getSemesters() {
        return this.semesters.values();
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

    /**
     * Returns a list of all the courses in the studyplan as Course objects
     * @return a list of all the courses in the studyplan
     */
    public ArrayList<Course> getCourses() {
        ArrayList<Course> array = new ArrayList<>();
        for(Integer key : this.semesters.keySet()) {
            array.addAll(semesters.get(key).getCourses());
        }
        return array;
    }

    /**
     * Iterates through every semester in the studyplan, invoking the fillWithElectives method in each.
     * This method fills the semester with elective courses until the semester has 30 studypoints total
     */
    public void fillSemesterWithElectives() {
        for (Integer key : semesters.keySet()) {
            semesters.get(key).fillWithElectives(key);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Integer key : semesters.keySet()) {
            sb.append(key);
            sb.append(": ");
            sb.append(semesters.get(key));
            sb.append("\n");
        }
        return sb.toString();
    }

}
