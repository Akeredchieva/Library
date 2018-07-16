package services;

import entities.User;
import repositories.UserRepository;
import repositories.UserRepositoryImpl;

public class AuthenticationService {

    private UserRepository userRepository = new UserRepositoryImpl();


    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createAccount(User user) {
        userRepository.createUser(user);
    }

    public boolean isAcceptGdpr(String username){
        if(!userRepository.getGdpr(username)) {
            throw new IllegalArgumentException("You must accept GDPR!");
        }
        return true;
    }


    //TODO: Razvii si logikata
//    String LogIn(String username, String password){
//        String token = "";
//       if(userRepository.canUserLogIn(username, password)){
//           //TODO: check if the exact user with exact password is presenting in the DB.
//       }
//        //TODO: after the user is login to be sent a token with which will be authorized that is login.
//       return token;
//    }

    //TODO: change the token to empty string.
//    void LogOut(){
//
//    }

}
