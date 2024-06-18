package com.rajancodes.Java8;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CollectorsProblems {

    /**
     * 1. Grouping and Counting within Groups
     * Given a list of Person objects, group them by their city and then count the number of people in each city.
     */
    @Test
    void _01_groupingAndCountingWithinGroups(){
        record Person(String name, String city){};

        List<Person> people = Arrays.asList(
                new Person("Alice", "New York"),
                new Person("Bob", "Los Angeles"),
                new Person("Charlie", "New York"),
                new Person("David", "Los Angeles"),
                new Person("Eve", "Chicago")
        );

        Map<String, Long> map = people.stream().collect(Collectors.groupingBy(Person::city, Collectors.counting()));
        printConsumer.accept(map);
    }

    /**
     * 2. Grouping and Summarizing Data
     * Given a list of Transaction objects, group them by their type and then summarize the amount for each type.
     */
    @Test
    void _02_groupingAndSummarizingData(){
        record Transaction(String type, Double amount){};
        List<Transaction> transactions = Arrays.asList(
                new Transaction("Grocery", 100.0),
                new Transaction("Electronics", 200.0),
                new Transaction("Grocery", 150.0),
                new Transaction("Clothing", 50.0),
                new Transaction("Electronics", 80.0)
        );

        Map<String, DoubleSummaryStatistics> map =
                transactions.stream().collect(
                        Collectors.groupingBy(Transaction::type, Collectors.summarizingDouble(Transaction::amount))
                );
        printConsumer.accept(map);
    }

    /**
     * 3. Custom Collector for Concatenating Strings
     * Given a list of strings, concatenate them with a custom delimiter, prefix, and suffix using a custom collector.
     */
    @Test
    void _03_customCollectorForString(){
        // StringJoiner is a utility class in Java 8 that constructs a sequence of characters separated by a delimiter and optionally prefixed and suffixed by given strings. It simplifies the process of creating formatted strings from collections of elements.
        // StringJoiner methods include add(CharSequence), merge(StringJoiner), setEmptyValue(CharSequence), toString(), length(), and setEmptyValue(CharSequence).

        List<String> words = Arrays.asList("apple", "banana", "cherry");
        // Custom collector for concatenating strings
        Collector<String, StringJoiner, String> customCollector = Collector.of(
                // Supplier: Creates the initial value of the mutable result container
                () -> new StringJoiner(", ", "[", "]"),
                // Accumulator: Adds a value to the mutable result container
                StringJoiner::add,
                // Combiner: Merges two partial results
                StringJoiner::merge,
                // Finisher: Final transformation from the mutable result container to the final result
                StringJoiner::toString
        );

        System.out.println(words.stream().collect(customCollector));
    }

    /**
     * 4.  Advanced Reducing and Mapping
     * Given a list of Order objects, find the total revenue per customer.
     */
    @Test
    void _04_advancedReducingMapping(){
        record Order(String customer, Double amount){}
        List<Order> orders = Arrays.asList(
                new Order("Alice", 250.0),
                new Order("Bob", 150.0),
                new Order("Alice", 200.0),
                new Order("Charlie", 300.0),
                new Order("Bob", 100.0)
        );

        Map<String, Double> totalRevenuePerCustomer =
                orders.stream().collect(Collectors.groupingBy(Order::customer,
                        Collectors.reducing(0.0, Order::amount, Double::sum)));
        printConsumer.accept(totalRevenuePerCustomer);
    }

    /**
     * 5. Complex Grouping and Summarization
     *  Given a list of Transaction objects with customer, product, and amount, group them by customer and then by product,
     *  and summarize the total amount spent on each product by each customer.
     */
    @Test
    void _05_complexGroupingAndSummarization(){
        record Transaction(String customer, String product, double amount){}
        List<Transaction> transactions = Arrays.asList(
                new Transaction("Alice", "Apple", 100.0),
                new Transaction("Bob", "Banana", 200.0),
                new Transaction("Alice", "Apple", 150.0),
                new Transaction("Alice", "Banana", 50.0),
                new Transaction("Bob", "Apple", 80.0)
        );

        Map<String, Map<String, Double>> nestedMap = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::customer,
                        Collectors.groupingBy(Transaction::product,
                        Collectors.reducing(0.0, Transaction::amount, Double::sum))));
        printConsumer.accept(nestedMap);

        Map<String, Map<String, DoubleSummaryStatistics>> nestedMap1 = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::customer,
                        Collectors.groupingBy(Transaction::product,
                                Collectors.summarizingDouble(Transaction::amount))));
        printConsumer.accept(nestedMap1);
    }

    /**
     * 6. Partitioning and Reducing
     * Given a list of Employee objects with name, department, and salary, partition them into those with a salary above and below a certain threshold.
     * For each group, find the employee with the highest salary.
     */
    @Test
    void _06_partitioningAndReducing(){
        record Employee(String name, String department, Double salary){}

        List<Employee> employees = Arrays.asList(
                new Employee("Alice", "HR", 60000.0),
                new Employee("Bob", "IT", 70000.0),
                new Employee("Charlie", "HR", 80000.0),
                new Employee("David", "IT", 55000.0),
                new Employee("Eve", "IT", 90000.0)
        );

        Double salaryThreshold = 60000.0;

        Map<Boolean, Optional<Employee>> partitionedList = employees.stream()
                .collect(Collectors.partitioningBy(e -> e.salary > salaryThreshold, Collectors.maxBy(Comparator.comparingDouble(Employee::salary))));
        partitionedList.entrySet().forEach(e -> System.out.println(e.getKey() + " --> " + e.getValue().orElse(null)));
    }

    /**
     * 7. Complex Reduction and Mapping
     * Given a list of Employee objects with department and salary,
     * find the total salary per department, and for each department, find the employee with the highest salary.
     */
    @Test
    void _07_complexReductionAndMapping(){
        record Employee(String name, String department, Double salary){}
        List<Employee> employees = Arrays.asList(
                new Employee("Alice", "HR", 60000.0),
                new Employee("Bob", "IT", 70000.0),
                new Employee("Charlie", "HR", 80000.0),
                new Employee("David", "IT", 55000.0),
                new Employee("Eve", "IT", 90000.0)
        );

        Map<String, Map<String, Object>> result = employees.stream()
                .collect(Collectors.groupingBy(Employee::department,
                        Collectors.collectingAndThen(Collectors.toList(),
                                list -> {
                                    Double total = list.stream().mapToDouble(Employee::salary).sum();
                                    Employee highestPaidEmployee = list.stream()
                                            .max(Comparator.comparing(Employee::salary)).orElse(null);
                                    Map<String, Object> departmentInfo = new HashMap<>();
                                    departmentInfo.put("Total salary", total);
                                    departmentInfo.put("Highest paid employee", highestPaidEmployee);
                                    return departmentInfo;
                                })));
    }


    private Consumer printConsumer = (t) -> {
        System.out.println("----------------------------------------------");
        if(t instanceof Map<?,?>){
            ((Map<?, ?>) t).entrySet().forEach(c -> System.out.println(c));
        }
        else if (t instanceof List<?>) {
            ((Collection<?>) t).forEach(c -> System.out.println(c));
        } else {
            System.out.println(t);
        }
        System.out.println("----------------------------------------------");
    };
}
