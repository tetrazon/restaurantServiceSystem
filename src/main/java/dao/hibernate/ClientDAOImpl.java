package dao.hibernate;

import dao.ClientDAO;
import entity.users.Client;
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
public class ClientDAOImpl implements ClientDAO {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void deleteClientById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Client.class);
            Client client = (Client) criteria.add(Restrictions.eq("id", id)).uniqueResult();
            //Transaction trx = session.beginTransaction();
            session.delete(client);
            //trx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Client> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Client.class);
            List<Client> clients = (List<Client>) criteria.list();
            return clients;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Client getClientByEmail(String emailToCheck) {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Client.class);
            return (Client) criteria.add(Restrictions.eq("email", emailToCheck)).uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Client getClientById(int clientId) {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Client.class);
            Client client = (Client) criteria.add(Restrictions.eq("id", clientId)).uniqueResult();
            return client;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateClient(Client client) {
        try (Session session = sessionFactory.openSession()) {
            //Client tempClient = session.
            //Transaction trx = session.beginTransaction();
            //session.merge(client);
            session.saveOrUpdate(client);
            session.flush();
            //trx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(Client client) {
        try (Session session = sessionFactory.openSession()) {
            //Transaction trx = session.beginTransaction();
            session.save(client);
            //trx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }


}