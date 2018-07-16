package repositories;

import entities.*;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository{

    List<User> users = new ArrayList<>();

    public UserRepositoryImpl(List<User> users) {
        this.users = users;
    }

    @Override
    public User findUserByUsername(String username) {

        for (User userDB : this.users) {
            String userDbName = userDB.getCredentials().getUsername();

            if ((username.equals(userDbName))){
                return userDB;
            }
        }
        throw new IllegalArgumentException("The user can not be found in the DB");
    }

    //TODO: opravi si metoda - ne e korektno da priema User.
    @Override
    public void createUser(User user) {

            try {
                this.findUserByUsername(user.getCredentials().getUsername());
            } catch(IllegalArgumentException noFoundUserEx) {
                this.users.add(user);
            }
    }

    @Override
    public boolean getGdpr(String username) {
        for (User userDB : this.users) {
            if (userDB.getCredentials().getUsername().equals(username)){
                return userDB.getGdpr();
            }

        }
        return false;
    }

    @Override
    public boolean canUserLogIn(String username, String password) {
        User user;
        try {
            user = this.findUserByUsername(username);
        } catch(IllegalArgumentException noFoundUserEx) {
            return false;
        }
        return user.getCredentials().getPassword().equals(password);
    }
}
