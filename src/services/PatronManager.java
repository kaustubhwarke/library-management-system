package services;

import models.Patron;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Manages library patrons.
 * Demonstrates Single Responsibility Principle.
 */
public class PatronManager {
    private static final Logger logger = Logger.getLogger(PatronManager.class.getName());

    private Map<String, Patron> patrons; // PatronId -> Patron

    public PatronManager() {
        this.patrons = new HashMap<>();
    }

    /**
     * Add a new patron
     */
    public void addPatron(Patron patron) {
        if (patrons.containsKey(patron.getPatronId())) {
            logger.log(Level.WARNING, String.format(
                "Patron already exists: %s (ID: %s)",
                patron.getName(), patron.getPatronId()
            ));
            return;
        }

        patrons.put(patron.getPatronId(), patron);
        logger.log(Level.INFO, String.format(
            "Patron registered: %s (ID: %s)",
            patron.getName(), patron.getPatronId()
        ));
    }

    /**
     * Update patron information
     */
    public boolean updatePatron(Patron updatedPatron) {
        String patronId = updatedPatron.getPatronId();
        if (!patrons.containsKey(patronId)) {
            logger.log(Level.WARNING, String.format(
                "Patron not found for update: ID %s", patronId
            ));
            return false;
        }

        patrons.put(patronId, updatedPatron);
        logger.log(Level.INFO, String.format(
            "Patron updated: %s (ID: %s)",
            updatedPatron.getName(), patronId
        ));
        return true;
    }

    /**
     * Get a patron by ID
     */
    public Patron getPatron(String patronId) {
        return patrons.get(patronId);
    }

    /**
     * Get all patrons
     */
    public Collection<Patron> getAllPatrons() {
        return new ArrayList<>(patrons.values());
    }

    /**
     * Remove a patron
     */
    public boolean removePatron(String patronId) {
        if (!patrons.containsKey(patronId)) {
            logger.log(Level.WARNING, String.format(
                "Patron not found for removal: ID %s", patronId
            ));
            return false;
        }

        Patron patron = patrons.remove(patronId);
        logger.log(Level.INFO, String.format(
            "Patron removed: %s (ID: %s)",
            patron.getName(), patronId
        ));
        return true;
    }

    /**
     * Check if patron exists
     */
    public boolean patronExists(String patronId) {
        return patrons.containsKey(patronId);
    }

    /**
     * Get patron count
     */
    public int getPatronCount() {
        return patrons.size();
    }
}

