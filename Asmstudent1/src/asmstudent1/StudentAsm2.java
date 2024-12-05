/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package asmstudent1;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class Student {
    private String id;
    private String name;
    private double mark;

    public Student(String id, String name, double mark) {
        this.id = id;
        this.name = name;
        this.mark = mark;
    }

    public String getId() {
        return id;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        String rank = mark < 5.0 ? "Fail" : mark < 6.5 ? "Medium" : mark < 7.5 ? "Good" : mark < 9.0 ? "Very Good" : "Excellent";
        return String.format("ID: %s, Name: %s, Mark: %.2f, Rank: %s", id, name, mark, rank);
    }
}

class StudentManager {
    private final List<Student> students = new LinkedList<>();
    private final Scanner scanner = new Scanner(System.in);

    public void addStudent() {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        double mark = -1;
        while (mark < 0 || mark > 10) {
            System.out.print("Enter Mark (0 to 10): ");
            if (scanner.hasNextDouble()) {
                mark = scanner.nextDouble();
                scanner.nextLine();
                if (mark < 0 || mark > 10) {
                    System.out.println("Invalid mark. Please enter a number between 0 and 10.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); 
            }
        }

        students.add(new Student(id, name, mark));
        System.out.println("Student added.");
    }

    public void editStudent() {
        System.out.print("Enter Student ID to edit: ");
        String id = scanner.nextLine();
        Student student = getStudentById(id);
        if (student != null) {
            System.out.print("Enter new mark for " + student.getId() + ": ");
            double newMark = scanner.nextDouble();
            scanner.nextLine();
            student.setMark(newMark);
            System.out.println("Updated successfully!");
        } else {
            System.out.println("Student not found! Try again.");
        }
    }

    private Student getStudentById(String id) {
        for (Student s : students) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public void deleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        String id = scanner.nextLine();
        students.removeIf(student -> student.getId().equals(id));
        System.out.println("Student removed.");
    }

    public void searchStudent() {
        System.out.print("Enter Student ID to search: ");
        String id = scanner.nextLine();
        Student student = getStudentById(id);
        System.out.println(student != null ? student : "Student not found.");
    }

    public void sortStudents() {
        mergeSort(students);
        System.out.println("Students sorted by marks.");
    }

    public void displayAll() {
        if (students.isEmpty()) {
            System.out.println("No students to display.");
        } else {
            students.forEach(System.out::println);
        }
    }
  
    private void mergeSort(List<Student> list) {
        if (list.size() <= 1) {
            return; 
        }
        List<Student> left = new LinkedList<>(list.subList(0, list.size() / 2));
        List<Student> right = new LinkedList<>(list.subList(list.size() / 2, list.size()));
        mergeSort(left);
        mergeSort(right);
        merge(list, left, right);
    }
    
   
    private void merge(List<Student> result, List<Student> left, List<Student> right) {
        result.clear(); 
        int i1 = 0; 
        int i2 = 0; 
        
        while (i1 < left.size() && i2 < right.size()) {
            if (left.get(i1).getMark() <= right.get(i2).getMark()) {
                result.add(left.get(i1++)); 
            } else {
                result.add(right.get(i2++)); 
            }
        }
  
        while (i1 < left.size()) {
            result.add(left.get(i1++));
        }

        while (i2 < right.size()) {
            result.add(right.get(i2++));
        }
    }


    public void menu() {
        boolean running = true;
        while (running) {
            try {
                System.out.println("\n1. Add Student\n2. Edit Student\n3. Delete Student\n4. Search Student\n5. Sort Students\n6. Display All\n7. Exit");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> editStudent();
                    case 3 -> deleteStudent();
                    case 4 -> searchStudent();
                    case 5 -> sortStudents();
                    case 6 -> displayAll();
                    case 7 -> running = false;
                    default -> System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                
            }
        }
    }
}

public class StudentAsm2 {
    public static void main(String[] args) {
        new StudentManager().menu();
    }
}


