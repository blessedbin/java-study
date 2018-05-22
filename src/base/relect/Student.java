package base.relect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by xubin on 2018/5/22.
 *
 * @author 37075
 * @date 2018/5/22
 * @time 10:22
 * @tool intellij idea
 */
public class Student {

    public String name;

    private String sex;

    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    //---------------构造方法-------------------
    //（默认的构造方法）
    Student(String str){
        System.out.println("(默认)的构造方法 s = " + str);
    }

    //无参构造方法
    public Student(){
        System.out.println("调用了公有、无参构造方法执行了。。。");
    }

    //有一个参数的构造方法
    public Student(char name){
        System.out.println("姓名：" + name);
    }

    //有多个参数的构造方法
    public Student(String name ,int age){
        System.out.println("姓名："+name+"年龄："+ age);//这的执行效率有问题，以后解决。
    }

    //受保护的构造方法
    protected Student(boolean n){
        System.out.println("受保护的构造方法 n = " + n);
    }

    //私有构造方法
    private Student(int age){
        System.out.println("私有的构造方法   年龄："+ age);
    }

    //**************成员方法***************//
    public void show1(String s){
        System.out.println("调用了：公有的，String参数的show1(): s = " + s);
    }
    protected void show2(){
        System.out.println("调用了：受保护的，无参的show2()");
    }
    void show3(){
        System.out.println("调用了：默认的，无参的show3()");
    }
    private String show4(int age){
        System.out.println("调用了，私有的，并且有返回值的，int参数的show4(): age = " + age);
        return "abcd";
    }

    public static void main(String[] args) {
        Student stu1 = new Student();
        Class<? extends Student> aClass = stu1.getClass();
        System.out.println(aClass.getName());

        //第二种方式获取Class对象
        Class stuClass2 = Student.class;
        //判断第一种方式获取的Class对象和第二种方式获取的是否是同一个
        System.out.println(aClass == stuClass2);


        //第三种方式获取Class对象
        try {
            Class clazz = Class.forName("base.relect.Student");//注意此字符串必须是真实路径，就是带包名的类路径，包名.类名

            //2.获取所有公有构造方法
            System.out.println("**********************所有公有构造方法*********************************");
            Constructor[] conArray = clazz.getConstructors();
            for(Constructor c : conArray){
                System.out.println(c);
            }

            System.out.println("************所有的构造方法(包括：私有、受保护、默认、公有)***************");
            conArray = clazz.getDeclaredConstructors();
            for(Constructor c : conArray){
                System.out.println(c);
            }

            System.out.println("*****************获取公有、无参的构造方法*******************************");
            Constructor con = clazz.getConstructor(null);
            //1>、因为是无参的构造方法所以类型是一个null,不写也可以：这里需要的是一个参数的类型，切记是类型
            //2>、返回的是描述这个无参构造函数的类对象。

            System.out.println("con = " + con);
            //调用构造方法
            Object obj = con.newInstance();
            //  System.out.println("obj = " + obj);
            //  Student stu = (Student)obj;

            System.out.println("******************获取私有构造方法，并调用*******************************");
            con = clazz.getDeclaredConstructor(char.class);
            System.out.println(con);
            //调用构造方法
            // con.setAccessible(true);//暴力访问(忽略掉访问修饰符)
            obj = con.newInstance('男');


            //2.获取字段
            System.out.println("************获取所有公有的字段********************");
            Field[] fieldArray = clazz.getFields();
            for(Field f : fieldArray){
                System.out.println(f);
            }


            System.out.println("************获取所有的字段(包括私有、受保护、默认的)********************");
            fieldArray = clazz.getDeclaredFields();
            for(Field f : fieldArray){
                System.out.println(f);
            }

            System.out.println("*************获取公有字段**并调用***********************************");
            Field f = clazz.getField("name");
            System.out.println(f);
            //获取一个对象
            Object obj1 = clazz.getConstructor().newInstance();//产生Student对象--》Student stu = new Student();
            //为字段设置值
            f.set(obj1, "刘德华");//为Student对象中的name属性赋值--》stu.name = "刘德华"
            //验证
            Student stu = (Student)obj1;
            System.out.println("验证姓名：" + stu.name);


            System.out.println("**************获取私有字段****并调用********************************");
            f = clazz.getDeclaredField("phoneNum");
            System.out.println(f);
            f.setAccessible(true);//暴力反射，解除私有限定
            f.set(obj1, "18888889999");
            System.out.println("验证电话：" + stu);



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
