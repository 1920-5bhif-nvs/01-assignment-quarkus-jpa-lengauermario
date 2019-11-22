package at.htl.cinema.rest;

import at.htl.cinema.database.HallFacade;
import at.htl.cinema.model.Hall;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("hall")
public class HallEndpoint {

    @Inject
    HallFacade hallFacade;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Hall> cinemas = hallFacade.getAll();
        return Response.ok(cinemas).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getById(@PathParam("id") long id) {
        Hall c = hallFacade.getById(id);
        if(c != null){
            return Response.ok(c).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/cinema/{id}")
    public Response getByCinemaId(@PathParam("id") long id) {
        List<Hall> list = hallFacade.getByCinemaId(id);

        return Response.ok(list).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteHall(@PathParam("id")long id){
        Hall c = hallFacade.getById(id);
        if(c != null){
            hallFacade.delete(c);
        }
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addHall(Hall c) {
        try {
            c = hallFacade.insert(c);
            return Response.ok(c).build();
        }catch(PersistenceException e){
            return Response.status(400).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateHall(Hall c) {
        c = hallFacade.update(c);
        return Response.ok(c).build();
    }
}