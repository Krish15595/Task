package com.d2k.task.ui.dashboard.userlistview

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.d2k.task.databinding.FragmentGraphViewBinding
import com.d2k.task.util.Status
import com.d2k.tmb.extension.showProgress
import com.d2k.tmb.extension.showToast
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Use the [UserListViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserListViewFragment : Fragment(), GridViewAdapter.OnListClick {
    @Inject
    lateinit var userListViewModel: UserListViewModel
    private var _graphViewBinding: FragmentGraphViewBinding? = null
    lateinit var dialog: Dialog
    private val graphViewBinding get() = _graphViewBinding!!
    val gridList = arrayListOf<GridViewResponse>()
    lateinit var gridViewAdapter: GridViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _graphViewBinding = FragmentGraphViewBinding.inflate(inflater, container, false)
        return graphViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        dialog = Dialog(requireContext())
        graphViewBinding.viewmodel = userListViewModel
        userListViewModel.userDetailsFlow()
        setHomeUI()

/*        graphViewBinding.btnUserRole.setOnClickListener {
            val bundle = bundleOf("location_type" to "HO")
            findNavController().navigate(R.id.action_graphViewFragment_to_locationFragment, bundle)
        }*/
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    private fun setHomeUI() {
        lifecycleScope.launchWhenCreated {
            userListViewModel.userDetailsFlow.catch {
                showToast(it.cause.toString())
            }.collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        showProgress(dialog, 1)

                        if (it.data?.data != null) {
                            gridList.clear()
                            for (i in 0 until it.data.data.size) {
                                gridList.add(
                                    GridViewResponse(
                                        it.data.data.get(i).first_name.toString(),
                                        it.data.data.get(i).avatar.toString(),
                                        i == 0
                                    )
                                )
                            }
                            graphViewBinding.rvHorizontal.layoutManager =
                                GridLayoutManager(
                                    requireContext(), 2,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                            gridViewAdapter =
                                GridViewAdapter(requireContext(), gridList, this@UserListViewFragment)
                            graphViewBinding.rvHorizontal.adapter = gridViewAdapter

                        }
                    }
                    Status.ERROR -> {
                        showProgress(dialog, 1)
                        showToast(it.error.toString())
                    }
                    Status.LOADING -> {
                        showProgress(dialog)
                    }
                }
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onClicked(pos: Int, gridModel: GridViewResponse) {
        gridViewAdapter.notifyDataSetChanged()
    }

}