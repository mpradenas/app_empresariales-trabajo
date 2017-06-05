/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.InsEstudiantes;
import entities.InsInstitucionEducativa;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author Gamalyon
 */
public class InsInstitucionEducativaJpaController implements Serializable {

    public InsInstitucionEducativaJpaController(UserTransaction utx, EntityManagerFactory emf) {
       
        this.emf = emf;
    }
    public InsInstitucionEducativaJpaController() {
       this.emf = Persistence.createEntityManagerFactory("institucionEducativaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InsInstitucionEducativa insInstitucionEducativa) throws RollbackFailureException, Exception {
        if (insInstitucionEducativa.getInsEstudiantesCollection() == null) {
            insInstitucionEducativa.setInsEstudiantesCollection(new ArrayList<InsEstudiantes>());
        }
        EntityManager em = null;
        try {
            
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<InsEstudiantes> attachedInsEstudiantesCollection = new ArrayList<InsEstudiantes>();
            for (InsEstudiantes insEstudiantesCollectionInsEstudiantesToAttach : insInstitucionEducativa.getInsEstudiantesCollection()) {
                insEstudiantesCollectionInsEstudiantesToAttach = em.getReference(insEstudiantesCollectionInsEstudiantesToAttach.getClass(), insEstudiantesCollectionInsEstudiantesToAttach.getEstIdestudiante());
                attachedInsEstudiantesCollection.add(insEstudiantesCollectionInsEstudiantesToAttach);
            }
            insInstitucionEducativa.setInsEstudiantesCollection(attachedInsEstudiantesCollection);
            em.persist(insInstitucionEducativa);
            for (InsEstudiantes insEstudiantesCollectionInsEstudiantes : insInstitucionEducativa.getInsEstudiantesCollection()) {
                InsInstitucionEducativa oldEstIdinstitucionOfInsEstudiantesCollectionInsEstudiantes = insEstudiantesCollectionInsEstudiantes.getEstIdinstitucion();
                insEstudiantesCollectionInsEstudiantes.setEstIdinstitucion(insInstitucionEducativa);
                insEstudiantesCollectionInsEstudiantes = em.merge(insEstudiantesCollectionInsEstudiantes);
                if (oldEstIdinstitucionOfInsEstudiantesCollectionInsEstudiantes != null) {
                    oldEstIdinstitucionOfInsEstudiantesCollectionInsEstudiantes.getInsEstudiantesCollection().remove(insEstudiantesCollectionInsEstudiantes);
                    oldEstIdinstitucionOfInsEstudiantesCollectionInsEstudiantes = em.merge(oldEstIdinstitucionOfInsEstudiantesCollectionInsEstudiantes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InsInstitucionEducativa insInstitucionEducativa) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InsInstitucionEducativa persistentInsInstitucionEducativa = em.find(InsInstitucionEducativa.class, insInstitucionEducativa.getIntsIdinstitucion());
            /*Collection<InsEstudiantes> insEstudiantesCollectionOld = persistentInsInstitucionEducativa.getInsEstudiantesCollection();
            Collection<InsEstudiantes> insEstudiantesCollectionNew = insInstitucionEducativa.getInsEstudiantesCollection();
            Collection<InsEstudiantes> attachedInsEstudiantesCollectionNew = new ArrayList<InsEstudiantes>();
            for (InsEstudiantes insEstudiantesCollectionNewInsEstudiantesToAttach : insEstudiantesCollectionNew) {
                insEstudiantesCollectionNewInsEstudiantesToAttach = em.getReference(insEstudiantesCollectionNewInsEstudiantesToAttach.getClass(), insEstudiantesCollectionNewInsEstudiantesToAttach.getEstIdestudiante());
                attachedInsEstudiantesCollectionNew.add(insEstudiantesCollectionNewInsEstudiantesToAttach);
            }
            insEstudiantesCollectionNew = attachedInsEstudiantesCollectionNew;
            insInstitucionEducativa.setInsEstudiantesCollection(insEstudiantesCollectionNew);
            */
            insInstitucionEducativa = em.merge(insInstitucionEducativa);
            /*for (InsEstudiantes insEstudiantesCollectionOldInsEstudiantes : insEstudiantesCollectionOld) {
                if (!insEstudiantesCollectionNew.contains(insEstudiantesCollectionOldInsEstudiantes)) {
                    insEstudiantesCollectionOldInsEstudiantes.setEstIdinstitucion(null);
                    insEstudiantesCollectionOldInsEstudiantes = em.merge(insEstudiantesCollectionOldInsEstudiantes);
                }
            }
            for (InsEstudiantes insEstudiantesCollectionNewInsEstudiantes : insEstudiantesCollectionNew) {
                if (!insEstudiantesCollectionOld.contains(insEstudiantesCollectionNewInsEstudiantes)) {
                    InsInstitucionEducativa oldEstIdinstitucionOfInsEstudiantesCollectionNewInsEstudiantes = insEstudiantesCollectionNewInsEstudiantes.getEstIdinstitucion();
                    insEstudiantesCollectionNewInsEstudiantes.setEstIdinstitucion(insInstitucionEducativa);
                    insEstudiantesCollectionNewInsEstudiantes = em.merge(insEstudiantesCollectionNewInsEstudiantes);
                    if (oldEstIdinstitucionOfInsEstudiantesCollectionNewInsEstudiantes != null && !oldEstIdinstitucionOfInsEstudiantesCollectionNewInsEstudiantes.equals(insInstitucionEducativa)) {
                        oldEstIdinstitucionOfInsEstudiantesCollectionNewInsEstudiantes.getInsEstudiantesCollection().remove(insEstudiantesCollectionNewInsEstudiantes);
                        oldEstIdinstitucionOfInsEstudiantesCollectionNewInsEstudiantes = em.merge(oldEstIdinstitucionOfInsEstudiantesCollectionNewInsEstudiantes);
                    }
                }
            }*/
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = insInstitucionEducativa.getIntsIdinstitucion();
                if (findInsInstitucionEducativa(id) == null) {
                    throw new NonexistentEntityException("The insInstitucionEducativa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            
            em = getEntityManager();
            em.getTransaction().begin();
            InsInstitucionEducativa insInstitucionEducativa;
            try {
                insInstitucionEducativa = em.getReference(InsInstitucionEducativa.class, id);
                insInstitucionEducativa.getIntsIdinstitucion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The insInstitucionEducativa with id " + id + " no longer exists.", enfe);
            }
            Collection<InsEstudiantes> insEstudiantesCollection = insInstitucionEducativa.getInsEstudiantesCollection();
            for (InsEstudiantes insEstudiantesCollectionInsEstudiantes : insEstudiantesCollection) {
                insEstudiantesCollectionInsEstudiantes.setEstIdinstitucion(null);
                insEstudiantesCollectionInsEstudiantes = em.merge(insEstudiantesCollectionInsEstudiantes);
            }
            em.remove(insInstitucionEducativa);
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InsInstitucionEducativa> findInsInstitucionEducativaEntities() {
        return findInsInstitucionEducativaEntities(true, -1, -1);
    }

    public List<InsInstitucionEducativa> findInsInstitucionEducativaEntities(int maxResults, int firstResult) {
        return findInsInstitucionEducativaEntities(false, maxResults, firstResult);
    }

    private List<InsInstitucionEducativa> findInsInstitucionEducativaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InsInstitucionEducativa.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public InsInstitucionEducativa findInsInstitucionEducativa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InsInstitucionEducativa.class, id);
        } finally {
            em.close();
        }
    }

    public int getInsInstitucionEducativaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InsInstitucionEducativa> rt = cq.from(InsInstitucionEducativa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
