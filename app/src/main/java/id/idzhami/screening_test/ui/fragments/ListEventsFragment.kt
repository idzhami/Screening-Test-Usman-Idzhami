package id.idzhami.screening_test.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.dynamic.SupportFragmentWrapper
import id.idzhami.screening_test.R
import id.idzhami.screening_test.data.adapter.RecycleViewAdapterScreenThree
import id.idzhami.screening_test.data.sqLite.DbAdapter
import id.idzhami.screening_test.databinding.FragmentListEventsBinding
import id.idzhami.screening_test.ui.base.BaseFragment
import kotlin.properties.Delegates


class ListEventsFragment : BaseFragment<FragmentListEventsBinding>() {
    private var adapterTransaction by Delegates.notNull<RecycleViewAdapterScreenThree>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataListDummy()
        binding.swipeRefreshLayout?.setOnRefreshListener {
            binding.swipeRefreshLayout!!.isRefreshing = false
            getDataListDummy()
            binding.rvMain.startLayoutAnimation()
        }
    }

    private fun getDataListDummy() {
        val db = DbAdapter(requireContext())
        var listData = db.TableEvents().getAllData()
        if (listData.isEmpty()) {
            db.TableEvents().initDummy()
            listData = db.TableEvents().getAllData()
        }

        adapterTransaction = RecycleViewAdapterScreenThree(
            requireContext(),
            listData,
            this.parentFragmentManager
        )
        binding.rvMain.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMain.adapter = adapterTransaction
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentListEventsBinding.inflate(inflater, container, false)


}