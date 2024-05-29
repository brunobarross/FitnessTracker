package com.altamiro.fitnesstracker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altamiro.fitnesstracker.model.Calc

class ListCalcActivity : AppCompatActivity() {
    private lateinit var rv_list_calc: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_calc)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val type = intent?.extras?.getString("type") ?: throw IllegalArgumentException("type not found")

        Thread{
            val app = application as App
            val dao = app.db.calcDao()
            val response = dao.getRegisterByType(type)
            runOnUiThread{
                Log.i("Teste", "resposta: $response")

                val listAdapter = this@ListCalcActivity.ListAdapter(response)
                rv_list_calc.adapter = listAdapter
                rv_list_calc.layoutManager = LinearLayoutManager(this)


            }

        }.start()
    }
    private inner class ListAdapter(private val listData: List<Calc>) :
        RecyclerView.Adapter<ListAdapter.ViewHolder>() {

        // Subclasse interna ViewHolder dentro de MainAdapter
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            // Método para configurar os componentes de visualização com os dados do item atual
            fun bind(item: Calc){
                val name: TextView = itemView.findViewById(android.R.id.text1)
                name.setText(item.res.toString())

            }
        }

        // Sobrescreve o método para criar e retornar um novo ViewHolder
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
            return ViewHolder(view)
        }

        // Sobrescreve o método para retornar o número total de itens na lista
        override fun getItemCount(): Int {
            return listData.size
        }

        // Sobrescreve o método para vincular os dados do item atual ao ViewHolder correspondente
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val itemCurrent = listData[position]
            holder.bind(itemCurrent)
        }
    }

}