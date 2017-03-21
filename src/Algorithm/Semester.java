package Algorithm;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Erlend on 06.03.2017.
 */
public class Semester {

    private Collection courses = new ArrayList<Course>();
    private boolean isSpring;
    private boolean isAutumn;

    public Semester() { }

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

    public void setSpring() {
        isAutumn = false;
        isSpring = true;
    }

    public void setAutumn() {
        isSpring = false;
        isAutumn = true;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

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
