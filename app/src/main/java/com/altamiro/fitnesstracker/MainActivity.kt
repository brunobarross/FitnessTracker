package com.altamiro.fitnesstracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    //variavel que inicia depois do tipo linearlayout
    private lateinit var btnImc: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //escutar eventos de touch

        btnImc = findViewById(R.id.btn_imc)

        btnImc.setOnClickListener{
            //navegar para a próxima tela
            //toda tela tem que ter no minimo uma actitity
            // intent - uma intenção de abrir algum recurso do android

            // primeiro param: a atividade current que está em execução, ou seja, o contexto atual (this = MainActitivity)
            // segundo param: a classe que o kit de desenvolvimento do android precisa criar o objeto

            val i = Intent(this, ImcActivity::class.java)
            //inicializar a intenção
            startActivity(i)

        }


    }
}