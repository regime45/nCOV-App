package ai.kun.socialdistancealarm.view.home

import ai.kun.opentracesdk_fat.BLETrace
import ai.kun.opentracesdk_fat.DeviceRepository
import ai.kun.socialdistancealarm.R
import ai.kun.socialdistancealarm.model.HomeViewModel
import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * The home fragment shows the list of currently detected devices from the database.
 * Note that this is nearly identical to
 * the code in the history fragment.  The plan was to keep this and make history look different using
 * some gamification, but since Apple pulled the application from the App Store we gave up on doing
 * more.
 *
 *
 */
class HomeFragment : Fragment() {
    private val TAG = "HomeFragment"

    private val REQUEST_FINE_LOCATION = 2
    private val REQUEST_BACKGROUND_LOCATION = 3

    private var mIsChecking = false

    private var mIsTraceEnabled = true
    private var mFineLocationGranted = true
    private var mBackgroundLocationGranted = true
    private var mBluetoothEnabled = true

    /**
     * Inflate, add the recycler view for the devices, then observe it for changes in the state
     * of detection.  When detection is paused we show a different card and hide the one with
     * the list of currently detected devices.  There were plans to have a count down timer but
     * we didn't have time to implement that.
     *
     * @param inflater Layout inflater
     * @param container View group
     * @param savedInstanceState not used
     * @return the inflated observed view
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val homeViewModel: HomeViewModel by viewModels()
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView_devices)
        context?.let { fragmentContext ->
            val deviceListAdapter = DeviceListAdapter(fragmentContext)

            recyclerView.apply {
                adapter = deviceListAdapter
                layoutManager = LinearLayoutManager(activity)
                addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
            }

            homeViewModel.devices.observe(viewLifecycleOwner, Observer { devices ->
                // Update the cached copy of the devices in the adapter.
                devices?.let {
                    if (devices.isEmpty()) {
                        emptyDevices.visibility = View.VISIBLE
                        recyclerView_devices.visibility = View.GONE
                    } else {
                        emptyDevices.visibility = View.GONE
                        recyclerView_devices.visibility = View.VISIBLE
                        deviceListAdapter.setDevices(it)
                    }
                } ?: kotlin.run {
                    emptyDevices.visibility = View.VISIBLE
                    recyclerView_devices.visibility = View.GONE
                }
            })

            // Watch for pausing...
            homeViewModel.isStarted.observe(viewLifecycleOwner, Observer { isStarted ->
                isStarted?.let {
                    setVisibility(root, it)
               }
            })
        }

        return root
    }

    /**
     * Set up visibility based on the state of scanning
     *
     * @param root the view
     * @param isStarted set to true if scanning has been started.
     */
    private fun setVisibility(root: View, isStarted: Boolean) {
        if (isStarted) {
            // Update the devices
            GlobalScope.launch {DeviceRepository.updateCurrentDevices()}

            root.findViewById<RecyclerView>(R.id.recyclerView_devices).visibility =
                View.VISIBLE
            root.findViewById<ConstraintLayout>(R.id.constraintLayout_paused).visibility =
                View.GONE
            root.findViewById<FloatingActionButton>(R.id.pausePlayFab).setImageResource(R.drawable.ic_baseline_pause_24)
        } else {
            root.findViewById<RecyclerView>(R.id.recyclerView_devices).visibility =
                View.GONE
            root.findViewById<ConstraintLayout>(R.id.constraintLayout_paused).visibility =
                View.VISIBLE
            root.findViewById<FloatingActionButton>(R.id.pausePlayFab).setImageResource(R.drawable.ic_baseline_play_arrow_24)
        }
    }

    /**
     * Post creation we deal with setting up the state of things with respect to the state of scanning
     * for devices.
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO: Move this to a shared pref
        mIsTraceEnabled = true

        mFineLocationGranted = true
        mBackgroundLocationGranted = true
        mBluetoothEnabled = true

        // Initialize the visibility
        BLETrace.isStarted.value?.let {
            setVisibility(view, it)
        }

        // Initialize the resume when tapping on the blue text...
        view.findViewById<TextView>(R.id.TextView_resume_detecting).setOnClickListener {
            BLETrace.isPaused = false
        }

        view.findViewById<FloatingActionButton>(R.id.pausePlayFab).setOnClickListener {
            BLETrace.isStarted.value?.let { isStarted ->
                BLETrace.isPaused = isStarted
            }
        }
    }

    /**
     * Start tracing if we have the permissions.
     *
     */
    override fun onResume() {
        super.onResume()
        //TODO: Change this to enable with a shared pref
        context?.let {
            checkPermissions(it)
            checkBluetooth(it)
        }

        startTrace()
    }

