package sharycar.authentication.api;

import sharycar.authentication.api.User;

import java.security.MessageDigest;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.exceptions.JWTCreationException;

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

    public static String cryptString(String pass){
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] passBytes = pass.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<digested.length;i++){
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (Exception ex) {
            return null;
        }

    }

    @POST
    public Response registerUser(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()
                || user.getEmail() == null || user.getEmail().isEmpty()
        || user.getPassword() == null || user.getPassword().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            // Encrypt it
            user.setPassword(cryptString(user.getPassword()));
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

        return Response.status(Response.Status.CREATED).entity(user).build();

    }

    @POST
    @Path("/login")
    public Response login(User user) {
        try {
            Query query = em.createQuery("SELECT u FROM User u WHERE u.password = :encryPsw and u.username =:userNm");
            query.setParameter("encryPsw", cryptString(user.getPassword()));
            query.setParameter("userNm", user.getUsername());
            if (query.getResultList().size() > 0) {
                return Response.ok(query.getResultList()).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }


        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }


    }


    /**
     *  Proof of concept - @by Jaka
     */
    @GET
    @Path("/info")
    public Response getProjectInfo() {
        ProjectInfoClass pic = new ProjectInfoClass();
        return Response.ok(pic).build();
    }
//    /**
//     *  Proof of concept - @by Jaka
//     */
//    @GET
//    @Path("/{id}")
//    public Response getUserJWT(@PathParam("id") Integer id) {
//
//        // TODO Check password
//        User u = em.find(User.class, id);
//
//        String token;
//        try {
//            Algorithm algorithm = Algorithm.HMAC256("replaceWithSomeSecret");
//            token = JWT.create()
//                    .withIssuer("auth0")
//                    .sign(algorithm);
//        } catch (JWTCreationException exception){
//            //Invalid Signing configuration / Couldn't convert Claims.
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//        }
//
//        return Response.ok(token).build();
//    }


}
