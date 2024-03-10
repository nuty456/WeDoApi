package cz.wedo.api.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The ArticlesTest class represents the unit tests for the Articles class.
 */
public class ArticlesTest {
  
    /**
     * Test for the add method in the Articles class.
     * It verifies if an article is successfully added into the list of articles.
     */
    @Test
    public void testAddMethod() {
        // Prepare
        Articles articles = new Articles(null);

        // Act
        articles.add("Article 1");
        
        // Assert
        Assertions.assertFalse(articles.getArticles().isEmpty(), "Article list should not be empty after adding an article");
        Assertions.assertEquals("Article 1", articles.getArticles().get(0), "The first article in the list should be the one we added");
    }

}