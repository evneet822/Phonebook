package com.example.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

public class PhonebookActivity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;
    List<Phonebook> phonebooks;
//    ListView listView;

    SwipeMenuListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonebook);

        listView = findViewById(R.id.phone_list_view);
        phonebooks = new ArrayList<>();
        dataBaseHelper = new DataBaseHelper(this);
        loadContacts();


        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem deleteitem = new SwipeMenuItem(getApplicationContext());
                deleteitem.setBackground(new ColorDrawable(Color.RED));
                deleteitem.setTitle("Delete");
                deleteitem.setTitleSize(15);
                deleteitem.setTitleColor(Color.WHITE);
                deleteitem.setWidth(170);

                menu.addMenuItem(deleteitem);

                SwipeMenuItem updateitem = new SwipeMenuItem(getApplicationContext());
                updateitem.setBackground(new ColorDrawable(Color.GRAY));
                updateitem.setWidth(170);
                updateitem.setTitle("Update");
                updateitem.setTitleSize(15);
                updateitem.setTitleColor(Color.WHITE);

                menu.addMenuItem(updateitem);

            }
        };

        listView.setMenuCreator(swipeMenuCreator);
    }


    private void loadContacts(){
        /*
        String sql = "select * from employees";
        Cursor cursor = mDatabase.rawQuery(sql,null);

         */

        Cursor cursor  = dataBaseHelper.getallcontacts();

        if(cursor.moveToFirst()){
            do{
//                employeeList.add(new Employee(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getDouble(4)));
                phonebooks.add(new Phonebook(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4)));
                System.out.println(cursor.getInt(0));
                System.out.println(cursor.getString(1));

            }while (cursor.moveToNext());
            cursor.close();

            //show items in list view
            //we use a custom adaptor to show employee

//            EmployeeAdaptor employeeAdaptor = new EmployeeAdaptor(this,R.layout.list_layout_employee,employeeList,databaseHelper);
//            listView.setAdapter(employeeAdaptor);

            ListViewAdaptor listViewAdaptor = new ListViewAdaptor(this,R.layout.display_phonebook,phonebooks);
            listView.setAdapter(listViewAdaptor);



        }
    }
}
