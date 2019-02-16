/**
 * Abstract Class: Employee
 * <p>
 * Represents an employee for payroll calculations. All calculations are based
 * on an hourly wage. Therefore, for salaried employees, their annual salary
 * must be converted to an equivalent hourly wage.
 * <p>
 *
 * @author Debra M. Duke
 * @version 3.0, updated 2/20/2016
 */
public abstract class Employee {

    /**
     * The employee's first name
     */
    private String firstName;

    /**
     * The employee's last name
     */
    private String lastName;

    /**
     * The <b>hourly</b> wage. For salaried employees, their annual salary must
     * be converted to an equivalent hourly wage.
     */
    private double wage;

    /**
     * default contructor
     */
    public Employee() {
        firstName = "";
        lastName = "";
        wage = 0.0;
    }

    /**
     * parameterized contructor
     *
     * @param first
     * @param last
     * @param hourlyWage
     */
    public Employee(String first, String last, double hourlyWage) {
        firstName = first;
        lastName = last;
        setWage(hourlyWage);
    }

    /**
     * calculates the weekly pay for this employee
     *
     * @param hours
     *            the number of hours worked for the week
     * @return the pay amount based on <code>hours</code>
     */
    public abstract double computePay(double hours);

    /**
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param name
     *            the first name to set
     */
    public void setFirstName(String name) {
        firstName = name;
    }

    /**
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param name
     *            the last name to set
     */
    public void setLastName(String name) {
        lastName = name;
    }

    /**
     * @return the full name
     */
    public String getName() {
        return lastName + ", " + firstName;
    }

    /**
     * @return the wage
     */
    public double getWage() {
        return wage;
    }

    /**
     * The wage <b>must</b> be in the form of the <b>hourly</b> wage. Convert
     * all other forms of salary compensation into an hourly equivalent before
     * setting this value.
     *
     * @param wage
     *            the wage to set
     * @throws IllegalArgumentException
     *             if parameter is negative
     */
    public void setWage(double wage) {
        if (wage >= 0.0)
            this.wage = wage;
        else
            throw new IllegalArgumentException("Wage cannot be negative.");
    }

    /**
     * sets the wage to the new amount calculated by increasing the existing
     * wage by the percent increase
     *
     * @param percent
     *            the percent increase
     */
    public void raiseWages(double percent) {
        wage = wage * ((100 + percent) / 100);
    }
}