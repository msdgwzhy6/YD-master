package com.bzh.recycler;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * ========================================================== <br>
 * <b>版权</b>：　　　音悦台 版权所有(c) 2015 <br>
 * <b>作者</b>：　　　别志华 zhihua.bie@yinyuetai.com<br>
 * <b>创建日期</b>：　15-12-11 <br>
 * <b>描述</b>：　　　公共的ViewHolder，实现了缓存View<br>
 * <b>版本</b>：　    V1.0 <br>
 * <b>修订历史</b>：　<br>
 * ========================================================== <br>
 */
public class ExViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> yViews;

    public ExViewHolder(View itemView) {
        super(itemView);
        this.yViews = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId) {
        View view = yViews.get(viewId);
        if (null == view) {
            view = itemView.findViewById(viewId);
            yViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     */
    public ExViewHolder setText(int viewId, CharSequence text) {
        TextView view = getView(viewId);
        if (view != null) {
            view.setText(text);
        }
        return this;
    }

    public ExViewHolder setText(int viewId, int intStr) {
        TextView view = getView(viewId);
        if (view != null) {
            view.setText(String.valueOf(intStr));
        }
        return this;
    }


    public ExViewHolder setViewVisiblity(int viewId, int visablity) {
        View view = getView(viewId);
        if (view != null) {
            view.setVisibility(visablity);
        }
        return this;
    }

    public ExViewHolder setTextColor(int viewId, int color) {
        TextView view = getView(viewId);
        if (view != null) {
            view.setTextColor(color);
        }
        return this;
    }

    public ExViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
        return this;
    }

    public ExViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnTouchListener(listener);
        }
        return this;
    }

    public ExViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        if (view != null) {
            view.setImageResource(drawableId);
        }
        return this;
    }

    public ExViewHolder setButton(int viewId, String text, int resId) {
        Button view = getView(viewId);
        if (view != null) {
            view.setBackgroundResource(resId);
            view.setText(text);
        }
        return this;
    }

    public ExViewHolder setButton(int viewId, String text, int resId, View.OnClickListener lsn) {
        Button view = getView(viewId);
        if (view != null) {
            view.setBackgroundResource(resId);
            view.setText(text);
            view.setOnClickListener(lsn);
        }
        return this;
    }


    public ExViewHolder setCheckBox(int viewId) {
        CheckBox view = getView(viewId);
        return this;
    }

    public ExViewHolder setCheckBox(int viewId, boolean isCheck) {
        CheckBox view = getView(viewId);
        if (view != null) {
            view.setChecked(isCheck);
        }
        return this;
    }

    public ExViewHolder setBackGround(int viewId, int resId) {
        View view = getView(viewId);
        if (view != null) {
            view.setBackgroundResource(resId);
        }
        return this;
    }

    public ExViewHolder addChildView(int viewId, View view) {
        ViewGroup view1 = (ViewGroup) getView(viewId);
        if (view1 != null && view != null) {
            view1.addView(view);
        }
        return this;
    }

    public ExViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        if (view != null) {
            view.setTag(tag);
        }
        return this;
    }

    public int getChildCount(int viewId) {
        return ((ViewGroup) getView(viewId)).getChildCount();
    }

    public View getChildAt(int viewId, int index) {
        return ((ViewGroup) getView(viewId)).getChildAt(index);
    }

    public void setEnableState(int viewId, boolean isEnable) {
        View view = getView(viewId);
        if (view != null) {
            view.setEnabled(isEnable);
        }
    }
}
