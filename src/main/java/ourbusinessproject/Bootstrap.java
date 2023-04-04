package ourbusinessproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

/**
 * Class describing object responsible for the initialization of the application
 */
@Component
public class Bootstrap {

    private static Logger logger = Logger.getLogger(Bootstrap.class.getName());
    private InitializationService initializationService;

    /**
     * Create a Bootstrap
     * @param initializationService the initialization service used by the Bootstrap
     */
    public Bootstrap(@Autowired InitializationService initializationService) {
        this.initializationService = initializationService;
    }

    /**
     *  Init method triggered when the application is launched
     */
    @PostConstruct
    public void init() {
        try {
            initializationService.initProjects();
            initializationService.initPartnerships();
        } catch(Exception e) {
            logger.severe(e.getMessage());
        }
    }

    public InitializationService getInitializationService() {
        return this.initializationService;
    }
}
