package Java8.EmployeeQueriesStreams;

import java.util.*;
import java.util.stream.Collectors;

public class Queries {
    public static void main(String[] args) {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(111, "Jiya Brein", 32, "Female", "HR", 2011, 25000.0));
        employeeList.add(new Employee(122, "Paul Niksui", 25, "Male", "Sales And Marketing", 2015, 13500.0));
        employeeList.add(new Employee(133, "Martin Theron", 29, "Male", "Infrastructure", 2012, 18000.0));
        employeeList.add(new Employee(144, "Murali Gowda", 28, "Male", "Product Development", 2014, 32500.0));
        employeeList.add(new Employee(155, "Nima Roy", 27, "Female", "HR", 2013, 22700.0));
        employeeList.add(new Employee(166, "Iqbal Hussain", 43, "Male", "Security And Transport", 2016, 10500.0));
        employeeList.add(new Employee(177, "Manu Sharma", 35, "Male", "Account And Finance", 2010, 27000.0));
        employeeList.add(new Employee(188, "Wang Liu", 31, "Male", "Product Development", 2015, 34500.0));
        employeeList.add(new Employee(199, "Amelia Zoe", 24, "Female", "Sales And Marketing", 2016, 11500.0));
        employeeList.add(new Employee(200, "Jaden Dough", 38, "Male", "Security And Transport", 2015, 11000.5));
        employeeList.add(new Employee(211, "Jasna Kaur", 27, "Female", "Infrastructure", 2014, 15700.0));
        employeeList.add(new Employee(222, "Nitin Joshi", 25, "Male", "Product Development", 2016, 28200.0));
        employeeList.add(new Employee(233, "Jyothi Reddy", 27, "Female", "Account And Finance", 2013, 21300.0));
        employeeList.add(new Employee(244, "Nicolus Den", 24, "Male", "Sales And Marketing", 2017, 10700.5));
        employeeList.add(new Employee(255, "Ali Baig", 23, "Male", "Infrastructure", 2018, 12700.0));
        employeeList.add(new Employee(266, "Sanvi Pandey", 26, "Female", "Product Development", 2015, 28900.0));
        employeeList.add(new Employee(277, "Anuj Chettiar", 31, "Male", "Product Development", 2012, 35700.0));

        // 1.  How many male and female employees are there in the organization?
        Map<String, Long> genderMap =  employeeList.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
        System.out.println(genderMap);

        // 2. Print the name of all departments in the organization?
        List<String> departments = employeeList.stream().map(Employee::getDepartment).distinct().collect(Collectors.toList());
        print(departments);

        // 3. What is the average age of male and female employees?
        Map<String, Double> averageAge = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingInt(Employee::getAge)));
        print(averageAge.entrySet());

        // 4. Get the details of highest paid employee in the organization?
        Employee highestPaidEmployee = employeeList.stream()
                .max(Comparator.comparingDouble(Employee::getSalary))
                .orElse(null);
        System.out.println(highestPaidEmployee);

        // 5.  Get the names of all employees who have joined after 2015?
        List<String> employeesAfter2015 = employeeList.stream()
                .filter(e -> e.getYearOfJoining() > 2015)
                .map(Employee::getName)
                .collect(Collectors.toList());
        print(employeesAfter2015);

        // 6. Count the number of employees in each department?
        Map<String, Long> numberOfEmployeesPerDepartment = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        System.out.println(numberOfEmployeesPerDepartment);

        // 7. What is the average salary of each department?
        Map<String, Double> averageSalaryPerDepartment = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
        print(averageSalaryPerDepartment.entrySet());

        // 8. Get the details of youngest male employee in the product development department?
        Employee youngestEmployee = employeeList.stream()
                .filter(e -> e.getDepartment().equals("Product Development"))
                .min(Comparator.comparingInt(Employee::getAge))
                .orElse(null);
        System.out.println(youngestEmployee);

        // 9. Who has the most working experience in the organization?
        Employee mostExperienced = employeeList.stream()
                .sorted(Comparator.comparingInt(Employee::getYearOfJoining))
                .findFirst()
                .orElse(null);
        System.out.println(mostExperienced);

        // 10. How many male and female employees are there in the sales and marketing team?
        Map<String, Long> genderMap1 = employeeList.stream()
                .filter(e -> e.getDepartment().equals("Sales And Marketing"))
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
        print(genderMap1.entrySet());

        // 11.  What is the average salary of male and female employees?
        Map<String, Double> avgSalary = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingDouble(Employee::getSalary)));
        print(avgSalary.entrySet());

        // ###############################################################################
        // 12. List down the names of all employees in each department?
        Map<String, List<String>> employeesPerDepartment = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.mapping(Employee::getName, Collectors.toList())));
        print(employeesPerDepartment.entrySet());
        // ################################################################################

        // 13. What is the average salary and total salary of the whole organization?
        DoubleSummaryStatistics summaryStatistics = employeeList.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(String.format("Avg - %.2f, Total = %.2f", summaryStatistics.getAverage(), summaryStatistics.getSum()));

        // 14.  Separate the employees who are younger or equal to 25 years from those employees who are older than 25 years.
        Map<Boolean, List<Employee>> separatedEmployee = employeeList.stream()
                .collect(Collectors.groupingBy(e -> e.getAge()<=25, Collectors.toList()));
        print(separatedEmployee.entrySet());

        // Using partitioningBy(Predicate)
        Map<Boolean, List<Employee>> partitionEmployee = employeeList.stream()
                .collect(Collectors.partitioningBy(e-> e.getAge() > 25));
        print(partitionEmployee.entrySet());
        System.out.println(partitionEmployee.get(false).size());

        // 15. Who is the oldest employee in the organization? What is his age and which department he belongs to?
        Employee oldestEmployee = employeeList.stream()
                .max(Comparator.comparing(Employee::getAge))
                .orElse(null);
        System.out.println(oldestEmployee);
    }

    private static <T> void print(Collection<T> collection){
        System.out.println("---------------------------");
        collection.forEach(c ->
            System.out.println(c));
        System.out.println("---------------------------");
    }
}
