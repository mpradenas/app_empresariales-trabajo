/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entities.InsEstudiantes;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.InsInstitucionEducativa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author Gamalyon
 */
public class InsEstudiantesJpaController implements Serializable {

    public InsEstudiantesJpaController( EntityManagerFactory emf) {
        this.emf = emf;
    }
     public InsEstudiantesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("institucionEducativaPU");;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InsEstudiantes insEstudiantes) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
           
            em = getEntityManager();
            em.getTransaction().begin();
            InsInstitucionEducativa estIdinstitucion = insEstudiantes.getEstIdinstitucion();
            if (estIdinstitucion != null) {
                estIdinstitucion = em.getReference(estIdinstitucion.getClass(), estIdinstitucion.getIntsIdinstitucion());
                insEstudiantes.setEstIdinstitucion(estIdinstitucion);
            }
            em.persist(insEstudiantes);
            if (estIdinstitucion != null) {
                estIdinstitucion.getInsEstudiantesCollection().add(insEstudiantes);
                estIdinstitucion = em.merge(estIdinstitucion);
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

    public void edit(InsEstudiantes insEstudiantes) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InsEstudiantes persistentInsEstudiantes = em.find(InsEstudiantes.class, insEstudiantes.getEstIdestudiante());
            InsInstitucionEducativa estIdinstitucionOld = persistentInsEstudiantes.getEstIdinstitucion();
            InsInstitucionEducativa estIdinstitucionNew = insEstudiantes.getEstIdinstitucion();
            if (estIdinstitucionNew != null) {
                estIdinstitucionNew = em.getReference(estIdinstitucionNew.getClass(), estIdinstitucionNew.getIntsIdinstitucion());
                insEstudiantes.setEstIdinstitucion(estIdinstitucionNew);
            }
            insEstudiantes = em.merge(insEstudiantes);
            if (estIdinstitucionOld != null && !estIdinstitucionOld.equals(estIdinstitucionNew)) {
                estIdinstitucionOld.getInsEstudiantesCollection().remove(insEstudiantes);
                estIdinstitucionOld = em.merge(estIdinstitucionOld);
            }
            if (estIdinstitucionNew != null && !estIdinstitucionNew.equals(estIdinstitucionOld)) {
                estIdinstitucionNew.getInsEstudiantesCollection().add(insEstudiantes);
                estIdinstitucionNew = em.merge(estIdinstitucionNew);
            }
               em.getTransaction().commit();
         
            
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = insEstudiantes.getEstIdestudiante();
                if (findInsEstudiantes(id) == null) {
                    throw new NonexistentEntityException("The insEstudiantes with id " + id + " no longer exists.");
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
            InsEstudiantes insEstudiantes;
            try {
                insEstudiantes = em.getReference(InsEstudiantes.class, id);
                insEstudiantes.getEstIdestudiante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The insEstudiantes with id " + id + " no longer exists.", enfe);
            }
            InsInstitucionEducativa estIdinstitucion = insEstudiantes.getEstIdinstitucion();
            if (estIdinstitucion != null) {
                estIdinstitucion.getInsEstudiantesCollection().remove(insEstudiantes);
                estIdinstitucion = em.merge(estIdinstitucion);
            }
           
            em.remove(insEstudiantes);
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

    public List<InsEstudiantes> findInsEstudiantesEntities() {
        return findInsEstudiantesEntities(true, -1, -1);
    }

    public List<InsEstudiantes> findInsEstudiantesEntities(int maxResults, int firstResult) {
        return findInsEstudiantesEntities(false, maxResults, firstResult);
    }

    private List<InsEstudiantes> findInsEstudiantesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InsEstudiantes.class));
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

    public InsEstudiantes findInsEstudiantes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InsEstudiantes.class, id);
        } finally {
            em.close();
        }
    }

    public int getInsEstudiantesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InsEstudiantes> rt = cq.from(InsEstudiantes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
