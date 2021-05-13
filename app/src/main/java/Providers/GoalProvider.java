package Providers;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Interfaces.OnGoalRetrievedListener;
import Interfaces.OnGoodsRetrievedListener;
import Models.Goal;
import Models.Good;
import Models.User;

public class GoalProvider {

    private FirebaseDatabase db;
    private DatabaseReference goal;

    public GoalProvider(){
        db = FirebaseDatabase.getInstance();
        goal = db.getReference().child("Goals");
    }



    public void addGoal(Goal g){
        DatabaseReference push= goal.push();
        g.setKey(push.getKey());
        push.setValue(g);
    }

    public void updateGoal(Goal g) {

        goal.child(g.getKey()).setValue(g);
    }



    public void getGoalsFromFirebase(String keyOfUser, OnGoalRetrievedListener listener){
        ArrayList<Goal> goalList=new ArrayList<>();
        Query query = goal.orderByChild("userKey").equalTo(keyOfUser);
        //__________________________________________________
        //**************************************************
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot single : snapshot.getChildren()){
                    Goal goal = (Goal)single.getValue(Goal.class);
                    goalList.add(goal);
                }
                listener.onGoalRetrieved(goalList);
                goalList.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
