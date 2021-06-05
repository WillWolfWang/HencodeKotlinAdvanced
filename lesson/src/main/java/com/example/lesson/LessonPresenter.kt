package com.example.lesson

import com.example.core.http.EntityCallback
import com.example.core.http.HttpClient
import com.example.core.utils.Utils
import com.example.lesson.entity.Lesson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.ArrayList

class LessonPresenter constructor(var activity: LessonActivity){

    companion object {
        const val LESSON_PATH: String = "lessons"
    }

    private var lessons: List<Lesson> = ArrayList()

//    private val type: Type = TypeToken<ArrayList<Lesson>>().type

    private val type = object : TypeToken<List<Lesson>>() {

    }.type

    fun fetchData() {
        HttpClient.get(LESSON_PATH, type, object : EntityCallback<List<Lesson>>{
            override fun onFailure(message: String?) {
                activity.runOnUiThread {
                    Utils.toast(message)
                }
            }

            override fun onSuccess(entity: List<Lesson>) {
                this@LessonPresenter.lessons = entity
                activity.runOnUiThread{
                    activity.showResult(lessons)
                }
            }

        })
    }

    fun showPlayback() {
        activity.showResult(lessons.filter {
            it.state == Lesson.State.PLAYBACK
        })
    }
}