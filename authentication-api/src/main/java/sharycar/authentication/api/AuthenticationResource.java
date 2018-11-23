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
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;

@Path("/users")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

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

            User u = em.find(User.class, id);
            if (u == null)
                return Response.status(Response.Status.NOT_FOUND).build();
            return Response.ok(u).build();
    }

    /**
     *  Proof of concept - @by Jaka
     */
    @GET
    @Path("/{id}")
    public Response getUserJWT(@PathParam("id") Integer id) {

        // TODO Check password
        User u = em.find(User.class, id);

        String token;
        try {
            Algorithm algorithm = Algorithm.HMAC256("replaceWithSomeSecret");
            token = JWT.create()
                    .withIssuer("auth0")
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok(token).build();
    }


}
