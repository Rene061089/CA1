package rest;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HobbiesDTO;
import dtos.PersonDTO;
import dtos.PersonsDTO;
import facades.HobbyFacade;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("hobby")
public class HobbyResource {
    private final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private final HobbyFacade hobbyFacade =  HobbyFacade.getHobbyFacadeMethods(EMF);
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @Hidden
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @Operation(summary = "Hobby amount",
            tags = {"Amounts of hobbies"},
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = HobbiesDTO.class))),
                    @ApiResponse(responseCode = "200", description = "All hobbies "),
                    @ApiResponse(responseCode = "400", description = "Entity not found")})

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonCount() {

        int count = hobbyFacade.getAllHobbiesCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

    @Operation(summary = "All hobbies",
            tags = {"All hobbies"},
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = HobbiesDTO.class))),
                    @ApiResponse(responseCode = "200", description = "All hobbies "),
                    @ApiResponse(responseCode = "400", description = "Entity not found")})

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllHobbies(){
        HobbiesDTO hobbiesDTO = hobbyFacade.getAllHobbies();
        return Response.ok().entity(GSON.toJson(hobbiesDTO)).build();
    }


}
