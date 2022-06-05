package id.idzhami.screening_test.data.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import id.idzhami.screening_test.R
import id.idzhami.screening_test.data.models.event_model
import id.idzhami.screening_test.ui.dialog.DialogEventFragment

class RecycleViewAdapterScreenThree(

    private val context: Context,
    private var resultTransaction: List<event_model>,
    private val FragmentManager : FragmentManager

) : RecyclerView.Adapter<RecycleViewAdapterScreenThree.ViewHolderTransaction>() {
    private val TAG = javaClass.simpleName
    companion object {
        private const val VIEW_TYPE_DATA = 0;
        private const val VIEW_TYPE_PROGRESS = 1;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTransaction {
        return when (viewType) {
            VIEW_TYPE_DATA -> {//inflates row layout
                val view = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.item_list_screen_three, parent, false)
                ViewHolderTransaction(view)
            }
            VIEW_TYPE_PROGRESS -> {//inflates progressbar layout
                val view = LayoutInflater.from(parent?.context)
                    .inflate(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parent, false)
                ViewHolderTransaction(view)
            }
            else -> throw IllegalArgumentException("Different View type")
        }
    }

    override fun getItemCount(): Int = resultTransaction.size

    fun refreshAdapter(resultTransaction: List<event_model>) {
        this.resultTransaction = resultTransaction
        notifyItemRangeChanged(0, this.resultTransaction.size)
    }


    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(
        holder: RecycleViewAdapterScreenThree.ViewHolderTransaction,
        position: Int
    ) {
        val TXT_TITLE: TextView
        val TXT_DESCRIPTION: TextView
        val TXT_DATE: TextView
        val TXT_TIME : TextView
        val LY_CONTAINER : LinearLayout

        TXT_TITLE = holder.itemView.findViewById(R.id.TXT_TITLE) as TextView
        TXT_DESCRIPTION = holder.itemView.findViewById(R.id.TXT_DESCRIPTION) as TextView
        TXT_DATE = holder.itemView.findViewById(R.id.TXT_DATE) as TextView
        TXT_TIME = holder.itemView.findViewById(R.id.TXT_TIME) as TextView

        LY_CONTAINER = holder.itemView.findViewById(R.id.LY_CONTAINER) as LinearLayout
        if (holder.itemViewType == VIEW_TYPE_DATA) {
            val resultItem = resultTransaction[position]
            TXT_TITLE.text = resultItem.Title
            TXT_DESCRIPTION.text = resultItem.Description
            TXT_DATE.text = resultItem.DateTime
            LY_CONTAINER.setOnClickListener {
                val newFragment: DialogFragment = DialogEventFragment()
                val args = Bundle()
                args.putString("id", resultItem.Id.toString())
                args.putString("title", resultItem.Title)
                args.putString("description", resultItem.Description)
                args.putString("datetime", resultItem.DateTime)
                args.putString("lat", resultItem.Lat.toString())
                args.putString("long", resultItem.Lng.toString())

                newFragment.setArguments(args)
                newFragment.show(
                    this.FragmentManager,
                    "BottomSheetDialog"
                )
            }
        }

    }
    override fun getItemViewType(position: Int): Int {
        return if (resultTransaction[position] == null) {
            VIEW_TYPE_PROGRESS
        } else {
            VIEW_TYPE_DATA
        }
    }
    inner class ViewHolderTransaction(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

}