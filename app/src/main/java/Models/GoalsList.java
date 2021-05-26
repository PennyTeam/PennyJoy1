package Models;

import java.util.ArrayList;

public class GoalsList extends ArrayList<Goal> {
    private GoalsList(){

    }

    private static class GoalListHolder{
        private final static GoalsList instance = new GoalsList();
    }

    public static GoalsList getInstance(){
        return GoalListHolder.instance;
    }





}
