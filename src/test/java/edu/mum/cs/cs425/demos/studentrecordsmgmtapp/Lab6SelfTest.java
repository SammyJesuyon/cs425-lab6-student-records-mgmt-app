package edu.mum.cs.cs425.demos.studentrecordsmgmtapp;

import edu.mum.cs.cs425.demos.studentrecordsmgmtapp.model.Student;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;

/**
 * Dependency-free test runner so the assignment can be verified with only a JDK.
 */
public final class Lab6SelfTest {
    private static int passed;

    private Lab6SelfTest() {
    }

    public static void main(String[] args) {
        testStudentConstructorsAndAccessors();
        testPlatinumAlumniFilteringAndOrdering();
        testHelloWorldOutput();
        testSecondBiggestExamplesAndEdgeCases();

        System.out.println();
        System.out.println("All " + passed + " Lab 6 checks passed.");
    }

    private static void testStudentConstructorsAndAccessors() {
        Student student = new Student();
        student.setStudentId(120001);
        student.setName("Maya");
        student.setDateOfAdmission(LocalDate.of(2001, 9, 1));
        assertEquals(120001L, student.getStudentId(), "default constructor + studentId setter");
        assertEquals("Maya", student.getName(), "name accessor/mutator");
        assertEquals(LocalDate.of(2001, 9, 1), student.getDateOfAdmission(), "date accessor/mutator");

        Student parsed = new Student(120002, "Nora", "02/14/1995");
        assertEquals(LocalDate.of(1995, 2, 14), parsed.getDateOfAdmission(), "string-date constructor");
    }

    private static void testPlatinumAlumniFilteringAndOrdering() {
        Student[] students = {
                new Student(1, "Oldest", "01/01/1970"),
                new Student(2, "Newest Platinum", "01/01/1990"),
                new Student(3, "Recent", LocalDate.now().minusYears(5))
        };

        List<Student> result =
                MyStudentRecordsMgmtApp.getListOfPlatinumAlumniStudents(students);
        assertEquals(2, result.size(), "platinum alumni filter");
        assertEquals("Newest Platinum", result.get(0).getName(), "descending admission-date order");
        assertEquals("Oldest", result.get(1).getName(), "oldest platinum alumnus follows");
    }

    private static void testHelloWorldOutput() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(output));
            MyStudentRecordsMgmtApp.printHelloWorld(new int[] {1, 5, 7, 10, 14, 35});
        } finally {
            System.setOut(originalOut);
        }

        String normalized = output.toString().replace("\r\n", "\n").trim();
        assertEquals(
                "Hello\nWorld\nHello\nWorld\nHelloWorld",
                normalized,
                "Hello/World/HelloWorld output");
    }

    private static void testSecondBiggestExamplesAndEdgeCases() {
        assertEquals(
                4,
                MyStudentRecordsMgmtApp.findSecondBiggest(new int[] {1, 2, 3, 4, 5}),
                "first assignment example");
        assertEquals(
                12,
                MyStudentRecordsMgmtApp.findSecondBiggest(new int[] {19, 9, 11, 0, 12}),
                "second assignment example");
        assertEquals(
                5,
                MyStudentRecordsMgmtApp.findSecondBiggest(new int[] {5, 9, 9, 5, 1}),
                "duplicate maximum values");
        assertEquals(
                -2,
                MyStudentRecordsMgmtApp.findSecondBiggest(new int[] {-8, -2, -1, -5}),
                "negative values");

        boolean rejected = false;
        try {
            MyStudentRecordsMgmtApp.findSecondBiggest(new int[] {7, 7, 7});
        } catch (IllegalArgumentException expected) {
            rejected = true;
        }
        assertEquals(true, rejected, "input without two distinct values is rejected");
    }

    private static void assertEquals(Object expected, Object actual, String description) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(
                    description + ": expected <" + expected + "> but was <" + actual + ">");
        }
        passed++;
        System.out.println("PASS - " + description);
    }
}
