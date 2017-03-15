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

    public Semester(String season) {
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

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public void addCourseList(List<Course> courseList) { this.courses = courseList; }

    public Collection getCourses() {
        return this.courses;
    }

    public boolean isFilled() {
        if (courses.size() > 4) {
            return true;
        } else {
            return false;
        }
    }



}
