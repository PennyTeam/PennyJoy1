package Models;

public class Goal {
    private String image;
    private String name;
    private double cost;
    private String whatFor;
    private String userKey;
    private String key;


    public Goal(String image, String name, double cost, String whatFor, String keyOfUser) {
        this.image = image;
        this.name = name;
        this.cost = cost;
        this.whatFor = whatFor;
        this.userKey=keyOfUser;
    }

    public Goal() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getWhatFor() {
        return whatFor;
    }

    public void setWhatFor(String whatFor) {
        this.whatFor = whatFor;
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
