package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testrecyclerview.R;

import java.util.ArrayList;

import models.SanPham;

public class SanPhamAdapter extends  RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SanPham> list;

    public SanPhamAdapter(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        View view=inflater.inflate(R.layout.item_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     holder.tvIDSanPham.setText("ID: "+list.get(position).getMaloai());
     holder.tvTenSanPham.setText("Tên: "+list.get(position).getTen());
     holder.tvTenLoaiSanPham.setText("Loại: "+list.get(position).getLoai());
     holder.tvGiaSanPham.setText("Giá: "+list.get(position).getGia()+" VND");
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvIDSanPham,tvTenSanPham,tvTenLoaiSanPham,tvGiaSanPham;
        ImageButton ibEdit, ibDelete;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tvIDSanPham=itemView.findViewById(R.id.tvIDSanPham);
            tvTenSanPham=itemView.findViewById(R.id.tvTenSanPham);
            tvTenLoaiSanPham=itemView.findViewById(R.id.tvLoai);
            tvGiaSanPham=itemView.findViewById(R.id.tvGiaSP);
            ibEdit=itemView.findViewById(R.id.ibEdit);
            ibDelete=itemView.findViewById(R.id.ibDelete);
        }
    }
}
