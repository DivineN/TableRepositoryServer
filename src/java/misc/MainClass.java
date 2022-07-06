/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc;

import entities.*;
import utils.GenericDAO;

/**
 *
 * @author divine
 */
public class MainClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Table table0 = new Table(false);
       Reservation reservation0 = new Reservation("John Doe", "johndoe@gmail.com", table0);
        User user0 = new User("janedoe@gmail.com", "Jane Doe", "root");
        GenericDAO tdao = new GenericDAO(Table.class);
        GenericDAO rda0 = new GenericDAO(Reservation.class);
        GenericDAO udao = new GenericDAO(User.class);
        tdao.create(table0);
        //rda0.create(reservation0);
        udao.create(user0);
    }
    
}
