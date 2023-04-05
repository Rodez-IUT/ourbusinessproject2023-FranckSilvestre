package ourbusinessproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller relative to Partnership
 */
@RestController
public class PartnershipController {

    private EnterpriseProjectService enterpriseProjectService;
    private PartnershipService partnershipService;

    /**
     * Create a PartnershipController
     * @param enterpriseProjectService the enterprise project  service
     * @param partnershipService the partnership service
     */
    public PartnershipController(@Autowired EnterpriseProjectService enterpriseProjectService,
                                 @Autowired PartnershipService partnershipService) {
        this.enterpriseProjectService = enterpriseProjectService;
        this.partnershipService = partnershipService;
    }

    /**
     * Add a new partnership
     * @param projectId the project id
     * @param enterpriseId the enterprise id
     */
    @PostMapping("/api/v1/partnerships")
    public Partnership addPartnership(@RequestParam("project_id") long projectId, @RequestParam("enterprise_id")long enterpriseId) {
        Project project = enterpriseProjectService.findProjectById(projectId);
        Enterprise enterprise = enterpriseProjectService.findEnterpriseById(enterpriseId);
        return partnershipService.save(new Partnership(project,enterprise));
    }

    /**
     * Remove a partnership
     * @param partnershipId the id of the partnership to remove
     */
    @DeleteMapping("/api/v1/partnerships/{partnership_id}")
    public void removePartnership(@PathVariable("partnership_id") long partnershipId) {
        Partnership partnership = partnershipService.findPartnershipById(partnershipId);
        partnershipService.remove(partnership);
    }

    /**
     * Search partnerships
     * @param projectTitle the project title of partnerships
     * @param enterpriseName the enterprise name of partnerships
     * @return the list of found partnerships
     */
    @RequestMapping("/api/v1/partnerships/search")
    public List<Partnership> search(@RequestParam(value = "project_title", required = false) String projectTitle,
                                    @RequestParam(value = "enterprise_name", required = false) String enterpriseName) {
        return partnershipService.search(projectTitle, enterpriseName);
    }

    /**
     * Search partnerships v2
     * @param projectTitle the project title of partnerships
     * @param enterpriseName the enterprise name of partnerships
     * @return the list of found partnerships
     */
    @RequestMapping(value = "/api/v2/partnerships/search", method = RequestMethod.GET)
    public Page<Partnership> searchPartnerships(@RequestParam(value = "project_title",required = false)String projectTitle,
                                                @RequestParam(value = "enterprise_name",required = false)String enterpriseName,
                                                Pageable pageable) {
        Example<Partnership> example = getPartnershipExample(projectTitle, enterpriseName);
        Page<Partnership> res = partnershipService.search(example, pageable);
        return res;
    }

    private Example<Partnership> getPartnershipExample(String projectTitle, String enterpriseName) {
        Partnership partnership = new Partnership();
        if (projectTitle != null) {
            Project project = new Project();
            project.setTitle(projectTitle);
            partnership.setProject(project);
        }
        if (enterpriseName != null) {
            Enterprise enterprise = new Enterprise();
            enterprise.setName(enterpriseName);
            partnership.setEnterprise(enterprise);
        }
        return Example.of(partnership);
    }
}
