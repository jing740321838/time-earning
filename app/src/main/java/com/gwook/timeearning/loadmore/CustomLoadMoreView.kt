package com.gwook.timeearning.loadmore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.gwook.timeearning.R

class CustomLoadMoreView : BaseLoadMoreView() {
    override fun getRootView(parent: ViewGroup): View {
        // 整个 LoadMore 布局
        return LayoutInflater.from(parent.context).inflate(R.layout.view_load_more, parent, false)
    }

    override fun getLoadingView(holder: BaseViewHolder): View {
        // 布局中 “加载中”的View
        return holder.getView(R.id.load_more_loading_view)
    }

    override fun getLoadComplete(holder: BaseViewHolder): View {
        // 布局中 “当前一页加载完成”的View
        return holder.getView(R.id.load_more_load_complete_view)
    }

    override fun getLoadEndView(holder: BaseViewHolder): View {
        // 布局中 “全部加载结束，没有数据”的View
        return holder.getView(R.id.load_more_load_end_view)
    }

    override fun getLoadFailView(holder: BaseViewHolder): View {
        // 布局中 “加载失败”的View
        return holder.getView(R.id.load_more_load_fail_view)
    }

    override fun convert(holder: BaseViewHolder, position: Int, loadMoreStatus: LoadMoreStatus) {
        when (loadMoreStatus) {
            LoadMoreStatus.Complete -> {
                getLoadingView(holder).visibility = View.GONE
                getLoadComplete(holder).visibility = View.GONE
                getLoadComplete(holder).visibility = View.GONE
                getLoadEndView(holder).visibility = View.GONE
                holder.itemView.isEnabled = false
            }
            LoadMoreStatus.Loading -> {
                getLoadingView(holder).visibility = View.VISIBLE
                getLoadComplete(holder).visibility = View.GONE
                getLoadComplete(holder).visibility = View.GONE
                getLoadEndView(holder).visibility = View.GONE
            }
            LoadMoreStatus.Fail -> {
                getLoadingView(holder).visibility = View.GONE
                getLoadComplete(holder).visibility = View.GONE
                getLoadComplete(holder).visibility = View.VISIBLE
                getLoadEndView(holder).visibility = View.GONE
            }
            LoadMoreStatus.End -> {
                getLoadingView(holder).visibility = View.GONE
                getLoadComplete(holder).visibility = View.GONE
                getLoadComplete(holder).visibility = View.GONE
                getLoadEndView(holder).visibility = View.VISIBLE
            }
        }
    }
}