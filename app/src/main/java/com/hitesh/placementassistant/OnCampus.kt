package com.hitesh.placementassistant

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.hitesh.genie.adapter.OffCampusAdapter
import com.hitesh.genie.adapter.OnCampusAdapter

class OnCampus : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var onCampusArraylist: ArrayList<OffCampusModel>
    lateinit var onCampusAdapter: OnCampusAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_on_campus, container, false)
        recyclerView = v.findViewById(R.id.rv_on_campus)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        onCampusArraylist = arrayListOf()

        onCampusAdapter = OnCampusAdapter(onCampusArraylist)
        recyclerView.adapter = onCampusAdapter

        val db = FirebaseFirestore.getInstance()

        db.collection("OnCampus").get().addOnSuccessListener { documents ->
            try {
                for (document in documents) {
                    onCampusArraylist.add(document.toObject(OffCampusModel::class.java))
                }
                onCampusAdapter.notifyDataSetChanged();
            }
            catch (e: Exception){
                Log.d("Firestore Error", e.message.toString())
            }
        }


        return v
    }
}