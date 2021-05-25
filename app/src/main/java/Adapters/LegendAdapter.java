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
    private double costOfCategory;
    private int percentage;
    private ArrayList<Object> dataOfCategory;

    public LegendAdapter(@NonNull Context context, int resource, @NonNull List<LegendEntry> legendEntries, double costOfCategory
            , int percentage, ArrayList<Object> dataOfCategory) {
        super(context, resource, legendEntries);

        this.Context = context;
        this.resource = resource;
        this.legendEntryArrayList = legendEntries;
        this.percentage = percentage;
        this.costOfCategory = costOfCategory;
        this.dataOfCategory = dataOfCategory;
    }

    //smth
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(this.Context).inflate(resource, null);

        DecimalFormat decimalFormat = new DecimalFormat("#.###");

        LegendEntry legendEntry = this.getItem(position);


        View colorOfCategory = v.findViewById(R.id.rectangleForCategoryColor);
        colorOfCategory.setBackgroundColor(legendEntry.formColor);


        //________________________________________
        //сделали так потому что при использование лейбла в конструкотре entire все ужасно вышлядит и иконка сливается с названием.
        // А visibility не делается, вообщем только так

        TextView lblNameOfCategory = v.findViewById(R.id.lblNameOfCategory);

        String name = "";
        switch ((int)dataOfCategory.get(position)) {
            case 0:
                name = "Продукты";
                break;
            case 1:
                name = "Путешествия";
                break;
            case 2:
                name = "Транспорт";
                break;
            case 3:
                name = "Автомобиль";
                break;
            case 4:
                name = "Одежда";
                break;
            case 5:
                name = "Долги";
                break;
            case 6:
                name = "Инвестиции";
                break;
            case 7:
                name = "Цели";
                break;
            case 8:
                name = "Жилье";
                break;
            case 9:
                name = "Развлечения и досуг";
                break;
            case 10:
                name = "Красота и здоровье";
                break;
            case 11:
                name = "Покупки";
                break;
            case 12:
                name = "Прочее";
                break;
        }

        //________________________________________



        lblNameOfCategory.setText(name);


        TextView lblCostOfCategory = v.findViewById(R.id.lblCostOfCategory);
        lblCostOfCategory.setText(decimalFormat.format(costOfCategory));

        TextView lblCurrencyOfCategoryCost = v.findViewById(R.id.lblCurrencyOfCategoryCost);
        Auth auth = Auth.getInstance();
        lblCurrencyOfCategoryCost.setText(auth.getCurrentCurrency().getLabel());

        TextView lblPercentageOfCategory = v.findViewById(R.id.lblPercentageOfCategory);
        lblPercentageOfCategory.setText(decimalFormat.format(percentage));


        return v;
    }
}
