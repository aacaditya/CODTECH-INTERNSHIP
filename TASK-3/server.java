import java.io.File;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.util.List;

public class RecommendationApp {

    public static void main(String[] args) {
        try {
            // Load sample data
            File dataFile = new File("data/preferences.csv");
            DataModel model = new FileDataModel(dataFile);

            // Calculate similarity between users
            UserSimilarity similarity =
                    new PearsonCorrelationSimilarity(model);

            // Define neighborhood
            UserNeighborhood neighborhood =
                    new NearestNUserNeighborhood(2, similarity, model);

            // Create recommender
            Recommender recommender =
                    new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Recommend items for User ID 1
            List<RecommendedItem> recommendations =
                    recommender.recommend(1, 2);

            System.out.println("===== Product Recommendations =====");
            System.out.println("User ID : 1");

            for (RecommendedItem item : recommendations) {
                System.out.println(
                        "Recommended Item ID: " + item.getItemID()
                                + " | Score: " + item.getValue()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
