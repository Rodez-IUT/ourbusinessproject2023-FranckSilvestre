package ourbusinessproject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.List;

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
    @Autowired private InitializationService initializationService;

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
        Partnership partnership = new Partnership(project,partnerEnterprise);

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
        Partnership partnership = new Partnership(project,partnerEnterprise);
        partnershipService.save(partnership);

        // when removing the partnership
        partnershipService.remove(partnership);

        // then the partnership is no more in the database
        assertThat(partnershipService.findPartnershipById(partnership.getId()),nullValue());

    }

    @Test
    public void testPartnershipInitialization() {

        // expect 3 partnerships
        assertThat(initializationService.getPartnershipP1E1WithE2(), notNullValue());
        assertThat(initializationService.getPartnershipP1E2WithE1(), notNullValue());
        assertThat(initializationService.getPartnershipP2E1WithE2(), notNullValue());

        // expect partnership between project1 Enterprise 1 and enterprise 2
        assertThat(initializationService.getPartnershipP1E1WithE2().getProject().getId(),
                is(initializationService.getProject1E1().getId()));
        assertThat(initializationService.getPartnershipP1E1WithE2().getEnterprise().getId(),
                is(initializationService.getEnterprise2().getId()));

        // expect partnership between project1 Enterprise 2 and enterprise 1
        assertThat(initializationService.getPartnershipP1E2WithE1().getProject().getId(),
                is(initializationService.getProject1E2().getId()));
        assertThat(initializationService.getPartnershipP1E2WithE1().getEnterprise().getId(),
                is(initializationService.getEnterprise1().getId()));

        // expect partnership between project2 Enterprise 1 and enterprise 2
        assertThat(initializationService.getPartnershipP2E1WithE2().getProject().getId(),
                is(initializationService.getProject2E1().getId()));
        assertThat(initializationService.getPartnershipP2E1WithE2().getEnterprise().getId(),
                is(initializationService.getEnterprise2().getId()));

    }

    @Test public void testSearchEngine() {
        // When searching without parameters
        List<Partnership> res = partnershipService.search((String) null, null);
        // then we get the 3 partnership
        assertEquals(3, res.size());
        // when searching with project title
        res = partnershipService.search("p1E1", null);
        // then get 1 partnership
        assertEquals(1, res.size());
        assertEquals(initializationService.getProject1E1().getId(), res.get(0).getProject().getId());
        // when searching with enterprise name
        res = partnershipService.search(null, "MyComp2");
        // then get 2 partnership
        assertEquals(2, res.size());
        assertEquals(initializationService.getProject1E1().getId(), res.get(0).getProject().getId());
        assertEquals(initializationService.getProject2E1().getId(), res.get(1).getProject().getId());
        assertEquals(initializationService.getEnterprise2().getId(), res.get(0).getEnterprise().getId());
        assertEquals(initializationService.getEnterprise2().getId(), res.get(1).getEnterprise().getId());
    }

    @Test public void testSearchEngineWithRepository() {
        // When searching without parameters
        Page<Partnership> res = partnershipService.search(Example.of(new Partnership()), Pageable.ofSize(3));
        // then we get the 3 partnership
        assertEquals(3, res.getTotalElements());
        // when searching with project title
        Partnership partnership = new Partnership();
        Project project = new Project();
        project.setTitle("p1E1");
        partnership.setProject(project);
        res = partnershipService.search(Example.of(partnership), Pageable.ofSize(3));
        // then get 1 partnership
        assertEquals(1, res.getTotalElements());
        assertEquals(initializationService.getProject1E1().getId(), res.stream().toList().get(0).getProject().getId());
        // when searching with enterprise name
        Enterprise enterprise = new Enterprise();
        enterprise.setName("MyComp2");
        partnership.setProject(null);
        partnership.setEnterprise(enterprise);
        res = partnershipService.search(Example.of(partnership), Pageable.ofSize(3));
        // then get 2 partnership
        assertEquals(2, res.getTotalElements());
        assertEquals(initializationService.getProject1E1().getId(), res.stream().toList().get(0).getProject().getId());
        assertEquals(initializationService.getProject2E1().getId(), res.stream().toList().get(1).getProject().getId());
        assertEquals(initializationService.getEnterprise2().getId(), res.stream().toList().get(0).getEnterprise().getId());
        assertEquals(initializationService.getEnterprise2().getId(), res.stream().toList().get(1).getEnterprise().getId());
    }

}