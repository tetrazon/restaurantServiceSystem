package dao.hibernate;

import dao.DishDAO;
import entity.food.Dish;
import hibernate.HibernateSessionFactory;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DishDAOImpl implements DishDAO {

    @Autowired
    private SessionFactory sessionFactory;

//    public DishDAOImpl(){
//        sessionFactory = HibernateSessionFactory.getSessionFactory();
//    }

    @Override
    public void deleteDishById(int dishId) {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Dish.class);
            Dish dish = (Dish) criteria.add(Restrictions.eq("id", dishId)).uniqueResult();
            //Transaction trx = session.beginTransaction();
            session.delete(dish);
            //trx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDish(Dish dish) {
        try (Session session = sessionFactory.openSession()) {
            // Transaction trx = session.beginTransaction();
            session.update(dish);
            //trx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void create(Dish dish) {
        try (Session session = sessionFactory.openSession()) {
            //Transaction trx = session.beginTransaction();
            session.save(dish);
            // trx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Dish> getAllDishes() {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Dish.class);
            List<Dish> dishes = (List<Dish>) criteria.list();
            return dishes;
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Dish getDishById(int dishId) {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Dish.class);
            Dish dish = ((Dish) criteria.add(Restrictions.eq("id", dishId)).uniqueResult());
            return dish;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

}