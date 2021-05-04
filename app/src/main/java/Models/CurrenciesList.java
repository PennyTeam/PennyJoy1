package Models;

import java.util.ArrayList;

public class CurrenciesList {
    private final static ArrayList<Currency> currencies=new ArrayList<Currency>();
    private CurrenciesList(){

    }

    //не оч работает, объект всегда один, но проверка на нулл не работает
    private static class CurrencyListHolder{
        private final static  CurrenciesList instance= new CurrenciesList();
    }
    public static CurrenciesList getInstance(){

        return CurrenciesList.CurrencyListHolder.instance;

    }
    public void init() {
        if(currencies.isEmpty()) {
            currencies.add(new Currency(0, "KZT", "₸"));
            currencies.add(new Currency(1, "USD", "$"));
            currencies.add(new Currency(2, "RUB", "₽"));
            currencies.add(new Currency(3, "EUR", "€"));

        }else{
            return;
        }
    }
    public ArrayList<Currency> getCurrencies(){
        return currencies;
    }
}
