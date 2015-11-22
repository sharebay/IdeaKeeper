package lv.javaguru.java3.core.commands.ideas;

import lv.javaguru.java3.core.commands.DomainCommandResult;
import lv.javaguru.java3.core.dto.idea.IdeaDTO;

/**
 * Created by Anna on 28.10.2015.
 */
public class CreateIdeaResult implements DomainCommandResult {

    private IdeaDTO idea;

    public CreateIdeaResult(IdeaDTO idea) {
        this.idea = idea;
    }

    public IdeaDTO getIdea() {
        return idea;
    }

}
