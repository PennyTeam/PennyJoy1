package Models;

public class
Category {
    private int id;
    private String name;
    private String userKey;
    private String key;
    private static boolean isActive = true;

    public Category(int id, String name,boolean isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }

    public Category() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    @Override
    public String toString() {
        return getName();
    }

    public boolean getIsActive(){
        return isActive;
    }
    public void setIsActive(boolean newIsActive){
        isActive = newIsActive;
    }
}
