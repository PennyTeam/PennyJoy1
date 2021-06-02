package com.example.pennyjoy.Fragments;


import android.content.Intent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pennyjoy.HistoryActivity;

import com.example.pennyjoy.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;

import java.util.Date;


import Models.Auth;
import Models.Category;
import Models.CategoryList;
import Models.Goal;
import Models.GoalsList;
import Models.Good;
import Models.GoodsList;
import Providers.GoalProvider;
import Providers.GoodProvider;
import Providers.UserProvider;

public class AddGoodFragment extends Fragment {

    private EditText txtNameOfGood, txtCost,txtPurchaseOfPurpose;
    private ImageButton btnAddGood;
    private FloatingActionButton floatingActionButton;
    private Spinner dropDownCategory;
    private TextView lblCounterOfTextInput,currencyOfCostInAddGood;
    private int counterOfSymbols=0;
    private ProgressBar progressBar;
    private Auth auth=Auth.getInstance();
    private CategoryList categoryList=CategoryList.getInstance();





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_good, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



        floatingActionButton = view.findViewById(R.id.historyBtn);
        txtNameOfGood=view.findViewById(R.id.txtNameOfGood);
        txtCost=view.findViewById(R.id.txtCostOfGood);
        txtPurchaseOfPurpose=view.findViewById(R.id.txtPurchaseOfPurpose);
        btnAddGood=view.findViewById(R.id.btnAddGoodInFrag);
        dropDownCategory=view.findViewById(R.id.categoryDropDown);

        progressBar=view.findViewById(R.id.progressBarInAddGood);


        lblCounterOfTextInput= view.findViewById(R.id.counterOfTextInputAddGood);










        //делаю адаптер для вложенного списка
        ArrayAdapter<Category> categoryAdapter=new ArrayAdapter<Category>(view.getContext(),
                android.R.layout.simple_spinner_item,categoryList.getCategoryList());

        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dropDownCategory.setAdapter(categoryAdapter);
        //----------------------


