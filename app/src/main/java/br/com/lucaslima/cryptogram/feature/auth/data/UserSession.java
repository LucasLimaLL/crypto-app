package br.com.lucaslima.cryptogram.feature.auth.data;

public class UserSession {

    private static final String DEFAULT_USERNAME = "Jogador";

    private static UserSession instance;
    private String username = DEFAULT_USERNAME;

    private UserSession() {}

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void clear() {
        this.username = DEFAULT_USERNAME;
    }
}
