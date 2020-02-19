package com.example.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

public class PhonebookActivity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;
    List<Phonebook> phonebooks;
    List<Phonebook> searchedlist;
    String searchText;
//    ListView listView;

    SwipeMenuListView listView;
    Phonebook contact;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonebook);

        listView = findViewById(R.id.phone_list_view);
        searchView = findViewById(R.id.search_view);
        phonebooks = new ArrayList<>();
        searchedlist = new ArrayList<>();
        dataBaseHelper = new DataBaseHelper(this);
        loadContacts();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                contact = phonebooks.get(position);
                String fn = contact.getFirstName();
                String ln = contact.getLastName();
                String ad = contact.getAddress();
                String ph = String.valueOf(contact.getPhoneNumber());
                int cid  = contact.getId();
                boolean selected = true;
                Intent intent = new Intent(PhonebookActivity.this,MainActivity.class);
                intent.putExtra("fname",fn);
                intent.putExtra("lname",ln);
                intent.putExtra("address",ad);
                intent.putExtra("phone",ph);
                intent.putExtra("cid",cid);
                intent.putExtra("selected",selected);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);

            }
        });


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



            }
        };

        listView.setMenuCreator(swipeMenuCreator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index){
                    case 0:


                            if(dataBaseHelper.deleteContact(phonebooks.get(position).getId())){
                                loadContacts();
                                ListViewAdaptor listViewAdaptor = new ListViewAdaptor(PhonebookActivity.this,R.layout.display_phonebook,phonebooks);
                                listView.setAdapter(listViewAdaptor);
                                break;

                            }
                            
                }

                return false;
            }

        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchText = newText;

                if(!newText.isEmpty()){
                    searchedlist.clear();
                    for(int i = 0;i < phonebooks.size();i++){
                        Phonebook getcontact =  phonebooks.get(i);
                        if(getcontact.firstName.contains(newText)){
                            searchedlist.add(getcontact);
                        }
                    }

                    ListViewAdaptor listViewAdaptor = new ListViewAdaptor(PhonebookActivity.this,R.layout.display_phonebook,searchedlist);
                    listView.setAdapter(listViewAdaptor);
                }

                if(newText.isEmpty()){
                    ListViewAdaptor listViewAdaptor = new ListViewAdaptor(PhonebookActivity.this,R.layout.display_phonebook,phonebooks);
                    listView.setAdapter(listViewAdaptor);
                }




                return false;



            }
        });
    }


    private void loadContacts(){
        /*
        String sql = "select * from employees";
        Cursor cursor = mDatabase.rawQuery(sql,null);

         */

        Cursor cursor  = dataBaseHelper.getallcontacts();
        phonebooks.clear();

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
