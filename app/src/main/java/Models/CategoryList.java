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
            categoryList.add(new Category(1, "Продукты",true));
            categoryList.add(new Category(2, "Путешествия",true));
            categoryList.add(new Category(3, "Одежда",true));
            categoryList.add(new Category(4, "Долги",true));
            categoryList.add(new Category(5, "Цели",true));
        }
    }





    public ArrayList<Category> getCategoriesWichExist(){
        ArrayList<Category> categoryArrayList = new ArrayList<>();
        for(Category c : categoryList){
            if(c.getIsActive() == true){
                categoryArrayList.add(c);
            }
        }
        return categoryArrayList;
    }

    public ArrayList<Category> getUsersCategories(){
        ArrayList<Category> categoryArrayList = new ArrayList<>();
        for(int i = 5; ;i++){
            if(i == categoryList.size()){
                break;
            }
            categoryArrayList.add(getCategoriesWichExist().get(i));
        }
        return categoryArrayList;
    }

    public ArrayList<Category> getCategoryList(){
        return categoryList;
    }
}
