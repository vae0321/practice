package com.palmaplus.nagrand.brand.factory;

import com.palmaplus.nagrand.brand.entity.FetchErrorRecord;
import com.palmaplus.nagrand.brand.entity.ProcessRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.function.Function;

/**
 * Created by sifan on 2016/7/1.
 */
public class EntityFactory {
    private static Logger logger = LoggerFactory.getLogger(EntityFactory.class);

    private static EntityManagerFactory factory;

    private static synchronized EntityManagerFactory createEntityManagerFactory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory("brand");
        }
        return factory;
    }

    public static EntityManagerFactory entityManagerFactory() {
        if (factory == null) {
            factory = createEntityManagerFactory();
        }
        return factory;
    }

    public static List<ProcessRecord> findProcessRecords() {
        return execute(em -> {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProcessRecord> cq = cb.createQuery(ProcessRecord.class);
            Root<ProcessRecord> recordRoot = cq.from(ProcessRecord.class);
            cq.select(recordRoot);

            return em.createQuery(cq).getResultList();
        });
    }

    public static List<FetchErrorRecord> findFetchErrorRecords() {
        return execute(em -> {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<FetchErrorRecord> cq = cb.createQuery(FetchErrorRecord.class);
            Root<FetchErrorRecord> recordRoot = cq.from(FetchErrorRecord.class);
            cq.select(recordRoot);

            return em.createQuery(cq).getResultList();
        });
    }

    public static boolean deleteErrorRecord(FetchErrorRecord record) {
        return execute(em -> {
            try {
                em.remove(em.merge(record));
                return true;
            } catch (Exception e) {
                logger.warn("delete error record failed.", e);
                return false;
            }
        });
    }

    public static <R> R execute(Function<EntityManager, R> function) {
        EntityManager em = entityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        R r = null;
        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
            r = function.apply(em);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.warn("execute failed.", e);
        } finally {
            if (transaction.isActive()) {
                transaction.commit();
            }
            em.close();
        }
        return r;
    }
}
