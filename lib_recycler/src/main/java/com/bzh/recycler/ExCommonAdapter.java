package com.bzh.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

/**
 * ========================================================== <br>
 * <b>版权</b>：　　　音悦台 版权所有(c) 2015 <br>
 * <b>作者</b>：　　　别志华 zhihua.bie@yinyuetai.com<br>
 * <b>创建日期</b>：　15-12-11 <br>
 * <b>描述</b>：　　　公共的Adapter<br>
 * <b>版本</b>：　    V1.0 <br>
 * <b>修订历史</b>：　<br>
 * ========================================================== <br>
 */
public abstract class ExCommonAdapter<T> extends RecyclerView.Adapter<ExViewHolder> {

    public static final int VIEW_TYPES_HEADER = 1000;
    public static final int VIEW_TYPES_FOOTER = 1001;
    public static final int VIEW_TYPES_AD = 1002;

    private View yHeaderView;
    private View yFooterView;

    private Context yContext;
    private int yLayoutId;
    private int yCurrentDataIndex;

    private OnItemClickListener yOnItemClickListener;
    private OnItemLongClickListener yOnItemLongClickListener;

    public ExCommonAdapter(Context context) {
        this.yContext = context;
        this.yData = new ArrayList<>();
    }

    /**
     * 单条目类型所使用的构造函数
     * 如果是多条目类型，应该使用{#ExCommonAdapter(Context context)}构造方法，并重写{#inflateItemView(int type)}方法
     *
     * @param layoutId 条目布局
     */
    public ExCommonAdapter(Context context, int layoutId) {
        this(context);
        this.yLayoutId = layoutId;
    }

    /**
     * 创建Item视图
     */
    @Override
    public ExViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isHeaderView(viewType)) return new ExViewHolder(yHeaderView);
        else if (isFooterView(viewType)) return new ExViewHolder(yFooterView);
        else {
            if (isSingleTypeItem()) {
                return new ExViewHolder(LayoutInflater.from(yContext).inflate(yLayoutId, null));
            } else {
                T item = getItem(yCurrentDataIndex);
                int itemLayout = 0;
                if (item instanceof IExModelType) {
                    itemLayout = inflateItemView(((IExModelType) item).getModelType());
                }
                if (0 == itemLayout) {
                    throw new IllegalArgumentException("布局文件不存在");
                }
                View view = LayoutInflater.from(yContext).inflate(itemLayout, null);
                return new ExViewHolder(view);
            }
        }
    }

    private boolean isSingleTypeItem() {
        return 0 != yLayoutId;
    }

    private boolean isFooterView(int viewType) {
        return VIEW_TYPES_FOOTER == viewType && null != yFooterView;
    }

    private boolean isHeaderView(int viewType) {
        return VIEW_TYPES_HEADER == viewType && null != yHeaderView;
    }

    /**
     * 绑定View和数据
     */
    @Override
    public void onBindViewHolder(final ExViewHolder holder, final int position) {

        if (yHeaderView == null && yFooterView == null) {
            yCurrentDataIndex = position;
            executeItemView(holder);
        } else if (yHeaderView != null && yFooterView == null) {
            if (position != 0) {
                yCurrentDataIndex = position - 1;
                executeItemView(holder);
            }
        } else if (yHeaderView == null) {
            if (position != getItemCount() - 1) {
                yCurrentDataIndex = position;
                executeItemView(holder);
            }
        } else {
            if (position != 0 && position != getItemCount() - 1) {
                yCurrentDataIndex = position - 1;
                executeItemView(holder);
            }
        }
    }

    private void executeItemView(final ExViewHolder holder) {
        if (null != yOnItemClickListener) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    yOnItemClickListener.onItemClick(holder);
                }
            });
        }

        if (null != yOnItemLongClickListener) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    yOnItemLongClickListener.onItemLongClick(holder);
                    return true;
                }
            });
        }

        convert(holder, yData.get(yCurrentDataIndex));
    }

    protected abstract void convert(final ExViewHolder viewHolder, final T item);

    protected int inflateItemView(int type) {
        return 0;
    }

    @Override
    public int getItemCount() {
        int headerOrFooter = 0;
        if (null != yHeaderView) {
            headerOrFooter++;
        }
        if (null != yFooterView) {
            headerOrFooter++;
        }
        if (null == yData)
            return headerOrFooter;
        return yData.size() + headerOrFooter;
    }

    @Override
    public int getItemViewType(int position) {
        if (null != yHeaderView && 0 == position) {
            return VIEW_TYPES_HEADER;
        } else if (null != yFooterView && getItemCount() - 1 == position) {
            return VIEW_TYPES_FOOTER;
        } else if (null != yHeaderView) {
            yCurrentDataIndex = position - 1;
            T item = getItem(yCurrentDataIndex);
            if (item instanceof IExModelType) {
                return ((IExModelType) item).getModelType();
            }
            return super.getItemViewType(position);
        } else {
            yCurrentDataIndex = position;
            T item = getItem(yCurrentDataIndex);
            if (item instanceof IExModelType) {
                return ((IExModelType) item).getModelType();
            }
            return super.getItemViewType(position);
        }
    }

    // ===============DataEntity Area ===============

    private List<T> yData;

    public void setData(List<T> data) {
        this.yData = data;
        notifyDataSetChanged();
    }

    public void setData(T data, int position) {
        this.yData.set(position, data);
        if (yHeaderView != null) {
            position++;
        }
        notifyItemChanged(position);
    }

    public void clearData() {
        this.yData.clear();
        notifyDataSetChanged();
    }

    public void remove(int position) {
        yData.remove(position);
        if (yHeaderView != null) {
            position++;
        }
        notifyItemRemoved(position);
    }

    public void removeData(ArrayList<Integer> removePostion) {
        if (removePostion == null) {
            return;
        }
        for (int i = removePostion.size(); i > 0; i--) {
            int position = removePostion.get(i - 1);
            remove(position);
        }
    }

    public void addData(T data, int position) {
        if (data == null) {
            return;
        }
        yData.add(position, data);
        if (yHeaderView != null) {
            position++;
        }
        notifyItemInserted(position);
    }

    public void addData(List<T> datas) {
        if (datas == null) {
            return;
        }
        int oldSize = getDataSize();
        yData.addAll(datas);
        notifyItemRangeInserted(oldSize + 1, datas.size());
    }

    public T getItem(int position) {
        return yData.get(position);
    }

    public int getDataSize() {
        return yData.size();
    }

    public List<T> getData() {
        return yData;
    }

    public int getCurrentPos() {
        return yCurrentDataIndex;
    }

    // ===============Set/Get Method ===============

    public void setHeaderView(View headerView) {
        this.yHeaderView = headerView;
    }

    public void setFooterView(View footerView) {
        this.yFooterView = footerView;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.yOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.yOnItemLongClickListener = onItemLongClickListener;
    }

    // ===============Event Listener ===============

    public interface OnItemClickListener {
        void onItemClick(ExViewHolder viewHolder);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(ExViewHolder viewHolder);
    }
}
