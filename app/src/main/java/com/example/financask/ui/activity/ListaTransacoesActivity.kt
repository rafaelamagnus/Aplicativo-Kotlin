package com.example.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.example.financask.R
import com.example.financask.delegate.TransacaoDelegate
import com.example.financask.model.Tipo
import com.example.financask.model.Transacao
import com.example.financask.ui.ResumoView
import com.example.financask.ui.adapter.ListaTransacoesAdapter
import com.example.financask.ui.dialog.adicionaTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*


class ListaTransacoesActivity : AppCompatActivity() {


    private val transacoes: MutableList<Transacao> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {  //OnCreate é usado para configurar a interface de usuário
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes) //para passar o layout


        configuraResumo()
        configuraLista()
        configuraFab()

    }

    private fun configuraFab() {
        lista_transacoes_adiciona_receita.setOnClickListener {
            chamaDialogDeAdicao(Tipo.RECEITA)
        }

    }

    private fun chamaDialogDeAdicao(tipo: Tipo) {
        adicionaTransacaoDialog(window.decorView as ViewGroup, this)
            .chama(tipo, object : TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    atualizaTransacoes(transacao)
                    lista_transacoes_adiciona_menu.close(true)
                }

            })
    }


    private fun atualizaTransacoes(transacao: Transacao) {
        transacoes.add(transacao)
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val view = window.decorView
        val resumoView = ResumoView(this, view, transacoes)
        resumoView.atualiza()

    }


    private fun configuraLista() {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

}