    /**
     * Check to make sure that we have the correct positions and start tracing.
     *
     */
    private fun startTrace() {
        if (mIsTraceEnabled && !mIsChecking && mBluetoothEnabled && mFineLocationGranted) {
            BLETrace.start(false)
        }
    }

    /**
     * Since this is the first fragment that users see after onboarding and it's the spot where
     * scanning is turned on and off it is also where get permissions from the user and make sure
     * that the device has what it needs to run the app correctly.
     *
     * @param context
     * @return
     */
    private fun checkPermissions(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        // Check permissions
        if (!mIsChecking && mIsTraceEnabled) {
/*
            // I think this will get us kicked out of the PlayStore and I'm not 100% sure that
            // it is actually needed.  Many devices see our app as using too much battery because
            // it runs a lot, but it doesn't do anything consumptive when it runs (e.g. it doesn't
            // connect to the internet or the GPS)
            val intent = Intent()
            val packageName: String = requireActivity().packageName
            val pm: PowerManager = requireActivity().getSystemService(POWER_SERVICE) as PowerManager
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            }
*/

            if (mFineLocationGranted &&
                context.applicationContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                mIsChecking = true
                mFineLocationGranted = false
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_FINE_LOCATION
                )
                Log.d(TAG, "Requested user enable Location.")
                return false
            }
            if (mBackgroundLocationGranted &&
                context.applicationContext.checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                mIsChecking = true
                mBackgroundLocationGranted = false
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                    REQUEST_BACKGROUND_LOCATION
                )
                Log.d(TAG, "Requested user enable Location.")
                return false
            }

            return true
        } else {
            return false
        }
    }

    /**
     * Make sure Bluetooth is correctly supported
     *
     * @param context The context to use
     * @return True if everything is good and we can start scanning
     */
    private fun checkBluetooth(context: Context): Boolean {
        // ??? lazy load this on a thread ???
        val bluetoothManager =
            context.applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

        // Check if it's even there
        if (bluetoothManager.adapter == null) {
            //TODO: show something meaning full to the user
            Toast.makeText(context, "No Bluetooth Adapter, this phone is not supported.", Toast.LENGTH_LONG)
            Log.e(TAG, "Bluetooth adapter was null.")
            return false
        }

        // Check if bluetooth is enabled
        if (!bluetoothManager.adapter.isEnabled()) {
            // Request user to enable it
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivity(enableBtIntent)
            mBluetoothEnabled = false
            return false
        }

        // Check low energy support
        if (!context.applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            // Get a newer device
            Log.e(TAG, "No LE Support.")
            mBluetoothEnabled = false
            return false
        }

        // Check advertising
        if (!bluetoothManager.adapter.isMultipleAdvertisementSupported) {
            // Unable to run the server on this device, get a better device
            Log.e(TAG, "No Advertising Support.")
            mBluetoothEnabled = false
            return false
        }



        mBluetoothEnabled = true
        return true
    }

    /**
     * Deal with the results of asking for various permissions
     *
     * @param requestCode The request code used to ask for permissions
     * @param permissions The requested permissions
     * @param grantResults The results
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_BACKGROUND_LOCATION -> {
                if (!grantResults.isEmpty()) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        Log.w(TAG, "...Request for background location was denied.")
                        mIsChecking = false
                        mBackgroundLocationGranted = false
                        //TODO: add some code to disable background tracking in a shared pref
                    } else {
                        Log.w(TAG, "...Request for background location was granted.")
                        mIsChecking = false
                        mBackgroundLocationGranted = true
                    }
                } else {
                    // for some lame reason Android gives you a result with nothing in it before
                    // there is a real result?
                    Log.d(TAG, "Prompting for background location response...")
                }
            }
            REQUEST_FINE_LOCATION -> {
                if (!grantResults.isEmpty()) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        Log.w(TAG, "...Request for fine location was denied.")
                        mIsChecking = false
                        mFineLocationGranted = false
                        mIsTraceEnabled = false
                        //TODO: add some code to disable tracking in a shared pref and an indicator
                    } else {
                        Log.w(TAG, "...Request for fine location was granted.")
                        mFineLocationGranted = true
                        mIsChecking = false
                    }
                } else {
                    // for some lame reason Android gives you a result with nothing in it before
                    // there is a real result?
                    Log.d(TAG, "Prompting for fine location response...")
                }
            }
        }
    }
}
