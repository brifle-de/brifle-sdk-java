package de.brifle.sdk;

import java.util.Date;

public class ConnectorSession {

    private String authToken;
    private Date renewDate;

    private String tenantId;

    public ConnectorSession(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * checks if the authToken is expired
     * @return true if the authToken is expired
     */
    public boolean requiresAuthToken() {
        return authToken == null || renewDate.before(new Date());
    }

    /**
     * sets the authToken and the renewDate
     * @param authToken the authToken
     * @param renewDate the renewDate
     */
    public void setAuthToken(String authToken, Date renewDate) {
        this.authToken = authToken;
        this.renewDate = renewDate;
    }


    /**
     *
     * @return the authToken
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     *
     * @return the tenantId
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     *
     * @param tenantId
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

}
