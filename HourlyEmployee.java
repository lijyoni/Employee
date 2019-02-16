import java.text.DecimalFormat;

/**
 * <p>This class represents an employee that is paid by the hour and contains methods to work with it.</p>
 *
 * @author Jonathon Z. Headley
 * @version 1.0.0
 * @Date: 3/8/2016
 * @Course: CMSC256-001
 */
public class HourlyEmployee extends Employee {

    /**
     * @param first      First name of the hourly employee
     * @param last       Last name of the hourly employee
     * @param hourlyWage Wage of the hourly employee
     */
    public HourlyEmployee(String first, String last, double hourlyWage) {
        super(first, last, hourlyWage);
    }

    /**
     * @param hours the number of hours worked for the week
     * @return The pay the user will get for the enetered number of hours
     */
    @Override
    public double computePay(double hours) {
        if (hours > 40) {
            return (40 * super.getWage()) + (((hours - 40) * (1.5 * super.getWage())));
        } else {
            return hours * super.getWage();
        }
    }

    /**
     * @return a perfectly spaced(for no reason) string.... with first name, last name, and wage.
     */
    public String toString() {
        // gets the last name of the employee and adds it to the string. Starts off the string.
        String name = super.getName();
        String wage = new DecimalFormat("$#,##0.00").format(super.getWage()) + "/hour";
        int difference = 40 - (name.length() + wage.length());
        for (int i = 0; i < difference; i++) {
            name += " ";
        }
        return name + wage;
    }

}