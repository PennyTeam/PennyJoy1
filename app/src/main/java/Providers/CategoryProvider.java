package Providers;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Interfaces.OnCategoriesRetrievedListener;
import Models.Category;
import Models.CategoryList;

public class CategoryProvider {
    private FirebaseDatabase db;
    private DatabaseReference categories;

    public CategoryProvider(){
        db = FirebaseDatabase.getInstance();
        categories = db.getReference().child("Categories");
    }


    public void getCategoriesFromFirebase(String keyOfUser, OnCategoriesRetrievedListener listener){

        ArrayList<Category> categoryList=new ArrayList<>();
        Query query = categories.orderByChild("userKey").equalTo(keyOfUser);
        //__________________________________________________
        //**************************************************
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot single : snapshot.getChildren()){
                    Category category = (Category) single.getValue(Category.class);
                    if(category.getIsActive()) {
                        categoryList.add(category);
                    }
                }
                listener.OnCategoriesRetrieved(categoryList);
                categoryList.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void addCategory(Category category){
        DatabaseReference push=categories.push();
        category.setKey(push.getKey());
        push.setValue(category);
    }

    //надо это юзать вместо обычного адд в маине, в той недо-кнопке
    public void updateCategoriesWithNewCategory(Category category){
        categories.child(category.getKey()).setValue(category);

    }
}
