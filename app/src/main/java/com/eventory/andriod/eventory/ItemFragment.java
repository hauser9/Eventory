package com.eventory.andriod.eventory;

import android.content.Intent;
import android.os.Build;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

/**
 * Created by Michael on 2/26/2018.
 */

public class ItemFragment extends Fragment {


    private static final String URL_HEADER = "https://api.upcitemdb.com/prod/trial/lookup?upc=";
    private static final String ARG_ITEM_ID = "item_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;

    private Item mItem;
    public static EditText mNameField;
    public static EditText mQuantityField;
    public static Button mDateButton;

    public static ItemFragment newInstance(UUID itemId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_ITEM_ID,itemId);

        ItemFragment fragment = new ItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID itemId = (UUID) getArguments().getSerializable(ARG_ITEM_ID);
        mItem = Inventory.get(getActivity()).getItem(itemId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_item, container, false);

        mNameField = (EditText) v.findViewById(R.id.item_name);
        mNameField.setText(mItem.getName());
        mQuantityField = (EditText) v.findViewById(R.id.item_quantity);
        mQuantityField.setText("" + mItem.getQuantity());
        mDateButton = (Button) v.findViewById(R.id.item_date);
        updateDate();

        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //intentionally blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mItem.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //also left blank
            }
        });

        mQuantityField.addTextChangedListener(new TextWatcher() {
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
                int number = Integer.parseInt(quantity);
                mItem.setQuantity(number);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentManager manager = getFragmentManager();
                //TODO fix this when date is added to sql
                //DatePickerFragment dialog = DatePickerFragment.newInstance(mItem.getDate());
                //dialog.setTargetFragment(ItemFragment.this,REQUEST_DATE);
                //dialog.show(manager,DIALOG_DATE);

            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == REQUEST_DATE){
            Date date = (date)  intent.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            //TODO uncomment when date is added to sql
            //mItem.setDate(date);
            updateDate();
        }
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,intent);
        if(scanningResult != null)
        {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            String upcHttp = URL_HEADER + scanContent;
            FetchData process = new FetchData(upcHttp, getActivity());
            process.execute();


        }
        else{
                Toast.makeText(getActivity(),R.string.no_scan_data,Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDate() {

        //TODO fix this when date is added to sql
        // mDateButton.setText(mItem.getDate().toString());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.fragment_item,menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.delete_item:
                Inventory.get(getActivity()).deleteItem(mItem);
                getActivity().finish();
                return true;
            case R.id.scan_barcode:
                IntentIntegrator.forSupportFragment(this).initiateScan();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Inventory.get(getActivity()).updateItem(mItem);
    }

}
