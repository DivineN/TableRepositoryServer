/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import entities.Reservation;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import utils.GenericDAO;


@Path("reservation")
public class ReservationResources {
    GenericDAO rdao = new GenericDAO(Reservation.class);
    GenericDAO tdao = new GenericDAO(entities.Table.class);
    @GET
    public List<Reservation> getAllReservations(){
       List<Reservation> reservations = rdao.retrieveAll();
       return reservations;
    }
    @POST
    public void createReservation(Reservation reservation){
        int tableNo = reservation.getTable().getTableNo();
        entities.Table table = (entities.Table) tdao.findByTableNo(tableNo);
        if (table != null) {
            table.setIsReserved(true);
            tdao.update(table);
        }
        rdao.create(reservation);
        String message = "Dear "+ reservation.getFullName() + ", thank you for reserving table " + reservation.getTable().getTableNo() + ".";
        sendMail(reservation.getEmail(),message,reservation.getFullName());
    }
    @DELETE
    @Path("{id}")
    public Reservation deleteReservation(@PathParam("id") String id){
        int ids = Integer.parseInt(id);
        System.out.println(id);
        Reservation reservation = (Reservation) rdao.findByReservationNo(ids);
        if(reservation != null){
            System.out.println(reservation.getReservationNo());
            entities.Table table = (entities.Table) tdao.findByTableNo(reservation.getTable().getTableNo());
            if(table != null){
                table.setIsReserved(false);
                tdao.update(table);
            }
            boolean b = rdao.delete(reservation);
            if(b){
                return reservation;
            }else{
                return null;
            }
        }else{
           return null; 
        }
    }
    
    public static void sendMail(String emails,String message,String name){

         Email email = new SimpleEmail();
        try {
            String authuser = "divinenira17@gmail.com";
            String authpwd = "Aa123456!";
            email.setSmtpPort(587);
            email.setAuthenticator(new DefaultAuthenticator(authuser, authpwd));
            email.setDebug(true);
            email.setHostName("smtp.gmail.com");
            email.getMailSession().getProperties().put("mail.smtps.auth", "true");
            email.getMailSession().getProperties().put("mail.debug", "true");
            email.getMailSession().getProperties().put("mail.smtps.port", "587");
            email.getMailSession().getProperties().put("mail.smtps.socketFactory.port", "587");
            email.getMailSession().getProperties().put("mail.smtps.socketFactory.class",   "javax.net.ssl.SSLSocketFactory");
            email.getMailSession().getProperties().put("mail.smtps.socketFactory.fallback", "false");
            email.getMailSession().getProperties().put("mail.smtp.starttls.enable", "true");
            email.setFrom("divinenira17@gmail.com", "Administrator");
            email.setSubject("Booking Mail");
            email.setMsg(message);
            email.addTo(emails, name);
            //email.setStartTLSRequired(false);
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
