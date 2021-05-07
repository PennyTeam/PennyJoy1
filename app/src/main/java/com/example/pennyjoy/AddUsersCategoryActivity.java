package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import Models.Auth;
import Models.Category;
import Models.CategoryList;
import Providers.CategoryProvider;

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
            if(categoryList.getCategories().size()<=15) {
                String categoryName = txtCategoryName.getText().toString();
                Category category = new Category(categoryList.getCategories().size(), categoryName);
                category.setUserKey(auth.getCurrentUser().getKey());


                //добавление категории
                categoryList.getCategories().add(category);
                categoryProvider.addCategory(category);
                Toast.makeText(getApplicationContext(), "Категория добавлена!", Toast.LENGTH_SHORT);
                finish();
            }else{
                Toast.makeText(getApplicationContext(),"Количество категорий слишком большое",Toast.LENGTH_LONG);
            }

        }else{
            Toast.makeText(getApplicationContext(),"Заполните поле!",Toast.LENGTH_LONG);
        }
    }
}