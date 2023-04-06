package com.example.contactsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nameEdit;
    EditText developerEdit;
    Spinner spinner;
    DatabaseControl control;
    TextView resultView;
    Button getButton;
    Button addButton;
    Button deleteButton;
    Button getButton2;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        control = new DatabaseControl(this);

        nameEdit = findViewById(R.id.nameEdit);
        developerEdit = findViewById(R.id.developerEdit);
        spinner = findViewById(R.id.spinner);
        resultView = findViewById(R.id.resultView);
        getButton = findViewById(R.id.getButton);
        getButton2 = findViewById(R.id.getButton2);
        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);
        recyclerView = findViewById(R.id.recyclerView);

        // Gets the date
        getButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 control.open();
                 String date = control.getDate(nameEdit.getText().toString());
                 control.close();
                 resultView.setText(date);
             }
         });

        // Gets the developer
        getButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                control.open();
                String developer = control.getDeveloper(nameEdit.getText().toString());
                control.close();
                resultView.setText(developer);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                control.open();
                control.delete(name);
                //Log.d("MainActivity", "delete num: " + control.delete(name));
                boolean itWorked = control.delete(name) == 0;
                control.close();
                if (itWorked) {
                    Toast.makeText(getApplicationContext(), "Deleted " + name, Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "FAILED " + name, Toast.LENGTH_SHORT).show();
                onResume();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                String developer = developerEdit.getText().toString();
                String date = ((TextView) spinner.getSelectedView()).getText().toString();
                control.open();
                boolean itWorked = control.insert(name, date, developer);
                control.close();
                if (itWorked) {
                    Toast.makeText(getApplicationContext(), "Added " + name + " " + date + " " + developer, Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "FAILED " + name + " " + date + " " + developer, Toast.LENGTH_SHORT).show();
                onResume();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.states, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        control.open();
        String[] nameArray = control.getAllNamesArray();
        control.close();
        recyclerView.setAdapter(new CustomAdapter(nameArray));
    }

}