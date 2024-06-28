import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Stu_manage {
    //获取数据库连接
    static Sqlconnect sqlconnect = new Sqlconnect();
    static Connection con;
    //定义一个输入接口对象
    static Scanner sc = new Scanner(System.in);
    static {
        try {
            con = sqlconnect.getConnection();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Stu_manage() {
    }
    //读取数据库中的数据，放入students表中去
    public static void get_data(Connection con, ArrayList<Students> stus) throws SQLException {
        Statement stmt = con.createStatement();
        String sql = "select * from students";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            Double grade = rs.getDouble("grade");
            String major = rs.getString("major");
            String gender = rs.getString("gender");
            Students stu = new Students(name, age, id, grade, major, gender);
            stus.add(stu);
        }
    }
    public static void main(String[] args) throws SQLException {
        ArrayList<Students> students = new ArrayList<>();
        Stu_manage.get_data(con, students);
        Stu_manage stu = new Stu_manage();
        System.out.println("=================================");
        System.out.println("==========学生信息管理系统==========");
        System.out.println("1.添加学生");
        System.out.println("2.删除学生");
        System.out.println("3.修改学生信息");
        System.out.println("4.获取学生人数");
        System.out.println("5.输出指定学生信息");
        System.out.println("6.输出所有学生信息");
        System.out.println("0.退出系统");
        System.out.println("=================================");
        System.out.println("输入数字获取相应的功能工具\n1-添加，2-删除，3-修改，4-人数，5-输出指定学生信息，6-输出所有学生信息");
        int cin = 1;
        while (cin <= 6 && cin >= 1) {
            cin = sc.nextInt();//查询功能
            switch (cin) {
                case 1: {
                    stu.insert_stu(students);
                    break;
                }
                case 2: {
                    stu.destroy_stu(students);
                    break;
                }
                case 3: {
                    stu.update_stu(students);
                    break;
                }
                case 4: {
                    int a = stu.get_number(students);
                    System.out.printf("学生人数为%d\n", a);
                    break;
                }
                case 5: {
                    stu.select_stu(students);
                    break;
                }
                case 6: {
                    stu.select_all();
                }
            }
        }
    }
    //添加学生
    public void insert_stu(ArrayList<Students> stu) throws SQLException {
        System.out.println("输入学生姓名");
        String name = sc.next();//姓名
        System.out.println("输入学生年龄");
        int age = sc.nextInt();//年龄
        System.out.println("输入学生学号");
        int id = sc.nextInt();//学号
        System.out.println("输入学生成绩");
        double grade = sc.nextDouble();//成绩
        System.out.println("输入学生专业");
        String major = sc.next();//专业
        System.out.println("输入学生性别");
        String gender = sc.next();//0男1女
        //加入数据库的同时加入list表中去
        sqlconnect.insert_column(con, id, name, age, grade, major, gender);
        Students stu1 = new Students(name, age, id, grade, major, gender);
        stu.add(stu1);
        //System.out.println("加入的学生信息");
        //stu1.show();
    }
    //删除学生
    public void destroy_stu(ArrayList<Students> stu) throws SQLException {
        System.out.println("请输入待删除学生的学号id");
        boolean flag = false;
        while (!flag) {
            int id = sc.nextInt();
            int index = 0;
            for (Students stu1 : stu) {
                if (stu1.id == id) {
                    System.out.println("请检查学生信息");
                    stu1.show();
                    boolean flag0;
                    System.out.println("确认无误输入true");
                    flag0 = sc.nextBoolean();
                    if (flag0) {
                        stu.remove(index);
                        //连接数据库
                        sqlconnect.delete_column(con, id);
                        System.out.println("删除成功");
                        System.out.println("请继续选择功能");
                        flag = true;
                        break;
                    }
                }
                index++;
            }
            if (index == stu.size() && !flag) {
                System.out.println("未找到该学生信息");
                System.out.println("请重新输入");
            }
        }
    }
    //修改学生信息
    public void update_stu(ArrayList<Students> stu) throws SQLException {
        boolean flag = false;
        System.out.println("请输入要修改的学生学号");
        while (!flag) {
            int id = sc.nextInt();
            Students stu0 = null;
            int index = 0;
            for (Students stu1 : stu) {
                if (stu1.id == id) {
                    stu0 = stu1;
                    break;
                }
                index++;
            }
            if (index == stu.size()) {
                System.out.println("不含该学生");
                System.out.println("请重新输入学号");
                System.out.println("或者结束修改功能，请输入0");
                int a = sc.nextInt();
                if (a == 0)
                    break;
                else
                    continue;
            }
            System.out.println("输入1修改学生成绩\n输入2修改学生专业\n输入3同时修改成绩和专业");
            int in = sc.nextInt();
            switch (in) {
                case 3: {
                    System.out.println("请输入要修改的成绩");
                    Double new_grade = sc.nextDouble();
                    System.out.println("请输入新的专业");
                    String new_major = sc.next();
                    if (stu0 != null) {
                        stu0.ch_grade(new_grade);
                    }
                    sqlconnect.update_grade(con, id, new_grade);
                    if (stu0 != null) {
                        stu0.ch_major(new_major);
                    }
                    sqlconnect.update_major(con, id, new_major);
                    flag = true;
                }
                case 2: {
                    System.out.println("请输入新的专业");
                    String new_major = sc.next();
                    if (stu0 != null) {
                        stu0.ch_major(new_major);
                    }
                    //数据库连接
                    sqlconnect.update_major(con, id, new_major);
                    flag = true;
                }
                case 1: {
                    System.out.println("请输入要修改的成绩");
                    double new_grade = sc.nextDouble();
                    if (stu0 != null) {
                        stu0.ch_grade(new_grade);
                    }
                    //数据库连接
                    sqlconnect.update_grade(con, id, new_grade);
                    flag = true;
                }
            }
            System.out.println("修改完成");
            if (stu0 != null) {
                stu0.show();
            }
        }
    }
    //获取学生个数
    public int get_number(ArrayList<Students> stu) {
        return stu.size();
    }
    //查询学生基本信息
    public void select_stu(ArrayList<Students> stu) {
        System.out.println("请输入要查询学生的id");
        int id = sc.nextInt();
        int index = 0;
        for (Students stu1 : stu) {
            if (stu1.id == id) {
                stu1.show();
                break;
            }
            index++;
        }
        if (index == stu.size()) {
            System.out.println("未查询到该学生");
        }
    }
    //查询所有学生信息
    public void select_all() throws SQLException {
        sqlconnect.get_table(con);
    }
}
