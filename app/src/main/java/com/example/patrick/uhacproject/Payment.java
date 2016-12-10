package com.example.patrick.uhacproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Payment extends AppCompatActivity {
    Button bank,cash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        bank=(Button)findViewById(R.id.button5);
        cash=(Button)findViewById(R.id.button6);

        bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(Payment.this,BankPayment.class);
                startActivity(x);
            }
        });

        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(Payment.this,CashPayment.class);
                startActivity(x);
            }
        });

    }
}
