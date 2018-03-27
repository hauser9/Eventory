package com.eventory.andriod.eventory;



import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WastedItemsListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){ return new WastedItemsListFragment();}
}