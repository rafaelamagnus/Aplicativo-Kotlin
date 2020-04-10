package com.example.financask.model

import java.math.BigDecimal

class Resumo(private  val transacoes: List<Transacao>) {

    val receita get() = somaPor(Tipo.RECEITA)
    val menorValor get() = menorValor()

    private fun somaPor(tipo: Tipo): BigDecimal {
        val somaDeTransacoesPeloTipo = transacoes.filter { it.tipo == tipo }
            .sumByDouble { it.valor.toDouble() }
        return BigDecimal(somaDeTransacoesPeloTipo)

    }

    private fun menorValor(): BigDecimal {
      if  (transacoes.size<1){
          return BigDecimal.ZERO;
      }
        var menorvalor = transacoes[0].valor
      for (i in transacoes.indices) {
            if (transacoes[i].valor < menorvalor) {
              menorvalor = transacoes[i].valor

            }

        }
        return menorvalor
}
}

