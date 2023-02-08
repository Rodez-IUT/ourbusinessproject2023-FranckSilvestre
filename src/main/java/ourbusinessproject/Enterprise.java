package ourbusinessproject;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;

@Entity
public class Enterprise {
    @NotEmpty
    private String name;
    @Size(min = 10)
    private String description;
    @NotEmpty
    private String contactName;
    @Email @NotEmpty
    private String contactEmail;
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "enterprise") @JsonIgnore
    private Collection<Project> projects;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public Long getId() {
        return id;
    }

    public Collection<Project> getProjects() {
        return projects;
    }

    /**
     * Add a project to the enterprise
     * @param project the project to add
     */
    public void addProject(Project project) {
        if (projects == null) {
            projects = new HashSet<>();
        }
        projects.add(project);
    }
}
