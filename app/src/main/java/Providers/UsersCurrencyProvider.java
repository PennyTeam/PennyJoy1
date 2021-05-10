package Providers;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import Interfaces.OnUserRetrievedListener;
import Interfaces.OnUsersCurrencyRetrievedListener;
import Models.Currency;
import Models.User;

public class UsersCurrencyProvider {
    private FirebaseDatabase db;
    private DatabaseReference currencies;

    public UsersCurrencyProvider(){
        db = FirebaseDatabase.getInstance();
        currencies = db.getReference().child("Currencies");
    }



    public void addCurrency(Currency currency){
        DatabaseReference push= currencies.push();
        currency.setKey(push.getKey());
        push.setValue(currency);
    }

    public void updateCurrency(Currency currency){
        currencies.child(currency.getKey()).setValue(currency);
    }




    public void getUsersCurrencyFromFirebase(String keyOfUser, OnUsersCurrencyRetrievedListener listener){
        Currency currency=new Currency();
        Query query = currencies.orderByChild("userKey").equalTo(keyOfUser);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot single : snapshot.getChildren()){
                    Currency currency1 = (Currency)single.getValue(Currency.class);
                   currency.setUserKey(currency1.getUserKey());
                   currency.setKey(currency1.getKey());
                   currency.setLabel(currency1.getLabel());
                   currency.setCode(currency1.getCode());
                   currency.setId(currency1.getId());
                }
                listener.OnRetrieved(currency);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
