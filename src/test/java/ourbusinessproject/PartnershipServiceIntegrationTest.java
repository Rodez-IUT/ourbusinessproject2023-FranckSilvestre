package ourbusinessproject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class PartnershipServiceIntegrationTest {

    @Autowired private EnterpriseProjectService enterpriseProjectService;
    @Autowired private PartnershipService partnershipService;

    private Enterprise partnerEnterprise;
    private Project project;

    @Before
    public void setUp() throws Exception {
        // given a project with its enterprise
        Enterprise projectEnterprise = new Enterprise();
        projectEnterprise.setName("The Firm");
        projectEnterprise.setDescription("The firm description");
        projectEnterprise.setContactEmail("contact@thefirm.com");
        projectEnterprise.setContactName("Contact @ the firm");

        project = new Project();
        project.setEnterprise(projectEnterprise);
        project.setTitle("The project");
        project.setDescription("The project description");

        enterpriseProjectService.save(project);

        // given the partner enterprise
        partnerEnterprise = new Enterprise();
        partnerEnterprise.setName("The partner");
        partnerEnterprise.setDescription("The partner description");
        partnerEnterprise.setContactName("Contact @ partner");
        partnerEnterprise.setContactEmail("contact@thepartner.com");

        enterpriseProjectService.save(partnerEnterprise);

    }

    @Test
    public void testSavePartnership() {
        // given a partnership
        Partnership partnership = new Partnership();
        partnership.setEnterprise(partnerEnterprise);
        partnership.setProject(project);

        // when saving the partnership
        Partnership resPartnership = partnershipService.save(partnership);

        // the return partnership is the saved partnership
        assertThat(resPartnership, is(partnership));

        // and the returned partnership is stored in the database with an id
        assertThat(resPartnership.getId(), notNullValue());

        // when fetching the corresponding partnership
        Partnership fetchedPartnership = partnershipService.findPartnershipById(partnership.getId());

        // then the properties of the fetched partnership are set as expected
        assertThat(fetchedPartnership.getEnterprise().getId(), is(partnerEnterprise.getId()));
        assertThat(fetchedPartnership.getProject().getId(), is(project.getId()));
        assertThat(fetchedPartnership.getCreationDate(), notNullValue());

    }

    @Test
    public void testRemovePartnership() {
        // given a saved partnership
        Partnership partnership = new Partnership();
        partnership.setEnterprise(partnerEnterprise);
        partnership.setProject(project);
        partnershipService.save(partnership);

        // when removing the partnership
        partnershipService.remove(partnership);

        // then the partnership is no more in the database
        assertThat(partnershipService.findPartnershipById(partnership.getId()),nullValue());

    }

}