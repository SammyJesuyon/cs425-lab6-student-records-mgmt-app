package edu.mum.cs.cs425.demos.studentrecordsmgmtapp;

import edu.mum.cs.cs425.demos.studentrecordsmgmtapp.model.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * CS425 Lab 6 command-line application.
 */
public final class MyStudentRecordsMgmtApp {
    private MyStudentRecordsMgmtApp() {
    }

    public static void main(String[] args) {
        String mode = args.length == 0 ? "all" : args[0].toLowerCase();
        Student[] students = sampleStudents();

        if ("all".equals(mode) || "students".equals(mode)) {
            runStudentExercises(students);
        }

        if ("all".equals(mode) || "practice".equals(mode)) {
            runPracticeExercises();
        }

        if (!"all".equals(mode) && !"students".equals(mode) && !"practice".equals(mode)) {
            throw new IllegalArgumentException(
                    "Unknown mode '" + mode + "'. Use all, students, or practice.");
        }
    }

    private static Student[] sampleStudents() {
        return new Student[] {
                new Student(110001, "Dave", "11/18/1951"),
                new Student(110002, "Anna", "12/07/1990"),
                new Student(110003, "Erica", "01/31/1974"),
                new Student(110004, "Carlos", "08/22/2009"),
                new Student(110005, "Bob", "03/05/1990")
        };
    }

    private static void runStudentExercises(Student[] students) {
        System.out.println("CS425 LAB 6 - STUDENT RECORDS");
        System.out.println();
        System.out.println("Students ordered by name (ascending):");
        printListOfStudents(students);

        System.out.println();
        System.out.println("Platinum alumni ordered by admission date (descending):");
        List<Student> platinumAlumni = getListOfPlatinumAlumniStudents(students);
        for (Student student : platinumAlumni) {
            System.out.println(student);
        }
    }

    private static void runPracticeExercises() {
        int[] helloWorldInput = {1, 5, 7, 10, 14, 35};
        System.out.println("CS425 LAB 6 - CODING PRACTICE");
        System.out.println();
        System.out.println("printHelloWorld input: " + Arrays.toString(helloWorldInput));
        printHelloWorld(helloWorldInput);

        int[] firstInput = {1, 2, 3, 4, 5};
        int[] secondInput = {19, 9, 11, 0, 12};
        System.out.println();
        System.out.printf(
                "findSecondBiggest(%s) = %d%n",
                Arrays.toString(firstInput),
                findSecondBiggest(firstInput));
        System.out.printf(
                "findSecondBiggest(%s) = %d%n",
                Arrays.toString(secondInput),
                findSecondBiggest(secondInput));
    }

    /**
     * Prints all students in ascending order of name without mutating the input array.
     */
    public static void printListOfStudents(Student[] students) {
        Student[] ordered = Arrays.copyOf(students, students.length);
        Arrays.sort(ordered, Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER));
        for (Student student : ordered) {
            System.out.println(student);
        }
    }

    /**
     * Returns students admitted at least 30 years ago, newest admission first.
     */
    public static List<Student> getListOfPlatinumAlumniStudents(Student[] students) {
        LocalDate cutoff = LocalDate.now().minusYears(30);
        List<Student> platinumAlumni = new ArrayList<Student>();

        for (Student student : students) {
            LocalDate admissionDate = student.getDateOfAdmission();
            if (admissionDate != null && !admissionDate.isAfter(cutoff)) {
                platinumAlumni.add(student);
            }
        }

        platinumAlumni.sort(
                Comparator.comparing(Student::getDateOfAdmission).reversed());
        return platinumAlumni;
    }

    /**
     * Prints Hello for multiples of 5, World for multiples of 7, and HelloWorld
     * for multiples of both 5 and 7. Other integers produce no output.
     */
    public static void printHelloWorld(int[] numbers) {
        for (int number : numbers) {
            if (number % 5 == 0 && number % 7 == 0) {
                System.out.println("HelloWorld");
            } else if (number % 5 == 0) {
                System.out.println("Hello");
            } else if (number % 7 == 0) {
                System.out.println("World");
            }
        }
    }

    /**
     * Returns the second-largest distinct value in one pass without sorting.
     */
    public static int findSecondBiggest(int[] numbers) {
        if (numbers == null || numbers.length < 2) {
            throw new IllegalArgumentException("At least two integers are required.");
        }

        Integer biggest = null;
        Integer secondBiggest = null;

        for (int number : numbers) {
            if (biggest == null || number > biggest) {
                secondBiggest = biggest;
                biggest = number;
            } else if (number < biggest
                    && (secondBiggest == null || number > secondBiggest)) {
                secondBiggest = number;
            }
        }

        if (secondBiggest == null) {
            throw new IllegalArgumentException("At least two distinct integers are required.");
        }
        return secondBiggest;
    }
}
