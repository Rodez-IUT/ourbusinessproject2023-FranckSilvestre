package ourbusinessproject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Entity Partnership
 */
@Entity
public class Partnership {
    @NotNull
    private Date creationDate;

    @NotNull @ManyToOne
    private Enterprise enterprise;

    @NotNull @ManyToOne
    private Project project;

    @Id @GeneratedValue
    private Long id;

    public Partnership(Long id) {
        this.id = id;
    }

    public Partnership() {
    }

    public Partnership(Project project, Enterprise partnerEnterprise) {
        this.project = project;
        this.enterprise = partnerEnterprise;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public Long getId() {
        return id;
    }

    @PrePersist
    public void onCreate() {
        this.creationDate = new Date();
    }
}
