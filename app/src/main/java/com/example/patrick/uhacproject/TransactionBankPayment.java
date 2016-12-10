package com.example.patrick.uhacproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class TransactionBankPayment extends AppCompatActivity {

    TextView balance,totalOrder,afterPurchase;
    Button purchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_bank_payment);

        balance=(TextView)findViewById(R.id.textView);
        totalOrder=(TextView)findViewById(R.id.textView2);
        afterPurchase=(TextView)findViewById(R.id.textView4);

        purchase=(Button)findViewById(R.id.button);

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                purchase((config.balance-config.totalprice));
            }
        });

        balance.setText("Current balance: "+config.balance);
        totalOrder.setText("Total amount of orders: "+config.totalprice);
        afterPurchase.setText("Balance after purchase: "+(config.balance-config.totalprice));


    }
    public void purchase(int newbalance){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, config.url+"?operation=purchase&balance="+newbalance+"&account_number="+config.account_number+"&session="+config.session, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(TransactionBankPayment.this, response, Toast.LENGTH_SHORT).show();
                Intent x = new Intent(TransactionBankPayment.this,OrderOverview.class);
                startActivity(x);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TransactionBankPayment.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(stringRequest);
        rq.start();
    }
}
