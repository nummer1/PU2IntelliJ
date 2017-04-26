package Algorithm;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Course implements Comparable<Course>{

    private String courseId;
    private String courseName;
    private String description;
    private String faculty;
    private Date examDate;
    private int difficulty;
    private ArrayList<String> dependencies = new ArrayList<>();
    private boolean isSpring;
    private boolean isAutumn;
    private boolean isAgile;
    private double score;
    private double studypoints;
    private String URL;

    /**
     * One of two constructors for Course.java. We have two constructors since most courses got 7,5 studypoints,
     * but not all. The first constructor lets the developer choose studypoints, while the other defaults to 7,5
     * @param courseId e.g TDT4140
     * @param season e.g spring
     * @param studypoints e.g 7,5
     */
    public Course(String courseId, String season, Double studypoints) {
        this.courseId = courseId;
        this.studypoints = studypoints;
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

    public Course(String courseId, String season) {
        this.courseId = courseId;
        this.studypoints = 7.5;
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

    public boolean isAutumn() { return this.isAutumn; }

    public boolean isSpring() { return this.isSpring; }

    public boolean isAgile() { return this.isAgile; }

    public String getCourseId() {
        return courseId;
    }

    /**
     * Sanitize the input for courseId
     * First 2-6 chars, followed by 4-6 numbers.
     * In CS there is mainly 3 chars followed by 4 numbers. (only exphil is 4 chars + 4 numbers)
     * @param courseId the courseId to sanitize
     */
    public void setCourseId(String courseId) {
        String regex = "^[A-Z]{2,6}[0-9]{4,6}$";
        if (courseId.matches(regex)) {
            this.courseId = courseId;
        } else {
            throw new IllegalArgumentException("Invalid course ID");
        }
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        if (faculty == null) {
            this.faculty = null;
        } else if (faculty.chars().allMatch(Character::isLetter)) {
            this.faculty = faculty;
        } else {
            throw new IllegalArgumentException("The faculty name can only contain alphabetic characters");
        }
    }

    public Date getExamDate() {
        return examDate;
    }

    public String getPrintable_date () {
        String dateString;
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dateString = df.format(this.examDate);
            return dateString;
        } catch (NullPointerException e) {
            return "The exam date can't be found";
        }
    }

    public void setExamDate(String exam_string) {
        if (exam_string == null) {
            this.examDate = null;
        } else {
            Date exam_date;
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            try {
                exam_date = df.parse(exam_string);
            } catch (ParseException e) {
                exam_date = new Date(); // if parseException, sets date to now
            }
            if (exam_date.after(new Date())) {
                this.examDate = exam_date;
            } else {
                throw new IllegalArgumentException("You must pick a date after the current date");
            }
        }
    }

    public void setExam_Date(Date exam_date) {
        this.examDate = exam_date;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        if (1 <= difficulty && difficulty <= 10) {
            this.difficulty = difficulty;
        } else {
            throw new IllegalArgumentException("The difficulty must be between 1 and 10");
        }
    }

    public void addDependency(String dependency) {
        this.dependencies.add(dependency);
    }

    public void removeDependency(String dependency) {
        if (this.dependencies.contains(dependency)) {
            this.dependencies.remove(dependency);
        } else {
            throw new IllegalArgumentException("The dependency was not found.");
        }
    }

    public ArrayList<String> getDependencies() {
        return this.dependencies;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }


    public double getStudypoints() {
        return studypoints;
    }

    public void setStudypoints(double studypoints) {
        this.studypoints = studypoints;
    }

    public String getURL() { return this.URL; }

    public void setURL(String URL) { this.URL = URL; }

    @Override
    public int compareTo(Course o) {
        return Double.compare(this.score, o.score);
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Course) && courseId.equals(((Course) o).courseId);
    }

    @Override
    public String toString() {
        return courseName;
    }
}
