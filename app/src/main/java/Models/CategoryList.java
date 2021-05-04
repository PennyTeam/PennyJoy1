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

        return CategoryListHolder.instance;

    }
    public void init(){
        if(categoryList.isEmpty()) {
            Auth auth =new Auth();
            categoryList.add(new Category(0, "Не выбрано",auth.getCurrentUser().getKey()));
            categoryList.add(new Category(1, "Продукты",auth.getCurrentUser().getKey()));
            categoryList.add(new Category(2, "Путешествия",auth.getCurrentUser().getKey()));
            categoryList.add(new Category(3, "Одежда",auth.getCurrentUser().getKey()));
            categoryList.add(new Category(4, "Долги",auth.getCurrentUser().getKey()));
            categoryList.add(new Category(5, "Цели",auth.getCurrentUser().getKey()));
        }
    }



    public void addCategory(Category category){
        if(categoryList.size()<=15) {
            categoryList.add(category);
        }
    }

    public ArrayList<Category> getCategories(){
        return categoryList;
    }
}
