package com.example.patrick.uhacproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.patrick.uhacproject.config.session;

public class MainActivity extends AppCompatActivity {

    ImageView chicken,spag,fries;
    TextView quantityC,quantityS,quantityF;

    TextView totalPrice;
    ImageButton order;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // for imageview
        chicken=(ImageView)findViewById(R.id.image_C);
        spag=(ImageView)findViewById(R.id.image_S);
        fries=(ImageView)findViewById(R.id.image_F);
        // for textview
        quantityC=(TextView) findViewById(R.id.tv_c);
        quantityS=(TextView) findViewById(R.id.tv_s);
        quantityF=(TextView) findViewById(R.id.tv_f);
        // for edittext

        // for textview of table number
        totalPrice=(TextView)findViewById(R.id.totalprice);
        // for order button
        order=(ImageButton)findViewById(R.id.btn_order);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyyhhmmssSSS");
                    session = sdf.format(Calendar.getInstance().getTime());
                    insertOrder();

            }
        });

        chicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Chicken");
                final EditText et_chicken = new EditText(MainActivity.this);
                et_chicken.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(et_chicken);
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        quantityC.setText(et_chicken.getText().toString());
                        config.chickenQuantity=Integer.valueOf(quantityC.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel",null);
                builder.show();
            }
        });
        spag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Spaghetti");
                final EditText et_chicken = new EditText(MainActivity.this);
                et_chicken.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(et_chicken);
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        quantityS.setText(et_chicken.getText().toString());
                        config.spagQuantity=Integer.valueOf(quantityS.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel",null);
                builder.show();
            }
        });
        fries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Fries");
                final EditText et_chicken = new EditText(MainActivity.this);
                et_chicken.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(et_chicken);
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        quantityF.setText(et_chicken.getText().toString());
                        config.friesQuantity=Integer.valueOf(quantityF.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel",null);
                builder.show();
            }
        });


        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        computeTotalPrice();
                    }
                });
            }
        },100,100);


    }
    public void insertOrder(){
        String query=config.url + "?operation=insert_order&session=" + config.session + "&chicken=" + config.chickenQuantity + "&spag=" + config.spagQuantity + "&fries=" + config.friesQuantity + "&total=" + config.totalprice + "&status=unpaid&food_status=uncook";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, query, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent x = new Intent(MainActivity.this,Payment.class);
                startActivity(x);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(stringRequest);
        rq.start();
    }
    public void computeTotalPrice(){
        int chicken,spag,fries;

        chicken=Integer.valueOf(quantityC.getText().toString())*30;
        spag=Integer.valueOf(quantityS.getText().toString())*20;
        fries=Integer.valueOf(quantityF.getText().toString())*10;

        config.totalprice=chicken+spag+fries;

        totalPrice.setText("Total Price: "+String.valueOf(config.totalprice));

    }

}
