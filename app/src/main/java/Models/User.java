package Models;

public class User {

    private String name;
    private String surname;
    private String login;
    private String passwd;
    private double salary;
    private String key;
    private static boolean accIsActive ;
    private String usersCurrentDate;
    private double totalSpends;
    private String dateCreate;
    private int efficiency;




    public User(String Name, String surname, String login, String passwd, double salary,String dateCreate) {
        this.name = Name;
        this.surname = surname;
        this.login = login;
        this.passwd = passwd;
        this.salary = salary;
        this.dateCreate=dateCreate;

        this.accIsActive = true;
        this.totalSpends=0;
    }


    public User(){};

    //здесь геттеры и сеттеры


    public int getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public double getTotalSpends() {
        return totalSpends;
    }

    public void setTotalSpends(double totalSpends) {
        this.totalSpends = totalSpends;
    }


    public String getUsersCurrentDate() {
        return usersCurrentDate;
    }

    public void setUsersCurrentDate(String usersCurrentDate) {
        this.usersCurrentDate = usersCurrentDate;
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
