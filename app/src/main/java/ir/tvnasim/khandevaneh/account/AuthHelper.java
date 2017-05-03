package ir.tvnasim.khandevaneh.account;

import com.auth0.android.jwt.JWT;

/**
 * Created by hamidreza on 4/14/17.
 */

public class AuthHelper {

    public static boolean isTokenValid(String token) {
        JWT jwt = new JWT(token);
        return !(jwt.isExpired(10));
    }
}
