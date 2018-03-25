package com.eventory.andriod.eventory;


import android.support.v4.app.Fragment;

/**
 * Created by Michael on 2/19/2018.
 */

public class ItemListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){ return new ItemListFragment();
    }
}
