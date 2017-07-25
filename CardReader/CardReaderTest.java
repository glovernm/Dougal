import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.time.*;
import java.time.format.*;

public class CardReaderTest {
  private static Scanner in = new Scanner(System.in);
  private static Scanner employeeList;
  private static ArrayList<Employee> employees = new ArrayList<Employee>(20);

  private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EE: MM/dd");
  private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a");

  private static Thread runClock;
  private static Thread runSwipe;

  public static void main(String[] args) {
    readEmployees("EmployeeList.txt");

    runClock = new Thread() {
      public void run() {
        clock();
      }
    };
    runSwipe = new Thread() {
      public void run() {
        swipe();
      }
    };

    runClock.start();
    runSwipe.start();
  }

  public static void clock() {
    while(true) {
      LocalTime present = LocalTime.now();
      isLate(present);
      swipeOut(LocalTime.NOON, present);
    }
  }

  public static void swipe() {
    while(true) {
      System.out.println("Swipe Card Please:");
      String pluID = in.nextLine();
      if(pluID.length() < 12) {
        System.out.println("\nError Reading Card...\n");
        swipe();
      }
      pluID = pluID.substring(1, pluID.length() - 3);
      System.out.println("####################");
      for(int i = 0; i < employees.size(); i++) {
        Employee emp = employees.get(i);
        if(!emp.getSwiped()) {
          if(emp.getID().equals(pluID)) {
            System.out.println("\nWelcome " + emp.getName() + ".\n");
            writeToFile(emp);
          }
        }
      }
    }
  }

  public static void readEmployees(String filename) {
    try {
      employeeList = new Scanner(new File(filename));
    }
    catch(FileNotFoundException e) {
      System.out.println("File " + filename + " not found.");
      System.exit(0);
    }
    while(employeeList.hasNext()) {
      String[] tokens = employeeList.nextLine().split(",");
      Employee employee = new Employee(tokens[0], tokens[1], Boolean.parseBoolean(tokens[2]));
      employees.add(employee);
    }
  }

  public static BufferedWriter createWriter(boolean append) {
    try {
      File timeSheet = new File("Timesheet.csv");
      LocalDate today = LocalDate.now();
      LocalTime present = LocalTime.now();

      if(timeSheet.createNewFile()){
        PrintWriter pw = new PrintWriter(timeSheet);
        pw.write("Name, Date, Time In, Late Flag, Time Out\n");
        pw.close();
        System.out.println("Time Sheet created with Header.");
      }

      return new BufferedWriter(new FileWriter(timeSheet.getAbsoluteFile(), append));
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error Writing to or Creating Timesheet...");
      return null;
    }
  }

  public static void writeToFile(Employee emp){

          try {
            LocalDate today = LocalDate.now();
            LocalTime present = LocalTime.now();

            BufferedWriter bw = createWriter(true);
            bw.write(emp.getName() + "," + today.format(dateFormat) + ",");
            bw.write(present.format(timeFormat) + "," + emp.getLate() + ",********\n");
            bw.close();
            System.out.println("Written to TimeSheet.");

          } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error Writing to or Creating Timesheet...");
            return;
          }
  }

  public static void isLate(LocalTime present) {
    if(present.isAfter(LocalTime.of(8, 5))
    && present.isBefore(LocalTime.NOON)
    || present.isAfter(LocalTime.of(13, 5))) {
      for(int i = 0; i < employees.size(); i++) {
        Employee emp = employees.get(i);
        if(!emp.getSwiped())
          emp.setLate(true);
      }
    }
  }

  public static void swipeOut(LocalTime endShift, LocalTime present) {
    if(present.compareTo(LocalTime.NOON) == 0) {
      System.out.println("\nSwiping everyone out.\n");
      Scanner timesheet = null;
      try {
        timesheet = new Scanner(new File("Timesheet.csv"));
      }
      catch(FileNotFoundException e) {
        System.out.println("Timesheet not found.");
        System.exit(0);
      }

      ArrayList<String> entries = new ArrayList<String>();
      try {
        while(timesheet.hasNext()) {
          String shift = timesheet.nextLine();
          if(shift.endsWith("********")) {
            shift = shift.substring(0,shift.length() - 8);
            shift = shift + endShift.format(timeFormat);
          }
          entries.add(shift);
        }
        BufferedWriter bw = createWriter(false);
        for(int i = 0; i < entries.size(); i++)
          bw.write(entries.get(i) + "\n");
        bw.close();
      } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Error Writing to Timesheet...");
      }
      for(Employee emp : employees) {
        emp.setSwiped(false);
        emp.setLate(false);
      }
      System.out.println("\nBe back by 1:00 PM.\n");
      System.out.println("\nSwipe card please:");
    }
  }
}
