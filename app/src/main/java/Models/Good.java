package Models;



public class Good {
    private int category;
    private String name;
    private double cost;
    private String purchaseOfPurpose;
    private String userKey;
    private String key;
    private String createDate;
    private boolean isActual;
    private boolean isActiveForHistory;


    public Good() {
    }



    public Good(int category, String name, double price, String purchaseOfPurpose, String userKey,String createDate
    ,boolean isActual, boolean isActiveForHistory) {
        this.category = category;
        this.name = name;
        this.cost = price;
        this.purchaseOfPurpose = purchaseOfPurpose;
        this.userKey = userKey;
        this.createDate=createDate;

        this.isActual=isActual;
        this.isActiveForHistory=isActiveForHistory;
    }

    //геттеры и сеттеры


    public boolean getActual() {
        return isActual;
    }

    public void setActual(boolean actual) {
        isActual = actual;
    }

    public boolean getActiveForHistory() {
        return isActiveForHistory;
    }

    public void setActiveForHistory(boolean activeForHistory) {
        isActiveForHistory = activeForHistory;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getPurchaseOfPurpose() {
        return purchaseOfPurpose;
    }

    public void setPurchaseOfPurpose(String purchaseOfPurpose) {
        this.purchaseOfPurpose = purchaseOfPurpose;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
