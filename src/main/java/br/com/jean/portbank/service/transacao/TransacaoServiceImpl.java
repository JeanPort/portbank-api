package br.com.jean.portbank.service.transacao;

import br.com.jean.portbank.dto.ExtratoDTO;
import br.com.jean.portbank.dto.PagamentoDTO;
import br.com.jean.portbank.dto.TransferenciaDTO;
import br.com.jean.portbank.exception.InvalidAccountException;
import br.com.jean.portbank.exception.InvalidDataIntervalException;
import br.com.jean.portbank.exception.NotEnoughBalanceException;
import br.com.jean.portbank.model.Conta;
import br.com.jean.portbank.model.Transacao;
import br.com.jean.portbank.repo.ContaRepo;
import br.com.jean.portbank.repo.TransacaoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransacaoServiceImpl implements ITransacaoService{

    @Autowired
    private TransacaoRepo transacaoRepo;
    @Autowired
    private ContaRepo contaRepo;

    @Override
    public boolean efetuarPagamento(PagamentoDTO pagamentoDTO) {

        Optional<Conta> res = contaRepo.findById(pagamentoDTO.numeroConta());
        Conta conta = res.orElseThrow(() -> new InvalidAccountException("Conta invalida"));

        if (conta.getSaldo() + conta.getLimite() < pagamentoDTO.valor()) {
            throw new NotEnoughBalanceException("Saldo insuficiente");
        }

        double saldoIni = conta.getSaldo();
        double saldoFin = conta.getSaldo() - pagamentoDTO.valor();
        Transacao transacao = new Transacao();
        transacao.setConta(conta);
        transacao.setDescricao(pagamentoDTO.descricao());
        transacao.setDataHora(pagamentoDTO.dataHora());
        transacao.setNumeroDoc(pagamentoDTO.numDoc());
        transacao.setTipo(-1);
        transacao.setValor(pagamentoDTO.valor());
        transacao.setSaldoInicial(saldoIni);
        transacao.setSaldoFinal(saldoFin);
        transacaoRepo.save(transacao);
        conta.setSaldo(saldoFin);
        contaRepo.save(conta);

        return true;
    }

    @Override
    public boolean efetuarTransferencia(TransferenciaDTO transferenciaDTO) {

        Conta origem = contaRepo.findById(transferenciaDTO.contaOrigem()).orElseThrow(() -> new InvalidAccountException("Conta de numero " + transferenciaDTO.contaOrigem() + " invalida"));
        Conta destino = contaRepo.findById(transferenciaDTO.contaDestino()).orElseThrow(() -> new InvalidAccountException("Conta de numero " + transferenciaDTO.contaDestino() + " invalida"));

        if (origem.getLimite() + origem.getSaldo() < transferenciaDTO.valor()) {
            throw new NotEnoughBalanceException("Conta de numro " + origem.getNumeroConta()+ " com saldo insuficiente");
        }

        Transacao transacaoDebito = getTransacaoDebito(transferenciaDTO, origem);
        Transacao transacaoCredito = getTransacaoCredito(transferenciaDTO, destino);

        transacaoRepo.save(transacaoDebito);
        transacaoRepo.save(transacaoCredito);
        contaRepo.save(origem);
        contaRepo.save(destino);
        return true;
    }

    @Override
    public List<Transacao> getExtrato(ExtratoDTO extratoDTO) {

        Conta conta = contaRepo.findById(extratoDTO.numeroConta()).orElseThrow(() -> new InvalidAccountException("Conta invalida"));
        if (extratoDTO.fim().isBefore(extratoDTO.inicio())){
            throw new InvalidDataIntervalException("Data invalida");
        }

        List<Transacao> res = transacaoRepo.findAllByContaAndDataHoraBetween(conta, extratoDTO.inicio(), extratoDTO.fim());
        if (res.isEmpty()){
            return null;
        }

        return res;
    }

    private static Transacao getTransacaoDebito(TransferenciaDTO transferenciaDTO, Conta origem) {
        Transacao transacao = new Transacao();
        transacao.setValor(transferenciaDTO.valor());
        transacao.setTipo(-1);
        transacao.setConta(origem);
        transacao.setDataHora(transferenciaDTO.dataHora());
        transacao.setDescricao(transferenciaDTO.descricao());
        transacao.setNumeroDoc(UUID.randomUUID().toString());
        transacao.setSaldoInicial(origem.getSaldo());
        origem.setSaldo(origem.getSaldo() - transferenciaDTO.valor());
        transacao.setSaldoFinal(origem.getSaldo());
        return transacao;
    }

    private static Transacao getTransacaoCredito(TransferenciaDTO transferenciaDTO, Conta destino) {
        Transacao transacao = new Transacao();
        transacao.setValor(transferenciaDTO.valor());
        transacao.setTipo(1);
        transacao.setConta(destino);
        transacao.setDataHora(transferenciaDTO.dataHora());
        transacao.setDescricao(transferenciaDTO.descricao());
        transacao.setNumeroDoc(UUID.randomUUID().toString());
        transacao.setSaldoInicial(destino.getSaldo());
        destino.setSaldo(destino.getSaldo() + transferenciaDTO.valor());
        transacao.setSaldoFinal(destino.getSaldo());
        return transacao;
    }
}
