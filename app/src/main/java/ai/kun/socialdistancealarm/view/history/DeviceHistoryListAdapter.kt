package ai.kun.socialdistancealarm.view.history

import ai.kun.opentracesdk_fat.dao.Device
import ai.kun.opentracesdk_fat.util.BluetoothUtils
import ai.kun.opentracesdk_fat.util.Constants.SIGNAL_DISTANCE_LIGHT_WARN
import ai.kun.opentracesdk_fat.util.Constants.SIGNAL_DISTANCE_OK
import ai.kun.opentracesdk_fat.util.Constants.SIGNAL_DISTANCE_STRONG_WARN
import ai.kun.opentracesdk_fat.util.Constants.TIME_FORMAT
import ai.kun.socialdistancealarm.R
import ai.kun.socialdistancealarm.util.DateUtils.getFormattedDateString
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


/**
 * Lists the history of contacts with other users of the application.  We wanted to turn this
 * into some form of gamification similar to what the other applications have done, but we didn't
 * have the design resources to do it.
 *
 * @constructor
 * Constructs an adapter for the list view for device history
 *
 * @param adapterContext The adapter context to use
 */
class DeviceHistoryListAdapter internal constructor(
    adapterContext: Context
) : RecyclerView.Adapter<DeviceHistoryListAdapter.DeviceViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(adapterContext)
    private val context: Context = adapterContext
    private var devices = emptyList<Device>()

    /**
     * The view holder
     *
     * @constructor
     * populate the view with distance, time, etc.
     *
     * @param itemView The view to populate
     */
    inner class DeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val distanceTextView: TextView = itemView.findViewById(R.id.textView_distance)
        val signalTextView: TextView = itemView.findViewById(R.id.textView_signal)
        val timestampTextView: TextView = itemView.findViewById(R.id.textView_timestamp)
        val peopleImageView: ImageView = itemView.findViewById(R.id.imageView_people)
    }

    /**
     * Inflate the layout
     *
     * @param parent The parent view group
     * @param viewType The view type
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val itemView = inflater.inflate(R.layout.item_device_history, parent, false)
        return DeviceViewHolder(itemView)
    }

    /**
     * Translate the values in the holder into the correct UI.
     * The code below should have be DRYed, but I was in a hurry.  It's nearly identical to what
     * is done for home.  Again the plan was to eventually change this to something that had
     * gamification, but the priority was launching iOS and apple didn't let us launch.
     *
     * @param holder The holder to set up
     * @param position The position of the holder
     */
    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val current = devices[position]

        // Notify the user when we are adding a device that's too close
        val signal = BluetoothUtils.calculateSignal(current.rssi, current.txPower, current.isAndroid)
        when {
            signal <= SIGNAL_DISTANCE_OK -> {
                holder.distanceTextView.text = context.resources.getString(R.string.safer)
                holder.peopleImageView.setImageResource(R.drawable.ic_person_good_icon)
                holder.peopleImageView.imageTintList = ColorStateList.valueOf(ResourcesCompat.getColor(context.resources, R.color.green, context.theme))
            }
            signal <= SIGNAL_DISTANCE_LIGHT_WARN -> {
                holder.distanceTextView.text = context.resources.getString(R.string.warning)
                holder.peopleImageView.setImageResource(R.drawable.ic_person_warning_icon)
                holder.peopleImageView.imageTintList = ColorStateList.valueOf(ResourcesCompat.getColor(context.resources, R.color.yellow, context.theme))
            }
            signal <= SIGNAL_DISTANCE_STRONG_WARN -> {
                holder.distanceTextView.text = context.resources.getString(R.string.strong_warning)
                holder.peopleImageView.setImageResource(R.drawable.ic_person_danger_icon)
                holder.peopleImageView.imageTintList = ColorStateList.valueOf(ResourcesCompat.getColor(context.resources, R.color.orange, context.theme))
            }
            else -> {
                holder.distanceTextView.text = context.resources.getString(R.string.too_close)
                holder.peopleImageView.setImageResource(R.drawable.ic_person_too_close_icon)
                holder.peopleImageView.imageTintList = ColorStateList.valueOf(ResourcesCompat.getColor(context.resources, R.color.red, context.theme))

                val database = FirebaseDatabase.getInstance()
                val myRefer: DatabaseReference =
                    database.getReference("covid_tool").child("close_contacts")
                val newChildRef: DatabaseReference = myRefer.push()
                val key = newChildRef.key
                val sdf = SimpleDateFormat("dd/MMM/yyyy 'at' hh:mm aaa")
                val currentDateandTime = sdf.format(Date())

               // val hours = SimpleDateFormat("hh:00_aa")
                //val hour =  hours.format(Date())

                @SuppressLint("WrongConstant") val sharedPreferences: SharedPreferences =
                    context.getSharedPreferences("MySharedPref", Context.MODE_APPEND)

                val name = sharedPreferences.getString("name", "")
                val contact = sharedPreferences.getString("contact", "")

                val barang = sharedPreferences.getString("barangays", "")
                val puroks = sharedPreferences.getString("puruks", "")
                val imageURL_camera = sharedPreferences.getString("image_camera", "")
                val imageURL_gallery = sharedPreferences.getString("image_gallery", "")
                Toast.makeText(context, name , Toast.LENGTH_SHORT).show()


                // Here pref is the name of the file and 0 for private mode.
                // To read values from SharedPreferences, you can use getInt() method on
                // String str= ""+newstring+ "" +text ;
                val number = "+63"+contact

                val daily = SimpleDateFormat("dd/MMM/yyyy")
                val dailytime = daily.format(Date())

                val trace = SimpleDateFormat("hh:00 aa, dd MMM")
                val trace_date: String = trace.format(Date())


                    if (dailytime != null) {
                        myRefer.child("trace").child(number).child("status")
                            .setValue("High risk danger")
                        myRefer.child("trace").child(number).child("name")
                            .setValue(name)
                        myRefer.child("trace").child(number).child("signal")
                            .setValue(signal.toString() )
                        myRefer.child("trace").child(number).child("contact")
                            .setValue( number)
                        myRefer.child("trace").child(number).child("imageURL")
                           .setValue(imageURL_camera)
                        myRefer.child("trace").child(number).child("imageURL")
                            .setValue(imageURL_gallery)
                        myRefer.child("trace").child(number).child("purok")
                            .setValue(puroks)
                        myRefer.child("trace").child(number).child("barangay")
                            .setValue(barang)
                        myRefer.child("trace").child(number).child("date")
                            .setValue(getFormattedDateString(TIME_FORMAT, current.timeStamp))
                        myRefer.child("trace").child(number).child("trace_date")
                            .setValue(trace_date)
                    }

                // by hours
                val zone = SimpleDateFormat("hh:00 aa")
                val red_zone: String =  zone.format(Date())
                val myRefers: DatabaseReference =
                    database.getReference("covid_tool").child("close_contacts").child("red_green_zone")

                if (dailytime != null) {
                    myRefers.child(trace_date).child(number).child("status")
                        .setValue("High risk danger")
                    myRefers.child(trace_date).child(number).child("name")
                        .setValue(name)
                    myRefers.child(trace_date).child(number).child("signal")
                        .setValue(signal.toString() )
                    myRefers.child(trace_date).child(number).child("contact")
                        .setValue( number)
                    myRefers.child(trace_date).child(number).child("imageURL")
                        .setValue(imageURL_camera)
                    myRefers.child(trace_date).child(number).child("imageURL")
                        .setValue(imageURL_gallery)
                    myRefers.child(trace_date).child(number).child("purok")
                        .setValue(puroks)
                    myRefers.child(trace_date).child(number).child("barangay")
                        .setValue(barang)
                    myRefers.child(trace_date).child(number).child("date")
                        .setValue(getFormattedDateString(TIME_FORMAT, current.timeStamp))
                    myRefers.child(trace_date).child(number).child("trace_date")
                        .setValue(trace_date)
                }
            }
        }

        if (current.isTeamMember) {
            holder.distanceTextView.text = context.resources.getString(R.string.safer)
            holder.peopleImageView.setImageResource(R.drawable.ic_people_black_24dp)
            holder.peopleImageView.imageTintList = ColorStateList.valueOf(ResourcesCompat.getColor(context.resources, R.color.green, context.theme))
        }

        if (current.isTeamMember) {
            holder.distanceTextView.text = context.resources.getString(R.string.safer)
            holder.peopleImageView.setImageResource(R.drawable.ic_people_black_24dp)
            holder.peopleImageView.imageTintList = ColorStateList.valueOf(ResourcesCompat.getColor(context.resources, R.color.green, context.theme))
        }

        holder.signalTextView.text = signal.toString()
        holder.timestampTextView.text = getFormattedDateString(TIME_FORMAT, current.timeStamp)



    }

    /**
     * Update the device list.  The history view shows a live list of devices.
     *
     * @param devices The devices in the current view
     */
    internal fun setDevices(devices: List<Device>) {
        this.devices = devices
        notifyDataSetChanged()
    }

    /**
     * Get the number of detected devices
     *
     */
    override fun getItemCount() = devices.size
}