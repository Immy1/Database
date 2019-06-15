package sample.com.database.model;

public class ResultModel {
    String stagingId;
    String context;
    String status;
    String userId;
    String contactId;

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    /* public ResultModel(String stagingId, String context, String status, String userId) {
            this.stagingId = stagingId;
            this.context = context;
            this.status = status;
            this.userId = userId;
        }
    */
    public String getStagingId() {
        return stagingId;
    }

    public void setStagingId(String stagingId) {
        this.stagingId = stagingId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
