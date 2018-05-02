package com.foodorder.tatsuya.foodorder.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.foodorder.tatsuya.foodorder.R;
import com.foodorder.tatsuya.foodorder.adapter.OrderAdapter;
import com.foodorder.tatsuya.foodorder.model.orderpkg.FoodOrder;
import com.foodorder.tatsuya.foodorder.model.personpkg.Account;
import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;
import com.foodorder.tatsuya.foodorder.task.OrderGetter;
import com.foodorder.tatsuya.foodorder.utils.UserSession;

import java.util.ArrayList;
import java.util.List;

public class OrderTrackingActivity extends AppCompatActivity
        implements OnTaskCompleted<List<FoodOrder>>

{
    private ListView listViewOrderTracking;
    private List<FoodOrder> foodOrderList = new ArrayList<>();
    private Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);
        this.listViewOrderTracking = super.findViewById(R.id.listViewOrderTracking);
        this.btnRefresh = super.findViewById(R.id.btnRefresh);

        Account loggedAccount = UserSession.getInstance().getLoggedAccount();

        new OrderGetter(OrderTrackingActivity.this, OrderTrackingActivity.this)
                .execute(loggedAccount);
        btnRefresh.setOnClickListener(view -> {
            new OrderGetter(OrderTrackingActivity.this, OrderTrackingActivity.this)
                    .execute(loggedAccount);
        });
    }

    @Override
    public void handle(List<FoodOrder> value) {
        this.foodOrderList = value;
        OrderAdapter orderAdapter
                = new OrderAdapter(this, R.layout.order_line, value);
        this.listViewOrderTracking.setAdapter(orderAdapter);
    }
}
