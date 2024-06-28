import java.sql.*;
public class Sql {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/text";
        String user = "root";
        String password = "";
        Connection con = DriverManager.getConnection(url,user,password);
        int id0=20221219;
        String sql = "select * from students where id=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1,id0);
        show(stmt.executeQuery(), sql, stmt);
    }

    static void show(ResultSet resultSet, String sql, PreparedStatement pstmt) throws SQLException {
        ResultSet rs = resultSet;
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            Double grade = rs.getDouble("grade");
            String major = rs.getString("major");
            String gender= rs.getString("gender");
            System.out.println("id:" + id + " name:" + name + " age:" + age + " grade:" + grade + " major:" + major + " gender:" + gender);
        }
    }
}


