package com.altamiro.fitnesstracker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altamiro.fitnesstracker.model.Calc
import java.text.SimpleDateFormat
import java.util.Locale

class ListCalcActivity : AppCompatActivity() {
    private lateinit var rv_list_calc: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_calc)
        val result = mutableListOf<Calc>()
        val adapter = ListCalcAdapter(result)
        rv_list_calc = findViewById(R.id.rv_list_calc)
        rv_list_calc.layoutManager = LinearLayoutManager(this)
        rv_list_calc.adapter = adapter

        val type = intent?.extras?.getString("type") ?: throw IllegalArgumentException("type not found")



        Thread{
            val app = application as App
            val dao = app.db.calcDao()
            val response = dao.getRegisterByType(type)
            result.addAll(response)
            // avisa que foi inserido novos dados e renderiza novamente
            adapter.notifyDataSetChanged()

        }.start()
    }

    private inner class ListCalcAdapter(
        private val listCalc: List<Calc>,
    ) :
        RecyclerView.Adapter<ListCalcAdapter.ListViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
            val view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)
            return ListViewHolder(view)
        }

        override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
            val itemCurrent = listCalc[position]
            holder.bind(itemCurrent)
        }

        override fun getItemCount(): Int {
            return listCalc.size
        }
        private inner class ListViewHolder(iTemView: View) : RecyclerView.ViewHolder(iTemView) {
            fun bind(item: Calc) {
                val textV = itemView as TextView
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "br"))
                val data = sdf.format(item.createdAt)
                val res = item.res
                textV.text = getString(R.string.list_response, res, data)

            }
        }

    }


}