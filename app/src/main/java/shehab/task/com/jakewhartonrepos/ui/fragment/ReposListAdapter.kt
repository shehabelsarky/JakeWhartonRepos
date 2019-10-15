package shehab.task.com.jakewhartonrepos.ui.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import shehab.task.com.jakewhartonrepos.R
import shehab.task.com.jakewhartonrepos.data.model.ReposResponse
import java.util.ArrayList

const val TYPE_ITEM = 1
const val TYPE_LOADING = 2

class ReposListAdapter(
    private val mContext: Context,
    var reposList: ArrayList<ReposResponse>,
    var mListener: OnItemClickListener
) : androidx.recyclerview.widget.RecyclerView.Adapter<ReposListAdapter.ViewHolder>() {
    
    var layoutType = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var layout = -1
        when (viewType) {
            TYPE_ITEM -> layout = R.layout.item_list_repos
            TYPE_LOADING -> layout = R.layout.item_loading
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = reposList[position]

        if (repo.type == TYPE_ITEM) {
            holder.setRepoItemData(repo)
        }

        if (repo.type == TYPE_LOADING) {
            holder.setProgressBar(true)
        }


        holder.itemView.setOnClickListener {
            mListener.onItemClick(reposList[position])
        }
    }

    override fun getItemCount(): Int {
        return reposList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (reposList[position].type == TYPE_ITEM)
            layoutType = TYPE_ITEM

        if (reposList[position].type == TYPE_LOADING)
            layoutType = TYPE_LOADING

        return layoutType
    }

    interface OnItemClickListener {
        fun onItemClick(repoItem: ReposResponse)
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        //repos item iew
        private lateinit var tvName: TextView
        private lateinit var tvRepoInfo: TextView
        private lateinit var ivImage: RoundedImageView

        //loading item view
        private var progressBar: ProgressBar? = null

        init {

            when (layoutType) {
                TYPE_ITEM -> {
                    tvName = itemView.findViewById(R.id.tv_name)
                    tvRepoInfo = itemView.findViewById(R.id.tvRepoInfo)
                    ivImage = itemView.findViewById(R.id.iv_image)
                }
                TYPE_LOADING -> {
                    progressBar = itemView.findViewById(R.id.progressBar)
                }
            }

        }


        fun setRepoItemData(repo: ReposResponse) {
            tvName.text = repo.name
            tvRepoInfo.text = repo.owner.repos_url

            val avatarUrl = repo.owner.avatarUrl
            if (avatarUrl != null && avatarUrl.isNotEmpty())
                Picasso.with(mContext).load(repo.owner.avatarUrl).into(ivImage)
        }

        fun setProgressBar(isVisible: Boolean) {
            when {
                isVisible -> progressBar?.visibility = View.VISIBLE
                else -> progressBar?.visibility = View.GONE
            }

        }
    }
}
