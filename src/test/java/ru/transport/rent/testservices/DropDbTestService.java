package ru.transport.rent.testservices;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Service
public class DropDbTestService implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;

    private String tableNamesString;


    @Transactional
    public void dropAll() {
        entityManager.flush();
        entityManager.createNativeQuery("TRUNCATE TABLE " + tableNamesString + " RESTART IDENTITY")
                .executeUpdate();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        List<String> tableNames = entityManager.createNativeQuery(
                        "SELECT table_name FROM information_schema.tables WHERE table_schema='public'")
                .getResultList();
        tableNamesString = String.join(",", tableNames);
    }

}
