package datnt.com.orders.Order;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import datnt.com.orders.R;

public class EditOrderAdapter extends RecyclerView.Adapter<EditOrderAdapter.ViewHolder> {

    ArrayList<Order> listOrder;
    Context context;

    public EditOrderAdapter(ArrayList<Order> listOrder, Context context) {
        this.listOrder = listOrder;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_order, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tvNamePr.setText(listOrder.get(position).getNamePr());
        holder.tvPricePr.setText(listOrder.get(position).getPricePr());
        holder.etQuantity.setText(listOrder.get(position).getQuantityPr());

        final Order order = listOrder.get(position);
        holder.ivUn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn có chắc muốn xóa món?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                removeItem(position);
                                listener.onChecked(false, order);

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create().show();
            }
        });
    }

    public interface OnListenerChecked {
        void onChecked(boolean isSelected, Order order);
    }

    private OnListenerChecked listener;

    public void setListener(OnListenerChecked listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        if (listOrder == null) {
            return 0;
        } else {
            return listOrder.size();
        }
    }

    public void removeItem(int position) {
        listOrder.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivUn;
        TextView tvNamePr, tvPricePr;
        EditText etQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivUn = itemView.findViewById(R.id.ivUn);
            tvNamePr = itemView.findViewById(R.id.tvNamePr);
            tvPricePr = itemView.findViewById(R.id.tvPricePr);
            etQuantity = itemView.findViewById(R.id.etQuantity);
        }
    }
}
