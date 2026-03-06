package services;

import models.Book;
import models.Patron;
import strategies.RecommendationStrategy;
import strategies.GenreBasedRecommendationStrategy;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Generates book recommendations for patrons.
 * Demonstrates Strategy Pattern and Single Responsibility Principle.
 */
public class RecommendationEngine {
    private static final Logger logger = Logger.getLogger(RecommendationEngine.class.getName());

    private RecommendationStrategy strategy;

    /**
     * Constructor with default strategy
     */
    public RecommendationEngine() {
        this.strategy = new GenreBasedRecommendationStrategy();
    }

    /**
     * Constructor with custom strategy
     */
    public RecommendationEngine(RecommendationStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Set the recommendation strategy
     */
    public void setStrategy(RecommendationStrategy strategy) {
        this.strategy = strategy;
        logger.log(Level.INFO, String.format(
            "Recommendation strategy changed to: %s",
            strategy.getClass().getSimpleName()
        ));
    }

    /**
     * Get book recommendations for a patron
     */
    public List<Book> getRecommendations(Patron patron, List<Book> availableBooks) {
        logger.log(Level.INFO, String.format(
            "Generating recommendations for patron: %s using %s",
            patron.getName(), strategy.getClass().getSimpleName()
        ));

        List<Book> recommendations = strategy.recommend(patron, availableBooks);

        logger.log(Level.INFO, String.format(
            "Generated %d recommendations for patron: %s",
            recommendations.size(), patron.getName()
        ));

        return recommendations;
    }
}

