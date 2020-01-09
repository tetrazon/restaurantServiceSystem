package hibernate;

import dao.hibernate.ClientDAOHibernate;
import dao.hibernate.DishDAOHibernate;
import dao.hibernate.EmployeeDAOHibernate;
import dao.hibernate.OrderDAOHibernate;
import entity.food.Dish;
import entity.food.DishesInOrder;
import entity.order.Order;
import entity.order.Table;
import entity.users.Client;
import entity.users.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class HibernateCheck {
    public static void main(String[] args) {
        System.out.println("Hibernate tutorial");
        //SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
        OrderDAOHibernate orderDAOHibernate = new OrderDAOHibernate();
//        List<Table> tables = orderDAOHibernate.getAllTables();
//        for(Table t: tables){
//            System.out.println(t.getId() + "; is reserved " + t.getIsReserved());
//        }
//        System.out.println(orderDAOHibernate.getTableById(4).getIsReserved());
//        Order order= orderDAOHibernate.getById(20);
//        List<DishesInOrder> listDoshesOrder = orderDAOHibernate.getDishesFromOrder(order);
//        for(DishesInOrder d: listDoshesOrder){
//            System.out.println(d.getQuantity());
//        }
                ClientDAOHibernate clientDAOHibernate = new ClientDAOHibernate();
        Client client = clientDAOHibernate.getClientById(6);
        Table table = orderDAOHibernate.getTable(4);
        Order order = new Order();
        order.setOrderStatus("NEW");
        order.setInvoice(100500.);
        order.setClient(client);
        order.setTable(table);
        orderDAOHibernate.initOrder(order);
//        orderDAOHibernate.createOrder(order);
//        Order order= orderDAOHibernate.getById(20);
//        System.out.println("\n Order status:" + order.getOrderStatus());
//        ClientDAOHibernate clientDAOHibernate = new ClientDAOHibernate();
//        clientDAOHibernate.deleteClientById(42);
//        Client client = clientDAOHibernate.getClientById(6);
//        System.out.println(orderDAOHibernate.getOrderId(client, "NEW"));
        //EmployeeDAOHibernate employeeDAOHibernate = new EmployeeDAOHibernate();
        //employeeDAOHibernate.deleteEmployeeById(22);
        //Employee employee = employeeDAOHibernate.getEmployeeByEmail("waw@mail.ru");
        //System.out.println("employee: " + employee.getName());

//        employee.setName("dgsadgsd");
//        employee.setPassword("sfddsf");
//        employee.setEmail("dsfsgf");
//        employee.setSurname("dfgfd");
//        employee.setPosition("COOK");
//        employee.setLoadFactor(2);
//        employeeDAOHibernate.create(employee);
//        employee.setLoadFactor(3);
//        employeeDAOHibernate.changeLoadFactor(employee);
//        orderDAOHibernate.addEmployeesInOrder(employeeDAOHibernate.getFreeEmployee("WAITER"),
//                employeeDAOHibernate.getFreeEmployee("COOK"), 58);
//        List<Employee> employees = employeeDAOHibernate.getAllEmployees(employeeDAOHibernate.getFreeEmployee("MANAGER").getId());
//        for (Employee employee: employees){
//            System.out.println("Employee id :" + employee.getId() + "; name: "
//                    + employee.getName() + "; position: " + employee.getPosition());
//        }
//        List<Order> orders = orderDAOHibernate.getAllOrders(client);
//        for(Order order: orders){
//            System.out.println("order id: " + order.getId() + "; sum: " + order.getInvoice());
//        }
//        Table table = orderDAOHibernate.getTable(4);
//        System.out.println(table.getSeats());
//        ClientDAOHibernate clientDAOHibernate = new ClientDAOHibernate();
//        List<Client> clients = clientDAOHibernate.getAll();
//        for (Client client: clients) {
//            System.out.println("cliend id: " + client.getId() + ", client name: " + client.getName());
//        }
//        EmployeeDAOHibernate employeeDAOHibernate = new EmployeeDAOHibernate();
//        List<Employee> employees = employeeDAOHibernate.getAllEmployees();
//        for (Employee employee: employees){
//            System.out.println("Employee id :" + employee.getId() + "; name: "
//                    + employee.getName() + "; position: " + employee.getPosition());
//        }
//
//        DishDAOHibernate dishDAOHibernate = new DishDAOHibernate();
//        Dish dish = dishDAOHibernate.getDishById(3);
//        System.out.println("dish: " + dish.getName() + "; price: " + dish.getPrice());
//        List<Dish> dishes = dishDAOHibernate.getAllDishes();
//        for (Dish dish: dishes){
//            System.out.println("dish name: " + dish.getName() + "; price: " + dish.getPrice());
//        }


    }
}
