package com.example.pennyjoy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Adapters.CategoryAdapter;
import Interfaces.OnCategoriesRetrievedListener;
import Models.Auth;
import Models.Category;
import Models.CategoryList;
import Models.Currency;
import Models.User;
import Providers.CategoryProvider;
import Providers.CurrencyProvider;
import Providers.UserProvider;

public class CategoriesActivity extends AppCompatActivity {
private FloatingActionButton btn_addCategory;
private CategoryAdapter categoryAdapter;
private ArrayList<Category>categoryArrayList;
private ListView listViewCategories;
private Auth auth = Auth.getInstance();
private CategoryProvider categoryProvider;
private AlertDialog.Builder builder;
private CategoryList categoryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        btn_addCategory = findViewById(R.id.btn_add_category);
        categoryArrayList = new ArrayList<>();
        listViewCategories = findViewById(R.id.lv_categories);
        categoryList = CategoryList.getInstance();
        categoryAdapter = new CategoryAdapter(getApplicationContext(), R.layout.category_template, categoryList.getUsersCategories());
        listViewCategories.setAdapter(categoryAdapter);
        categoryProvider = new CategoryProvider();

        builder = new AlertDialog.Builder(this);

       // categoryArrayList.addAll(categoryList.getCategories());


        listViewCategories.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Category category = (Category) view.getTag(R.string.id_for_categoty_adapter);

                builder.setTitle("Удаление категории");
                builder.setMessage("Вы уверены, что хотите удалить категорию: " + category.getName() + "?");
                builder.setCancelable(false);
                builder.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Операция отменена", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        category.setIsActive(false);
                        categoryProvider.updateCategoriesWithNewCategory(category);
                        categoryList.getCategories().set(category.getId(), category);
                        categoryAdapter.notifyDataSetChanged();
                    }
                });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("AlertDialogExample");
                alert.show();

                return true;
            }
        });

        listViewCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = (Category) view.getTag(R.string.id_for_categoty_adapter);
                AlertDialog.Builder ad = new AlertDialog.Builder(getApplicationContext());
                final EditText editText = new EditText(view.getContext());
                ad.setTitle("Редактировать название");
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                editText.setEms(16);
                ad.setView(editText);
                ad.setCancelable(false);
                ad.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            String newTitle = editText.getText().toString();
                            if(!newTitle.isEmpty() && newTitle.length()>=2){
                                category.setName(newTitle);
                                categoryProvider.updateCategoriesWithNewCategory(category);
                                categoryAdapter.notifyDataSetChanged();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Минимум 2 символа!",Toast.LENGTH_SHORT ).show();
                                editText.setError("Заполните поле корректно!");
                            }
                    }
                });

                ad.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Операция отменена", Toast.LENGTH_SHORT).show();
                    }
                });
              //  ad.show();
            }
        });
    }
    public void goToAddCategoryClicked(View view){
        Intent intent=new Intent(getApplicationContext(),AddUsersCategoryActivity.class);
        startActivity(intent);
    }

}