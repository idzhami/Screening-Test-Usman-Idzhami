package id.idzhami.screening_test.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import id.idzhami.screening_test.R
import id.idzhami.screening_test.data.models.event_model
import id.idzhami.screening_test.data.sqLite.DbAdapter


class MapViewFragment : Fragment() {
    private var googleMap: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_map_view, container, false)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment!!.getMapAsync { resMap ->
            this.googleMap = resMap
            this.googleMap!!.clear()
            googleMap!!.setOnMapClickListener {
                val card = container?.findViewById<CardView>(R.id.overlayCard)
                if (card!!.isVisible) {
                    card.visibility = View.GONE
                }
            }
            googleMap!!.setOnMarkerClickListener {
                Toast.makeText(requireContext(), it.position.toString(), Toast.LENGTH_LONG).show()

                val card = container?.findViewById<CardView>(R.id.overlayCard)
                if (!card!!.isVisible) {
                    card.visibility = View.VISIBLE
                }

                val markerInfo: event_model = it.tag as event_model
                val txTitle = container?.findViewById<TextView>(R.id.txMarkerTitle)
                txTitle.text = markerInfo.Title
                false
            }
            val db = DbAdapter(requireContext())
            var listData = db.TableEvents().getAllData()
            val listDataWithCoordinates = ArrayList<event_model>()
            if (listData.isEmpty()) {
                db.TableEvents().initDummy()
                listData = db.TableEvents().getAllData()
            }
            for (i in listData) {
                try {
                    val loc = LatLng(i.Lat!!, i.Lng!!)
                    listDataWithCoordinates.add(i)
                    val marker = googleMap!!.addMarker(MarkerOptions().position(loc).title(i.Title))
                    if (marker != null) {
                        marker.tag = i
                    }

                } catch (e: Exception) {
                    continue
                }

            }
            var lat = listDataWithCoordinates[0].Lat;
            var lng = listDataWithCoordinates[0].Lng;

            if (lat == null || lng == null) {
                //set default to bandung
                lat = -6.8902647
                lng = 107.6146987
            }
            val loc = LatLng(lat, lng)

            val camBuilder = CameraPosition.builder()
            camBuilder.target(loc)
            camBuilder.zoom(18f)
            val cp = camBuilder.build()

            googleMap!!.moveCamera(CameraUpdateFactory.newCameraPosition(cp))
        }
        return rootView
    }

}