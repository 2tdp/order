package datnt.com.orders.Products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import datnt.com.orders.R;

public class ListPrAdapter extends RecyclerView.Adapter<ListPrAdapter.ViewHolder> {

    private ArrayList<Product> listPr;
    private Context context;
    private OnItemListener onItemListener;

    public ListPrAdapter(ArrayList<Product> listPr, Context context, OnItemListener onItemListener) {
        this.listPr = listPr;
        this.context = context;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_pr, parent, false);

        return new ViewHolder(itemView, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = listPr.get(position);
        if (product.getImagePr() == "") {
            Glide.with(context)
                    .load(listPr.get(position).getImagePr())
                    .into(holder.ivPr);
        } else {
            holder.ivPr.setImageResource(R.drawable.food);
        }
        holder.tvNamePr.setText(listPr.get(position).getNamePr());
    }

    @Override
    public int getItemCount() {
        return listPr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvNamePr;
        ImageView ivPr;
        OnItemListener onItemListener;

        public ViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            tvNamePr = itemView.findViewById(R.id.tvNamePr);
            ivPr = itemView.findViewById(R.id.ivPr);
            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition(), listPr);
        }
    }

    public interface OnItemListener {
        void onItemClick(int position, ArrayList<Product> listProduct);
    }
}
