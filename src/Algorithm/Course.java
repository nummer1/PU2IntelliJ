package Algorithm;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Erlend on 13.02.2017.
 */

//TODO fix nullpointer exception when setting values in course

public class Course implements Comparable<Course>{

    private String course_id;
    private String course_name;
    private String description;
    private String faculty;
    private Date exam_date;
    private int difficulty;
    private ArrayList<Course> dependencies = new ArrayList<Course>();
    private boolean isSpring;
    private boolean isAutumn;
    private boolean isAgile;
    private int score;

    public Course(String course_id, String season) {
        this.course_id = course_id;
        if (season.equals("agile")) {
            isAgile = true;
            isAutumn = true;
            isSpring = true;
        } else if (season.equals("spring")) {
            isSpring = true;
            isAutumn = false;
            isAgile = false;
        } else if (season.equals("autumn")) {
            isAutumn = true;
            isSpring = false;
            isAgile = false;
        }
    }

    public String getCourse_id() {
        return course_id;
    }

    // Sanitize the input for course_id
    // First 2-6 chars, followed by 4-6 numbers.
    // In CS there is mainly 3 chars followed by 4 numbers. (only exphil is 4 chars + 4 numbers)
    public void setCourse_id(String course_id) {
        String regex = "^[A-Z]{2,6}[0-9]{4,6}$";
        if (course_id.matches(regex)) {
            this.course_id = course_id;
        } else {
            throw new IllegalArgumentException("Invalid course ID");
        }
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
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

    public String getPrintable_date () {
        String dateString = null;
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dateString = df.format(this.exam_date);
            return dateString;
        } catch (NullPointerException e) {
            return "The exam date can't be found";
        }
    }

    // Sanitize the input to make sure it is after the current day
    // May be better to take a string as input, and convert it into date in this method
    public void setExam_date(String exam_string) {
        Date exam_date;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            exam_date = df.parse(exam_string);
            String newDateString = df.format(exam_date);
        } catch (ParseException e) {
            exam_date = new Date(); // if parseException, sets date to now
        }
        if (exam_date.after(new Date())) {
            this.exam_date = exam_date;
        } else {
            throw new IllegalArgumentException("You must pick a date after the current date");
        }
    }

    public void setExam_Date(Date exam_Date) {
        this.exam_date = exam_date;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(Course o) {
        return Integer.compare(this.score, o.score);
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Course) && course_id.equals(((Course) o).course_id);
    }

    @Override
    public String toString() {
        return course_id;
    }
}
