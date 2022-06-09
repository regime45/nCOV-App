package ai.kun.socialdistancealarm.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import ai.kun.socialdistancealarm.R
import ai.kun.socialdistancealarm.model.HistoryViewModel
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * The history fragment shows contacts with other devices.  We wanted to turn this
 * into some form of gamification similar to what the other applications have done, but we didn't
 * have the design resources to do it.
 *
 */
class HistoryFragment : Fragment() {

    /**
     * Inflate the fragment and set up the recycler view, the observe it for live updates
     * from newly detected devices written into the database.
     *
     * @param inflater Layout inflater
     * @param container View group
     * @param savedInstanceState saved bundle
     * @return
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val historyViewModel: HistoryViewModel by viewModels()
        val root = inflater.inflate(R.layout.fragment_history, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView_history_devices)
        context?.let { fragmentContext ->
            val deviceHistoryListAdapter = DeviceHistoryListAdapter(fragmentContext)

            recyclerView.apply {
                adapter = deviceHistoryListAdapter
                layoutManager = LinearLayoutManager(activity)
                addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
            }

            historyViewModel.devices.observe(viewLifecycleOwner, Observer { devices ->
                // Update the cached copy of the words in the adapter.
                devices?.let { deviceHistoryListAdapter.setDevices(it) }
            })
        }

        return root
    }
}
