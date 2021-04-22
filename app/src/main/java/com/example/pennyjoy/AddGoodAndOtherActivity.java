package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import Models.CategoryList;
import Models.Good;
import Models.User;
import Providers.GoodProvider;
import Providers.UserProvider;

public class AddGoodAndOtherActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private CategoryList categoryList;
    private View myViewAddGood;
    private EditText txtNameOfGood, txtCostOfGood,txtPurchaseOfPurpose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_good_and_other);

        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.lamp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.addgooditem));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.timer));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        categoryList=new CategoryList();
        categoryList.initCategoryList();
        myViewAddGood=getLayoutInflater().inflate(R.layout.fragment_add_good_tab, null);

        txtCostOfGood = myViewAddGood.findViewById(R.id.txtCostOfGood);
        txtNameOfGood=myViewAddGood.findViewById(R.id.txtNameOfGood);



        final ViewPager viewPager= (ViewPager) findViewById(R.id.pager);
        final PagerAdapter pagerAdapter= new Adapters.PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void addGoodInFragClicked(View v){


            String nameOfGood=txtNameOfGood.getText().toString();
            //float costOfGood=Integer.parseInt(txtCostOfGood.getText().toString());
            String userKey=null;
            Good good=new Good(1,nameOfGood,12,null,userKey);
            GoodProvider provider=new GoodProvider();
            provider.addGood(good);
            Toast.makeText(getApplicationContext(),"Покупка добавлена",Toast.LENGTH_SHORT);
        }


}