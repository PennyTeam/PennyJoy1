package Providers;

import android.net.http.HttpResponseCache;
import android.util.Log;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Scanner;

import Interfaces.OnCurrencyConvertRetrievedListener;
import Models.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CurrencyProvider {

    String result="";
    public void setNewCurrency(OnCurrencyConvertRetrievedListener listener, String fromC, String toC) {


        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://currency-converter18.p.rapidapi.com/api/v1/convert?from="+fromC+"&to="+toC+"&amount=1")
                .get()
                .addHeader("x-rapidapi-key", "99754c9422mshf63cd6afa844028p1cfe51jsn42bd68e9c9c7")
                .addHeader("x-rapidapi-host", "currency-converter18.p.rapidapi.com")
                .build();


        {
            try {
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.isSuccessful()) {

                            String myResponse =response.body().string();

                            for (int i = myResponse.lastIndexOf("\"") + 2; ; i++) {
                                if (myResponse.charAt(i) == '}') {
                                    break;
                                }
                                result += myResponse.charAt(i);
                            }
                            DecimalFormat decimalFormat = new DecimalFormat( "#.###" );
                            result=decimalFormat.format(Double.parseDouble(result));


                            listener.onRetrieved(Double.parseDouble(result));


                        }
                    }
                });

            } catch (Exception e) {

            }

        }

    }

}
