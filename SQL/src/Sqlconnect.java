import java.sql.*;
public class Sqlconnect {
    //获取一个链接对象
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/text";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    //查询所有数据
    public void get_table(Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        String sql = "select * from students";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            Double grade = rs.getDouble("grade");
            String major = rs.getString("major");
            String gender= rs.getString("gender");
            System.out.println("id:" + id + " name:" + name + " age:" + age + " grade:" + grade + " major:" + major + " gender:" + gender+"\n");
        }
    }
    //根据id查询用户
    public void get_column(Connection con ,int id0) throws SQLException {
        String sql = "select * from students where id=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id0);
        System.out.println("查询成功");
        Sql.show(stmt.executeQuery(), sql, stmt);
    }
    //根据id删除用户
    public void delete_column(Connection con,int id0) throws SQLException {
        String sql = "delete from students where id=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id0);
        stmt.executeUpdate();
        String sql1 = "select * from students where id=?";
        stmt = con.prepareStatement(sql1);
        stmt.setInt(1, id0);
        ResultSet rs = stmt.executeQuery();
        if(rs.next())
            System.out.println("删除失败");
        else
            System.out.println("删除成功");
    }
    //根据id更新用户成绩
    public void update_grade(Connection con,int id0,Double new_grade) throws SQLException {
        String sql = "update students set grade=? where id=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setDouble(1, new_grade);
        stmt.setInt(2, id0);
        stmt.executeUpdate();
        System.out.println("更新成功");
        get_column(con,id0);
    }
    ///更新用户专业
    public void update_major(Connection con,int id,String major) throws SQLException {
        String sql = "update students set major=? where id=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, major);
        stmt.setInt(2, id);
        stmt.executeUpdate();
        System.out.println("更新成功");
        get_column(con,id);
    }
    //增加一个
    public void insert_column(Connection con,int id,String name,int age,Double grade,String major,String gender) throws SQLException {
        String sql = "insert into students values(?,?,?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.setString(2, name);
        stmt.setInt(3, age);
        stmt.setDouble(4, grade);
        stmt.setString(5, major);
        stmt.setString(6, gender);
        stmt.executeUpdate();
        System.out.println("增加成功");
        get_column(con,id);
    }
}