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
            categoryList.add(new Category(2, "Транспорт"));
            categoryList.add(new Category(3, "Автомобиль"));
            categoryList.add(new Category(4, "Одежда"));
            categoryList.add(new Category(5, "Долги"));
            categoryList.add(new Category(6, "Инвестиции"));
            categoryList.add(new Category(7, "Цели"));
            categoryList.add(new Category(8, "Жилье"));
            categoryList.add(new Category(9, "Развлечения и досуг"));
            categoryList.add(new Category(10, "Красота и здоровье"));
            categoryList.add(new Category(11, "Покупки"));
            categoryList.add(new Category(12, "Прочее"));
        }
    }





    public ArrayList<Category> getCategoryList(){
        return categoryList;
    }


}
