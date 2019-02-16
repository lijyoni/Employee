/**
 * <p>
 * Object representing a Student Worker.
 * </p>
 *
 * @author Jonathon Z. Headley
 * @version 1.0.0
 * @Date: 3/8/2016
 * @Course: CMSC256-001
 */
public class StudentWorker extends HourlyEmployee {
    /**
     * Constructor for the StudentWorker
     *
     * @param first      firstName of the student worked
     * @param last       lastName of the student worked
     * @param hourlyWage hourly Wage of the student worker
     */
    public StudentWorker(String first, String last, double hourlyWage) {
        super(first, last, hourlyWage);
    }

    /**
     * @param hours the number of hours worked for the week
     * @return the pay that the Student Worker should receive for the hours worked
     */
    public double computePay(double hours) {
        if (hours > 20) {
            hours = 20;
        }
        return hours * super.getWage();
    }

}