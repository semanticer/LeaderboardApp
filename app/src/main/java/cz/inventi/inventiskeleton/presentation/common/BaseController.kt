package cz.inventi.inventiskeleton.presentation.common

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.view.View

import com.bluelinelabs.conductor.Controller
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

import cz.inventi.inventiskeleton.domain.common.ActionBarProvider

/**
 * Created by tomas.valenta on 5/11/2017.
 */

abstract class BaseController<V : MvpView, P : MvpPresenter<V>> : RefWatchingController<V, P> {

    protected constructor() {}

    protected constructor(args: Bundle) : super(args) {}

    // Note: This is just a quick demo of how an ActionBar *can* be accessed, not necessarily how it *should*
    // be accessed. In a production app, this would use Dagger instead.
    protected val actionBar: ActionBar?
        get() {
            val actionBarProvider = activity as ActionBarProvider?
            return actionBarProvider?.getSupportActionBar()
        }

    override fun onAttach(view: View) {
        setTitle()
        super.onAttach(view)
    }

    protected fun setTitle() {
        var parentController = parentController
        while (parentController != null) {
            if (parentController is BaseController<*, *> && parentController.title != null) {
                return
            }
            parentController = parentController.parentController
        }

        val title = title
        val actionBar = actionBar
        if (title != null && actionBar != null) {
            actionBar.title = title
        }
    }

    protected val title: String?
        get() = null
}