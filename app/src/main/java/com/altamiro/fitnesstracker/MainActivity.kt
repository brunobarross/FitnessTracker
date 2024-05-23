package com.altamiro.fitnesstracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity(), OnItemClickListener {

    //variavel que inicia depois do tipo linearlayout
    private lateinit var rv_main: RecyclerView
    private lateinit var btnImc: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainItems = mutableListOf<MainItem>()
        mainItems.add(
            MainItem(
                id = 1,
                drawableId = R.drawable.baseline_wb_sunny_24,
                textStringId = R.string.imc_label,
                color = R.color.colorLightGrey
            ),

            )
        mainItems.add(
            MainItem(
                id = 2,
                drawableId = R.drawable.baseline_category_24,
                textStringId = R.string.tmb,
                color = androidx.appcompat.R.color.material_blue_grey_800
            ),

            )

        //escutar eventos de touch
//        btnImc = findViewById(R.id.btn_imc)
//
//        btnImc.setOnClickListener{
//            //navegar para a próxima tela
//            //toda tela tem que ter no minimo uma actitity
//            // intent - uma intenção de abrir algum recurso do android
//
//            // primeiro param: a atividade current que está em execução, ou seja, o contexto atual (this = MainActitivity)
//            // segundo param: a classe que o kit de desenvolvimento do android precisa criar o objeto
//
//            val i = Intent(this, ImcActivity::class.java)
//            //inicializar a intenção
//            startActivity(i)
//
//        }

        // 1) o layout XML
        // 2) a onde a recyclerview vai aparecer (tela principal, tela cheia)
        // 3) logica - conectar o xml da celula DENTRO do recyclerView + a sua quantidade de elementos dinamicos
        //jogo dados dentro do adapter
        val adapter = MainAdapter(mainItems, this)
        rv_main = findViewById(R.id.rv_main)
        rv_main.adapter = adapter
        rv_main.layoutManager = GridLayoutManager(this, 2)


    }

    override fun onClick(id: Int) {
        when(id){
            1 -> {
                val intent = Intent(this, ImcActivity::class.java)
                startActivity(intent)
            }
            2 -> {
                //abrir uma outra activity
            }
        }
        Log.i("Teste", "clicou no $id!!!")
    }

    private inner class MainAdapter(
        private val mainItems: List<MainItem>,
        private val onItemClickListener: OnItemClickListener
    ) :
        RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

        // 1 - Qual é o layout XML da celula especifica (item)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }

        // 2 - disparado toda vez houver uma rolagem na tela e for necessario trocar o conteudo
        // da celula
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = mainItems[position]
            holder.bind(itemCurrent)
        }

        // 3 - informar quantas celulas essa listagem terá
        override fun getItemCount(): Int {
            return mainItems.size
        }

        // é a classe da celula (componente da lista) em si!!!
        private inner class MainViewHolder(iTemView: View) : RecyclerView.ViewHolder(iTemView) {
            fun bind(item: MainItem) {
                val img: ImageView = itemView.findViewById(R.id.item_img_icon)
                val name: TextView = itemView.findViewById(R.id.item_text_name)
                val container: ConstraintLayout = itemView.findViewById(R.id.item_container_imc)
                img.setImageResource(item.drawableId)
                name.setText(item.textStringId)
                container.setBackgroundColor(item.color)
                // 3 maneiras de escutar eventos de click usando celular (viewholder)
                // 1. impl interface
                container.setOnClickListener{
                        onItemClickListener.onClick(item.id)
                }

            }
        }

    }




}