package curatetechnologies.com.curate.network.model;

/**
 * Created by mremondi on 2/13/18.
 */

public class CurateRegisterUser {
    private CurateAPIUser user;
    private String token;

    public CurateRegisterUser(CurateAPIUser user, String token) {
        this.user = user;
        this.token = token;
    }

    public CurateAPIUser getUser() {
        return user;
    }

    public void setUser(CurateAPIUser user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
