package com.example.phonebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListViewAdaptor extends ArrayAdapter {

    Context mContext;
    int layoutRes;
    List<Phonebook> phonebookList;


    public ListViewAdaptor(@NonNull Context context, int resource, List<Phonebook> phonebookList) {
        super(context, resource,phonebookList);
        this.mContext = mContext;
        this.layoutRes = layoutRes;
        this.phonebookList = phonebookList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(layoutRes,null);

        TextView name = view.findViewById(R.id.text_view_name);
        TextView phone = view.findViewById(R.id.text_view_phone);
        TextView address = view.findViewById(R.id.text_view_adress);

        Phonebook phonebook = phonebookList.get(position);

        name.setText(phonebook.getFirstName() + " " + phonebook.getLastName());
        phone.setText(phonebook.getPhoneNumber());
        address.setText(phonebook.getAddress());



        return view;
    }
}
