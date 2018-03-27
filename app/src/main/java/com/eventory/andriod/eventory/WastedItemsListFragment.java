package com.eventory.andriod.eventory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

/**
 * Created by Michael on 2/19/2018.
 */

public class WastedItemsListFragment extends Fragment {
    private RecyclerView mItemRecyclerView;
    private ItemAdapter mAdapter;
    private static final String STARTED_FROM_NEW = "NEW_POST";
    private static final String STARTED_FROM_NEW2 = "STATISTICS";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_removed_item_list,container,false);
        mItemRecyclerView = (RecyclerView)view.findViewById(R.id.item_recycler_view);
        mItemRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI(){
        RemovedInventory removedInventory = RemovedInventory.get(getActivity());
        List<RemovedItem> items = removedInventory.getRemovedWastedItems();

        if(mAdapter == null) {
            mAdapter = new ItemAdapter(items);
            mItemRecyclerView.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.setItems(items);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_removed_item_list_menu,menu);
    }

    //TODO: menu new item data will be lost when rotated need to fix this once database is added
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.mAllItems:
                Intent removedIntent = new Intent(getActivity(), RemovedItemListActivity.class);
                startActivity(removedIntent);
                return true;
            case R.id.mHome:
                Intent homeIntent = new Intent(getActivity(), ItemListActivity.class);
                startActivity(homeIntent);
                return true;
            case R.id.mWasted:
                Intent wastedIntent = new Intent(getActivity(), WastedItemsListActivity.class);
                startActivity(wastedIntent);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mNameTextView;
        private TextView mQuantityTextView;
        private RemovedItem mItem;

        public ItemHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item,parent,false));
            itemView.setOnClickListener(this);

            mNameTextView = (TextView) itemView.findViewById(R.id.item_name);
            mQuantityTextView = (TextView) itemView.findViewById(R.id.item_quantity);
        }

        public void bind(RemovedItem item){
            mItem = item;
            String name,quantityString;
            int quantity;

            if(!mItem.getName().equals(""))
            {
                name = "Name: "+ mItem.getName();
                quantityString = "Quantity: "+ mItem.getQuantity();
            }else
            {
                name = mItem.getName();
                quantity =  mItem.getQuantity();
                quantityString = "" + quantity;
            }
            mNameTextView.setText(name);
            mQuantityTextView.setText(""+quantityString);
        }

        @Override
        public void onClick(View view){
            UUID id = mItem.getId();
            RemovedItem item = RemovedInventory.get(getActivity()).getRemovedItem(id);
            Intent intent = ItemActivity.newIntent(getActivity(),mItem.getId());
            intent.putExtra(STARTED_FROM_NEW,false);
            startActivity(intent);
        }
    }

    public class ItemAdapter extends RecyclerView.Adapter<ItemHolder>{

        private List<RemovedItem> mItems;

        public ItemAdapter(List<RemovedItem> items){
            mItems = items;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new ItemHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position){
            RemovedItem item = mItems.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount(){
            return mItems.size();
        }

        public void setItems(List<RemovedItem> items){
            mItems = items;
        }
    }
}
