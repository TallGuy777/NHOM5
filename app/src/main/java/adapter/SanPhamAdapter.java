package adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testrecyclerview.R;

import java.util.ArrayList;

import dao.SanPhamDao;
import models.SanPham;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SanPham> list;
    private SanPhamDao sanPhamDao;
    private boolean isCaseOne;  // Thêm biến trạng thái

    public SanPhamAdapter(Context context, ArrayList<SanPham> list, SanPhamDao sanPhamDao, boolean isCaseOne) {
        this.context = context;
        this.list = list;
        this.sanPhamDao = sanPhamDao;
        this.isCaseOne = isCaseOne;  // Khởi tạo biến trạng thái
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sanPham = list.get(position);

        holder.tvIDSanPham.setText("ID: " + sanPham.getMaloai());
        holder.tvTenSanPham.setText("Tên: " + sanPham.getTen());
        holder.tvTenLoaiSanPham.setText("Loại: " + sanPham.getLoai());
        holder.tvGiaSanPham.setText("Giá: " + sanPham.getGia() + " VND");

        // Hiển thị hình ảnh từ drawable dựa trên tên hình ảnh
        int imageResId = context.getResources().getIdentifier(sanPham.getHinhAnh(), "drawable", context.getPackageName());
        if (imageResId != 0) {
            holder.imageViewLoaiSanPham.setImageResource(imageResId);
        } else {
            holder.imageViewLoaiSanPham.setImageResource(R.drawable.banana); // Hình ảnh mặc định nếu không tìm thấy
        }

        // Điều chỉnh visibility của các nút dựa trên biến trạng thái
        if (isCaseOne) {
            holder.linerUser.setVisibility(View.VISIBLE);
            holder.linerAdmin.setVisibility(View.GONE);
        } else {
            holder.linerUser.setVisibility(View.GONE);
            holder.linerAdmin.setVisibility(View.VISIBLE);
        }

        holder.ibEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogUpdate(sanPham);
            }
        });

        holder.ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn xóa " + sanPham.getTen() + " Không ?");
                builder.setIcon(R.drawable.warning);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int check = sanPhamDao.xoaSanPham(sanPham.getMaloai());
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
                builder.setNegativeButton("Không", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIDSanPham, tvTenSanPham, tvTenLoaiSanPham, tvGiaSanPham;
        ImageButton ibEdit, ibDelete;
        ImageView imageViewLoaiSanPham;
        LinearLayout linerAdmin, linerUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIDSanPham = itemView.findViewById(R.id.tvIDSanPham);
            tvTenSanPham = itemView.findViewById(R.id.tvTenSanPham);
            tvTenLoaiSanPham = itemView.findViewById(R.id.tvLoai);
            tvGiaSanPham = itemView.findViewById(R.id.tvGiaSP);
            ibEdit = itemView.findViewById(R.id.ibEdit);
            ibDelete = itemView.findViewById(R.id.ibDelete);
            imageViewLoaiSanPham = itemView.findViewById(R.id.imageViewLoaiSanPham);
            linerAdmin = itemView.findViewById(R.id.linerAdmin);
            linerUser = itemView.findViewById(R.id.linerUser);
        }
    }

    private void showDialogUpdate(SanPham sanPham) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        EditText edtTenSP = view.findViewById(R.id.edtTenSP);
        EditText edtLoai = view.findViewById(R.id.edtLoai);
        EditText edtGiaSP = view.findViewById(R.id.edtGiaSP);
        EditText edtHinhAnh = view.findViewById(R.id.edtHinhAnh); // Thêm dòng này

        Button btnEdit = view.findViewById(R.id.btnEdit);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        // Điền thông tin hiện tại vào các trường EditText
        edtTenSP.setText(sanPham.getTen());
        edtLoai.setText(sanPham.getLoai());
        edtGiaSP.setText(sanPham.getGia());
        edtHinhAnh.setText(sanPham.getHinhAnh()); // Hiển thị tên hình ảnh hiện tại

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenSP = edtTenSP.getText().toString();
                String loaiSP = edtLoai.getText().toString();
                String giaSP = edtGiaSP.getText().toString();
                String hinhAnh = edtHinhAnh.getText().toString(); // Lấy tên hình ảnh từ EditText

                if (hinhAnh.isEmpty()) {
                    hinhAnh = sanPham.getHinhAnh(); // Nếu không có giá trị mới, giữ giá trị cũ
                }

                SanPham sanphamUpdate = new SanPham(sanPham.getMaloai(), tenSP, loaiSP, giaSP, hinhAnh);
                boolean check = sanPhamDao.suaSanPham(sanphamUpdate);
                if (check) {
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(context, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });


        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    private void loadData() {
        list.clear();
        list.addAll(sanPhamDao.getDSloaiSach());  // Thay đổi từ list = sanPhamDao.getDSloaiSach(); thành list.addAll()
        notifyDataSetChanged();
    }
}
