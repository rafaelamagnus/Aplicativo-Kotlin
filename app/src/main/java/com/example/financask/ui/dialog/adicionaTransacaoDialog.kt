package com.example.financask.ui.dialog
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.financask.R
import com.example.financask.delegate.TransacaoDelegate
import com.example.financask.extesion.converteParaCalendar
import com.example.financask.extesion.formataParaBrasileiro
import com.example.financask.model.Tipo
import com.example.financask.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.lang.NumberFormatException
import java.math.BigDecimal
import java.util.*


class adicionaTransacaoDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) {
    private val viewCriada = criaLayout()
    private val campoValor = viewCriada.form_transacao_valor.text
    private val campoCategoria = viewCriada.form_transacao_categoria
    private val campoData = viewCriada.form_transacao_data

 fun chama(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {
        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario(tipo, transacaoDelegate)
    }

    private fun configuraFormulario(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {

        val titulo= tituloPor(tipo)

        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(viewCriada)
            .setPositiveButton("Adicionar",
                { _, _ ->
                    val valorEmTexto = campoValor.toString()
                    val dataEmTexto = campoData.text.toString()

                    val categoriaEmTexto =
                        campoCategoria.selectedItem.toString()

                    val valor = converteCampoValor(valorEmTexto)


                    val data= dataEmTexto.converteParaCalendar()

                    val transacaoCriada = Transacao(
                        tipo = tipo,
                        valor = valor,
                        data = data,
                        categoria = categoriaEmTexto
                    )

                        transacaoDelegate.delegate(transacaoCriada)

                })
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun tituloPor(tipo: Tipo): Int {
         if (tipo == Tipo.RECEITA) {
             return  R.string.adiciona_receita
        }
           return  R.string.adiciona_despesa
    }


    private fun converteCampoValor(valorEmTexto:String): BigDecimal{
      return  try {
            BigDecimal(valorEmTexto)
        } catch (exception: NumberFormatException) {
            Toast.makeText(
                context, "Falha na conversão da valor",
                Toast.LENGTH_LONG
            )
                .show()
            BigDecimal.ZERO
        }
    }


    private fun configuraCampoCategoria(tipo: Tipo) {

        val categorias= categoriasPor(tipo)
        val adapter = ArrayAdapter.createFromResource(context,
           categorias,
            android.R.layout.simple_spinner_dropdown_item
        )
        campoCategoria.adapter = adapter
    }

    private fun categoriasPor(tipo: Tipo): Int {
          return  R.array.categorias_de_receita
        }

    private fun configuraCampoData() {
        val hoje = Calendar.getInstance()
        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)


        campoData.setText(hoje.formataParaBrasileiro())
        campoData.setOnClickListener {
            DatePickerDialog(context,
               { _, ano, mes, dia ->
                    val dataSelecionada = Calendar.getInstance()
                    dataSelecionada.set(ano, mes, dia)
                    campoData.setText(dataSelecionada.formataParaBrasileiro())
                }
                , ano, mes, dia)
                .show()
        }
    }

    private fun criaLayout(): View {
        return LayoutInflater.from(context).inflate(R.layout.form_transacao, viewGroup, false)

    }
}