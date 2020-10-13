package datnt.com.orders.Products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import datnt.com.orders.Order.OrderAdapter;
import datnt.com.orders.R;

public class DeletePrAdapter extends RecyclerView.Adapter<DeletePrAdapter.ViewHolder> {

    private ArrayList<Product> listPr;
    private Context context;

    public DeletePrAdapter(ArrayList<Product> listPr, Context context) {
        this.listPr = listPr;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_delete_pr, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvNamePr.setText(listPr.get(position).getNamePr());
        holder.cbDel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    listener.onCheck(true, position);
                } else {
                    listener.onCheck(false, position);
                }
            }
        });
    }

    public interface OnChecked {
        void onCheck(boolean isCheck, int position);
    }

    private OnChecked listener;

    public void setListener(OnChecked listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return listPr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNamePr;
        ImageView ivPr;
        CheckBox cbDel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamePr = itemView.findViewById(R.id.tvNamePr);
            ivPr = itemView.findViewById(R.id.ivPr);
            cbDel = itemView.findViewById(R.id.cbPr);
        }
    }
}
