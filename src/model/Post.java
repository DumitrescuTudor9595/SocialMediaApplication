package model;

import java.time.LocalDateTime;

public class Post {

    int id;
    int user_id;
    String message;
    LocalDateTime createdAt;

    public Post(int id, int user_id, String message, LocalDateTime createdAt) {
        this.id = id;
        this.user_id = user_id;
        this.message = message;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
