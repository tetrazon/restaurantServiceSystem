package dao.hibernate;

import dao.ClientDAO;
import entity.users.Client;
import hibernate.HibernateSessionFactory;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ClientDAOHibernate implements ClientDAO {

    private SessionFactory sessionFactory;

    public ClientDAOHibernate(){
        sessionFactory = HibernateSessionFactory.getSessionFactory();
    }

    @Override
    public void deleteClientById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Client.class);
            Client client = (Client) criteria.add(Restrictions.eq("id", id)).uniqueResult();
            Transaction trx = session.beginTransaction();
            session.delete(client);
            trx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Client> getAll() {
        try (Session session = sessionFactory.openSession();) {
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
            return (Client) criteria.add(Restrictions
                    .eq("email", emailToCheck)).uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Client getClientById(int clientId){
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
    public void updateClientDeposit(Client client) {
        try (Session session = sessionFactory.openSession()) {
            Transaction trx = session.beginTransaction();
            session.update(client);
            trx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(Client client) {
        try (Session session = sessionFactory.openSession()) {
            Transaction trx = session.beginTransaction();
            session.save(client);
            trx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }


}
