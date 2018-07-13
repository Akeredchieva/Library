package repositories;

import entities.User;

public interface UserRepository {

    User findUserByUsername(String username);

    void createUser(User user);

    boolean getGdpr(String username);

    boolean canUserLogIn(String username, String password);



}
