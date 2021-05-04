package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pennyjoy.R;

import java.math.BigDecimal;
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
        TextView productTitle = v.findViewById(R.id.goodTitle);
        TextView anEssay = v.findViewById(R.id.essayOfGood);
        TextView priceInNumbers = v.findViewById(R.id.numberPrice);
        TextView currencyOfCostInHistoryOfGood=v.findViewById(R.id.currencyOfCostInHistoryOfGood);

        Good good = this.getItem(position);
        productTitle.setText(good.getName());
        Auth auth=Auth.getInstance();
        currencyOfCostInHistoryOfGood.setText(auth.getCurrentCurrency().getLabel());

        //перевел double to BigDecimal, чтобы число отображалось корректно (у даблов не хватает на многие прайсы)

        priceInNumbers.setText(good.getCost()+ "");
        anEssay.setText(good.getPurchaseOfPurpose());
        v.setTag(good.getUserKey());
        v.setTag(R.string.good_int_for_tag, good);



        return v;
    }
}
