package lv.javaguru.java3.core.services.attempts;

import lv.javaguru.java3.core.domain.attempt.Attempt;

import java.util.Date;

/**
 * Created by Anna on 02.11.2015.
 */

@Deprecated
public interface AttemptFactory {

    Attempt create(Long userId, String login, int attempts,	Date lastModified);

}
