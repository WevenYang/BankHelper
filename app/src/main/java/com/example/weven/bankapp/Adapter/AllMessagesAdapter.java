package com.example.weven.bankapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weven.bankapp.Bean.GetAllMessagesResult;
import com.example.weven.bankapp.R;

import java.util.List;

/**
 * Created by Administrator on 2017/3/16.
 */

public class AllMessagesAdapter extends RecyclerView.Adapter<AllMessagesAdapter.ViewHolder> {

    Context context;
    List<GetAllMessagesResult.DataBean> data;
    private RecyclerItemClickListener listener;

    public AllMessagesAdapter(Context context, List data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv_all_messages_acy, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.messages_time.setText(data.get(position).getDate());
        switch (Integer.valueOf(data.get(position).getType())){
            case 0:
                holder.icon.setImageResource(R.mipmap.nav_icon);
                holder.messages_type.setText("充值通知");
                holder.messages_content.setText("您有一笔" + data.get(position).getNum() + "元的充值");
                break;
            case 1:
                holder.icon.setImageResource(R.mipmap.nav_icon);
                holder.messages_type.setText("转账通知");
                holder.messages_content.setText("您有一笔" + data.get(position).getNum() + "元的转账");
                break;
            case 2:
                holder.icon.setImageResource(R.mipmap.nav_icon);
                holder.messages_type.setText("定期转入通知");
                holder.messages_content.setText("您的定期余额有一笔" + data.get(position).getNum() + "元的转入,等待审核");
                break;
            case 3:
                holder.icon.setImageResource(R.mipmap.nav_icon);
                holder.messages_type.setText("定期转出通知");
                holder.messages_content.setText("您的定期余额有一笔" + data.get(position).getNum() + "元的转出");
                break;
            case 4:
                holder.icon.setImageResource(R.mipmap.nav_icon);
                holder.messages_type.setText("提现通知");
                holder.messages_content.setText("您有一笔" + data.get(position).getNum() + "元的提现");
                break;
            case 5:
                holder.icon.setImageResource(R.mipmap.nav_icon);
                holder.messages_type.setText("定期转入通知");
                holder.messages_content.setText("您的定期余额有一笔" + data.get(position).getNum() + "元的转入,审核成功，等待收益");
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // 此处用来绑定listener，根据需求的不同，传入不同的listener
    public void setOnItemClickListener(RecyclerItemClickListener listener) {
        this.listener = listener;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView messages_type, messages_time, messages_content;
        ImageView icon;

        public ViewHolder(final View itemView) {
            super(itemView);
            messages_type = (TextView) itemView.findViewById(R.id.tv_messages_type);
            messages_time = (TextView) itemView.findViewById(R.id.tv_messages_time);
            messages_content = (TextView) itemView.findViewById(R.id.tv_messages_content);
            icon = (ImageView) itemView.findViewById(R.id.iv_messages_icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onItemClick(itemView, getAdapterPosition());
                        //消息点击后呈现已读状态
                        messages_content.setTextColor(context.getResources().getColor(R.color.deepGrey));
                        messages_type.setTextColor(context.getResources().getColor(R.color.deepGrey));
                        messages_time.setTextColor(context.getResources().getColor(R.color.deepGrey));
                    }
                }
            });
        }
    }
}
