package Models;

import java.util.ArrayList;

public class GoalsList extends ArrayList<Goal> {
    //private static ArrayList<Goal>goalArrayList = new ArrayList<>();
    private GoalsList(){

    }

    private static class GoalListHolder{
        private final static GoalsList instance = new GoalsList();
    }

    public static GoalsList getInstance(){
        return GoalListHolder.instance;
    }





}
