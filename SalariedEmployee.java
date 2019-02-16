import java.text.DecimalFormat;

/**
 * <p>
 * Represents an employee that is paid based off a yearly salary.
 * </p>
 *
 * @author Jonathon Z. Headley
 * @version 1.0.0
 * @Date: 3/8/2016
 * @Course: CMSC256-001
 */
public class SalariedEmployee extends Employee {

    /**
     * @param first        First name of the SalariedEmployee
     * @param last         Last
     * @param annualSalary
     */
    public SalariedEmployee(String first, String last, double annualSalary) {
        super(first, last, 0);
        setAnnualSalary(annualSalary);
    }

    /**
     * computes the amount an employee should be paid.
     *
     * @param hours the number of hours worked for the week
     * @return a double containing the wage the employee should be paid
     */
    public double computePay(double hours) {
        return super.getWage() * hours;
    }

    /**
     * @return the annual Salary of the SalariedEmployee
     */
    public double getAnnualSalary() {
        return super.getWage() * 52 * 40;
    }


    /**
     * Setter method for the annualSalary
     *
     * @param annualSalary annual salary to give the employee
     */
    public void setAnnualSalary(double annualSalary) {
        super.setWage((annualSalary / 52) / 40);
    }

    /**
     * @return A string representation of the Salaried employee with perfect spacing.
     */
    public String toString() {
        String name = super.getName();
        String wage = new DecimalFormat("$#,##0.00").format(getAnnualSalary()) + "/year";
        int difference = 40 - (name.length() + wage.length());
        for (int i = 0; i < difference; i++) {
            name += " ";
        }
        return name + wage;
    }
}