package repository;

import model.Post;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PostRepository {
    public ArrayList<Post> readPostsFromUserWithId(int userId) {
        ArrayList<Post> postarileUserului = new ArrayList<>();
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM post WHERE user_id = " + userId);
            while (rs.next()) {
                postarileUserului.add(extractPostFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return postarileUserului;
    }

    private Post extractPostFromResultSet(ResultSet rs) {
        try {
            return new Post(rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("mesaj"),
                    LocalDateTime.of(rs.getInt("an"),
                            rs.getInt("luna"),
                            rs.getInt("zi"),
                            rs.getInt("ora"),
                            rs.getInt("minut")));
        } catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<Post> readAll() {
        ArrayList<Post> allPosts = new ArrayList<>();
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM post");
            while (rs.next()) {
                allPosts.add(extractPostFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allPosts;
    }

    public int create(int userId, String mesaj, LocalDateTime createdAt) {
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            String template = "INSERT INTO post (user_id, mesaj, an, luna, zi, ora, minut)" +
                    " VALUES (%d, '%s', %d, %d, %d, %d, %d)";
            return st.executeUpdate(String.format(template,
                    userId,
                    mesaj,
                    createdAt.getYear(),
                    createdAt.getMonthValue(),
                    createdAt.getDayOfMonth(),
                    createdAt.getHour(),
                    createdAt.getMinute()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Post readById(int postId) {
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM post WHERE id =" + postId);
            rs.next();
            return new Post(postId, rs.getInt("user_id"), rs.getString("mesaj"),
                    LocalDateTime.of(rs.getInt("an"),
                            rs.getInt("luna"),
                            rs.getInt("zi"),
                            rs.getInt("ora"),
                            rs.getInt("minut")));
        } catch (SQLException e) {
            return null;
        }

    }

    public int update(int postId, String mesajNou) {
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            String template = "UPDATE post SET mesaj = '%s' WHERE id = %d";
            return st.executeUpdate(String.format(template, mesajNou, postId));
        } catch (SQLException e) {
            return 0;
        }
    }

    public int delete(int postId) {
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            return st.executeUpdate("DELETE FROM post WHERE id=" + postId);
        } catch (SQLException e) {
            return 0;
        }
    }
}
