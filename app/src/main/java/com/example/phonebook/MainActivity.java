package com.example.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    DataBaseHelper dataBaseHelper;
    EditText editTextFirstName,editTextLastName,editTextAddress,editTextPhone;
    Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBaseHelper = new DataBaseHelper(this);

        editTextFirstName = findViewById(R.id.edit_text_firstname);
        editTextLastName = findViewById(R.id.edit_text_lastname);
        editTextAddress = findViewById(R.id.edit_text_adress);
        editTextPhone = findViewById(R.id.edit_text_pnumber);

        buttonAdd = findViewById(R.id.btn_add);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = editTextFirstName.getText().toString().trim();
                String lastname = editTextLastName.getText().toString().trim();
                String address = editTextAddress.getText().toString().trim();
                String phonenumber = editTextPhone.getText().toString().trim();

                if(firstname.isEmpty() && lastname.isEmpty() && address.isEmpty() && phonenumber.isEmpty()){
                    Toast.makeText(MainActivity.this, "Fill the required fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(dataBaseHelper.addContact(firstname,lastname,address,Integer.parseInt(phonenumber))){
                    Toast.makeText(MainActivity.this, "Contact saved", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Not saved", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }
}
