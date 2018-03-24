package com.eventory.andriod.eventory;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by Michael on 3/21/2018.
 */

public class ItemDeletionActivity extends SingleFragmentActivity {

    private static final String EXTRA_ITEM_ID = "com.eventory.android.eventory";

    public static Intent newIntent(Context packageContext, UUID itemId){
        Intent intent = new Intent(packageContext, ItemDeletionActivity.class);
        intent.putExtra(EXTRA_ITEM_ID,itemId);

        return intent;
    }


    @Override
    protected Fragment createFragment(){
        UUID itemId = (UUID) getIntent().getSerializableExtra(EXTRA_ITEM_ID);
        return ItemDeletionFragment.newInstance(itemId);
    }
}
