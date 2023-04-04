package ourbusinessproject;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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
}
