package Models;

public class Auth {
    private static User currentUser;
    private static Currency currentCurrency;
    private Auth(){

    }


    private static class AuthHolder{
        private final static Auth instance= new Auth();
    }
    public static Auth getInstance(){

        return Auth.AuthHolder.instance;

    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }


    public Currency getCurrentCurrency() {
        return currentCurrency;
    }

    public void setCurrentCurrency(Currency currentCurrency) {
        Auth.currentCurrency = currentCurrency;
    }


}
