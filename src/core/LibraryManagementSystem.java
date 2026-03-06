package core;

import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Central management system for all library branches.
 * Demonstrates Singleton Pattern to ensure single point of control.
 */
public class LibraryManagementSystem {
    private static final Logger logger = Logger.getLogger(LibraryManagementSystem.class.getName());

    private static LibraryManagementSystem instance;
    private Map<String, LibraryBranch> branches;

    /**
     * Private constructor for Singleton pattern
     */
    private LibraryManagementSystem() {
        this.branches = new HashMap<>();
        logger.log(Level.INFO, "Library Management System initialized");
    }

    /**
     * Get the singleton instance
     */
    public static synchronized LibraryManagementSystem getInstance() {
        if (instance == null) {
            instance = new LibraryManagementSystem();
        }
        return instance;
    }

    /**
     * Add a library branch
     */
    public void addBranch(LibraryBranch branch) {
        if (branches.containsKey(branch.getBranchId())) {
            logger.log(Level.WARNING, String.format(
                "Branch already exists: %s (%s)",
                branch.getBranchName(), branch.getBranchId()
            ));
            return;
        }

        branches.put(branch.getBranchId(), branch);
        logger.log(Level.INFO, String.format(
            "Branch added to system: %s (%s)",
            branch.getBranchName(), branch.getBranchId()
        ));
    }

    /**
     * Get a branch by ID
     */
    public LibraryBranch getBranch(String branchId) {
        return branches.get(branchId);
    }

    /**
     * Get all branches
     */
    public List<LibraryBranch> getAllBranches() {
        return new ArrayList<>(branches.values());
    }

    /**
     * Remove a branch
     */
    public boolean removeBranch(String branchId) {
        if (!branches.containsKey(branchId)) {
            logger.log(Level.WARNING, String.format(
                "Branch not found: %s", branchId
            ));
            return false;
        }

        LibraryBranch branch = branches.remove(branchId);
        logger.log(Level.INFO, String.format(
            "Branch removed from system: %s (%s)",
            branch.getBranchName(), branchId
        ));
        return true;
    }

    /**
     * Get branch count
     */
    public int getBranchCount() {
        return branches.size();
    }

    /**
     * Transfer a book between branches
     */
    public boolean transferBookBetweenBranches(String sourceBranchId, String targetBranchId, String isbn) {
        LibraryBranch sourceBranch = branches.get(sourceBranchId);
        LibraryBranch targetBranch = branches.get(targetBranchId);

        if (sourceBranch == null || targetBranch == null) {
            logger.log(Level.WARNING, "Invalid branch ID for book transfer");
            return false;
        }

        return sourceBranch.transferBook(isbn, targetBranch);
    }

    @Override
    public String toString() {
        return String.format("LibraryManagementSystem{Branches=%d}", branches.size());
    }
}

