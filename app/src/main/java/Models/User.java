package Models;

public class User {
    private String name;
    private String surname;
    private String login;
    private String passwd;
    private float salary;
    private String key;
    private static User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User(String name, String surname, String login, String passwd, float salary, String key) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.passwd = passwd;
        this.salary = salary;
        this.key = key;
    }

    public User(){};

    //здесь геттеры и сеттеры
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
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

    //конец геттеров и сеттеров
}
