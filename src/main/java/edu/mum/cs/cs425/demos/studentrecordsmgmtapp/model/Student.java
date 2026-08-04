package edu.mum.cs.cs425.demos.studentrecordsmgmtapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a university student record.
 */
public class Student {
    private static final DateTimeFormatter INPUT_DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private long studentId;
    private String name;
    private LocalDate dateOfAdmission;

    /** Default constructor. */
    public Student() {
    }

    /** Full constructor using a LocalDate admission date. */
    public Student(long studentId, String name, LocalDate dateOfAdmission) {
        this.studentId = studentId;
        this.name = name;
        this.dateOfAdmission = dateOfAdmission;
    }

    /** Convenience constructor using the assignment's MM/dd/yyyy date format. */
    public Student(long studentId, String name, String dateOfAdmission) {
        this(studentId, name, LocalDate.parse(dateOfAdmission, INPUT_DATE_FORMAT));
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfAdmission() {
        return dateOfAdmission;
    }

    public void setDateOfAdmission(LocalDate dateOfAdmission) {
        this.dateOfAdmission = dateOfAdmission;
    }

    @Override
    public String toString() {
        return String.format(
                "Student{studentId=%d, name='%s', dateOfAdmission=%s}",
                studentId,
                name,
                dateOfAdmission);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Student)) {
            return false;
        }
        Student student = (Student) other;
        return studentId == student.studentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }
}
