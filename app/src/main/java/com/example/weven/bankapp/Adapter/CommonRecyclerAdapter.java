package com.example.weven.bankapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolderR>
{
	protected Context mContext;
	protected int mLayoutId;
	protected List<T> mDatas;
	protected LayoutInflater mInflater;

	public CommonRecyclerAdapter(Context context, int layoutId, List<T> datas)
	{
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mLayoutId = layoutId;
		mDatas = datas;
	}

	@Override
	public ViewHolderR onCreateViewHolder(final ViewGroup parent, int viewType)
	{
		ViewHolderR viewHolderR = ViewHolderR.get(mContext, null, parent, mLayoutId, -1);
		return viewHolderR;
	}

	//获取Item的Position
	protected int getPosition(RecyclerView.ViewHolder viewHolder)
	{
		return viewHolder.getAdapterPosition();
	}


	@Override
	public void onBindViewHolder(ViewHolderR holder, int position)
	{
		holder.updatePosition(position);
		convert(holder, mDatas.get(position));
	}

	public abstract void convert(ViewHolderR holder, T t);

	@Override
	public int getItemCount()
	{
		return mDatas.size();
	}

}
