package at.htl.quickstart.rest;

import at.htl.quickstart.model.Cinema;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("cinema")
public class CinemaEndpoint {

    @Inject
    EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response findAll(){
        List res = em.createNamedQuery("Cinema.findAll", Cinema.class).getResultList();
        return Response.ok(em.createNamedQuery("Cinema.findAll", Cinema.class).getResultList()).build();
    }
}
