/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import entities.User;
import java.util.List;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import utils.GenericDAO;

/**
 *
 * @author divine
 */
@Path("user")
public class UserResources {
    GenericDAO udao = new GenericDAO(User.class);
    @POST
    public User authenticate(User user){
        String email = user.getEmail();
        System.out.println(email);
        String password = user.getPassword();
        User userFound = (User) udao.findByEmail(email);
        if(userFound == null){
            return null;
        }
        System.out.println(email);
        if(userFound.getPassword().equals(password)){
            return userFound;
        }
        return null;
    }
}
