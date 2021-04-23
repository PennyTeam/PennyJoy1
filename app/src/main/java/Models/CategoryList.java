package Models;

import java.util.ArrayList;

public class CategoryList {
    private final static ArrayList<Category> categoryList=new ArrayList<>();
    private CategoryList(){

    }

    //не оч работает, объект всегда один, но проверка на нулл не работает
    private static class CategoryListHolder{
        private final static CategoryList instance= new CategoryList();
    }
    public static CategoryList getInstance(){
        if(CategoryListHolder.instance != null) {
            categoryList.add(new Category(0, "Не выбрано"));
            categoryList.add(new Category(1, "Продукты"));
            categoryList.add(new Category(2, "Путешествия"));
            categoryList.add(new Category(3, "Одежда"));
            categoryList.add(new Category(4, "Долги"));
            categoryList.add(new Category(5, "Цели"));
            categoryList.add(new Category(6, "Свое"));
        }
        return CategoryListHolder.instance;

    }

    /*public void initCategoryList(){
        categoryList.add(new Category(0,"Продукты"));
        categoryList.add(new Category(1,"Путешествия"));
        categoryList.add(new Category(2,"Одежда"));
        categoryList.add(new Category(3,"Долги"));
        categoryList.add(new Category(4,"Цели"));
        categoryList.add(new Category(5,"Свое"));
    }*/

    public void addUsersCategory(){

    }

    public ArrayList<Category> getCategories(){
        return categoryList;
    }
}
