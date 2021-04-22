package Models;

import java.util.ArrayList;

public class CategoryList {
    private final static ArrayList<Category> categoryList=new ArrayList<>();

    public void initCategoryList(){
        categoryList.add(new Category(0,"Продукты"));
        categoryList.add(new Category(1,"Путешествия"));
        categoryList.add(new Category(2,"Одежда"));
        categoryList.add(new Category(3,"Долги"));
        categoryList.add(new Category(4,"Цели"));
        categoryList.add(new Category(5,"Свое"));
    }
    public void addUsersCategory(){

    }
    public ArrayList<String> getOnlyNames(){
        ArrayList<String> names=new ArrayList<>();
        for (Category c:categoryList) {
            names.add(c.getName());
        }
        return names;
    }
    public ArrayList<Category> getCategories(){
        return categoryList;
    }
}
