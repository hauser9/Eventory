package com.eventory.andriod.eventory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Michael on 3/20/2018.
 */

public class ItemDeletionFragment extends Fragment {

    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;

    private static Item mItem;
    private static RemovedItem mRemovedItem;
    private static List<RemovedItem> sRemovedItems;
    public static Button mRemoveDateButton;
    public static Spinner mRemovalSpinner;
    public static Button mRemoveButton;
    public static EditText mQuantityText;
    private static final String ARG_ITEM_ID = "item_id";
    private static int quantityToRemove;
    ArrayAdapter<CharSequence> adapter;

    public static ItemDeletionFragment newInstance(UUID itemId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_ITEM_ID,itemId);

        ItemDeletionFragment fragment = new ItemDeletionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID itemId = (UUID) getArguments().getSerializable(ARG_ITEM_ID);
        mItem = Inventory.get(getActivity()).getItem(itemId);
        mRemovedItem = new RemovedItem();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_item_delete, container, false);
        mRemovalSpinner = (Spinner) v.findViewById(R.id.reason_for_removing);
        mRemoveButton = (Button) v.findViewById(R.id.remove_button);
        mQuantityText = (EditText) v.findViewById(R.id.item_quantity_to_remove);
        mRemoveDateButton = (Button) v.findViewById(R.id.remove_date_button);
        mRemoveDateButton.setText(mRemovedItem.getDate().toString());
        mRemoveButton.setBackgroundColor(Color.RED);
        mRemoveButton.setText("Remove From Inventory");

        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstDate = "2018-02-27";
        String secondDate = "2018-03-23";
        Date startDate =  new Date();
        Date endDate = new Date();
        try {
            startDate = sdf.parse(firstDate);
            endDate = sdf.parse(secondDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<RemovedItem> inRange = RemovedInventory.get(getActivity()).getRemovedItems();
       inRange =  RemovedInventory.inDateRange(inRange,startDate,endDate);
        if(inRange.size() > 0)
        {
            mRemoveButton.setText(""  + inRange.size());
        }*/

        adapter = ArrayAdapter.createFromResource(getActivity(),R.array.reasons,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRemovalSpinner.setAdapter(adapter);
        mRemovalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Here is where you will get selection
                if(position == 0)
                {
                    mRemovedItem.setWaste(false);
                }
                else
                {
                    mRemovedItem.setWaste(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mQuantityText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String quantity = s.toString();
                if(quantity.equals(""))
                {
                    quantity = "0";
                }
                try{
                    quantityToRemove = Integer.parseInt(quantity);
                }
                catch (NumberFormatException e)
                {
                    Toast.makeText(getActivity(),"Only posistive integers are accepted in this field",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO need to add sql statements here to update deletion table
                int currentQuantity = mItem.getQuantity();
                if(quantityToRemove > currentQuantity){
                    Toast.makeText(getActivity(),"Error quantity to remove is greater than inventory",Toast.LENGTH_LONG).show();
                }
                else if(quantityToRemove == 0)
                {
                    Toast.makeText(getActivity(),"Enter a positive quantity",Toast.LENGTH_SHORT).show();
                }
                else if(quantityToRemove == currentQuantity)
                {
                    Inventory.get(getActivity()).deleteItem(mItem);
                }
                else{
                    mRemovedItem.setName(mItem.getName());
                    mRemovedItem.setQuantity(quantityToRemove);
                    mRemovedItem.setPrice(mItem.getPrice() * quantityToRemove);
                    mRemovedItem.setDate(new Date());
                    int newQuantity = currentQuantity - quantityToRemove;
                    mItem.setQuantity(newQuantity);
                    RemovedInventory.get(getActivity()).addRemovedItem(mRemovedItem);
                    Inventory.get(getActivity()).updateItem(mItem);
                    getActivity().finish();
                }




            }
        });

        mRemoveDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mRemovedItem.getDate());
                dialog.setTargetFragment(ItemDeletionFragment.this,REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK){
            return ;
        }

        if(requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mRemovedItem.setDate(date);
            mRemoveDateButton.setText(mRemovedItem.getDate().toString());
        }
    }

}
