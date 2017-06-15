package cz.inventi.inventiskeleton.presentation.post.detail

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import cz.inventi.inventiskeleton.R
import cz.inventi.inventiskeleton.data.comment.Comment
import cz.inventi.inventiskeleton.data.post.Post
import cz.inventi.inventiskeleton.di.conductorlib.ConductorInjection
import cz.inventi.inventiskeleton.presentation.common.BaseController
import cz.inventi.inventiskeleton.presentation.common.bindView
import javax.inject.Inject

/**
 * Created by ecnill on 6/7/2017.
 */

class PostDetailController(bundle: Bundle) : BaseController<PostDetailView, PostDetailPresenter>(bundle), PostDetailView {

    companion object {
        private val TAG = PostDetailController::class.java.name

        fun instance(id: Int) : PostDetailController {
            val args = Bundle()
            args.putInt(TAG, id)
            return PostDetailController(args)
        }
    }

    @Inject lateinit var postDetailPresenter : PostDetailPresenter
    val commentListAdapter = CommentListAdapter()

    internal val postTitle: TextView by bindView(R.id.txt_post_title)
    internal val postBody: TextView by bindView(R.id.txt_post_body)
    internal val postUserId: TextView by bindView(R.id.txt_post_user_id)
    internal val btnShowComments: Button by bindView(R.id.btn_show_comments)
    internal val listComments: RecyclerView by bindView(R.id.list_comments)

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_post_detail, container, false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        ConductorInjection.inject(this)
        postDetailPresenter.postId = args.getInt(TAG)
        return super.onCreateView(inflater, container)
    }

    override fun onViewBind(view: View) {
        btnShowComments.setOnClickListener { presenter.onShowMoreCommentsClicked() }
        listComments.layoutManager = LinearLayoutManager(activity)
        listComments.adapter = commentListAdapter
    }

    override fun createPresenter(): PostDetailPresenter {
        return postDetailPresenter
    }

    override fun showDetailPost(post: Post) {
        postTitle.text = post.title
        postBody.text = post.body
        postUserId.text = post.userId.toString()
        showComments(post.comments)
    }

    override fun showComments(comments: List<Comment>) {
       commentListAdapter.updateData(comments)
    }

    override fun hideMoreCommentButton() {
        btnShowComments.visibility = View.GONE
    }

}
