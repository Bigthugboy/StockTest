

import java.util.*;

public class OrderProblem {
    public static void main(String[] args) {
        Order order1 = new Order("001", OrderType.BUY, OrderKind.ADD, 20.0, 100);
        Order order2 = new Order("002", OrderType.SELL, OrderKind.ADD, 25.0, 200);
        Order order3 = new Order("003", OrderType.BUY, OrderKind.ADD, 23.0, 50);
        Order order4 = new Order("004", OrderType.BUY, OrderKind.ADD, 23.0, 70);
        Order order5 = new Order("003", OrderType.BUY, OrderKind.REMOVE, 23.0, 50);
        Order order6 = new Order("005", OrderType.SELL, OrderKind.ADD, 28.0, 100);
        List<Order> orders = new ArrayList<>(List.of(
                order1, order2, order3, order4, order5, order6
        ));


        System.out.println(processOrders(orders));
    }

    public static List<Order> removeOrders(List<Order> orders){
        List<Order> ordersToBeRemoved = orders
                .stream()
                .filter(order -> order.getOrderKind() == OrderKind.REMOVE)
                .toList();

        List<Order> ordersLeft = new ArrayList<>(orders
                .stream()
                .filter(order -> order.getOrderKind() == OrderKind.ADD)
                .toList());

        ordersToBeRemoved.forEach(ordersLeft::remove);

        return ordersLeft;
    }
    public static List<Order> processOrders(List<Order> orders){
        List<Order> ordersLeft = removeOrders(orders);

        Optional<Order> maxBuy = ordersLeft
                .stream()
                .filter(order -> order.getType() == OrderType.BUY)
                .max(Comparator.comparing(Order::getPrice).reversed().thenComparing(Order::getQuantity));

        Optional<Order> maxSell = ordersLeft
                .stream()
                .filter(order -> order.getType() == OrderType.SELL)
                .max(Comparator.comparing(Order::getPrice).thenComparing(Order::getQuantity));

        return new ArrayList<>(List.of(maxBuy.get(), maxSell.get()));
    }

}
enum OrderType{
    BUY, SELL
}
enum OrderKind {
    ADD, REMOVE
}
class Order implements Comparable<Order>{
    private String id;
    private OrderType type;
    private OrderKind orderKind;
    private Double price;
    private Integer quantity;

    public Order(String id, OrderType type, OrderKind order, Double price, Integer quantity) {
        this.id = id;
        this.type = type;
        this.orderKind = order;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public OrderKind getOrderKind() {
        return orderKind;
    }

    public void setOrderKind(OrderKind orderKind) {
        this.orderKind = orderKind;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", orderKind=" + orderKind +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && type == order.type && Objects.equals(price, order.price) && Objects.equals(quantity, order.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, price, quantity);
    }

    @Override
    public int compareTo(Order other) {
        int i = price.compareTo(other.price);
        if (i != 0) return i;

        i = quantity.compareTo(other.quantity);
        if (i != 0) return i;

//            return Integer.compare(age, other.age);
        return 0;
    }

}