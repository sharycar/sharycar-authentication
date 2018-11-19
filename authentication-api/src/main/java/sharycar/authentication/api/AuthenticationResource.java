package sharycar.authentication.api;

import sharycar.authentication.api.User;


import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

    //@TODO: Potrebno je vključiti context manager - trenutno še težave z bazo oz. persistence konfiguracijo??
    //
      @PersistenceContext
    private EntityManager em;

    /**
     *  Proof of concept - @by Jaka
     */
    @GET
    public Response getUsers() {

        TypedQuery<User> query = em.createNamedQuery("User.findAll", User.class);

        List<User> users = query.getResultList();

        return Response.ok(users).build();
    }

    /**
     *  Proof of concept - @by Jaka
     */
    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") Integer id) {

        return Response.ok("Requested user"+String.valueOf(id)).build();

    }
}
