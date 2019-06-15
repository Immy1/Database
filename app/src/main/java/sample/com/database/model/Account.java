package sample.com.database.model;

public class Account {

    public Account() {
    }

    public Account(String userId, String context) {
        this.userId = userId;
        this.context = context;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Account(int status, String userId, String context) {

        this.status = status;
        this.userId = userId;
        this.context = context;
    }

    int status;
    String userId;
    String context;
}
