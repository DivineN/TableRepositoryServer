/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import entities.Table;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import utils.GenericDAO;

/**
 *
 * @author Aubin
 */
@Path("table")
public class TableResources {
    GenericDAO tdao = new GenericDAO(Table.class);
    @GET
    public List<entities.Table> getAllTables(){
        List<entities.Table> tables = tdao.retrieveAll();
        return tables;
    }
    
    @GET
    @Path("reserved")
    public List<entities.Table> getAvailableTables(){
        List<entities.Table> availableTables = tdao.retrieveAvailableTables();
        return availableTables;
    }
    
    @POST
    public void createTable(entities.Table table){
        tdao.create(table);
    }
    
    @GET
    @Path("{id}")
    public entities.Table getTable(@PathParam("id") String id){
        int tableNo = Integer.parseInt(id);
        entities.Table table = (entities.Table) tdao.findByTableNo(tableNo);
        if(table != null){
            return table;
        }
        return null;
    }
}
