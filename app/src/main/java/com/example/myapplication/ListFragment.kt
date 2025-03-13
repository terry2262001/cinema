package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.FocusHighlight
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.leanback.widget.OnItemViewClickedListener
import androidx.leanback.widget.OnItemViewSelectedListener
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.Row
import androidx.leanback.widget.RowPresenter
import com.example.myapplication.models.CastResponse
import com.example.myapplication.presenter.CastItemPresenter
import com.example.myapplication.presenter.ItemPresenter
import com.example.tvandmovie.models.DataModel


class ListFragment : RowsSupportFragment() {

    private var itemSelectedListener: ((DataModel.Result.Detail) -> Unit)? = null
    private var itemClickListener: ((DataModel.Result.Detail) -> Unit)? = null

    private val listRowPresenter = object : ListRowPresenter(FocusHighlight.ZOOM_FACTOR_MEDIUM) {
        override fun isUsingDefaultListSelectEffect(): Boolean {
            return false
        }
    }.apply {
        shadowEnabled = false
    }

    private var rootAdapter: ArrayObjectAdapter = ArrayObjectAdapter(listRowPresenter)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = rootAdapter

        onItemViewSelectedListener = ItemViewSelectedListener()
        onItemViewClickedListener = ItemViewClickListener()
    }

    fun bindData(dataList: DataModel) {

        dataList.result.forEachIndexed { index, result ->
            val arrayObjectAdapter = ArrayObjectAdapter(ItemPresenter())

            result.details.forEach {
                arrayObjectAdapter.add(it)
            }

            val headerItem = HeaderItem(result.title)
            val listRow = ListRow(headerItem, arrayObjectAdapter)
            rootAdapter.add(listRow)

        }

    }


    fun setOnContentSelectedListener(listener: (DataModel.Result.Detail) -> Unit) {
        this.itemSelectedListener = listener
    }

    fun setOnItemClickListener(listener: (DataModel.Result.Detail) -> Unit) {
        this.itemClickListener = listener
    }

    inner class ItemViewSelectedListener : OnItemViewSelectedListener {
        override fun onItemSelected(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {
            if (item is DataModel.Result.Detail) {
                itemSelectedListener?.invoke(item)
            }

        }
    }

    inner class ItemViewClickListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {
            if (item is DataModel.Result.Detail) {
                itemClickListener?.invoke(item)
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra("id", item.id)
                startActivity(intent)
            }
        }

    }

    fun requestFocus(): View {
        val view = view
        view?.requestFocus()
        return view!!
    }

    fun bindCastData(casts: List<CastResponse.Cast>) {
        val arrayObjectAdapter = ArrayObjectAdapter(CastItemPresenter())
        casts.forEach { content ->
            arrayObjectAdapter.add(content)
        }
        val headerItem = HeaderItem("Cast & Crew")
        val listRow = ListRow(headerItem, arrayObjectAdapter)
        rootAdapter.add(listRow)

    }

}