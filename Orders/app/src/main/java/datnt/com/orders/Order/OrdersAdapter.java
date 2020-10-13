package datnt.com.orders.Order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import datnt.com.orders.R;

public class OrdersAdapter extends BaseAdapter {

    ArrayList<String> listBat;
    Context context;

    public OrdersAdapter(ArrayList<String> listBat, Context context) {
        this.listBat = listBat;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listBat.size();
    }

    @Override
    public Object getItem(int i) {
        return listBat.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        @SuppressLint("ViewHolder") View v = inflater.inflate(R.layout.listorder, viewGroup, false);

        TextView tvName = v.findViewById(R.id.tvBat);

        tvName.setText(listBat.get(i));

        return v;
    }
}
