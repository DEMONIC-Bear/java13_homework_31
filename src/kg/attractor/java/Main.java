package kg.attractor.java;

// import static java.util.stream.Collectors.*;
// import static java.util.Comparator.*;

// используя статические imports
// мы импортируем *всё* из Collectors и Comparator.
// теперь нам доступны такие операции как
// toList(), toSet() и прочие, без указания уточняющего слова Collectors. или Comparator.
// вот так было до импорта Collectors.toList(), теперь стало просто toList()


import kg.attractor.java.homework.RestaurantOrders;
import kg.attractor.java.homework.domain.Order;

public class Main {

    public static void main(String[] args) {

        var orders = RestaurantOrders.read("orders_100.json").getOrders();

        // это для домашки
        // выберите любое количество заказов, какое вам нравится.




        // список из N заказов имеющих наибольшую общую стоимость.
        var expensive = RestaurantOrders.getExpensive(orders,12);
        expensive.forEach(Order::printList);

        // список из N заказов, имеющих наименьшую общую стоимость.
        RestaurantOrders.printListAndMinMaxForDelivery(orders);
        System.out.println("All orders sum price: " + RestaurantOrders.totalPrice(orders));

        //Список заказов на дом
        System.out.println("Order list: ");
        var deliveryList = RestaurantOrders.getDeliveryList(orders);
        deliveryList.forEach(Order::printList);

        // Заказы с общей суммой меньше maxOrderTotal.
        var listLessMax = RestaurantOrders.lessMaxTotalList(orders);
        listLessMax.forEach(Order::printList);
        // Заказы с общей суммой больше minOrderTotal
        var listMoreThanMin = RestaurantOrders.moreThanMinTotalList(orders);
        listMoreThanMin.forEach(Order::printList);

        // Получение эл почты
        var mails = RestaurantOrders.getMail(orders);
        mails.forEach(System.out::println);

        //списки заказов, сгруппированных по имени заказчика.
        var customerList = RestaurantOrders.getListOrdersByCustomerName(orders);
        customerList.forEach((key, value) -> value.forEach(Order::printList));

        //список уникальных заказчиков и общую сумму заказов для каждого из них
        System.out.println("#".repeat(90) + "\nList of customers and the total amount for each.");
        var customerListTotalPrice = RestaurantOrders.getTotalAmountByCustomer(orders);
        System.out.printf("%-20s | %s\n", "Name","Total price");
        System.out.println("-".repeat(21) + "|" + "-".repeat(15));
        customerListTotalPrice.forEach((key,value) -> System.out.printf("%-20s | %.3f\n",key.getFullName(),value));
        System.out.println("#".repeat(90));

//        // клиент с максимальной суммой всех его заказов.
        System.out.println("Client with the maximum amount of all his orders.");
        System.out.println(RestaurantOrders.customerMax(customerListTotalPrice));
        System.out.println("-".repeat(45));
//        // клиент с минимальной суммой всех его заказов.
        System.out.println("Client with the minimum amount of all his orders.");
        System.out.println(RestaurantOrders.customerMin(customerListTotalPrice));

        // "товары" по их общему количеству
        var itemsList = RestaurantOrders.getListSoldItems(orders);
        System.out.println("#".repeat(90) + "\nProducts sold.");
        System.out.printf("%-25s | %s\n", "Name","Amount");
        System.out.println("-".repeat(26) + "|" + "-".repeat(10));
        itemsList.forEach((key,value) -> System.out.printf("%-25s | %s\n", key,value));






    }
}
