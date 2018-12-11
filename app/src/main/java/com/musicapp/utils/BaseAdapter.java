package com.musicapp.utils;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.musicapp.BR;

import java.util.List;

/**
 * Created by Chetan on 9/8/18.
 */
public class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {

    private List<? extends T> list;
    private int viewId;
    private BaseInterface listener;

    public BaseAdapter(int viewId, List<? extends T> list, BaseInterface listener) {
        this.list = list;
        this.viewId = viewId;
        this.listener = listener;
    }

    public void setData(List<T> data) {
        list = data;
        if (list != null && list.size() > 0)
            Log.d("mytag", "Adapter SETDATA DataType " + list.get(0).getClass().getSimpleName());
        notifyDataSetChanged();
    }

    public void setListener(BaseInterface baseInterface) {
        this.listener = baseInterface;
    }

    public void removeDataAt(int data) {
        list.remove(data);
        notifyItemRemoved(data);
    }

    public List<? extends T> getList() {
        return list;
    }

    public class ViewHolder<V extends ViewDataBinding> extends RecyclerView.ViewHolder {
        private V v;

        public ViewHolder(V v) {
            super(v.getRoot());
            this.v = v;
            v.setVariable(BR.callback, listener);
        }

        public V getBinding() {
            return v;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return viewId;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public BaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding bind = DataBindingUtil.bind(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
        return new ViewHolder<>(bind);
    }

    @Override
    public void onBindViewHolder(BaseAdapter.ViewHolder holder, int position) {
        T model = list.get(position);
        holder.getBinding().setVariable(BR.model, model);
        holder.getBinding().setVariable(BR.position, position);
        holder.getBinding().executePendingBindings();
    }

    public interface BaseInterface {
    }
}
