package com.HoopStretchApi.config;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class HibernateFilterConfig {

//    @PersistenceContext
//    private final EntityManager entityManager;
//
//    @PostConstruct
//    public void enableDeletedFilter() {
//        final Session session = entityManager.unwrap(Session.class);
//        session.enableFilter("deletedFilter")
//                .setParameter("isDeleted", true);
//    }
}