package hibernate;

import dao.hibernate.ClientDAOImpl;
import dao.hibernate.OrderDAOImpl;
import entity.order.Order;
import entity.order.Table;
import entity.users.Client;

public class HibernateCheck {
    public static void main(String[] args) {
        System.out.println("Hibernate tutorial");
        //SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
        OrderDAOImpl orderDAOImpl = new OrderDAOImpl();
//        List<Table> tables = orderDAOImpl.getAllTables();
//        for(Table t: tables){
//            System.out.println(t.getId() + "; is reserved " + t.getIsReserved());
//        }
//        System.out.println(orderDAOImpl.getTableById(4).getIsReserved());
//        Order order= orderDAOImpl.getById(20);
//        List<DishesInOrder> listDoshesOrder = orderDAOImpl.getDishesFromOrder(order);
//        for(DishesInOrder d: listDoshesOrder){
//            System.out.println(d.getQuantity());
//        }
//                ClientDAOImpl clientDAOImpl = new ClientDAOImpl();
//        Client client = clientDAOImpl.getClientById(6);
//        Table table = orderDAOImpl.getTable(4);
//        Order order = new Order();
//        order.setOrderStatus("NEW");
//        order.setInvoice(100500.);
//        order.setClient(client);
//        order.setTable(table);
//        orderDAOImpl.initOrder(order);
//        orderDAOImpl.createOrder(order);
//        Order order= orderDAOImpl.getById(20);
//        System.out.println("\n Order status:" + order.getOrderStatus());
//        ClientDAOImpl clientDAOImpl = new ClientDAOImpl();
//        clientDAOImpl.deleteClientById(42);
//        Client client = clientDAOImpl.getClientById(6);
//        System.out.println(orderDAOImpl.getOrderId(client, "NEW"));
        //EmployeeDAOImpl employeeDAOHibernate = new EmployeeDAOImpl();
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
//        orderDAOImpl.addEmployeesInOrder(employeeDAOHibernate.getFreeEmployee("WAITER"),
//                employeeDAOHibernate.getFreeEmployee("COOK"), 58);
//        List<Employee> employees = employeeDAOHibernate.getAllEmployees(employeeDAOHibernate.getFreeEmployee("MANAGER").getId());
//        for (Employee employee: employees){
//            System.out.println("Employee id :" + employee.getId() + "; name: "
//                    + employee.getName() + "; position: " + employee.getPosition());
//        }
//        List<Order> orders = orderDAOImpl.getAllOrders(client);
//        for(Order order: orders){
//            System.out.println("order id: " + order.getId() + "; sum: " + order.getInvoice());
//        }
//        Table table = orderDAOImpl.getTable(4);
//        System.out.println(table.getSeats());
//        ClientDAOImpl clientDAOImpl = new ClientDAOImpl();
//        List<Client> clients = clientDAOImpl.getAll();
//        for (Client client: clients) {
//            System.out.println("cliend id: " + client.getId() + ", client name: " + client.getName());
//        }
//        EmployeeDAOImpl employeeDAOHibernate = new EmployeeDAOImpl();
//        List<Employee> employees = employeeDAOHibernate.getAllEmployees();
//        for (Employee employee: employees){
//            System.out.println("Employee id :" + employee.getId() + "; name: "
//                    + employee.getName() + "; position: " + employee.getPosition());
//        }
//
//        DishDAOImpl dishDAOHibernate = new DishDAOImpl();
//        Dish dish = dishDAOHibernate.getDishById(3);
//        System.out.println("dish: " + dish.getName() + "; price: " + dish.getPrice());
//        List<Dish> dishes = dishDAOHibernate.getAllDishes();
//        for (Dish dish: dishes){
//            System.out.println("dish name: " + dish.getName() + "; price: " + dish.getPrice());
//        }


    }
}
