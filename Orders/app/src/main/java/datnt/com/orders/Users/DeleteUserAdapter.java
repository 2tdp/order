package datnt.com.orders.Users;

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
import datnt.com.orders.Users.User;

public class DeleteUserAdapter extends RecyclerView.Adapter<DeleteUserAdapter.ViewHolder> {

    private ArrayList<User> listUser;
    private Context context;

    public DeleteUserAdapter(ArrayList<User> listUser, Context context) {
        this.listUser = listUser;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_del_user, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvUserName.setText(listUser.get(position).getFullName());
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
        return listUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivUser;
        TextView tvUserName;
        CheckBox cbDel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUser = itemView.findViewById(R.id.ivUser);
            tvUserName = itemView.findViewById(R.id.tvfullName);
            cbDel = itemView.findViewById(R.id.cbPr);
        }
    }
}
