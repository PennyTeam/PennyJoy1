package Models;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Interfaces.OnUsersRetrievedListener;

public class UserProvider {
    private FirebaseDatabase db;
    private DatabaseReference users;

    public UserProvider(){
        db = FirebaseDatabase.getInstance();
        users = db.getReference().child("Users");
    }

    public User getUser(String key){
        return null;
    }

    //не знаю почему, но начал делать получение. Это оставлю

    public void getUsers(OnUsersRetrievedListener listener){
        ArrayList<User> userArrayList = new ArrayList<>();
        Query query = users.orderByChild("age");//.startAt(18);

        //вопросик, стоит ли это юзать, то есть все время связь с серваком держать?
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userArrayList.clear();
                for (DataSnapshot dataUser : snapshot.getChildren()) {
                    User user = dataUser.getValue(User.class);
                    userArrayList.add(user);
                }
               listener.OnUsersRetrieved(userArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

    }

    public void addUser(User user){
        DatabaseReference push=users.push();
        push.setValue(user);
    }

    public void updateUser(User user){ }

    public void deleteUser(User user){ }
}


