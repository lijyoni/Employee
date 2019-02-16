import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * <p>
 * This class reads in employees from a file into a HumanResourceDept object.  Then reads in updates from a
 * file and performs them on the hr department.  Then reads in a file of hours worked by each employee and prints the
 * amounts that should be paid to each employee to a file called WeeklyPayroll.txt.
 * </p>
 *
 * @author Jonathon Z. Headley
 * @version 1.0.0
 * @Date: 3/8/2016
 * @Course: CMSC256-001
 */
public class Project3 {
    private static HumanResourceDept hrDept;

    public static void main(String[] cheese) {
        printHeading();
        File updatesFile = null;
        File hoursWorked = null;

        try {
            hrDept = readEmployees();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        try {
            hoursWorked = new File("HoursWorked.dat");
            payEmployees(hoursWorked);
        } catch ( IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Reads the employees in from a file named EmployeesIn.dat
     *
     * @return A hr department containing all of the employees read in from the file.
     */
    private static HumanResourceDept readEmployees() throws FileNotFoundException{
        System.out.println("\n\nreadEmployees");
        HumanResourceDept humanResourcesDept = new HumanResourceDept();

        File employeesInFile = new File("EmployeesIn.dat");
        Scanner scanner = new Scanner(employeesInFile);

        System.out.println(employeesInFile.toString());
        while (scanner.hasNextLine()) {
        System.out.println("fasd");
            String line = scanner.nextLine().replaceAll(",", ""); // gets rid of
            // the comma so the line can be split into easy pieces
            String[] split = line.split(" ");
            
            switch (split[2]) {
                // adds the appropriate type of employee
                
                case "h":
                    humanResourcesDept.addEmployee(new HourlyEmployee(split[1], split[0], Double.parseDouble(split[4])));
                    break;
                case "s":
                    humanResourcesDept.addEmployee(new SalariedEmployee(split[1], split[0], Double.parseDouble(split[4])));
                    break;
                case "i":
                    humanResourcesDept.addEmployee(new StudentWorker(split[1], split[0], Double.parseDouble(split[4])));
                    break;
            }
        }
        return humanResourcesDept;
    }

    /**
     * updates the employees in the hr department
     *
     * @param updatesFile        File containg the updates to be performed
     * @param humanResourcesDept hr department to perform the updates on
     * @return a modified version of the hr department passed into the method.
     */
    public static HumanResourceDept updateEmployees(File updatesFile, HumanResourceDept humanResourcesDept) throws IOException {
        System.out.println("\n\nupdateEmployees\n");
        Scanner scanner = new Scanner(updatesFile);
        // Creation of arrays to add all changes into so that it can be printed
        // in the correct order and be formatted at the end.
        ArrayList<Employee> newEmployees = new ArrayList<Employee>();
        ArrayList<Employee> deletedEmployees = new ArrayList<Employee>();
        Employee employee = null;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.replaceAll(",", "").split(" ");

            switch (split[0]) {
                case "n":
                    System.out.println("New Employee");
                    // Determines what type of employee the employee should be added as
                    switch (split[3]) {
                        case "h":
                            employee = new HourlyEmployee(split[2], split[1], Double.parseDouble(split[4]));
                            break;
                        case "s":
                            employee = new SalariedEmployee(split[2], split[1], Double.parseDouble(split[4]));

                            break;
                        case "i":
                            employee = new StudentWorker(split[2], split[1], Double.parseDouble(split[4]));
                            break;
                        default:
                            System.err.println("Why am I here...");
                            for (String str : split) {
                                System.err.println(str);
                            }
                            break;
                    }
                    humanResourcesDept.addEmployee(employee);
                    newEmployees.add(employee);
                    break;
                case "r":
                    System.out.println("Raise wages by " + split[1] + "%");
                    humanResourcesDept.raiseAllWages(Double.parseDouble(split[1]));
                    break;
                case "d":
                    for (Employee emp : humanResourcesDept.getEmployees()) {
                        if (emp != null) {
                            if (emp.getLastName().equalsIgnoreCase(split[1])) {
                                employee = emp;
                            }
                        }
                    }
                    humanResourcesDept.deleteEmployee(employee);
                    deletedEmployees.add(employee);
                    System.out.println("Delete employee:\t" + employee.getLastName() + ", " + employee.getFirstName());
                    break;
            }
            System.out.println();
        }

        System.out.println(Arrays.toString(newEmployees.toArray()));
        System.out.println(Arrays.toString(deletedEmployees.toArray()));
        
        return humanResourcesDept;
    }

    /**
     * Helper method for the updateEmployees method that writes all of the updates to a file in a specific order
     *
     * @param newEmployees      employees added to the hr department
     * @param deletedEmployees  employees removed from the hr department
     * @param humanResourceDept the hr department that is being modified
     */
    private static void writeUpdates(ArrayList<Employee> newEmployees, ArrayList<Employee> deletedEmployees, HumanResourceDept humanResourceDept) throws  IOException {

        File employeesOut = new File("EmployeesOut.dat");
        System.out.println(employeesOut);
        BufferedWriter writer = new BufferedWriter(new FileWriter(employeesOut));
        for (Employee employee : newEmployees) {
            writer.write("New Employee added:\t" + employee.getLastName() + ", " + employee.getFirstName() + "\n");
        }
        writer.write("New Wages:\n");
        for (Employee employee : humanResourceDept.getEmployees()) {
            if (employee != null)
                writer.write("\t" + employee.toString() + "\n");
        }
        for (Employee employee : deletedEmployees) {
            writer.write("Deleted Employee:\t" + employee.getLastName() + ", " + employee.getFirstName());
        }
        writer.close();
    }

    /**
     * Pays the employees their salaries based on hoursworked
     *
     * @param hoursWorked File containing the employee and their hours worked.
     * @throws IOException        if the file can't be writen
     * @throws URISyntaxException If the file path doesn't work
     */
    public static void payEmployees(File hoursWorked) throws IOException {
        //BufferedWriter writer = new BufferedWriter(new FileWriter(new File(new URL(Project3.class.getResource("").toURI() + ).toURI())));
        PrintWriter writer = new PrintWriter("WeeklyPayroll.txt");
        Scanner scanner = new Scanner(hoursWorked);
        writer.write("Paycheck amount:\n");
        double totalPaid = 0.0;
        HumanResourceDept employeesToBePaid = new HumanResourceDept();
        HumanResourceDept otherEmployees = new HumanResourceDept();
        for (Employee employee : hrDept.getEmployees()) {
            if (!(employee instanceof HourlyEmployee)) {
                employeesToBePaid.addEmployee(employee);
            } else {
                otherEmployees.addEmployee(employee);
            }
        }
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] split = line.split(" ");
            Employee employee = findEmployee(split[0]);
            employeesToBePaid.deleteEmployee(employee);
            if (employee instanceof HourlyEmployee) {
                otherEmployees.deleteEmployee(employee);
            }
            double wage = 0;
            if (employee != null) {
                wage = employee.computePay(Double.parseDouble(split[1]));
            }
            totalPaid += wage;
            writer.write("\t" + spacifyPaycheck(employee.getLastName() + ", " + employee.getFirstName(), new DecimalFormat("$#,##0.00").format(wage)) + "\n");
        }
        for (Employee employee : employeesToBePaid.getEmployees()) {
            if (employee != null) {
                writer.write("\t" + spacifyPaycheck(employee.getLastName() + ", " + employee.getFirstName(), new DecimalFormat("$#,##0.00").format(employee.computePay(40))) + "\n");
            }
        }
        for (Employee employee : otherEmployees.getEmployees()) {
            if (employee != null) {
                writer.write("\t" + spacifyPaycheck(employee.getLastName() + ", " + employee.getFirstName(), new DecimalFormat("$#,##0.00").format(employee.computePay(0))) + "\n");
            }
        }
        writer.write("\t" + spacifyPaycheck("", "----------") + "\n");
        writer.write("\t" + spacifyPaycheck("Total", new DecimalFormat("$#,##0.00").format(totalPaid)));
        writer.close();
    }

    /**
     * Adds padding between string1 and string2 so that everything is aligned correctly
     *
     * @param string1 String that is on the left of the padded String
     * @param string2 String that is on the right of the padded String
     * @return returns a padded string of that is 40 characters in length
     */
    private static String spacifyPaycheck(String string1, String string2) {
        int difference = 40 - (string1.length() + string2.length());
        for (int i = 0; i < difference; i++) {
            string1 += " ";
        }
        return string1 + string2;
    }

    /**
     * Finds an employee by name in hrDepartment
     *
     * @param employeeName name of the employee to find
     * @return returns the {@link Employee} object for the employee with the given name
     */
    private static Employee findEmployee(String employeeName) {
        for (Employee employee : hrDept.getEmployees()) {
            if (employee != null) {
                if (employee.getLastName().equalsIgnoreCase(employeeName)) {
                    return employee;
                }
            }
        }
        return null;
    }


    private static void printHeading() {
        System.out.println("Name: Jonathon Z. Headley");
        System.out.println("V#: V00746112");
        System.out.println("Project Number: 3");
        System.out.println("Course Identifier: CMSC 256-001");
        System.out.println("Semester: Spring 2016");
        System.out.println();
    }
}