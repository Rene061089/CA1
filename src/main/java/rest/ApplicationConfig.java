package rest;

import io.swagger.v3.jaxrs2.integration.resources.AcceptHeaderOpenApiResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import java.util.Set;
import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(
                title = "CA1 API dokument",
                description = "API endpoints & dokumentation"),
        servers = {
                @Server(
                        description = "Droplet URL",
                        url = "https://dataelev.dk/CA1"
                ),
                @Server(
                        description = "Local URL",
                        url = "http://localhost:8080/CA1_war_exploded/"
                )

        }
)

@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
@Hidden
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        resources.add(OpenApiResource.class);
        resources.add(AcceptHeaderOpenApiResource.class);
        return resources;
    }
@Hidden
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(errorhandling.GenericExceptionMapper.class);
        resources.add(org.glassfish.jersey.server.wadl.internal.WadlResource.class);
        resources.add(PersonResource.class);
        resources.add(HobbyResource.class);
        resources.add(Filter.class);

    }
    
}
