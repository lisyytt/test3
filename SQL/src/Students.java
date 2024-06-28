import java.util.Scanner;
public class Students {
    //学生基本信息
    String name;//姓名
    int age;//年龄
    int id;//学号
    double grade;//成绩
    String major;//专业
    String gender;//0男1女
    //构造函数
    public Students(String name, int age, int id, double grade, String major, String gender) {
        this.name = name;
        this.age = age;
        this.id = id;
        this.grade = grade;
        this.major = major;
        this.gender = gender;
    }
    //修改成绩
    public void ch_grade(double new_grade) {
        this.grade = new_grade;
    }
    //修改专业
    public void ch_major(String new_major) {
        this.major = new_major;
    }
    public void show() {
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("ID: " + id);
            System.out.println("Grade: " + grade);
            System.out.println("Major: " + major);
            System.out.println("Gender: " + gender);
        }
}
