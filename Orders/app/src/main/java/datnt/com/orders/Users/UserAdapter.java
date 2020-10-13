package datnt.com.orders.Users;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import datnt.com.orders.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private ArrayList<User> listUser;
    private Context context;
    OnItemListener onItemListener;

    public UserAdapter(ArrayList<User> listUser, Context context, OnItemListener onItemListener) {
        this.listUser = listUser;
        this.context = context;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_per, parent, false);

        return new ViewHolder(itemView, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ivUser.setImageResource(R.drawable.user);
        holder.tvUserName.setText(listUser.get(position).getFullName());
    }


    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivUser;
        TextView tvUserName;
        OnItemListener onItemListener;

        public ViewHolder(@NonNull final View itemView, OnItemListener onItemListener) {
            super(itemView);
            ivUser = itemView.findViewById(R.id.ivUser);
            tvUserName = itemView.findViewById(R.id.tvfullName);
            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition(), listUser);
        }
    }

    public interface OnItemListener {
        void onItemClick(int position, ArrayList<User> listUser);
    }
}
