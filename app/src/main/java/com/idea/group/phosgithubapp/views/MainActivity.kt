package com.idea.group.phosgithubapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.idea.group.phosgithubapp.R
import com.idea.group.phosgithubapp.repos.DatabaseRepository
import com.idea.group.phosgithubapp.repos.GithubUserRepo
import com.idea.group.phosgithubapp.services.BaseNetworkService
import com.idea.group.phosgithubapp.viewModels.GitHubViewModel
import com.idea.group.phosgithubapp.viewModels.factories.GitHubViewModelFactory
import com.idea.group.phosgithubapp.views.adapters.GitHubRepoAdapter

class MainActivity : AppCompatActivity() {

    var viewModel : GitHubViewModel? = null
    private var recyclerView : RecyclerView? = null
    private var progressBar : ProgressBar? = null
    private var adapter : GitHubRepoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val baseNetworkService = BaseNetworkService()
        viewModel = GitHubViewModelFactory(GithubUserRepo(baseNetworkService),
            baseNetworkService,
            DatabaseRepository()).create(GitHubViewModel::class.java)

        setUpViewModelObservers()
        setUpUI()

        viewModel?.getItems()
    }

    private fun setUpUI() {
        progressBar = findViewById(R.id.progress_bar)
        progressBar?.visibility = View.GONE

        adapter = GitHubRepoAdapter(emptyList())

        recyclerView = findViewById(R.id.git_hub_repos_recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = adapter
    }

    private fun setUpViewModelObservers() {
        viewModel?.showError?.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viewModel?.showLoading?.observe(this, Observer {
            if(it)
                progressBar?.visibility = View.VISIBLE
            else
                progressBar?.visibility = View.GONE
        })

        viewModel?.gitHubItems?.observe(this, Observer {
            adapter?.items = it
        })

        viewModel?.getItems()

    }

    override fun onStart() {
        super.onStart()
        setUpViewModelObservers()
    }

    override fun onStop() {
        super.onStop()
        viewModel?.removeListeners(this)
    }
}