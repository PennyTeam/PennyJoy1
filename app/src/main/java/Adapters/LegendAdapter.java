package Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pennyjoy.R;
import com.github.mikephil.charting.components.LegendEntry;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Models.Auth;
import Models.Good;

public class LegendAdapter extends ArrayAdapter<LegendEntry> {


    private android.content.Context Context;
    private List<LegendEntry> legendEntryArrayList;
    private int resource;
    private ArrayList<Double> costOfCategoryList;
    private ArrayList<Double> percentageList;
    private ArrayList<Object> dataOfCategory;

    public LegendAdapter(@NonNull Context context, int resource, @NonNull List<LegendEntry> legendEntries, ArrayList<Double> costList
            , ArrayList<Double> percentageList, ArrayList<Object> dataOfCategory) {
        super(context, resource, legendEntries);

        this.Context = context;
        this.resource = resource;
        this.legendEntryArrayList = legendEntries;
        this.percentageList = percentageList;
        this.costOfCategoryList = costList;
        this.dataOfCategory = dataOfCategory;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(this.Context).inflate(resource, null);

        LegendEntry legendEntry = this.getItem(position);
        if((int)dataOfCategory.get(position) != -1) {

            DecimalFormat decimalFormat = new DecimalFormat("#.###");


            View colorOfCategory = v.findViewById(R.id.rectangleForCategoryColor);
            colorOfCategory.setBackgroundColor(legendEntry.formColor);


            TextView lblCostOfCategory = v.findViewById(R.id.lblCostOfCategory);


            TextView lblCurrencyOfCategoryCost = v.findViewById(R.id.lblCurrencyOfCategoryCost);
            Auth auth = Auth.getInstance();
            lblCurrencyOfCategoryCost.setText(auth.getCurrentCurrency().getLabel());

            TextView lblPercentageOfCategory = v.findViewById(R.id.lblPercentageOfCategory);


            //________________________________________
            //?????????????? ?????? ???????????? ?????? ?????? ?????????????????????????? ???????????? ?? ???????????????????????? entire ?????? ???????????? ???????????????? ?? ???????????? ?????????????????? ?? ??????????????????.
            // ?? visibility ???? ????????????????, ?????????????? ???????????? ??????

            TextView lblNameOfCategory = v.findViewById(R.id.lblNameOfCategory);

            String name = "";
            switch ((int) dataOfCategory.get(position)) {
                case 0:
                    name = "????????????????";
                    lblCostOfCategory.setText(decimalFormat.format(costOfCategoryList.get((int) dataOfCategory.get(position))));
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    break;
                case 1:
                    lblCostOfCategory.setText(decimalFormat.format(costOfCategoryList.get((int) dataOfCategory.get(position))));
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    name = "??????????????????????";
                    break;
                case 2:
                    lblCostOfCategory.setText(decimalFormat.format(costOfCategoryList.get((int) dataOfCategory.get(position))));
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get(position)));
                    name = "??????????????????";
                    break;
                case 3:
                    lblCostOfCategory.setText(decimalFormat.format(costOfCategoryList.get((int) dataOfCategory.get(position))));
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    name = "????????????????????";
                    break;
                case 4:
                    lblCostOfCategory.setText(decimalFormat.format(costOfCategoryList.get((int) dataOfCategory.get(position))));
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    name = "????????????";
                    break;
                case 5:
                    lblCostOfCategory.setText(decimalFormat.format(costOfCategoryList.get((int) dataOfCategory.get(position))));
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    name = "??????????";
                    break;
                case 6:
                    lblCostOfCategory.setText(decimalFormat.format(costOfCategoryList.get((int) dataOfCategory.get(position))));
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    name = "????????????????????";
                    break;
                case 7:
                    lblCostOfCategory.setText(decimalFormat.format(costOfCategoryList.get((int) dataOfCategory.get(position))));
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    name = "????????";
                    break;
                case 8:
                    lblCostOfCategory.setText(decimalFormat.format(costOfCategoryList.get((int) dataOfCategory.get(position))));
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    name = "??????????";
                    break;
                case 9:
                    lblCostOfCategory.setText(decimalFormat.format(costOfCategoryList.get((int) dataOfCategory.get(position))));
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    name = "?????????????????????? ?? ??????????";
                    break;
                case 10:
                    lblCostOfCategory.setText(decimalFormat.format(costOfCategoryList.get((int) dataOfCategory.get(position))));
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    name = "?????????????? ?? ????????????????";
                    break;
                case 11:
                    lblCostOfCategory.setText(decimalFormat.format(costOfCategoryList.get((int) dataOfCategory.get(position))));
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    name = "??????????????";
                    break;
                case 12:
                    lblCostOfCategory.setText(decimalFormat.format(costOfCategoryList.get((int) dataOfCategory.get(position))));
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    name = "????????????";
                    break;
            }

            //________________________________________


            lblNameOfCategory.setText(name);





            return v;
        }
        return v;
    }
}
