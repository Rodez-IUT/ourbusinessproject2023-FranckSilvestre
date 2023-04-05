package ourbusinessproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ourbusinessproject.repositories.PartnerRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Service to manage PartnerShip entities
 */
@Service
@Transactional
public class PartnershipService {

    @Autowired
    private PartnerRepository partnerRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Save a partnership
     * @param partnership the partnership to save
     * @return the saved partnership
     */
    public Partnership save(Partnership partnership) {
        entityManager.persist(partnership);
        return partnership;
    }

    /**
     * Find partnership by id
     * @param id the id of the partnership
     * @return the found partnership,or null
     */
    public Partnership findPartnershipById(Long id) {
        return entityManager.find(Partnership.class,id);
    }

    /**
     * Remove a partnership
     * @param partnership the partnership to remove
     */
    public void remove(Partnership partnership) {
        entityManager.remove(partnership);
    }

    /**
     * Search engine or partnerships
     * @param projectTitle the project title of the searched partnerships
     * @param enterpriseName the enterprise name of the searched partnerships
     * @return the list of found partnership
     */
    public List<Partnership> search(String projectTitle,
                                    String enterpriseName) {
        String jpqlQuery = null;
        TypedQuery<Partnership> query = null;
        if (projectTitle != null && !projectTitle.isBlank()) {
            if (enterpriseName != null && !enterpriseName.isBlank()) {
                jpqlQuery = "select part from Partnership part where part.project.title like :projectTitle and part.enterprise.name like :enterpriseName order by part.project.title";
                query = entityManager.createQuery(jpqlQuery,Partnership.class);
                query.setParameter("projectTitle", projectTitle);
                query.setParameter("enterpriseName", enterpriseName);
            } else {
                jpqlQuery = "select part from Partnership part where part.project.title like :projectTitle order by part.project.title";
                query = entityManager.createQuery(jpqlQuery,Partnership.class);
                query.setParameter("projectTitle", projectTitle);
            }
        } else if (enterpriseName != null && !enterpriseName.isBlank()) {
            jpqlQuery = "select part from Partnership part where part.enterprise.name like :enterpriseName order by part.project.title";
            query = entityManager.createQuery(jpqlQuery, Partnership.class);
            query.setParameter("enterpriseName", enterpriseName);
        } else {
            jpqlQuery = "select part from Partnership part order by part.project.title";
            query = entityManager.createQuery(jpqlQuery,Partnership.class);
        }
        return query.getResultList();
    }

    /**
     * Search engine or partnerships
     * @param partnershipExample the Example of partnership used to perform the query
     * @param pageable the object descriping page and ordering
     * @return the list of found partnership
     */
    public Page<Partnership> search(Example<Partnership> partnershipExample, Pageable pageable) {
        return partnerRepository.findAll(partnershipExample,pageable);
    }
}


