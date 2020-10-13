package datnt.com.orders.Order;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import datnt.com.orders.Products.Product;
import datnt.com.orders.R;
import datnt.com.orders.SQLHelper;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private ArrayList<Product> listPr;
    private Context context;

    public OrderAdapter(ArrayList<Product> listPr, Context context) {
        this.listPr = listPr;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.order, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tvNamePr.setText(listPr.get(position).getNamePr());
        holder.tvPricePr.setText(String.valueOf(listPr.get(position).getPricePr() - listPr.get(position).getDiscountPr()));

        holder.cbPr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b && holder.etQuantity.getText().toString().equals("")) {
                    holder.cbPr.setChecked(false);
                    Toast.makeText(context, "Nhập số lượng!", Toast.LENGTH_SHORT).show();
                } else if (b && !holder.etQuantity.getText().toString().equals("")) {
                    listener.onChecked(true, listPr.get(position).getIdPr(), holder.tvNamePr.getText().toString(), holder.tvPricePr.getText().toString(), holder.etQuantity.getText().toString());
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Bạn có chắc chắn muốn bỏ chọn?")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    listener.onChecked(false, listPr.get(position).getIdPr(), holder.tvNamePr.getText().toString(), holder.tvPricePr.getText().toString(), holder.etQuantity.getText().toString());
                                    holder.etQuantity.setText("");
                                }
                            })
                            .create().show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (listPr == null) {
            return 0;
        } else
            return listPr.size();
    }

    public interface OnListenerChecked {
        void onChecked(boolean isSelected, long idPr, String namePr, String pricePr, String quantityPr);
    }

    private OnListenerChecked listener;

    public void setListener(OnListenerChecked listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox cbPr;
        TextView tvNamePr, tvPricePr;
        EditText etQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cbPr = itemView.findViewById(R.id.cbPr);
            tvNamePr = itemView.findViewById(R.id.tvNamePr);
            tvPricePr = itemView.findViewById(R.id.tvPricePr);
            etQuantity = itemView.findViewById(R.id.etQuantity);
        }
    }
}
