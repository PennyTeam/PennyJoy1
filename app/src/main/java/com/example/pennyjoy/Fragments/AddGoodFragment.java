package com.example.pennyjoy.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pennyjoy.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import Providers.GoodProvider;

public class AddGoodFragment extends Fragment {

    private EditText txtNameOfGood, txtCost,txtPurchaseOfPurpose;
    private Button btnAddGood;
    private Spinner dropDownCategory;

    private GoodProvider provider= new GoodProvider();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_good, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        txtNameOfGood=view.findViewById(R.id.txtNameOfGood);
        txtCost=view.findViewById(R.id.txtCostOfGood);
        txtPurchaseOfPurpose=view.findViewById(R.id.txtPurchaseOfPurpose);
        btnAddGood=view.findViewById(R.id.btnAddGoodInFrag);
        dropDownCategory=view.findViewById(R.id.categoryDropDown);

        btnAddGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(view, "Work!!", BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });
    }
}
