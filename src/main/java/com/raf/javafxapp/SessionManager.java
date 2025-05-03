package com.raf.javafxapp;

/**
 * Singleton class to manage user session data across the application.
 * Currently stores only the ID of the logged-in kandidat/therapist.
 */
public class SessionManager {
    
    private static SessionManager instance;
    
    // Session data
    private int loggedInKandidatId;
    private boolean isTherapistCertified;
    
    // Private constructor to prevent instantiation
    private SessionManager() {
        loggedInKandidatId = 0; // 0 means no one is logged in
        isTherapistCertified = false;
    }
    
    /**
     * Gets the single instance of the SessionManager
     * @return The SessionManager instance
     */
    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    /**
     * Sets the ID of the logged-in kandidat
     * @param kandidatId The ID of the kandidat who logged in
     */
    public void setLoggedInKandidatId(int kandidatId) {
        this.loggedInKandidatId = kandidatId;
    }
    
    /**
     * Sets the certification status of the logged-in therapist
     * @param isCertified Whether the therapist is certified
     */
    public void setTherapistCertified(boolean isCertified) {
        this.isTherapistCertified = isCertified;
    }
    
    /**
     * Gets the ID of the logged-in kandidat
     * @return The kandidat ID, or 0 if no one is logged in
     */
    public int getLoggedInKandidatId() {
        return loggedInKandidatId;
    }
    
    /**
     * Gets the certification status of the logged-in therapist
     * @return true if therapist is certified, false otherwise
     */
    public boolean isTherapistCertified() {
        return isTherapistCertified;
    }
    
    /**
     * Checks if a user is currently logged in
     * @return true if someone is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return loggedInKandidatId > 0;
    }
    
    /**
     * Clears the session data (logs out the user)
     */
    public void clearSession() {
        loggedInKandidatId = 0;
        isTherapistCertified = false;
    }
} 