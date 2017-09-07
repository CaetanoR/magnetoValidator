package com.xmen.mutants.dao;

import com.xmen.mutants.model.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
@Transactional
public class RequestDao {

    private static final Logger logger = LoggerFactory.getLogger(RequestDao.class);

    @PersistenceContext
    private EntityManager entityManager;

    public void create(Request request) {
        logger.debug("saving Request instance");
        try {
            entityManager.persist(request);
            logger.debug("save successful");
        } catch (RuntimeException re) {
            logger.error("save failed", re);
            throw re;
        }
    }

    public void update(Request request) {
        logger.debug("updating Request instance");
        try {
            entityManager.merge(request);
            logger.debug("merge successful");
        } catch (RuntimeException re) {
            logger.error("merge failed", re);
            throw re;
        }
    }

    @SuppressWarnings("unchecked")
    public void delete(Request request) {
        logger.debug("deleting Request instance");
        try {
            request = entityManager.merge(request);
            entityManager.remove(request);
            logger.debug("delete successful");
        } catch (RuntimeException re) {
            logger.error("delete failed", re);
            throw re;
        }
    }

    public void deleteById(Integer id) {
        logger.debug("deleting Request instance");
        try {
            entityManager.remove(entityManager.getReference(Request.class, id));
            logger.debug("delete successful");
        } catch (RuntimeException re) {
            logger.error("delete failed", re);
            throw re;
        }
    }

    @SuppressWarnings("unchecked")
    public Request findById(Integer id) {
        logger.debug(String.format("getting Request instance with id: %s", id));
        try {
            Request instance = entityManager
                    .getReference(Request.class, id);
            if (instance == null) {
                logger.debug("get successful, no instance found");
            } else {
                logger.debug("get successful, instance found");
            }
            return instance;
        } catch (RuntimeException re) {
            logger.error("get failed", re);
            throw re;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Request> findAll() {
        CriteriaQuery<Request> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Request.class);
        criteriaQuery.from(Request.class);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
