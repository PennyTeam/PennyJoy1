package Models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class User {

    private String name;
    private String surname;
    private String login;
    private String passwd;
    private double salary;
    private String key;
    private static boolean accIsActive ;
    private int usersCurrentMonth;
    private double totalSpends;




    public User(String Name, String surname, String login, String passwd, double salary) {
        this.name = Name;
        this.surname = surname;
        this.login = login;
        this.passwd = passwd;
        this.salary = salary;

        this.accIsActive = true;
        this.totalSpends=0;
    }

    public User(){};

    //здесь геттеры и сеттеры


    public double getTotalSpends() {
        return totalSpends;
    }

    public void setTotalSpends(double totalSpends) {
        this.totalSpends = totalSpends;
    }

    public int getUsersCurrentMonth() {
        return usersCurrentMonth;
    }

    public void setUsersCurrentMonth(int usersCurrentMonth) {
        this.usersCurrentMonth = usersCurrentMonth;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccIsActive(boolean isExist){
        this.accIsActive = isExist;
    }
    public boolean getAccIsActive(){
        return accIsActive;
    }

    //конец геттеров и сеттеров
}
