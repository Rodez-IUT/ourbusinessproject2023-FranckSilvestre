package ourbusinessproject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Class representing a project
 */
@Entity
public class Project {

    @NotEmpty
    private String title;
    private String description;
    @Id
    @GeneratedValue
    private Long id;

    @NotNull @ManyToOne
    private Enterprise enterprise;

    /**
     * Create a new project initialized with title, description et enterprise
     * @param title the title of the project
     * @param description the description of the project
     * @param enterprise the enterprise of the project
     */
    public Project(String title, String description, Enterprise enterprise) {
        this.title = title;
        this.description = description;
        this.enterprise = enterprise;
    }

    /**
     * JPA  required no argument constructor
     */
    public Project() {}

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }
}
