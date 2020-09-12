package com.krodas.app.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.krodas.app.R
import com.krodas.app.adapter.MainAdapter
import com.krodas.app.handler.ResultOf
import com.krodas.app.listener.ClickListener
import com.krodas.app.model.Post
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var mainAdapter: MainAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        initViewModel()
        initView()

    }

    private fun initViewModel() {
        viewModel.fetchPostsFromApi(activity = activity!!)
        viewModel.posts.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ResultOf.Success -> {
                    mainAdapter!!.submitList(result.data)
                }
                is ResultOf.Error -> {
                    Toast.makeText(context, "Error getting photo from phone", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initView() {
        mainAdapter = MainAdapter(object : ClickListener {
            override fun onItemClick(post: Post) {
                Toast.makeText(context, "${post.url}", Toast.LENGTH_SHORT).show()
            }
        })

        val mLayoutManager = StaggeredGridLayoutManager(2, 1)
        mLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            mainRecyclerView.apply {
            layoutManager = mLayoutManager
            adapter = mainAdapter
            setHasFixedSize(true)
                setItemViewCacheSize(20)
        }
    }

}