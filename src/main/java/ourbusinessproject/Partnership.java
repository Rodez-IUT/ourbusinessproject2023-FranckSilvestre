package ourbusinessproject;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class Partnership {

    @NotNull
    private Date creationDate;
    @NotNull
    private Enterprise enterprise;
    @NotNull
    private Project project;

    public Partnership(Long id) {

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
}
