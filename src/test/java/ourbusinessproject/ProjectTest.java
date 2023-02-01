package ourbusinessproject;

import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;

import static org.junit.Assert.*;

public class ProjectTest {

    private Validator validator;
    private Project project;


    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        // given : a valid project
        project = new Project();
        project.setTitle("A project");
        project.setDescription("Project description");

    }

    @Test
    public void testProjectValidation() {

        // given : a project with a non empty title and a non empty description

        // expect : project is valid
        assertTrue("expected no constraints violation", validator.validate(project).isEmpty());

        // when: the project has an empty description
        project.setDescription("");

        // then: the project is still valid
        assertTrue("expected no constraints violation", validator.validate(project).isEmpty());

        // when: the project has no description
        project.setDescription(null);

        // then: the project is still valid
        assertTrue("expected no constraints violation", validator.validate(project).isEmpty());

    }

    @Test
    public void testProjectInvalidation() {

        // given : a project with a non empty title and a non empty description

        // when: the project has an empty title
        project.setTitle("");

        // then: the project is no more valid
        assertFalse("expected one constraint violation", validator.validate(project).isEmpty());

        // when: the project has no title
        project.setTitle(null);

        // then: the project is no more valid
        assertFalse("expected one constraint violation", validator.validate(project).isEmpty());

    }

}