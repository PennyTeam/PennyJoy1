package Providers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Interfaces.OnGoodsRetrievedListener;
import Interfaces.OnUserRetrievedListener;
import Models.Good;
import Models.User;

public class GoodProvider {
    private FirebaseDatabase db;
    private DatabaseReference goods;

    public GoodProvider(){
        db = FirebaseDatabase.getInstance();
        goods = db.getReference().child("Goods");
    }



    public void addGood(Good good){
        DatabaseReference push= goods.push();
        good.setKey(push.getKey());
        push.setValue(good);
    }

    public void updateGood(Good good) {

        goods.child(good.getKey()).setValue(good);
    }



    public void getGoodsFromFirebase(String keyOfUser, OnGoodsRetrievedListener listener){
        ArrayList<Good> goodList=new ArrayList<>();
        Query query = goods.orderByChild("userKey").equalTo(keyOfUser);
        //__________________________________________________
        //**************************************************
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot single : snapshot.getChildren()){
                    Good good = (Good)single.getValue(Good.class);
                    goodList.add(good);
                }
                listener.OnRetrieved(goodList);
                goodList.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getGoodsFromFirebaseByDate(String keyOfUser, OnGoodsRetrievedListener listener,String date){
        ArrayList<Good> goodList=new ArrayList<>();
        Query query = goods.orderByChild("userKey").equalTo(keyOfUser);
        //__________________________________________________
        //**************************************************
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot single : snapshot.getChildren()){

                    String [] dateList=date.split("-");

                    Good good = (Good)single.getValue(Good.class);
                    String [] dateListOfGood=good.getCreateDate().split("-");

                    if(dateList[0].equals(dateListOfGood[0]) && dateList[1].equals(dateListOfGood[1]) ) {
                        goodList.add(good);
                    }
                }
                listener.OnRetrieved(goodList);
                goodList.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
