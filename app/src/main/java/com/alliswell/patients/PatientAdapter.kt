import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alliswell.data.Patient
import com.alliswell.patients.R

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
class PatientAdapter(patients: List<Patient>,  context: Context) : RecyclerView.Adapter<PatientAdapter.ViewHolder>() {
    val _patients: List<Patient>
    val _context: Context

    init {
        _patients = patients
        _context = context
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {

        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val firstName = itemView.findViewById<TextView>(R.id.textView2)
        val lastName = itemView.findViewById<TextView>(R.id.textView3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        // Inflate the custom layout
        val view = inflater.inflate(R.layout.patient_list_item, parent, false)
        // Return a new holder instance
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return _patients.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val patient: Patient = _patients.get(position)
        // Set item views based on your views and data model
        val textView2 = holder.firstName
        textView2.setText(patient.firstName)
        val textView3 = holder.lastName
        textView3.setText(patient.lastName)

    }
}