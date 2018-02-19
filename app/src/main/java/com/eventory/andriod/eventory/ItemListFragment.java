package com.eventory.andriod.eventory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Michael on 2/19/2018.
 */

public class ItemListFragment extends Fragment {
    private RecyclerView mItemRecyclerView;
    private ItemAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanecState){
        View view = inflater.inflate(R.layout.fragment_item_list,container,false);
        mItemRecyclerView = (RecyclerView)view.findViewById(R.id.item_recycler_view);
        mItemRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI(){
        Inventory inventory = Inventory.get(getActivity());
        List<Item> items = inventory.getItems();

        mAdapter = new ItemAdapter(items);
        mItemRecyclerView.setAdapter(mAdapter);
    }

    public class ItemHolder extends RecyclerView.ViewHolder{

        public ItemHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item,parent,false));
        }
    }

    public class ItemAdapter extends RecyclerView.Adapter<ItemHolder>{

        private List<Item> mItems;

        public ItemAdapter(List<Item> items){
            mItems = items;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new ItemHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position){

        }

        @Override
        public int getItemCount(){
            return mItems.size();
        }
    }
}
