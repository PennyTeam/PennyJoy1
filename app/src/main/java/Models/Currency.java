package Models;

public class Currency {
    private int id;
    private String code;
    private String label;


    public Currency(int id, String code, String label) {
        this.id = id;
        this.code = code;
        this.label = label;
    }

    public Currency() {
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return getLabel();
    }
}
