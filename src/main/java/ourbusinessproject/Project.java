package ourbusinessproject;

import javax.validation.constraints.NotEmpty;

/**
 * Class representing a project
 */
public class Project {

    @NotEmpty
    private String title;
    private String description;

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
}
