package Algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Erlend on 06.03.2017.
 */
public class Semester {

    private Collection<Course> courses = new ArrayList<>();
    private boolean isSpring;
    private boolean isAutumn;
    private double studypoints;

    public Semester() { }

    public Semester(String season) {
        this.studypoints = 0.0;
        if(season.toLowerCase().equals("spring")) {
            this.isSpring = true;
            this.isAutumn = false;
        } else if (season.toLowerCase().equals("autumn")) {
            this.isAutumn = true;
            this.isSpring = false;
        } else {
            throw new IllegalArgumentException("The season must be either spring or autumn");
        }
    }

    public void setSpring() {
        isAutumn = false;
        isSpring = true;
    }

    public void setAutumn() {
        isSpring = false;
        isAutumn = true;
    }

    public void addCourse(Course course) {
        this.studypoints += course.getStudypoints();
        this.courses.add(course);
    }

    public void addCourseList(List<Course> courseList) {
        for (Course course : courseList) {
            this.studypoints += course.getStudypoints();
        }
        this.courses = courseList;
    }

    public Collection<Course> getCourses() {
        return this.courses;
    }

    public Double getStudypoints() {
        return studypoints;
    }

    public boolean isFilled() {
        return this.studypoints >= 30.0;
    }

    public void fillWithElectives() {
        while(!isFilled()) {
            Course c = new Course("valg", "agile", 7.5);
            c.setCourseName("Valgfag");
            addCourse(c);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Course c : courses) {
            sb.append(c.toString());
            sb.append(", ");
        }
        return sb.toString();
    }

}
