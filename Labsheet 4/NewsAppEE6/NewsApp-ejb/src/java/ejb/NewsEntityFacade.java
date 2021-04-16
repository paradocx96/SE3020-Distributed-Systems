/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author nb
 */
@Stateless
public class NewsEntityFacade {
    @PersistenceContext(unitName = "NewsApp-ejbPU")
    private EntityManager em;

    // TODO: Uncomment method to test the postcontruct callback function    
    @PostConstruct
    public void someMethod(){
        System.out.println("I'm doing something after the NewsentityFacade is constructed");
    }

    public void create(NewsEntitySorted newsEntity) {
        em.persist(newsEntity);
    }

    public void edit(NewsEntitySorted newsEntity) {
        em.merge(newsEntity);
    }

    public void remove(NewsEntitySorted newsEntity) {
        em.remove(em.merge(newsEntity));
    }

    public NewsEntitySorted find(Object id) {
        return em.find(NewsEntitySorted.class, id);
    }

    public List<NewsEntitySorted> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        
        // TODO: Change the following line to read the NewsEntityFacade objects from the data source
        cq.select(cq.from(NewsEntitySorted.class));
        return em.createQuery(cq).getResultList();
    }
    
    // Added method to sort in the descending order
     public List<NewsEntitySorted> findAllSortedDesc(){
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(NewsEntitySorted.class));
        List news = em.createQuery(cq).getResultList();
        Collections.sort(news,new NewsEntitySorter());
        return news;
    }
  

    public List<NewsEntitySorted> findRange(int[] range) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(NewsEntitySorted.class));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<NewsEntitySorted> rt = cq.from(NewsEntitySorted.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
