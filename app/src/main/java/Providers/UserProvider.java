package Providers;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import Interfaces.OnUserRetrievedListener;
import Models.User;

public class UserProvider {
    private FirebaseDatabase db;
    private DatabaseReference users;

    public UserProvider(){
        db = FirebaseDatabase.getInstance();
        users = db.getReference().child("Users");
    }



    public void addUser(User user){
        DatabaseReference push=users.push();
        user.setKey(push.getKey());
        push.setValue(user);
    }

    public void updateUser(User user){
        users.child(user.getKey()).setValue(user);

    }




    public void getUserFromFirebaseByLogin(String login, OnUserRetrievedListener listener){
        User user = new User();
        Query query = users.orderByChild("login").equalTo(login);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot single : snapshot.getChildren()){
                  User user1 = (User)single.getValue(User.class);
                  user.setAccIsActive(user1.getAccIsActive());
                  user.setKey(user1.getKey());
                  user.setLogin(user1.getLogin());
                  user.setPasswd(user1.getPasswd());
                  user.setName(user1.getName());
                  user.setSurname(user1.getSurname());
                  user.setSalary(user1.getSalary());
                  user.setTotalSpends(user1.getTotalSpends());
                  user.setUsersCurrentMonth(user1.getUsersCurrentMonth());
                  user.setDateCreate(user1.getDateCreate());
                }
                listener.OnRetrieved(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}


