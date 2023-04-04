package ourbusinessproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * The service containing initialization methods
 */
@Service
public class InitializationService {
    private Project project1E1;
    private Project project1E2;
    private Project project2E1;
    private Enterprise enterprise1;
    private Enterprise enterprise2;

    @Autowired
    private EnterpriseProjectService enterpriseProjectService;
    @Autowired
    private PartnershipService partnershipService;
    private Partnership partnershipP1E1WithE2;
    private Partnership partnershipP1E2WithE1;
    private Partnership partnershipP2E1WithE2;

    /**
     * Initialization of the initial list of projects
     */
    @Transactional
    public void initProjects() {
        initEnterprise();
        project1E1 = new Project("p1E1","P1E1 desc",enterprise1);
        enterpriseProjectService.save(project1E1);
        project1E2 = new Project("p1E2","P1E2 desc",enterprise2);
        enterpriseProjectService.save(project1E2);
        project2E1 = new Project("p2E1","P2E1 desc",enterprise1);
        //project2E1 = new Project("","P2E1 desc",enterprise1);
        enterpriseProjectService.save(project2E1);
    }

    private void initEnterprise() {
        // enterprise 1
        enterprise1 = new Enterprise();
        enterprise1.setName("MyComp1");
        enterprise1.setDescription("My comp1 description");
        enterprise1.setContactEmail("comp1@com.com");
        enterprise1.setContactName("comp1 contact name");
        // enterprise 2
        enterprise2 = new Enterprise();
        enterprise2.setName("MyComp2");
        enterprise2.setDescription("My comp2 description");
        enterprise2.setContactEmail("comp2@com.com");
        enterprise2.setContactName("comp2 contact name");
    }

    @Transactional
    public void initPartnerships() {
        partnershipP1E1WithE2 = partnershipService.save(new Partnership(project1E1, enterprise2));
        partnershipP1E2WithE1 = partnershipService.save(new Partnership(project1E2, enterprise1));
        partnershipP2E1WithE2 = partnershipService.save(new Partnership(project2E1, enterprise2));
    }

    /**
     * @return the Project 1 attached to Enterprise 1
     */
    public Project getProject1E1() {
        return project1E1;
    }

    /**
     * @return the Project 1 attached to Enterprise 2
     */
    public Project getProject1E2() {
        return project1E2;
    }

    /**
     * @return the Project 2 attached to Enterprise 1
     */
    public Project getProject2E1() {
        return project2E1;
    }

    /**
     * @return the Enterprise 1
     */
    public Enterprise getEnterprise1() {
        return enterprise1;
    }

    /**
     * @return the Enterprise 2
     */
    public Enterprise getEnterprise2() {
        return enterprise2;
    }


    public Partnership getPartnershipP1E1WithE2() {
        return partnershipP1E1WithE2;
    }

    public Partnership getPartnershipP1E2WithE1() {
        return partnershipP1E2WithE1;
    }

    public Partnership getPartnershipP2E1WithE2() {
        return partnershipP2E1WithE2;
    }

}
