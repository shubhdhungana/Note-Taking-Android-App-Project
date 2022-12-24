package com.example.babybuy.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.babybuy.Adapter.ManageProductAdapter;
import com.example.babybuy.Database.Database;
import com.example.babybuy.Model.ProductDataModel;
import com.example.babybuy.R;

import java.util.ArrayList;


public class ManageProductFragment extends Fragment {

    public ManageProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        Button purchasedbtn = view.findViewById(R.id.purchasedprodfrag);
        Button tobuybtn = view.findViewById(R.id.tobuyprod);

        // Load purchased product when fragment is called
       try{
           int i = 1;
           if (i == 1) {
               int productsts = 1;
               Database db = new Database(getActivity());
               ProductDataModel productDataModel = new ProductDataModel();
               ArrayList<ProductDataModel> alldata = db.productfetchdataforpurchased(productsts);
               RecyclerView product_recy = view.findViewById(R.id.pruductmanagerecy);
               product_recy.setLayoutManager(new LinearLayoutManager(getActivity()));
               ManageProductAdapter adapter = new ManageProductAdapter(getActivity(), alldata);
               product_recy.setAdapter(adapter);
           }
       }catch (Exception exp){
           Toast.makeText(getActivity(), "No item found", Toast.LENGTH_SHORT).show();
       }



        // Load purchased product when purchased button is clicked
        purchasedbtn.setOnClickListener(v -> {
            int productsts = 1;
            Database db = new Database(getActivity());
            ProductDataModel productDataModel = new ProductDataModel();
            ArrayList<ProductDataModel> alldata = db.productfetchdataforpurchased(productsts);
            RecyclerView product_recy = view.findViewById(R.id.pruductmanagerecy);
            product_recy.setLayoutManager(new LinearLayoutManager(getActivity()));
            ManageProductAdapter adapter = new ManageProductAdapter(getActivity(), alldata);
            product_recy.setAdapter(adapter);
        });


        //Load tobuy product when tobuy is clicked
        try {
            tobuybtn.setOnClickListener(v -> {
                int productsts = -1;
                Database db = new Database(getActivity());
                ProductDataModel productDataModel = new ProductDataModel();
                ArrayList<ProductDataModel> alldata = db.productfetchdataforpurchased(productsts);
                RecyclerView product_recy = view.findViewById(R.id.pruductmanagerecy);
                product_recy.setLayoutManager(new LinearLayoutManager(getActivity()));
                ManageProductAdapter adapter = new ManageProductAdapter(getActivity(), alldata);
                product_recy.setAdapter(adapter);
            });
        }
        catch (Exception exp){
            Toast.makeText(getActivity(), "No item found", Toast.LENGTH_SHORT).show();
        }


        return view;

    }

}