package Models;

import java.util.ArrayList;

public class CategoryList {
    private final static ArrayList<Category> categoryList=new ArrayList<>();
    private CategoryList(){

    }


    private static class CategoryListHolder{
        private final static CategoryList instance= new CategoryList();
    }
    public static CategoryList getInstance(){

        return CategoryListHolder.instance;

    }
    public void init(){
        if(categoryList.isEmpty()) {
            categoryList.add(new Category(0, "Продукты"));
            categoryList.add(new Category(1, "Путешествия"));
            categoryList.add(new Category(2, "Одежда"));
            categoryList.add(new Category(3, "Долги"));
            categoryList.add(new Category(4, "Цели"));
            categoryList.add(new Category(5, "Прочее"));
        }
    }





    public ArrayList<Category> getCategoryList(){
        return categoryList;
    }


}
