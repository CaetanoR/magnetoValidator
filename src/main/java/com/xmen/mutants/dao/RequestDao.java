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
    private static final String SUCCESSFUL = "%s successful";
    private static final String FAILED = "%s failed";
    private static final String OPERATION_ON_INSTANCE = "%s Request instance";
    private static final String SAVE = "save";
    private static final String SAVING = "saving";
    private static final String MERGE = "merge";
    private static final String UPDATING = "updating";
    private static final String DELETE = "delete";
    private static final String DELETING = "deleting";

    @PersistenceContext
    private EntityManager entityManager;

    public void create(Request request) {
        logger.debug(String.format(OPERATION_ON_INSTANCE, SAVING));
        try {
            entityManager.persist(request);
            logger.debug(String.format(SUCCESSFUL, SAVE));
        } catch (RuntimeException re) {
            logger.error(FAILED, SAVE);
            throw re;
        }
    }

    public void update(Request request) {
        logger.debug(String.format(OPERATION_ON_INSTANCE, UPDATING));
        try {
            entityManager.merge(request);
            logger.debug(String.format(SUCCESSFUL, MERGE));
        } catch (RuntimeException re) {
            logger.error(String.format(FAILED, MERGE), re);
            throw re;
        }
    }

    @SuppressWarnings("unchecked")
    public void delete(Request request) {
        logger.debug(String.format(OPERATION_ON_INSTANCE, DELETING));
        try {
            request = entityManager.merge(request);
            entityManager.remove(request);
            logger.debug(String.format(SUCCESSFUL, DELETE));
        } catch (RuntimeException re) {
            logger.error(String.format(FAILED, DELETE), re);
            throw re;
        }
    }

    public void deleteById(Integer id) {
        logger.debug(String.format(OPERATION_ON_INSTANCE, DELETING));
        try {
            entityManager.remove(entityManager.getReference(Request.class, id));
            logger.debug(String.format(SUCCESSFUL, DELETE));
        } catch (RuntimeException re) {
            logger.error(String.format(FAILED, DELETE), re);
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
