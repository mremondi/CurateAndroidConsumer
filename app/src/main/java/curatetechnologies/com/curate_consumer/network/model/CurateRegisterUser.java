package curatetechnologies.com.curate_consumer.network.model;

/**
 * Created by mremondi on 2/13/18.
 */

public class CurateRegisterUser {
    private CurateSimpleAPIUser user;
    private String token;

    public CurateRegisterUser(CurateSimpleAPIUser user, String token) {
        this.user = user;
        this.token = token;
    }

    public CurateSimpleAPIUser getUser() {
        return user;
    }

    public void setUser(CurateSimpleAPIUser user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // TODO: this will change soon
    public class CurateSimpleAPIUser{
        private Integer id;
        private String Email;
        private String Password;

        public CurateSimpleAPIUser(Integer id, String Email, String Password) {
            this.id = id;
            this.Email = Email;
            this.Password = Password;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String Password) {
            this.Password = Password;
        }
    }
}
