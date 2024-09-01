import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HibernateQueryTest {

    private SessionFactory sessionFactory;
    private Session session;

    @BeforeEach
    public void setUp() {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterEach
    public void tearDown() {
        if (session != null) {
            session.getTransaction().commit();
            session.close();
        }
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    public void testQuery() {
        // Insert test data
        MyEntity entity = new MyEntity();
        entity.setF1(5L);
        entity.setF2("SelimMkocak");
        session.persist(entity);
        //session.save(entity);

        MyEntity entity2 = new MyEntity();
        entity2.setF1(11L);
        entity2.setF2("FeyzaNurKocak");
        session.persist(entity2);
        //session.save(entity2);


        MyEntity entity3 = new MyEntity();
        entity3.setF1(13L);
        entity3.setF2("SuaraMelekKocak");
        session.persist(entity3);
        //session.save(entity3);

        // Flush and clear the session to simulate a new transaction
        session.flush();
        session.clear();


        //Query<MyEntity> query = session.createQuery("from MyEntity where f2 = :f2", MyEntity.class);
        Query<MyEntity> query = session.createQuery("FROM MyEntity", MyEntity.class);
        ScrollableResults scrollableResults = query.scroll();


        //jdk8 implementation
        /*if (scrollableResults.next()) {
            Object[] rows = scrollableResults.get();
            if (rows.length == 1) {
                MyEntity row = (MyEntity) rows[0];

                assertNotNull(row);
            }
        }*/


        //jdk11
        if(scrollableResults.next()){
            Object row = scrollableResults.get();

            assertNotNull(row);
        }

        List<MyEntity> resultList = query.getResultList();


        //Object[] myEntity = new Object[]{query.scrollableResults().get()};
        // Execute the query
        //MyEntity result = query.uniqueResult();

        // Assertions
        assertNotNull(resultList);
        assertEquals(3, resultList.size());
        assertEquals(5L, resultList.get(0).f1());
    }
}
