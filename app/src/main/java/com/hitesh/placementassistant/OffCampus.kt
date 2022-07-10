package com.hitesh.placementassistant

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.hitesh.genie.adapter.OffCampusAdapter
import java.util.*
import kotlin.collections.ArrayList

class OffCampus : Fragment() {

    private lateinit var recyclerView: RecyclerView
    lateinit var offCampusArraylist: ArrayList<OffCampusModel>
    lateinit var offCampusAdapter: OffCampusAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_off_campus, container, false)
        recyclerView = v.findViewById(R.id.rv_off_campus)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        offCampusArraylist = arrayListOf()

        offCampusAdapter = OffCampusAdapter(offCampusArraylist)
        recyclerView.adapter = offCampusAdapter


        val db = FirebaseFirestore.getInstance()

        db.collection("OffCampus").get().addOnSuccessListener { documents ->
            try {
                for (document in documents) {
                    if (document != null) {
                        offCampusArraylist.add(document?.toObject(OffCampusModel::class.java)!!)
                    } else {
                        Toast.makeText(activity, "No job found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            catch (e: Exception){
                Log.d("Firestore Error", e.message.toString())
            }
        }
        return v
    }
}