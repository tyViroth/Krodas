package com.krodas.localimage.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.krodas.localimage.R
import com.krodas.localimage.adapter.MainAdapter
import com.krodas.localimage.handler.ResultOf
import com.krodas.localimage.listener.ClickListener
import com.krodas.localimage.model.Post
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

        mainRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = mainAdapter
            setHasFixedSize(true)
        }
    }
}