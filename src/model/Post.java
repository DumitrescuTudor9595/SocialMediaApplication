package model;

import java.time.LocalDateTime;

public class Post {

    int id;
    int user_id;
    String mesaj;
    LocalDateTime createdAt;

    public Post(int id, int user_id, String mesaj, LocalDateTime createdAt) {
        this.id = id;
        this.user_id = user_id;
        this.mesaj = mesaj;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "\nPost{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", mesaj='" + mesaj + '\'' +
                ", createdAt=" + createdAt +
                "}";
    }
}
