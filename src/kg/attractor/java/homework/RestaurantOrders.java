package kg.attractor.java.homework;

import com.google.gson.Gson;
import kg.attractor.java.homework.domain.Customer;
import kg.attractor.java.homework.domain.Item;
import kg.attractor.java.homework.domain.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;


public class RestaurantOrders {
    // Этот блок кода менять нельзя! НАЧАЛО!
    private List<Order> orders;

    private RestaurantOrders(String fileName) {
        var filePath = Path.of("data", fileName);
        Gson gson = new Gson();
        try {
            orders = List.of(gson.fromJson(Files.readString(filePath), Order[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RestaurantOrders read(String fileName) {
        var ro = new RestaurantOrders(fileName);
        ro.getOrders().forEach(Order::calculateTotal);
        return ro;
    }

    public List<Order> getOrders() {
        return orders;
    }
    // Этот блок кода менять нельзя! КОНЕЦ!

    //----------------------------------------------------------------------
    //------   Реализация ваших методов должна быть ниже этой линии   ------
    //----------------------------------------------------------------------

    // Наполните этот класс решением домашнего задания.
    // Вам необходимо создать все необходимые методы
    // для решения заданий из домашки :)
    // вы можете добавлять все необходимые imports
    //

    public static List<Order> getCheap(List<Order> orders, int a) {
        return orders.stream()
                .sorted(Comparator.comparing(Order::calculateTotal))
                .limit(a)
                .collect(Collectors.toList());
    }

    public static List<Order> getExpensive(List<Order> orders, int b) {
        return orders.stream()
                .sorted(Comparator.comparing(Order::calculateTotal).reversed())
                .limit(b)
                .collect(Collectors.toList());
    }

    public static List<Order> getDeliveryList(List<Order> orders) {
        return orders.stream()
                .filter(Order::isHomeDelivery)
                .collect(Collectors.toList());
    }

    public static void printListAndMinMaxForDelivery(List<Order> orders) {
        var delivery = getDeliveryList(orders);
        var max = getExpensive(delivery, 1);
        var min = getCheap(delivery, 1);
        System.out.println("*".repeat(90));
        System.out.println("List of orders with the highest total cost: ");
        max.forEach(Order::printList);
        System.out.println("*".repeat(90));
        System.out.println("List of orders with the lowest total cost: ");
        min.forEach(Order::printList);
        System.out.println("#".repeat(90));
    }

    public static double totalPrice(List<Order> orders) {
        return orders.stream()
                .mapToDouble(Order::calculateTotal)
                .sum();
    }

        public static List<Order> lessMaxTotalList(List<Order> orders) {
        return orders.stream()
                .filter(Order::isHomeDelivery)
                .takeWhile(order -> order.calculateTotal() < maxOrderTotal(orders).calculateTotal())
                .sorted(Comparator.comparing(Order::calculateTotal))
                .collect(Collectors.toList());
    }

    public static List<Order> moreThanMinTotalList(List<Order> orders) {
        return orders.stream()
                .filter(Order::isHomeDelivery)
                .takeWhile(order -> order.calculateTotal() > minOrderTotal(orders).calculateTotal())
                .sorted(Comparator.comparing(Order::calculateTotal))
                .collect(Collectors.toList());
    }

    public static Order maxOrderTotal(List<Order> orders) {
        var max = getExpensive(orders, 1);
        return max.get(0);
    }

    public static Order minOrderTotal(List<Order> orders) {
        var min = getExpensive(orders, 1);
        return min.get(0);
    }

    public static TreeSet<String> getMail(List<Order> orders) {
        return orders.stream()
                .map(Order::getCustomer)
                .map(Customer::getEmail)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public static Map<Customer, List<Order>> getListOrdersByCustomerName(List<Order> orders) {
        return orders.stream()
                .collect(groupingBy(Order::getCustomer));
    }

    public static Map<Customer, Double> getTotalAmountByCustomer(List<Order> orders) {
        return orders.stream()
                .collect(groupingBy(Order::getCustomer,
                        summingDouble(Order::calculateTotal)));
    }

    public static Customer customerMax(Map<Customer, Double> customerDoubleMap) {
        return Collections.max(customerDoubleMap.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public static Customer customerMin(Map<Customer, Double> customerDoubleMap) {
        return Collections.min(customerDoubleMap.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public static Map<String, Integer> getListSoldItems(List<Order> orders) {
        var items = orders.stream()
                .flatMap(i -> i.getItems().stream()).toList();
        return items.stream()
                .collect(groupingBy(Item::getName,
                        summingInt(Item::getAmount)));
    }









}
