package com.example.lab2_bai3;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText etId;
    EditText etName;
    RadioButton rd_chinhthuc, rdBtnKhong;
    RadioGroup rgType;
    Button btnNhap;
    //TextView tv2;
    ListView lvNV;
    ArrayList<Employee> employees;
    ArrayAdapter<Employee> adapter;
    Employee employee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etId = (EditText) findViewById(R.id.etId);
        etName = (EditText) findViewById(R.id.etName);
        rgType = (RadioGroup) findViewById(R.id.rgType);
        btnNhap = (Button) findViewById(R.id.btnNhap);
        lvNV = (ListView) findViewById(R.id.lv_NV);
        //tv2 = (TextView) findViewById(R.id.txtQLSV);
        employees = new ArrayList<Employee>();
        adapter = new ArrayAdapter<Employee>(this, android.R.layout.simple_list_item_1,employees);
        lvNV.setAdapter(adapter);

        btnNhap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int radId = rgType.getCheckedRadioButtonId();
                String id = etId.getText().toString();
                String name = etName.getText().toString();
                if (radId == R.id.rd_chinhthuc) {

                    employee = new EmployeeFulltime();
                } else {

                    employee = new EmployeeParttime();
                }

                employee.setId(id);
                employee.setName(name);

                employees.add(employee);

                adapter.notifyDataSetChanged();
            }
        });
    }
}