package ourbusinessproject;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class EnterpriseProjectService {

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Save a new project
     *
     * @param project the project to save
     */
    public void save(Project project) {
        Enterprise enterprise = project.getEnterprise();
        if (enterprise != null) {
            enterprise.addProject(project);
            save(enterprise);
        }
        entityManager.persist(project);
        entityManager.flush();
    }

    /**
     * Save a new enterprise
     *
     * @param enterprise the enterprise to save
     */
    public void save(Enterprise enterprise) {
        entityManager.persist(enterprise);
        entityManager.flush();
    }

    /**
     * Find a project by id
     *
     * @param anId the id of the targeted project
     * @return the found project
     */
    public Project findProjectById(Long anId) {
        return entityManager.find(Project.class, anId);
    }

    /**
     * Find an enterprise by id
     *
     * @param anId the id of the targeted enterprise
     * @return the found enterprise
     */
    public Enterprise findEnterpriseById(Long anId) {
        return entityManager.find(Enterprise.class, anId);
    }

    /**
     * Find all projects
     *
     * @return the list of projects
     */
    public List<Project> findAllProjects() {
        String query = "select p from Project p join fetch p.enterprise order by p.title";
        TypedQuery<Project> typedQuery = entityManager.createQuery(query, Project.class);
        return typedQuery.getResultList();
    }
}
