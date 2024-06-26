package com.altamiro.fitnesstracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.altamiro.fitnesstracker.model.Calc

class ImcActivity : AppCompatActivity() {

    private lateinit var inputHeight: EditText
    private lateinit var inputWeigth: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)
        inputHeight = findViewById(R.id.edit_imc_height)
        inputWeigth = findViewById(R.id.edit_imc_weight)
        val buttonCalculate: Button = findViewById(R.id.btn_imc_send)

        buttonCalculate.setOnClickListener {

            if (!validate()) {
                Toast.makeText(
                    this,
                    "Todos os campos precisam ser maiores que zero!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            val weight = inputWeigth.text.toString().toInt()
            val height = inputHeight.text.toString().toInt()
            val result = calculateImc(weight, height)
            val imcResponseId = imcResponse(result)

            //mostrar dialog
            // class builder, uma classe construtora que provê funções, métodos, pra você criar uma propriedade que você queira
            //            val dialog = AlertDialog.Builder(this)
            //            val title = getString(R.string.imc_response, result)
            //            dialog.setTitle("Seu IMC é: $title")
            //            dialog.setMessage(imcResponseId)
            //// opção 1 - dá mais trabalho escrever
            //            dialog.setPositiveButton(android.R.string.ok, object : DialogInterface.OnClickListener {
            //                override fun onClick(dialog: DialogInterface?, witch: Int) {
            //
            //                }
            //            })
            //
            //// opção 2 - utilizando lambda
            //            dialog.setPositiveButton(
            //                android.R.string.ok
            //            ) { dialog, witch ->
            //
            //
            //            }
            //            val d = dialog.create()
            //            d.show()

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.imc_response, result))
                .setMessage(imcResponseId)
                .setPositiveButton(
                    android.R.string.ok
                ) { dialog, witch ->

                    //aqui vai rodar depois do click
                }
                .setNegativeButton(R.string.save){ dialog, which ->
                    Thread{
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type ="imc", res = result))
                        runOnUiThread{
//                            Toast.makeText(this@ImcActivity, R.string.calc_saved, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@ImcActivity, ListCalcActivity::class.java)
                            intent.putExtra("type", "imc")
                            startActivity(intent)

                        }

                    }.start()

                }
                .create()
                .show()
            // obter serviços para gerenciar teclado
            //convertendo any em um outro tipo, aqui usamos o tipo InputMethodManager
            //utilizamos o services para gerenciar serviços disponíveis no android.
            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            //esconder o teclado
            //especifico a ação que vai acontecer no focus atual
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)




        }
    }

    /*
    * funções que retornam uma string de dentro de ressource(R), devem retornar int pois o valor dessas string fazem referencia a um numero na classe R.
    * xml no kotlin é convertido para um valor inteiro.

     */

    @StringRes
    private fun imcResponse(imc: Double): Int {

        when {
            imc < 15.0 -> return R.string.imc_severely_low_weight
            imc < 16.0 -> return R.string.imc_very_low_weight
            imc < 18.5 -> return R.string.imc_low_weight
            imc < 25.0 -> return R.string.normal
            imc < 30.0 -> return R.string.imc_high_weight
            imc < 35.0 -> return R.string.imc_so_high_weight
            imc < 40.0 -> return R.string.imc_severely_high_weight
            else -> return R.string.imc_extreme_weight
        }
    }

    private fun validate(): Boolean {
        return (inputWeigth.text.toString()
            .isNotEmpty() && inputHeight.text.isNotEmpty() && !inputWeigth.text.toString()
            .startsWith("0") && !inputHeight.text.toString().startsWith("0"))

    }

    private fun calculateImc(weight: Int, height: Int): Double {
        // peso / (altura * altura)
        return weight / ((height / 100.0) * (height / 100.0))

    }
}