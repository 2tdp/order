package datnt.com.orders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import datnt.com.orders.Order.Order;

public class PayAdapter extends BaseAdapter {

    private ArrayList<Order> listOrders;
    private Context context;

    public PayAdapter(ArrayList<Order> listOrders, Context context) {
        this.listOrders = listOrders;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listOrders.size();
    }

    @Override
    public Object getItem(int i) {
        return listOrders.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        @SuppressLint("ViewHolder") View v = inflater.inflate(R.layout.list_pr, viewGroup, false);

        TextView tvName = v.findViewById(R.id.tvNamePr);
        TextView tvQuantity = v.findViewById(R.id.tvQuantity);

        String name = "", namePr = listOrders.get(i).getNamePr();

        for (Order o : listOrders) {
            name = o.getNamePr();
            if (name.equals(namePr)) {
                tvName.setText(namePr);
                tvQuantity.setText(listOrders.get(i).getQuantityPr());
            }
        }

        return v;
    }
}
