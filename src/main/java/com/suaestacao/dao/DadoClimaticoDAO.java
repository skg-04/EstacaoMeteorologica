package com.suaestacao.dao;

import com.suaestacao.modelo.DadoClimatico;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class DadoClimaticoDAO {
    
    private SessionFactory sessionFactory;

    public DadoClimaticoDAO() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void salvar(DadoClimatico dado) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();
            session.persist(dado);
            transaction.commit();
            System.out.println("SUCESSO: Registro inserido no SQL Server!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }
}