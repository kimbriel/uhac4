package com.example.patrick.uhacproject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
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

public class OrderOverview extends AppCompatActivity {

    TextView tableNumber,transactionNumber,orderList,totalAmount,foodStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_overview);

        tableNumber=(TextView)findViewById(R.id.textView6);
        transactionNumber=(TextView)findViewById(R.id.textView9);
        orderList=(TextView)findViewById(R.id.textView10);
        totalAmount=(TextView)findViewById(R.id.textView8);
        foodStatus=(TextView)findViewById(R.id.textView3);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getOrder();
            }
        });

        // Add some
    }

    public void getOrder(){
        String q=config.url+"?operation=overview&session="+config.session.toString();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, q, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = (JSONObject)response.get(0);
                    tableNumber.setText(String.valueOf(jsonObject.getInt("table_number")));
                    transactionNumber.setText(String.valueOf(jsonObject.getInt("transac_id")));
                    String chicken="",spag="",fries="";
                    String list="Order list:";
                    if(jsonObject.getInt("chicken")>0){
                        chicken="Chicken: "+String.valueOf(jsonObject.getInt("chicken"));
                        list+="\n"+"Chicken: "+String.valueOf(jsonObject.getInt("chicken"));
                    }
                    if(jsonObject.getInt("spag")>0){
                        spag="Spaghetti: "+String.valueOf(jsonObject.getInt("spag"));
                        list=list+"\n"+spag;
                    }
                    if(jsonObject.getInt("fries")>0){
                        fries="Fries: "+String.valueOf(jsonObject.getInt("fries"));
                        list=list+"\n"+fries;
                    }

                    orderList.setText(list);
                    totalAmount.setText("Total amount of order: "+String.valueOf(config.totalprice));
                    if(jsonObject.getString("food_status").equals("cook")){
                        foodStatus.setText("Your food is ready.");
                        foodStatus.setTextColor(Color.GREEN);
                    }
                    else{
                        foodStatus.setText("");
                    }
                    getOrder();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(OrderOverview.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderOverview.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(jsonArrayRequest);
        rq.start();
    }
}
