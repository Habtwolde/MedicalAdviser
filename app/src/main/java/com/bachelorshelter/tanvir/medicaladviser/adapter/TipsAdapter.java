package com.bachelorshelter.tanvir.medicaladviser.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bachelorshelter.tanvir.medicaladviser.R;
import com.bachelorshelter.tanvir.medicaladviser.model.TipsItem;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by anikc on 2017-05-20.
 */

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.TipsHolder> {
    private List<TipsItem> listData;
    private LayoutInflater inflater;
    private View container;
    private TipsItemClickCallback tipsItemClickCallback;

    public interface TipsItemClickCallback{
        void onItemClick(int p,View view);
    }

    public void setTipsItemClickCallback(final TipsItemClickCallback tipsItemClickCallback){
        this.tipsItemClickCallback =tipsItemClickCallback;
    }

    public TipsAdapter(List<TipsItem> listData, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    @Override
    public TipsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_f_tips,parent,false);
        return new TipsHolder(view);
    }

    @Override
    public void onBindViewHolder(TipsHolder holder, int position) {
        final TipsItem item = listData.get(position);

        holder.headline.setText(item.getHeadline());
        holder.description.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class TipsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView headline;
        private TextView description;

        public TipsHolder(View itemView) {
            super(itemView);

            headline = (TextView)itemView.findViewById(R.id.headline);
            description = (TextView)itemView.findViewById(R.id.description);


            container = itemView.findViewById(R.id.cont_item_f_tips);
            container.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            tipsItemClickCallback.onItemClick(getAdapterPosition(),container);
        }
    }

    public void setFilter(ArrayList<TipsItem> filterList){

        listData = new ArrayList<>();
        listData.addAll(filterList);
        notifyDataSetChanged();

    }
}
