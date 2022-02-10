package com.deathhit.my_good_doggo_app.fragment.thumbnail_list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.deathhit.my_good_doggo_app.R

class LoadStateViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(LAYOUT, parent, false)
    ) {
    companion object {
        private const val ID_BTN_RETRY = R.id.button_retry
        private const val ID_PROGRESS_BAR = R.id.progressBar
        private const val ID_TEXT_ERROR_MSG = R.id.textView_errorMsg
        private const val LAYOUT = R.layout.item_thumbnail_list_load_state
    }

    val btnRetry: Button = itemView.findViewById(ID_BTN_RETRY)
    val progressBar: ProgressBar = itemView.findViewById(ID_PROGRESS_BAR)
    val textErrorMsg: TextView = itemView.findViewById(ID_TEXT_ERROR_MSG)
}