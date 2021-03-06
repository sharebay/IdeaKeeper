package lv.javaguru.java3.core.services.attempts;

/**
 * Created by Anna on 02.11.2015.
 */

import lv.javaguru.java3.core.domain.attempt.Attempt;

import java.util.Date;;



public interface AttemptService {

    Attempt update(Long attemptId, String login, int attempts,	Date lastModified);

    Attempt get(Long attemptId);

    Attempt get (String userLogin);

    Attempt updateFailAttempts(Attempt attempt);

    Attempt resetBySuccessfulLogin(Attempt attempt);

    Attempt resetByTime(Attempt attempt);
}
