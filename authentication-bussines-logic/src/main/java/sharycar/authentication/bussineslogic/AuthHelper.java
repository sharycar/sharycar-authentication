package sharycar.authentication.bussineslogic;

import java.security.MessageDigest;

public class AuthHelper {

    /**
     * Encrypt user password Helper function
     * @param pass
     * @return
     */
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

    /**
     *  This does not provide any security, used just for demo.
     * @return
     */
    public static String getSalt() {
        return "zDcO7TXvpJt70XKwLkgUPgx5vY7I9t7z2ajh8LWK";
    }

}
