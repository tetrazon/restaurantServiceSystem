package dao.hibernate;

import dao.OrderDAO;
import entity.enumeration.OrderStatus;
import entity.food.Dish;
import entity.food.DishesInOrder;
import entity.order.Order;
import entity.order.Table;
import entity.users.Client;
import entity.users.Employee;
import hibernate.HibernateSessionFactory;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderDAOImpl implements OrderDAO {
    private static Logger logger = LoggerFactory.getLogger(OrderDAOImpl.class);
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void insertDishesInOrder(DishesInOrder dishesInOrder) {
        try (Session session = sessionFactory.openSession()) {
            //Transaction transaction = session.beginTransaction();
            session.save(dishesInOrder);
            //transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createOrder(Order order) {
        try (Session session = sessionFactory.openSession()) {
            //Transaction transaction = session.beginTransaction();
            session.save(order);
            // transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setTimeOfOrder(Order order) {
        try (Session session = sessionFactory.openSession()) {
            //Transaction trx = session.beginTransaction();
            logger.info("updated order time: " + order.getTimestamp());
            session.update(order);
            // trx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean initOrder(Order order) {
        try (Session session = sessionFactory.openSession()) {
            //Transaction trx = session.beginTransaction();
            session.save(order);
            //trx.commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean changeOrderStatus(Order order) {
        try (Session session = sessionFactory.openSession()) {
            //Transaction trx = session.beginTransaction();
            session.update(order);
            //trx.commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Order getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Order.class);
            Order resultOrder = ((Order) criteria.add(Restrictions.eq("id", id)).uniqueResult());
            // Order resultOrder = (Order)session.load(Order.class, id);//org.hibernate.LazyInitializationException: could not initialize proxy - no Session
            return resultOrder;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer getOrderId(Client client, String orderStatus) {//client must be initialized FULL!!!
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Order.class);
            Order order = (Order) criteria.add(Restrictions.eq("client", client)).add(Restrictions.eq("orderStatus", OrderStatus.valueOf(orderStatus))).list().get(0);
            return order.getId();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> getAllOrders(Client client) {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Order.class);
            List<Order> orders = (List<Order>) criteria.add(Restrictions.eq("client", client)).list();
            return orders;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addEmployeesInOrder(Employee waiter, Employee cook, int orderId) {
        try (Session session = sessionFactory.openSession()) {
            Criteria orderCriteria = session.createCriteria(Order.class);
            Order order = (Order) orderCriteria.add(Restrictions.eq("id", orderId)).uniqueResult();
            order.setCook(cook);
            order.setWaiter(waiter);
            Transaction transaction = session.beginTransaction();
            session.update(order);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Table> getAllTables() {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Table.class);
            List<Table> tables = (List<Table>) criteria.list();

            return tables;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Table getTableById(int tableId) {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Table.class);
            return (Table) criteria.add(Restrictions.eq("id", tableId)).uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setTableStatus(Table table) {
        try (Session session = sessionFactory.openSession()) {
            Transaction trx = session.beginTransaction();
            session.update(table);
            trx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<DishesInOrder> getDishesFromOrder(Order order) {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(DishesInOrder.class);
            List<DishesInOrder> dishesInOrderList = criteria.add(Restrictions.eq("order", order)).list();
            return dishesInOrderList;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Table getTable(int id) {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Table.class);
            Table table = ((Table) criteria.add(Restrictions.eq("id", id)).uniqueResult());
            return table;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

}