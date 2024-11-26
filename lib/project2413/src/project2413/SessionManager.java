package project2413;

public class SessionManager {
    private static SessionManager instance;
    private String currentUser; // whichever user is logged in

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(String username) {
        this.currentUser = username;
    }

    public void logout() {
        this.currentUser = null;
    }

    public String getCurrentUser() {
        return currentUser;
    }
}
