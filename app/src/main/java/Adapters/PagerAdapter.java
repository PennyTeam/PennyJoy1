package Adapters;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import Fragments.AddGoodTab;
import Fragments.AdviserTab;
import Fragments.TimerTab;


public class PagerAdapter extends FragmentStatePagerAdapter {
    private int numberOfTabs;

    public PagerAdapter(FragmentManager fm, int numberOfTabs){
        super(fm);
        this.numberOfTabs=numberOfTabs;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                TimerTab timerTab=new TimerTab();
                return timerTab;
            case 1:
                AddGoodTab addGoodTab=new AddGoodTab();
                return addGoodTab;
            case 2:
                AdviserTab adviserTab=new AdviserTab();
                return adviserTab;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return this.numberOfTabs;
    }
}
