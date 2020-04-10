package com.example.financask.ui
import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import com.example.financask.R
import com.example.financask.extesion.formataParaBrasileiro
import com.example.financask.model.Resumo
import com.example.financask.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*



class ResumoView(private val context: Context,
                 private  val view: View,
                 transacoes:  List<Transacao>) {


    private val resumo: Resumo= Resumo(transacoes)
    private val corReceita = ContextCompat.getColor(context, R.color.receita)



    fun atualiza(){
        adicionaReceita()
        adicionaMenorValor()

    }

   private fun adicionaReceita() {
       val totalReceita= resumo.receita
        with(view.resumo_card_receita){
            setTextColor(corReceita)
            text = totalReceita.formataParaBrasileiro()


        }
    }
    private fun adicionaMenorValor() {
        val menorValor = resumo.menorValor
      with(view.resumo_card_menorvalor){
            setTextColor(corReceita)
          text = menorValor.formataParaBrasileiro()
      }
   }

}



