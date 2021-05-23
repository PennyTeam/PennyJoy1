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
import com.github.mikephil.charting.components.LegendEntry;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Models.Auth;
import Models.Good;

public class LegendAdapter  extends ArrayAdapter<LegendEntry> {


    private android.content.Context Context;
    private List<LegendEntry> legendEntryArrayList;
    private int resource;
    private double costOfCategory;
    private int percentage;

    public LegendAdapter(@NonNull Context context, int resource, @NonNull List<LegendEntry> legendEntries, double costOfCategory
            , int percentage) {
        super(context, resource, legendEntries);

        this.Context = context;
        this.resource = resource;
        this.legendEntryArrayList = legendEntries;
        this.percentage=percentage;
        this.costOfCategory=costOfCategory;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(this.Context).inflate(resource,null);

        DecimalFormat decimalFormat =new DecimalFormat("#.###");

        LegendEntry legendEntry=this.getItem(position);




        View colorOfCategory=v.findViewById(R.id.rectangleForCategoryColor);
        colorOfCategory.setBackgroundColor(legendEntry.formColor);

        TextView lblNameOfCategory=v.findViewById(R.id.lblNameOfCategory);
        lblNameOfCategory.setText(legendEntry.label);


        TextView lblCostOfCategory=v.findViewById(R.id.lblCostOfCategory);
        lblCostOfCategory.setText(decimalFormat.format(costOfCategory));

        TextView lblCurrencyOfCategoryCost=v.findViewById(R.id.lblCurrencyOfCategoryCost);
        Auth auth=Auth.getInstance();
        lblCurrencyOfCategoryCost.setText(auth.getCurrentCurrency().getLabel());

        TextView lblPercentageOfCategory=v.findViewById(R.id.lblPercentageOfCategory);
        lblPercentageOfCategory.setText(decimalFormat.format(percentage));


        return v;
    }
}
