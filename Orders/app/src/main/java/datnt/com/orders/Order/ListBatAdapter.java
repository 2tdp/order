package datnt.com.orders.Order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import datnt.com.orders.Products.Product;
import datnt.com.orders.R;

public class ListBatAdapter extends RecyclerView.Adapter<ListBatAdapter.ViewHolder> {

    private ArrayList<String> listOrder;
    private Context context;
    OnItemListener onItemListener;

    public ListBatAdapter(ArrayList<String> listOrder, Context context, OnItemListener onItemListener) {
        this.listOrder = listOrder;
        this.context = context;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.listorder, parent, false);
        return new ViewHolder(itemView, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvBat.setText(listOrder.get(position));
    }

    @Override
    public int getItemCount() {
        if (listOrder == null) {
            return 0;
        } else {
            return listOrder.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvBat;
        OnItemListener onItemListener;

        public ViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            tvBat = itemView.findViewById(R.id.tvBat);
            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition(), listOrder);
        }
    }

    public interface OnItemListener {
        void onItemClick(int position, ArrayList<String> listOrder);
    }
}
