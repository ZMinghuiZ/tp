package company;

/**
 * Represents a company managed by player in the game, including
 * company name, number of employees, employee salary, and revenue per employee.
 */
public class Company {
    private final String name;
    private int numberOfEmployees;
    private int employeeSalary;
    private int revenuePerEmployee;

    public Company() {
        this.name = "ECONO CROP";
        this.numberOfEmployees = 0;
        this.employeeSalary = 800;
        this.revenuePerEmployee = 1200;
    }

    public Company(String name, int numberOfEmployees, int employeeSalary, int revenuePerEmployee) {
        this.name = name;
        this.numberOfEmployees = numberOfEmployees;
        this.employeeSalary = employeeSalary;
        this.revenuePerEmployee = revenuePerEmployee;
    }

    /**
     * Hires a number of employees to the company based on the player's choice.
     *
     * @param number the number of employees to hire from the player.
     */
    public void hireEmployee(int number) {
        numberOfEmployees += number;
    }

    /**
     * Removes a number of employees from the company based on the player's choice.
     *
     * @param number the number of employees to remove from the player.
     */
    public void removeEmployee(int number) {
        numberOfEmployees -= number;
    }

    /**
     * Returns the number of employees in the company.
     *
     * @return the number of employees in the company.
     */
    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    /**
     * Returns the salary of each employee in the company.
     *
     * @return the salary of each employee in the company.
     */
    public int getEmployeeSalary() {
        return employeeSalary;
    }

    /**
     * Returns the revenue per employee in the company.
     *
     * @return the revenue per employee in the company.
     */
    public int getRevenuePerEmployee() {
        return revenuePerEmployee;
    }

    /**
     * Pays the salaries of the employees in the company.
     *
     * @return the total amount of salaries paid to the employees.
     */
    private int paySalaries() {
        return numberOfEmployees * employeeSalary;
    }

    /**
     * Receives the revenue generated from the employees in the company each round.
     *
     * @return the total amount of revenue received from the employees each round.
     */
    private int receiveRevenue() {
        return numberOfEmployees * revenuePerEmployee;
    }

    /**
     * Outputs the name of the company.
     *
     * @return the name of the company.
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the revenue generated per employee in the company by a specified amount.
     *
     * @param amount the amount to update the revenue per employee.
     */
    public void updateRevenue(int amount) {
        revenuePerEmployee += amount;
    }

    /**
     * Updates the salary of each employee in the company by a specified amount.
     *
     * @param amount the amount to update the salary of each employee.
     */
    public void updateSalary(int amount) {
        employeeSalary += amount;
    }

    /**
     * Calculates the profit generated by the company each round.
     *
     * @return the profit generated by the company each round.
     */
    public int profitPerRound() {
        return receiveRevenue() - paySalaries();
    }

    /**
     * Outputs the company's information in a formatted string.
     *
     * @return the company's information in a formatted string.
     */
    public String toString() {
        return "Company: " + name + "\n" +
                "Number of Employees: " + numberOfEmployees + "\n" +
                "Employee Salary: " + employeeSalary + "\n" +
                "Revenue Per Employee: " + revenuePerEmployee + "\n" +
                "Profit per Round: " + profitPerRound() + "\n";
    }
}
