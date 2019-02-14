package com.example.learningapi.activity.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.learningapi.R;
import com.example.learningapi.model.Biodata;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<Biodata> biodataList;
    private ItemClickListener itemClickListener ;


    public MainAdapter(Context context, List<Biodata> biodataList, ItemClickListener itemClickListener) {
        this.context = context;
        this.biodataList = biodataList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_biodata,
                viewGroup, false);
        return new RecyclerViewAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter recyclerViewAdapter, int i) {
        Biodata biodata = biodataList.get(i);
        recyclerViewAdapter.tv_name.setText(biodata.getNama());
        recyclerViewAdapter.tv_address.setText(biodata.getAlamat());

    }

    @Override
    public int getItemCount() {
        return biodataList.size();
    }

    class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_name,tv_address;
        CardView card_item;
        ItemClickListener itemClickListener;

        RecyclerViewAdapter(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.name);
            tv_address = itemView.findViewById(R.id.address);
            card_item = itemView.findViewById(R.id.card_item);

            this.itemClickListener = itemClickListener;
            card_item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }


}
