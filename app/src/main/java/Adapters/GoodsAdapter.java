package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pennyjoy.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Models.Auth;
import Models.Good;

public class GoodsAdapter extends ArrayAdapter<Good> {
private Context Context;
private ArrayList<Good> goodArrayList;
private int resource;
    public GoodsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Good> goods) {
        super(context, resource, goods);

        this.Context = context;
        this.resource = resource;
        this.goodArrayList = goods;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(this.Context).inflate(resource,null);

        Good good = this.getItem(position);

        TextView productTitle = v.findViewById(R.id.goodTitle);
        TextView anEssay = v.findViewById(R.id.essayOfGood);
        TextView priceInNumbers = v.findViewById(R.id.numberPrice);
        TextView currencyOfCostInHistoryOfGood=v.findViewById(R.id.currencyOfCostInHistoryOfGood);

        RelativeLayout containerForLblTime=v.findViewById(R.id.containerForLblTime);
        TextView lblTime=v.findViewById(R.id.lblTime);


        //делаю header со временем
        lblTime.setText(good.getCreateDate());
        if (position > 0) {
            if (this.getItem(position).getCreateDate().equalsIgnoreCase(this.getItem(position - 1).getCreateDate())) {
                containerForLblTime.setVisibility(View.GONE);
            } else {
                containerForLblTime.setVisibility(View.VISIBLE);
            }
        } else {
            containerForLblTime.setVisibility(View.VISIBLE);
        }

//
        productTitle.setText(good.getName());
        Auth auth=Auth.getInstance();
        currencyOfCostInHistoryOfGood.setText(auth.getCurrentCurrency().getLabel());
        DecimalFormat decimalFormat=new DecimalFormat("#.###");
        priceInNumbers.setText(decimalFormat.format(good.getCost()));
        anEssay.setText(good.getPurchaseOfPurpose());
        v.setTag(good.getUserKey());
        v.setTag(R.string.good_int_for_tag, good);



        return v;
    }
}