        //кнопка для истории
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                startActivity(intent);
            }
        });


        //он клик для добавления обычного продукта
        View.OnClickListener listenerForUsualGood=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtNameOfGood.getText().toString().isEmpty() && !txtCost.getText().toString().isEmpty()
                        && !txtPurchaseOfPurpose.getText().toString().trim().isEmpty()
                        && Double.parseDouble(txtCost.getText().toString())>0
                        && counterOfSymbols >= 90
                        && !txtPurchaseOfPurpose.getText().toString().trim().isEmpty()){
                    //получаю текущего юзера и устанавливаю кей




                    progressBar.setVisibility(View.VISIBLE);
                    String keyOfUser= auth.getCurrentUser().getKey();
                    String nameOfGood= txtNameOfGood.getText().toString();
                    String purchaseOfPurpose= txtPurchaseOfPurpose.getText().toString();
                    double costOfGood= Double.parseDouble(txtCost.getText().toString());
                    Category category=(Category) dropDownCategory.getSelectedItem();


                    Date cDate = new Date();
                    String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);

                    Good good=new Good(category.getId(),nameOfGood,costOfGood,purchaseOfPurpose,keyOfUser,currentDate
                    ,true,true);
                    GoodProvider provider=new GoodProvider();
                    provider.addGood(good);
                    GoodsList goodsList=  GoodsList.getInstance();
                    goodsList.add(good);

                    auth.getCurrentUser().setTotalSpends(auth.getCurrentUser().getTotalSpends() + costOfGood);
                    UserProvider userProvider=new UserProvider();
                    userProvider.updateUser(auth.getCurrentUser());


                    txtNameOfGood.getText().clear();
                    txtCost.getText().clear();
                    txtPurchaseOfPurpose.getText().clear();
                    dropDownCategory.setSelection(0);
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(view.getContext(),"Товар добавлен!",Toast.LENGTH_LONG).show();


                }else{
                    Snackbar.make(getView(),"Заполните все поля!", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }
        };

        //он клик при выборе цели в категориях
        View.OnClickListener listenerForGoal =new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentGoal() != null) {
                    if (!txtCost.getText().toString().isEmpty() && Double.parseDouble(txtCost.getText().toString()) > 0) {
                        //получаю текущего юзера и устанавливаю кей
                        GoalProvider goalProvider = new GoalProvider();
                        Goal currentGoal = auth.getCurrentGoal();
                        double costOfGood = Double.parseDouble(txtCost.getText().toString());
                        //если юзер закончил цель
                        if(costOfGood + currentGoal.getFullness() >= currentGoal.getCost()){
                            goalProvider.deleteGoal(currentGoal);

                            Date cDate = new Date();
                            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);

                            currentGoal.setFullness(currentGoal.getFullness() + costOfGood);
                            Good good =new Good(7,currentGoal.getName(), currentGoal.getFullness()
                            ,currentGoal.getWhatFor(),currentGoal.getUserKey(),currentDate
                            ,true,true);

                            GoodProvider provider=new GoodProvider();
                            provider.addGood(good);

                            GoodsList goodsList=  GoodsList.getInstance();
                            goodsList.add(good);


                            auth.getCurrentUser().setTotalSpends(auth.getCurrentUser().getTotalSpends() + costOfGood);

                            UserProvider userProvider=new UserProvider();
                            userProvider.updateUser(auth.getCurrentUser());


                            GoalsList goalsList =GoalsList.getInstance();


                            goalsList.remove(currentGoal);

                            auth.getCurrentUser().setEfficiency(auth.getCurrentUser().getEfficiency() + 25);
                            userProvider.updateUser(auth.getCurrentUser());
                            if( goalsList != null && !goalsList.isEmpty()) {
                                auth.setCurrentGoal(goalsList.get(0));
                            }else{
                                auth.setCurrentGoal(null);
                            }
                            txtCost.getText().clear();
                            dropDownCategory.setSelection(0);

                            //________________________________________________________________
                            btnAddGood.setOnClickListener(listenerForUsualGood);
                            txtNameOfGood.setEnabled(true);
                            txtNameOfGood.setBackgroundResource(R.drawable.body_for_edit_text);

                            txtPurchaseOfPurpose.setEnabled(true);
                            txtPurchaseOfPurpose.setBackgroundResource(R.drawable.body_for_edit_text);
                            //________________________________________________________________
                            Toast.makeText(getContext(),"Поздравляем! Вы завершили свою цель!",Toast.LENGTH_SHORT).show();
                        }
                        else{


                            progressBar.setVisibility(View.VISIBLE);


                            currentGoal.setFullness(currentGoal.getFullness() + costOfGood);
                            goalProvider.updateGoal(currentGoal);

                            auth.getCurrentUser().setTotalSpends(auth.getCurrentUser().getTotalSpends() + costOfGood);

                            UserProvider userProvider=new UserProvider();
                            userProvider.updateUser(auth.getCurrentUser());

                            txtCost.getText().clear();
                            dropDownCategory.setSelection(0);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(view.getContext(), "Деньги добавлены к вашей цели", Toast.LENGTH_LONG).show();


                            //________________________________________________________________
                            btnAddGood.setOnClickListener(listenerForUsualGood);
                            txtNameOfGood.setEnabled(true);
                            txtNameOfGood.setBackgroundResource(R.drawable.body_for_edit_text);

                            txtPurchaseOfPurpose.setEnabled(true);
                            txtPurchaseOfPurpose.setBackgroundResource(R.drawable.body_for_edit_text);
                            //________________________________________________________________
                        }

                    } else {
                        Snackbar.make(getView(),"Заполните все поля!", BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }else{
                    Snackbar.make(getView(),"У вас пока нет целей", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }
        };
        dropDownCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 7 ){
                    btnAddGood.setOnClickListener(listenerForGoal);

                    txtNameOfGood.getText().clear();
                    txtPurchaseOfPurpose.getText().clear();

                   txtNameOfGood.setEnabled(false);
                   txtNameOfGood.setBackgroundResource(R.drawable.body_for_edit_text_enebled);

                   txtPurchaseOfPurpose.setEnabled(false);
                   txtPurchaseOfPurpose.setBackgroundResource(R.drawable.body_for_edit_text_enebled);

                }
                else{
                    btnAddGood.setOnClickListener(listenerForUsualGood);
                    txtNameOfGood.setEnabled(true);
                    txtNameOfGood.setBackgroundResource(R.drawable.body_for_edit_text);

                    txtPurchaseOfPurpose.setEnabled(true);
                    txtPurchaseOfPurpose.setBackgroundResource(R.drawable.body_for_edit_text);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAddGood.setOnClickListener(listenerForUsualGood);

        txtPurchaseOfPurpose.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable s) {
                lblCounterOfTextInput.setText(String.valueOf(s.toString().length()) + "/120" );
                counterOfSymbols=s.toString().length();

            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        currencyOfCostInAddGood=view.findViewById(R.id.currencyOfCostInAddGood);
        Auth auth=Auth.getInstance();
        currencyOfCostInAddGood.setText(auth.getCurrentCurrency().getLabel());
    }




}
