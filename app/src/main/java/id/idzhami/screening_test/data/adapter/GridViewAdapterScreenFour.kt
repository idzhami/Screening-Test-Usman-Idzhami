package id.idzhami.screening_test.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import id.idzhami.screening_test.R
import id.idzhami.screening_test.data.responses.UserResponses

class GridViewAdapterScreenFour(
    private val context: Context,
    private var resultData: UserResponses,
) :
    BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    override fun getCount(): Int {
        return resultData.data.size
    }
    override fun getItem(position: Int): Any? {
        return null
    }
    override fun getItemId(position: Int): Long {
        return 0
    }
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View? {
        var convertView = convertView
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.row_item_screen_four, null)
        }
        imageView = convertView!!.findViewById(R.id.imageView)
        textView = convertView.findViewById(R.id.textView)
        val data = resultData.data
//        for (i in data ){
            textView.text = data[position].first_name
            Picasso.get().load(data[position].avatar).into(imageView)
//        }
        return convertView
    }
}