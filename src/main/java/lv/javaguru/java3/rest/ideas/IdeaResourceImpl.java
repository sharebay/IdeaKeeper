package lv.javaguru.java3.rest.ideas;

import lv.javaguru.java3.core.commands.ideas.*;
import lv.javaguru.java3.core.dto.idea.IdeaDTO;
import lv.javaguru.java3.core.services.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Created by Anna on 22.11.2015.
 */

@Component
@Path("/ideas")
public class IdeaResourceImpl {

    private CommandExecutor commandExecutor;

    @Autowired
    public IdeaResourceImpl(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }


    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Path("/create/{userId}")
    public IdeaDTO create(@PathParam("userId") Long userId, IdeaDTO ideaDTO) {
        Long id = ideaDTO.getUserId();
        if(id==null){
            id = ideaDTO.getUserDTO().getUserId();
        }
        CreateIdeaCommand command = new CreateIdeaCommand(
                ideaDTO.getTitle(),
                ideaDTO.getDescription(),
                id
        );
        CreateIdeaResult result = commandExecutor.execute(command);
        return result.getIdea();
    }

    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Path("/get/{ideaId}")
    public IdeaDTO get(@PathParam("ideaId") Long ideaId) {
        GetIdeaCommand command = new GetIdeaCommand(ideaId);
        GetIdeaResult result = commandExecutor.execute(command);
        return result.getIdea();
    }

    @DELETE
    @Path("/delete/{ideaId}")
    public int delete(@PathParam("ideaId") Long ideaId) {
        DeleteIdeaCommand command = new DeleteIdeaCommand(ideaId);
        DeleteIdeaResult result = commandExecutor.execute(command);
        return result.getResult();
    }

    @PUT
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Path("/update/{ideaId}")
    public IdeaDTO updateDescription(@PathParam("ideaId") Long ideaId, IdeaDTO ideaDTO) {
        GetIdeaCommand command = new GetIdeaCommand(ideaId);
        GetIdeaResult result = commandExecutor.execute(command);
        IdeaDTO ideaDTOfromDB = result.getIdea();

        String newDescription = ideaDTO.getDescription();
        ideaDTOfromDB.setDescription(newDescription);

        UpdateIdeaCommand updateCommand = new UpdateIdeaCommand(
                ideaDTOfromDB.getIdeaId(),
                ideaDTOfromDB.getTitle(),
                ideaDTOfromDB.getDescription());

        UpdateIdeaResult updateResult = commandExecutor.execute(updateCommand);
        return updateResult.getIdea();
    }

}
