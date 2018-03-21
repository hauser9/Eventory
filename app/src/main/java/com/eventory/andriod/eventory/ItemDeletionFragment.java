package com.eventory.andriod.eventory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.UUID;

/**
 * Created by Michael on 3/20/2018.
 */

public class ItemDeletionFragment extends Fragment {

    private static Item mItem;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_item_delete, container, false);
        mRemovalSpinner = (Spinner) v.findViewById(R.id.reason_for_removing);
        mRemoveButton = (Button) v.findViewById(R.id.remove_button);
        mQuantityText = (EditText) v.findViewById(R.id.item_quantity_to_remove);

        adapter = ArrayAdapter.createFromResource(getActivity(),R.array.reasons,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRemovalSpinner.setAdapter(adapter);
        mRemovalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Here is where you will get selection
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
                    Toast.makeText(getActivity(),"Only posistive integers are accepted in this field",Toast.LENGTH_LONG).show();
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
                else if(quantityToRemove == currentQuantity)
                {
                    Inventory.get(getActivity()).deleteItem(mItem);
                }
                else{
                    int newQuantity = currentQuantity - quantityToRemove;
                    mItem.setQuantity(newQuantity);
                    Inventory.get(getActivity()).updateItem(mItem);
                    getActivity().finish();
                }




            }
        });

        return v;
    }

}
