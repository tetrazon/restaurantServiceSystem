package dao.hibernate;

import dao.EmployeeDAO;
import entity.enumeration.Position;
import entity.users.Employee;
import hibernate.HibernateSessionFactory;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmployeeDAOHibernate implements EmployeeDAO {

    private SessionFactory sessionFactory;

    public EmployeeDAOHibernate(){
        sessionFactory = HibernateSessionFactory.getSessionFactory();
    }

    public List<Employee> getAllEmployees() {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Employee.class);
            List<Employee> employees = (List<Employee>) criteria.list();
            return employees;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Employee> getAllEmployees(int emplId) {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Employee.class);
            List<Employee> employees = (List<Employee>) criteria.add(Restrictions.ne("id", emplId)).list();
            return employees;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteEmployeeById(int employeeId) {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Employee.class);
            Employee employee = (Employee) criteria.add(Restrictions.eq("id", employeeId)).uniqueResult();
            Transaction trx = session.beginTransaction();
            session.delete(employee);
            trx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void create(Employee employee) {
        try (Session session = sessionFactory.openSession()) {
            Transaction trx = session.beginTransaction();
            session.save(employee);
            trx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean changeLoadFactor(Employee employee) {
        try (Session session = sessionFactory.openSession()) {
            Transaction trx = session.beginTransaction();
            session.update(employee);
            trx.commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Employee getFreeEmployee(String position) {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Employee.class);
            List<Employee> employees = (List<Employee>) criteria.add(Restrictions
                    .eq("position", Position.valueOf(position))).list();
            Collections.sort(employees, Comparator.comparingInt(Employee::getLoadFactor));
            return employees.get(0);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Employee getEmployeeByEmail(String emailToCheck) {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Employee.class);
            return (Employee) criteria.add(Restrictions
                    .eq("email", emailToCheck)).uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

}
