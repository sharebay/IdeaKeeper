package lv.javaguru.java3.core.services.users;

/**
 * Created by Anna on 27.10.2015.
 */

import lv.javaguru.java3.core.database.AttemptDAO;
import lv.javaguru.java3.core.database.UserDAO;
import lv.javaguru.java3.core.domain.attempt.Attempt;
import lv.javaguru.java3.core.domain.user.AccessLevel;
import lv.javaguru.java3.core.domain.user.User;
import lv.javaguru.java3.core.services.attempts.AttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired private UserDAO userDAO;
    @Autowired private UserValidator userValidator;
    @Autowired private AttemptDAO attemptDAO;
    @Autowired private AttemptService attemptService;

    @Override
    public User update(Long userId,
                       String newLogin,
                       String newPassword,
                       String newName,
                       String newSurname,
                       String newEmail,
                       String newAccessLevel) {
        userValidator.validate(newLogin, newPassword, newName, newSurname, newEmail, newAccessLevel);
        User user = get(userId);
        user.setLogin(newLogin);
        user.setPassword(newPassword);
        user.setName(newName);
        user.setSurname(newSurname);
        user.setEmail(newEmail);
        user.setAccessLevel(newAccessLevel);
        userDAO.update(user);
        return user;
    }

    @Override
    public User get(Long userId) {
        return userDAO.getRequired(userId);
    }

    @Override
    public User get(String login) {
        return userDAO.getUserByLogin(login);
    }

    @Override
    public int delete(Long userId) {
        User user = get(userId);
        if(user != null){
            userDAO.delete(user);
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public User blockUser(User user) {
        return update(user.getUserId(), user.getLogin(), user.getPassword(), user.getName(), user.getSurname(), user.getEmail(), AccessLevel.BLOCKED.name());
    }

    @Override
    public User unblockUser(User user) {
        if(attemptService.get(user.getLogin())!=null){
            Attempt userLoginAttempts = attemptService.get(user.getLogin());
            attemptService.resetBySuccessfulLogin(userLoginAttempts);
        }
        return update(user.getUserId(), user.getLogin(), user.getPassword(), user.getName(), user.getSurname(), user.getEmail(), AccessLevel.USER.name());
    }

    @Override
    public User setStatusVIP(User user) {
        return update(user.getUserId(), user.getLogin(), user.getPassword(), user.getName(), user.getSurname(), user.getEmail(), AccessLevel.VIP.name());
    }

    @Override
    public List<User> getFirstFive() {
        List<User> firstFive = new ArrayList<User>();
        if(userDAO.getFirstFive() != null){
            firstFive = userDAO.getFirstFive();
        }
        return firstFive;
    }



}

