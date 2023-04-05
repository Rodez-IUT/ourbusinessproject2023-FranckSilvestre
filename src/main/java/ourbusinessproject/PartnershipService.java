package ourbusinessproject;

import org.springframework.stereotype.Service;

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
                                    String enterpriseName,
                                    PartnershipKeyOrder keyOrder,
                                    Order order) {
        String jpqlQuery = null;
        String orderByClause = keyOrder != null ? keyOrder.keyChain : "project.title";
        String orderDirection = order != null ? order.toString() : "ASC";
        orderByClause = orderByClause + " " + orderDirection;
        TypedQuery<Partnership> query = null;
        if (projectTitle != null && !projectTitle.isBlank()) {
            if (enterpriseName != null && !enterpriseName.isBlank()) {
                jpqlQuery = "select part from Partnership part where part.project.title like :projectTitle and part.enterprise.name like :enterpriseName order by part."+orderByClause;
                query = entityManager.createQuery(jpqlQuery,Partnership.class);
                query.setParameter("projectTitle", projectTitle);
                query.setParameter("enterpriseName", enterpriseName);
            } else {
                jpqlQuery = "select part from Partnership part where part.project.title like :projectTitle order by part."+orderByClause;
                query = entityManager.createQuery(jpqlQuery,Partnership.class);
                query.setParameter("projectTitle", projectTitle);
            }
        } else if (enterpriseName != null && !enterpriseName.isBlank()) {
            jpqlQuery = "select part from Partnership part where part.enterprise.name like :enterpriseName order by part."+orderByClause;
            query = entityManager.createQuery(jpqlQuery, Partnership.class);
            query.setParameter("enterpriseName", enterpriseName);
        } else {
            jpqlQuery = "select part from Partnership part order by part.project.title";
            query = entityManager.createQuery(jpqlQuery,Partnership.class);
        }
        return query.getResultList();
    }
}

enum PartnershipKeyOrder {
    PROJECT_TITLE("project.title"),
    ENTERPRISE_NAME ("enterprise.name");

    public String keyChain;

    PartnershipKeyOrder(String keyChain) {
        this.keyChain = keyChain;
    }
}

enum Order {
    ASC,
    DESC
}
