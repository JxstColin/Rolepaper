package eu.jxstcolin.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import eu.jxstcolin.main.Rolepaper;
import lombok.Getter;
import org.bson.Document;

public class MongoDB {

    @Getter private static final String DATABASE_NAME = "rolepaper";
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    public MongoDB() {
        if (isConnected()) {
            Rolepaper.getInstance().getLogger().warning("MONGODB | Already connected!");
            return;
        }

        try {
            Rolepaper.getInstance().getLogger().info("MONGODB | Connecting...");
            mongoClient = MongoClients.create("mongodb://localhost:27017/");
            database = mongoClient.getDatabase(DATABASE_NAME);
            Rolepaper.getInstance().getLogger().info("MONGODB | Successfully connected.");
        } catch (Exception e) {
            Rolepaper.getInstance().getLogger().severe("MONGODB | Connection failed: " + e.getMessage());
        }
    }

    public static void disconnect() {
        if (!isConnected()) {
            Rolepaper.getInstance().getLogger().warning("MONGODB | No active connection to close.");
            return;
        }

        Rolepaper.getInstance().getLogger().info("MONGODB | Disconnecting...");
        try {
            mongoClient.close();
            mongoClient = null;
            database = null;
            Rolepaper.getInstance().getLogger().info("MONGODB | Successfully disconnected.");
        } catch (Exception e) {
            Rolepaper.getInstance().getLogger().severe("MONGODB | Disconnection failed: " + e.getMessage());
        }
    }

    public static MongoCollection<Document> getCollection(String collectionName) {
        if (!isConnected()) {
            throw new IllegalStateException("MongoDB is not connected. Call connect() first.");
        }
        return database.getCollection(collectionName);
    }

    public static boolean isConnected() {
        return mongoClient != null && database != null;
    }
}