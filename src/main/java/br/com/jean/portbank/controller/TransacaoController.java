package br.com.jean.portbank.controller;

import br.com.jean.portbank.dto.ExtratoDTO;
import br.com.jean.portbank.dto.PagamentoDTO;
import br.com.jean.portbank.dto.ResponseDTO;
import br.com.jean.portbank.dto.TransferenciaDTO;
import br.com.jean.portbank.exception.InvalidAccountException;
import br.com.jean.portbank.exception.NotEnoughBalanceException;
import br.com.jean.portbank.model.Transacao;
import br.com.jean.portbank.service.transacao.ITransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class TransacaoController {

    @Autowired
    private ITransacaoService service;

    @PostMapping("/pagamentos")
    public ResponseEntity<ResponseDTO> pagamento(@RequestBody PagamentoDTO pagamentoDTO) {
        try {
            if (service.efetuarPagamento(pagamentoDTO)) {
                return ResponseEntity.status(201).body(new ResponseDTO("Pagamento realizado com sucesso"));
            }

            return ResponseEntity.badRequest().body(new ResponseDTO("Não foi possivel realzar o pagameto"));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage()));
        }
    }

    @PostMapping("/transferencias")
    public ResponseEntity<ResponseDTO> transferir(@RequestBody TransferenciaDTO transferenciaDTO) {
        try {
            if (service.efetuarTransferencia(transferenciaDTO)) {
                return ResponseEntity.status(201).body(new ResponseDTO("Transferencia concluida"));
            }
            return ResponseEntity.badRequest().body(new ResponseDTO("Não foi possivel realizar transferencia"));
        }catch (Exception e) {
            return ResponseEntity.status(400).body(new ResponseDTO(e.getMessage()));
        }
    }

    @GetMapping("/extratos")
    public ResponseEntity<?> getExtrato(@RequestBody ExtratoDTO extratoDTO) {
        try {
            List<Transacao> res = service.getExtrato(extratoDTO);
            if (res == null) {
                return ResponseEntity.badRequest().body(new ResponseDTO("Não foi encontrado nenhuma transação"));
            }
            return ResponseEntity.status(200).body(res);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage()));
        }
    }
}
