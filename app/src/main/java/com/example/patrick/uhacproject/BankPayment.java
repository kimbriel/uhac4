package com.example.patrick.uhacproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BankPayment extends AppCompatActivity {


    EditText account_number,pin;
    Button verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_payment);

        account_number=(EditText)findViewById(R.id.editText5);
        pin=(EditText)findViewById(R.id.editText4);

        verify=(Button)findViewById(R.id.button4);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBalance();
            }
        });

    }
    public void setBalance(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, config.url + "?operation=login&account_number=" + account_number.getText().toString() + "&pin=" + pin.getText().toString(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject =(JSONObject) response.get(0);
                    config.balance=jsonObject.getInt("balance");
                    if(Integer.valueOf(config.balance)>0){
                        config.account_number=Integer.valueOf(account_number.getText().toString());
                        Intent x = new Intent(BankPayment.this,TransactionBankPayment.class);
                        startActivity(x);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BankPayment.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(jsonArrayRequest);
        rq.start();
    }
}
