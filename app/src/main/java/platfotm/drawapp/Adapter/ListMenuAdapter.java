package platfotm.drawapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import platfotm.drawapp.R;

/**
 * @author Ly
 * @date 2018/1/4
 */

public class ListMenuAdapter extends RecyclerView.Adapter<ListMenuAdapter.ListMenuHolder> {
    private Context mContext;
    private List<String> mStringList = new ArrayList<>();
    private ItemClickListener mItemClickListener;


    public void addData(List<String> stringList) {
        mStringList.clear();
        mStringList.addAll(stringList);
        notifyDataSetChanged();

    }

    public ListMenuAdapter(Context context) {
        mContext = context;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @Override
    public ListMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListMenuHolder(LayoutInflater.from(mContext).inflate(R.layout.item_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(ListMenuHolder holder, final int position) {
        holder.mButton.setText(mStringList.get(position));
        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClickListener(mStringList.get(position));
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mStringList.size();
    }

    static class ListMenuHolder extends RecyclerView.ViewHolder {
        private Button mButton;

        ListMenuHolder(View itemView) {
            super(itemView);
            mButton = (Button) itemView.findViewById(R.id.bt_item_title);
        }
    }


    public interface ItemClickListener {
        /**
         * 点击事件
         *
         * @param string
         */
        void onItemClickListener(String string);
    }
}
