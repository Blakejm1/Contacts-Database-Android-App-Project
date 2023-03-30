package com.example.contactsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nameEdit;
    Spinner spinner;
    DatabaseControl control;
    TextView resultView;
    Button getButton;
    Button addButton;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        control = new DatabaseControl(this);

        nameEdit = findViewById(R.id.nameEdit);
        spinner = findViewById(R.id.spinner);
        resultView = findViewById(R.id.resultView);
        getButton = findViewById(R.id.getButton);
        addButton = findViewById(R.id.addButton);
        recyclerView = findViewById(R.id.recyclerView);

        getButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 control.open();
                 String state = control.getState(nameEdit.getText().toString());
                 control.getState(nameEdit.getText().toString());
                 control.close();
                 resultView.setText(state);
             }
         });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                String state = ((TextView) spinner.getSelectedView()).getText().toString();
                control.open();
                boolean itWorked = control.insert(name, state);
                control.close();
                if (itWorked) {
                    Toast.makeText(getApplicationContext(), "Added " + name + " " + state, Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "FAILED " + name + " " + state, Toast.LENGTH_SHORT).show();
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
        resultView.setText(nameArray[1]);
        recyclerView.setAdapter(new CustomAdapter(nameArray));
    }

}