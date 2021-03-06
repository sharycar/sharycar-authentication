/**
 *  Authentication API
 *
 *  This is API is used just for demo purpose and does not provide security.
 *  @TODO: Use cryptographic libraries for implementing secure logging and accessing resources
 *
 */

package sharycar.authentication.api;

import com.kumuluz.ee.logs.cdi.Log;
import jdk.nashorn.internal.runtime.JSONFunctions;
import sharycar.authentication.bussineslogic.AuthHelper;
import sharycar.authentication.bussineslogic.ProjectInfoClass;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.exceptions.JWTCreationException;

@Path("/users")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log
public class AuthenticationResource {

    @PersistenceContext
    private EntityManager em;

    /**
     * Get users
     *
     * @return
     */
    @GET
    public Response getUsers() {

        TypedQuery<User> query = em.createNamedQuery("User.findAll", User.class);

        List<User> users = query.getResultList();

        return Response.ok(users).build();
    }

    /**
     * Get user profile data
     * @param id
     * @return
     */
    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") Integer id) {

            User u = em.find(User.class, id);
            if (u == null)
                return Response.status(Response.Status.NOT_FOUND).build();
            return Response.ok(u).build();
    }


    @POST
    public Response registerUser(User user) {
        user.setId(null);
        user.setRegistration_date(new Date());
        if (user.getUsername() == null || user.getUsername().isEmpty()
                || user.getEmail() == null || user.getEmail().isEmpty()
        || user.getPassword() == null || user.getPassword().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            // Encrypt it
            user.setPassword(AuthHelper.cryptString(user.getPassword()));
            if (user.getPassword() == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(user).build();
            }
            // Create record in database
            try {
                em.getTransaction().begin();
                em.persist(user);
                em.getTransaction().commit();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(user).build();
            }
        }

        // User was not inserted
        if (user.getId() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid parameters").build();
        }

        return Response.status(Response.Status.CREATED).entity(user).build();

    }

    /**
     * Login user and return token
     * @param user
     * @return
     */
    @POST
    @Path("/login")
    public Response login(User user) {
        try {
            Query query = em.createQuery("SELECT u FROM User u WHERE u.password = :encryPsw and u.username =:userNm");
            query.setParameter("encryPsw", AuthHelper.cryptString(user.getPassword()));
            query.setParameter("userNm", user.getUsername());
            if (query.getResultList().size() > 0) {
                User u = (User) query.getResultList().get(0);
                String userToken = AuthHelper.cryptString(u.getPassword()+AuthHelper.getSalt());
                return Response.ok(JSONFunctions.quote(userToken)).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }


    }

    /**
     * Exposed project information
     *
     * @return
     */
    @GET
    @Path("/info")
    public Response getProjectInfo() {
        ProjectInfoClass pic = new ProjectInfoClass();
        return Response.ok(pic).build();
    }


}
