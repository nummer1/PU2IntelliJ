import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Erlend on 13.02.2017.
 */

public class Course {

    private String course_id;
    private String description;
    private String faculty;
    private Date exam_date;
    private int difficulty;
    ArrayList<Course> dependencies = new ArrayList<Course>();

    public Course(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    // Sanitize the input for course_id
    // First 2-6 chars, followed by 4-6 numbers.
    // In CS there is mainly 3 chars followed by 4 numbers. (only exphil is 4 chars + 4 numbers)
    public void setCourse_id(String course_id) {
        //TODO
        this.course_id = course_id;
    }

    public String getDescription() {
        return description;
    }

    // No sanitazion necessary, one is free to write whatever you may wish in the description
    public void setDescription(String description) {
        this.description = description;
    }

    public String getFaculty() {
        return faculty;
    }

    // Sanitize the input for faculties
    // Faculties are only alphabetic
    public void setFaculty(String faculty) {
        if (faculty.chars().allMatch(Character::isLetter)) {
            this.faculty = faculty;
        } else {
            throw new IllegalArgumentException("The faculty name can only contain alphabetic characters");
        }
    }

    public Date getExam_date() {
        return exam_date;
    }

    // Sanitize the input to make sure it is after the current day
    // May be better to take a string as input, and convert it into date in this method
    public void setExam_date(Date exam_date) {
        if (exam_date.after(new Date())) {
            this.exam_date = exam_date;
        } else {
            throw new IllegalArgumentException("You must pick a date after the current date");
        }
    }

    public int getDifficulty() {
        return difficulty;
    }

    // Sanitize the input for difficulty
    // Difficulty ranges from 1-10, where 10 is the most difficult
    public void setDifficulty(int difficulty) {
        if (1 <= difficulty && difficulty <= 10) {
            this.difficulty = difficulty;
        } else {
            throw new IllegalArgumentException("The difficulty must be between 1 and 10");
        }
    }

    public void addDependency(Course dependency) {
        this.dependencies.add(dependency);
    }

    public void removeDependency(Course dependency) {
        if (this.dependencies.contains(dependency)) {
            this.dependencies.remove(dependency);
        } else {
            throw new IllegalArgumentException("The dependency was not found.");
        }
    }

    public ArrayList<Course> getDependencies() {
        return this.dependencies;
    }

}
