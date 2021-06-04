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
        //проверка на то что юзер хоть что-то купил
        if((int)dataOfCategory.get(position) != -1) {
            //инициализируем переменные
            DecimalFormat decimalFormat = new DecimalFormat("#.###");


            View colorOfCategory = v.findViewById(R.id.rectangleForCategoryColor);
            colorOfCategory.setBackgroundColor(legendEntry.formColor);


            TextView lblCostOfCategory = v.findViewById(R.id.lblCostOfCategory);


            TextView lblCurrencyOfCategoryCost = v.findViewById(R.id.lblCurrencyOfCategoryCost);
            Auth auth = Auth.getInstance();
            lblCurrencyOfCategoryCost.setText(auth.getCurrentCurrency().getLabel());

            TextView lblPercentageOfCategory = v.findViewById(R.id.lblPercentageOfCategory);


            //________________________________________
            //сделали так потому что при использование лейбла в конструкотре entire все ужасно вышлядит и иконка сливается с названием.
            // А visibility не делается, вообщем только так

            TextView lblNameOfCategory = v.findViewById(R.id.lblNameOfCategory);

            String name = "";
            String cost=decimalFormat.format(costOfCategoryList.get((int) dataOfCategory.get(position)));

            switch ((int) dataOfCategory.get(position)) {
                case 0:
                    name = "Продукты";

                    if(cost.length() > 5){
                        String res="";
                        for(int i=0;i<=5;i++){
                            if( i == 5 ){
                                res+="...";
                                break;
                            }
                            res+=cost.charAt(i);
                        }
                        lblCostOfCategory.setText(res);
                    }else {
                        lblCostOfCategory.setText(cost);
                    }

                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    break;
                case 1:
                    if(cost.length() > 5){
                        String res="";
                        for(int i=0;i<=5;i++){
                            if( i == 5 ){
                                res+="...";
                                break;
                            }
                            res+=cost.charAt(i);
                        }
                        lblCostOfCategory.setText(res);
                    }else {
                        lblCostOfCategory.setText(cost);
                    }
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    name = "Путешествия";
                    break;
                case 2:
                    if(cost.length() > 5){
                        String res="";
                        for(int i=0;i<=5;i++){
                            if( i == 5 ){
                                res+="...";
                                break;
                            }
                            res+=cost.charAt(i);
                        }
                        lblCostOfCategory.setText(res);
                    }else {
                        lblCostOfCategory.setText(cost);
                    }
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    name = "Транспорт";
                    break;
                case 3:
                    if(cost.length() > 5){
                        String res="";
                        for(int i=0;i<=5;i++){
                            if( i == 5 ){
                                res+="...";
                                break;
                            }
                            res+=cost.charAt(i);
                        }
                        lblCostOfCategory.setText(res);
                    }else {
                        lblCostOfCategory.setText(cost);
                    }
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    name = "Автомобиль";
                    break;
                case 4:
                    if(cost.length() > 5){
                        String res="";
                        for(int i=0;i<=5;i++){
                            if( i == 5 ){
                                res+="...";
                                break;
                            }
                            res+=cost.charAt(i);
                        }
                        lblCostOfCategory.setText(res);
                    }else {
                        lblCostOfCategory.setText(cost);
                    }
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    name = "Одежда";
                    break;
                case 5:
                    if(cost.length() > 5){
                        String res="";
                        for(int i=0;i<=5;i++){
                            if( i == 5 ){
                                res+="...";
                                break;
                            }
                            res+=cost.charAt(i);
                        }
                        lblCostOfCategory.setText(res);
                    }else {
                        lblCostOfCategory.setText(cost);
                    }
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    name = "Долги";
                    break;
                case 6:
                    if(cost.length() > 5){
                        String res="";
                        for(int i=0;i<=5;i++){
                            if( i == 5 ){
                                res+="...";
                                break;
                            }
                            res+=cost.charAt(i);
                        }
                        lblCostOfCategory.setText(res);
                    }else {
                        lblCostOfCategory.setText(cost);
                    }
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    name = "Инвестиции";
                    break;
                case 7:
                    if(cost.length() > 5){
                        String res="";
                        for(int i=0;i<=5;i++){
                            if( i == 5 ){
                                res+="...";
                                break;
                            }
                            res+=cost.charAt(i);
                        }
                        lblCostOfCategory.setText(res);
                    }else {
                        lblCostOfCategory.setText(cost);
                    }
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    name = "Цели";
                    break;
                case 8:
                    if(cost.length() > 5){
                        String res="";
                        for(int i=0;i<=5;i++){
                            if( i == 5 ){
                                res+="...";
                                break;
                            }
                            res+=cost.charAt(i);
                        }
                        lblCostOfCategory.setText(res);
                    }else {
                        lblCostOfCategory.setText(cost);
                    }
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    name = "Жилье";
                    break;
                case 9:
                    if(cost.length() > 5){
                        String res="";
                        for(int i=0;i<=5;i++){
                            if( i == 5 ){
                                res+="...";
                                break;
                            }
                            res+=cost.charAt(i);
                        }
                        lblCostOfCategory.setText(res);
                    }else {
                        lblCostOfCategory.setText(cost);
                    }
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    name = "Развлечения и досуг";
                    break;
                case 10:
                    if(cost.length() > 5){
                        String res="";
                        for(int i=0;i<=5;i++){
                            if( i == 5 ){
                                res+="...";
                                break;
                            }
                            res+=cost.charAt(i);
                        }
                        lblCostOfCategory.setText(res);
                    }else {
                        lblCostOfCategory.setText(cost);
                    }
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    name = "Красота и здоровье";
                    break;
                case 11:
                    if(cost.length() > 5){
                        String res="";
                        for(int i=0;i<=5;i++){
                            if( i == 5 ){
                                res+="...";
                                break;
                            }
                            res+=cost.charAt(i);
                        }
                        lblCostOfCategory.setText(res);
                    }else {
                        lblCostOfCategory.setText(cost);
                    }
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    name = "Покупки";
                    break;
                case 12:
                    if(cost.length() > 5){
                        String res="";
                        for(int i=0;i<=5;i++){
                            if( i == 5 ){
                                res+="...";
                                break;
                            }
                            res+=cost.charAt(i);
                        }
                        lblCostOfCategory.setText(res);
                    }else {
                        lblCostOfCategory.setText(cost);
                    }
                    lblPercentageOfCategory.setText(decimalFormat.format(percentageList.get((int) dataOfCategory.get(position))));
                    name = "Прочее";
                    break;
            }

            //________________________________________


            lblNameOfCategory.setText(name);





            return v;
        }
        return v;
    }
}
