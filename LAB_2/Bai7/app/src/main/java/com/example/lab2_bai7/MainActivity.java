package com.example.lab2_bai7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtName;
    CheckBox chbxManager;
    Button btnAdd;
    RecyclerView rcv_Employee;
    ArrayList<Employee> employees;
    EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = (EditText) findViewById(R.id.edtName);
        chbxManager = (CheckBox) findViewById(R.id.chbxManager);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        rcv_Employee = (RecyclerView) findViewById(R.id.rcv_Employee);
        employees = new ArrayList<Employee>();

        adapter = new EmployeeAdapter(this, R.layout.item_employee,employees);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv_Employee.setLayoutManager(linearLayoutManager);
        rcv_Employee.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                EditText id = findViewById(R.id.edtId);
                String tempID = id.getText().toString();
                Employee employee = new Employee();
                employee.setFullName(name);
                employee.setId(tempID);
                if (chbxManager.isChecked()) {
                    employee.setManager(true);
                } else {
                    employee.setManager(false);
                }
                if (!check(employee, employees)) {
                    employees.add(employee);
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("da ton tai")
                            .setPositiveButton("OK", null)
                            .show();
                }
            }
        });
    }
    private boolean check(Employee employee, ArrayList<Employee>temp){
        if(!temp.isEmpty()){
            for (Employee e : temp){
                if (e.getId().equals(employee.getId()))
                    return true;
            }
        }
        return false;
    }
}