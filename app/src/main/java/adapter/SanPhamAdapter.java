package adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testrecyclerview.R;

import java.util.ArrayList;

import dao.SanPhamDao;
import models.SanPham;

public class SanPhamAdapter extends  RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SanPham> list;
    private  SanPhamDao sanPhamDao;
    public SanPhamAdapter(Context context, ArrayList<SanPham> list, SanPhamDao sanPhamDao) {
        this.context = context;
        this.list = list;
        this.sanPhamDao=sanPhamDao;
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
        holder.tvIDSanPham.setText("ID: " + list.get(position).getMaloai());
        holder.tvTenSanPham.setText("Tên: " + list.get(position).getTen());
        holder.tvTenLoaiSanPham.setText("Loại: " + list.get(position).getLoai());
        holder.tvGiaSanPham.setText("Giá: " + list.get(position).getGia() + " VND");

        holder.ibEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogUpdate(list.get(holder.getAdapterPosition()));
            }
        });
        holder.ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int check = sanPhamDao.xoaSanPham(list.get(holder.getAdapterPosition()).getMaloai());
                switch (check) {
                    case -1:
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(context, "Sản phẩm không tồn tại", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(context, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        break;
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateData(ArrayList<SanPham> newList) {
        list = newList;
        notifyDataSetChanged();
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
    private void showDialogUpdate(SanPham sanPham)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        View view= inflater.inflate(R.layout.dialog_edit,null);
        builder.setView(view);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        EditText edtTenSP=view.findViewById(R.id.edtTenSP);
        EditText edtLoai=view.findViewById(R.id.edtLoai);
        EditText edtGiaSP=view.findViewById(R.id.edtGiaSP);
        Button btnEdit=view.findViewById(R.id.btnEdit);
        Button btnHuy=view.findViewById(R.id.btnHuy);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenSP=edtTenSP.getText().toString();
                String loaiSP=edtLoai.getText().toString();
                String giaSP=edtGiaSP.getText().toString();
                SanPham sanphamUpdate= new SanPham(sanPham.getMaloai(),tenSP,loaiSP,giaSP);
                boolean check= sanPhamDao.suaSanPham(sanphamUpdate);
                if(check)
                {
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                    alertDialog.dismiss();
                }else
                {
                    Toast.makeText(context, "Cập nhật không công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
    private void loadData()
    {
        list.clear();
        list=sanPhamDao.getDSloaiSach();
        notifyDataSetChanged();
    }
}
