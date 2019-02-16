import java.util.Arrays;

/**
 * <p>
 * Represents a HumanResourceDept and contains an array of employees and methods to manipulate the array.
 * </p>
 *
 * @author Jonathon Z. Headley
 * @version 1.0.0
 * @Date: 3/8/2016
 * @Course: CMSC256-001
 */
public class HumanResourceDept {
    private Employee[] employees;

    /**
     * Contructor for the HumanResourceDept.
     * Initializes the array of employees;
     */
    public HumanResourceDept() {
        employees = new Employee[1];
    }

    /**
     * Adds an employee to the HumanResourceDept
     *
     * @param newEmployee The employee to be added to the HumanResource
     */
    public void addEmployee(Employee newEmployee) {

        for (int i = 0; i < employees.length; i++) {
            if (employees[i] == null) {
                employees[i] = newEmployee;
                break;
            } else if (i == employees.length - 1) {
                doubleArray();
            }
        }
    }


    /**
     * Removes an employee from the HumanResourceDept
     *
     * @param anEmployee employee to be removed from the HRDepartment
     */

    public void deleteEmployee(Employee anEmployee) {

        for (int i = 0; i < employees.length; i++) {
            if (employees[i] != null) {
                if (employees[i].getLastName().equalsIgnoreCase(anEmployee.getLastName())) {
                    employees[i] = null;
                    break;
                }

            }
        }
    }

    /**
     * Doubles the size of the array of employees for the HumanResourceDept
     */
    private void doubleArray() {
        employees = Arrays.copyOf(employees, employees.length * 2);
    }

    /**
     * Raises all wages of employees by a given percentage
     *
     * @param percentage Percentage for the employees wage to be raised by
     */
    public void raiseAllWages(double percentage) {
        for (Employee employee : employees) {
            if (employee != null) {
                employee.raiseWages(percentage);
            }
        }
    }

    /**
     * @return the array of employees
     */
    public Employee[] getEmployees() {
        return employees;
    }

}