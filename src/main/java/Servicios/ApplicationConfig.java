package Servicios;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author CAMILO
 */
@javax.ws.rs.ApplicationPath("servicios")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }
    
    /**
     * Agrega los servicios a los resources
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(Servicios.PruebaServicesJSON.class);
        resources.add(Servicios.serviciosWeb.class);
    }
    
}