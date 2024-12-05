package br.com.jean.portbank.service.transacao;

import br.com.jean.portbank.dto.ExtratoDTO;
import br.com.jean.portbank.dto.PagamentoDTO;
import br.com.jean.portbank.dto.TransferenciaDTO;
import br.com.jean.portbank.model.Transacao;

import java.util.List;

public interface ITransacaoService {

    boolean efetuarPagamento(PagamentoDTO pagamentoDTO);
    boolean efetuarTransferencia(TransferenciaDTO transferenciaDTO);
    List<Transacao> getExtrato(ExtratoDTO extratoDTO);
}
