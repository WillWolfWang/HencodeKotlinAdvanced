package com.example.lesson

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.core.BaseView
import com.example.lesson.entity.Lesson

class LessonActivity: AppCompatActivity(), BaseView<LessonPresenter>, Toolbar.OnMenuItemClickListener {

    override val presenter: LessonPresenter by lazy {
        LessonPresenter(this)
    }

    private val lessonAdapter: LessonAdapter = LessonAdapter()

    lateinit var refreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)

        findViewById<Toolbar>(R.id.toolbar).run {
            inflateMenu(R.menu.menu_lesson)
            setOnMenuItemClickListener(this@LessonActivity)
        }


        findViewById<RecyclerView>(R.id.list).run {
            layoutManager = LinearLayoutManager(this@LessonActivity)
            adapter = lessonAdapter
            addItemDecoration(DividerItemDecoration(this@LessonActivity, LinearLayoutManager.VERTICAL))

        }

        refreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout).apply {
            setOnRefreshListener {
                Log.e("WillWolf", "onRefresh-->")
                presenter.fetchData()
            }
            isRefreshing = true
        }
//        refreshLayout.setOnRefreshListener { SwipeRefreshLayout.OnRefreshListener {
//            Log.e("WillWolf", "onRefresh-->")
//            getPresenter().fetchData()
//            }
//        }

        presenter.fetchData()
    }

    fun showResult(lesson: List<Lesson>) {
        Log.e("WillWolf", "showResult")
        lessonAdapter.updateAndNotify(lesson)
        refreshLayout.isRefreshing = false
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        presenter.showPlayback()
        return false
    }
}