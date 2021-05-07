package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import Models.Auth;
import Models.Category;
import Models.CategoryList;

public class
AddUsersCategoryActivity extends AppCompatActivity {
    EditText txtCategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_users_category);
        txtCategoryName=findViewById(R.id.txtCategoryName);
    }


    public void addNewCategoryClicked(View view){
        if(!txtCategoryName.getText().toString().isEmpty()){
            CategoryList categoryList= CategoryList.getInstance();
            CategoryProvider categoryProvider=new CategoryProvider();
            Auth auth=Auth.getInstance();
            if(categoryList.getCategoryList().size()<=15) {
                String categoryName = txtCategoryName.getText().toString();
                Category category = new Category(categoryList.getCategoryList().size(), categoryName,true);
                category.setUserKey(auth.getCurrentUser().getKey());


                //добавление категории
               categoryList.getCategoryList().add(category);
                categoryProvider.addCategory(category);
                Toast.makeText(getApplicationContext(), "Категория добавлена!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"Количество категорий слишком большое",Toast.LENGTH_LONG);
            }

        }else{
            Toast.makeText(getApplicationContext(),"Заполните поле!",Toast.LENGTH_LONG);
        }

    }
    public void onClickForTry(View view){
        if(!txtCategoryName.getText().toString().isEmpty()){
            CategoryList categoryList= CategoryList.getInstance();
            if(categoryList.getCategoryList().size()<=15) {
                addCategory(categoryList);
            }else{
                Toast.makeText(getApplicationContext(),"Количество категорий слишком большое",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Заполните поле!",Toast.LENGTH_LONG).show();
        }

    }

    public void addCategory(CategoryList categoryList){
        CategoryProvider categoryProvider=new CategoryProvider();
        Auth auth=Auth.getInstance();

        String categoryName = txtCategoryName.getText().toString();
        System.out.println(categoryName);
        Category category = new Category(categoryList.getCategoryList().size(), categoryName,true);
        category.setUserKey(auth.getCurrentUser().getKey());
        System.out.println(category.getUserKey());

        System.out.println(categoryList.getCategoryList().size());
        categoryList.getCategoryList().add(category);
        System.out.println(categoryList.getCategoryList().size());
        categoryProvider.addCategory(category);
    }
}