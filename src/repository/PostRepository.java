package repository;

import model.Post;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PostRepository {


    public ArrayList<Post> readAll() {
        ArrayList<Post> posts = new ArrayList<>();
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM post_table");
            while (rs.next()) {
                posts.add(extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return posts;
    }

    public ArrayList<Post> readUserPosts(int userId) {
        ArrayList<Post> userPosts = new ArrayList<>();
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM post_table WHERE user_id = " + userId);
            while (rs.next()) {
                userPosts.add(extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userPosts;
    }




    public int create(int userId, String message, LocalDateTime present) {
        int affectedRows=0;
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
            String template = "INSERT INTO post_table (user_id,message,p_year,p_month,p_day,p_hour,p_minute)" +
                    "VALUES (%d,'%s',%d,%d,%d,%d,%d)";
            affectedRows= st.executeUpdate(String.format(template,userId,message,present.getYear(),
                    present.getMonthValue(),present.getDayOfMonth(),present.getHour(),present.getMinute()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return affectedRows;
    }

    public Post readById(int postId) {
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM post_table WHERE id=" + postId);
            rs.next();
            return extractFromResultSet(rs);
        } catch (SQLException e) {
           return null;
        }
    }


    private Post extractFromResultSet(ResultSet rs) {
        try {
            return new Post(rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("message"),
                    LocalDateTime.of(rs.getInt("p_year"),
                            rs.getInt("p_month"),
                            rs.getInt("p_day"),
                            rs.getInt("p_hour"),
                            rs.getInt("p_minute")));
        } catch (SQLException e) {
            return null;
        }
    }

    public int update(int postId, String message) {
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            String template = "UPDATE post_table SET message= '%s' WHERE id = %d";
            return st.executeUpdate(String.format(template,message,postId));
        } catch (SQLException e) {
           return 0;
        }
    }

    public int delete(int postId) {
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            return st.executeUpdate("DELETE FROM post_table WHERE id =" + postId);
        } catch (SQLException e) {
            return 0;
        }
    }
}
