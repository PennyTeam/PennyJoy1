package Models;

public class Good {
    private int category;
    private String name;
    private float cost;
    private String purchaseOfPurpose;
    private String userKey;

    public Good() {
    }

    public Good(int category, String name, float cost, String purchaseOfPurpose, String userKey) {
        this.category = category;
        this.name = name;
        this.cost = cost;
        this.purchaseOfPurpose = purchaseOfPurpose;
        this.userKey = userKey;
    }

    //геттеры и сеттеры


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

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
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
}
