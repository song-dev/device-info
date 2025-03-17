package com.song.deviceinfo.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.song.deviceinfo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by chensongsong on 2020/5/26.
 */
abstract class BaseFragment<T : Any> : Fragment() {
    protected abstract fun createAdapter(): BaseAdapter<T, *>?
    protected abstract fun createViewModel(): BaseViewModel<T>?

    protected var viewModel: BaseViewModel<T>? = null
    protected var adapter: BaseAdapter<T, *>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_base, container, false)
        viewModel = createViewModel()
        adapter = createAdapter()
        setupRecyclerView(view)
        setupSwipeRefreshLayout(view)
        viewModel?.items?.observe(viewLifecycleOwner) { items ->
            adapter?.setData(items)
        }
        return view
    }

    private fun setupRecyclerView(view: View) {
        view.findViewById<RecyclerView>(R.id.recycler_view)?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@BaseFragment.adapter
        }
    }

    private fun setupSwipeRefreshLayout(view: View) {
        view.findViewById<SwipeRefreshLayout>(R.id.srl)?.apply {
            setOnRefreshListener {
                refreshData()
                isRefreshing = false
            }
        }
    }

    protected open fun refreshData() {
        // To be implemented by subclasses
    }

    protected fun launchOnMain(block: suspend CoroutineScope.() -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                block()
            }
        }
    }

    protected fun launchOnIO(block: suspend CoroutineScope.() -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                block()
            }
        }
    }
}
