package lv.javaguru.java3.core.services.ideas;

import lv.javaguru.java3.core.domain.idea.Idea;

/**
 * Created by Anna on 28.10.2015.
 */
public interface IdeaService {

    Idea update(Long ideaId,
                String title,
                String description,
                Long userId);

    Idea get(Long ideaId);

    //List<Idea> getUserIdeas(Long userId);

}
