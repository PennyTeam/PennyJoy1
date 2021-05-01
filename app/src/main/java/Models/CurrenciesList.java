package Models;

import java.util.ArrayList;

public class CurrenciesList {
    private final static ArrayList<Currency> currencies=new ArrayList<Currency>();

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
