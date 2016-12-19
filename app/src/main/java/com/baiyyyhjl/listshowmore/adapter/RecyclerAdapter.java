package com.baiyyyhjl.listshowmore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baiyyyhjl.listshowmore.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangjinlong on 2016/3/4.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
    public final static int TYPE_ONE = 0;
    public final static int TYPE_TWO = 1;
    private Context mContext;
    private List<String> beans;
    private OnItemClickListener onItemClickListener;
    private boolean loadMore = false;

    public RecyclerAdapter(Context context) {
        this.mContext = context;
        this.beans = new ArrayList<>();
    }

    public boolean isLoadMore() {
        return loadMore;
    }

    public void setLoadMore(boolean loadMore) {
        this.loadMore = loadMore;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void OnItemClick(View view, int position);

        void OnItemLongClick(View view, int position);
    }

    public void add(String bean) {
        this.beans.add(bean);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (beans.size() > 3) {
            if (!loadMore) {
                if (position == 3) {
                    return TYPE_ONE;
                }

            }
        }
        return TYPE_TWO;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        ViewHolder holder = null;
        switch (viewType) {
            case TYPE_ONE:
                view = LayoutInflater.from(mContext).inflate(R.layout.load_more, parent, false);
                holder = new ViewHolderOne(view);
                break;
            case TYPE_TWO:
                view = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent, false);
                holder = new ViewHolderTwo(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_ONE:
                final ViewHolderOne holderOne = (ViewHolderOne) holder;
                onItemEventClick(holderOne);
                break;
            case TYPE_TWO:
                ViewHolderTwo holderTwo = (ViewHolderTwo) holder;
                holderTwo.text1.setText(beans.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (beans.size() > 3) {
            if (!loadMore) {
                return 4;
            }
        }
        return beans.size();
    }


    class ViewHolderOne extends RecyclerView.ViewHolder {
        TextView load_more_text;

        public ViewHolderOne(View itemView) {
            super(itemView);
            load_more_text = (TextView) itemView.findViewById(R.id.load_more_text);
        }
    }

    class ViewHolderTwo extends RecyclerView.ViewHolder {
        TextView text1;

        public ViewHolderTwo(View itemView) {
            super(itemView);
            text1 = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

    protected void onItemEventClick(ViewHolder holder) {
        final int position = holder.getLayoutPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(v, position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickListener.OnItemLongClick(v, position);
                return true;
            }
        });
    }

}
