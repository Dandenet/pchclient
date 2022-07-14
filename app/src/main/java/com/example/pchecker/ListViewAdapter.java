package com.example.pchecker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<JSONObject> {

    int listLayout;
    ArrayList<JSONObject> usersList;
    Context context;

    public ListViewAdapter(Context context, int listLayout , int field, ArrayList< JSONObject> usersList) {
        super(context, listLayout, field, usersList);
        this.context = context;
        this.listLayout=listLayout;
        this.usersList = usersList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listViewItem = inflater.inflate(listLayout, null, false);
        TextView name = listViewItem.findViewById(R.id.textViewName);
        TextView price = listViewItem.findViewById(R.id.textViewPrice);
        try{
            name.setText(usersList.get(position).getJSONObject("product").getString("name"));
            price.setText(usersList.get(position).getJSONObject("product").getString("price") + " руб");
        }catch (JSONException je){
            je.printStackTrace();
        }
        return listViewItem;
    }



}
