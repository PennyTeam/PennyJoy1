package Models;

import java.util.ArrayList;

public class GoodsList extends ArrayList<Good>{

    private GoodsList(){

    }

    private static class GoodsListHolder{
        private final static GoodsList instance = new GoodsList();
    }

    public static GoodsList getInstance(){
        return GoodsListHolder.instance;
    }

}
