package br.com.jean.portbank.service.conta;

import br.com.jean.portbank.dto.ContaDTO;
import br.com.jean.portbank.model.Cliente;
import br.com.jean.portbank.model.Conta;
import br.com.jean.portbank.repo.ClienteRepo;
import br.com.jean.portbank.repo.ContaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaServiceImpl implements IContaService{

    @Autowired
    private ContaRepo contaRepo;
    @Autowired
    private ClienteRepo clienteRepo;

    @Value("${portbank.bankNumber}")
    private Integer numeroBanco;

    @Override
    public Integer cadastrarNovaConta(ContaDTO dto) {

        Optional<Cliente> res = clienteRepo.findById(dto.idCliente());
        if (res.isEmpty()){
            return null;
        }
        Conta conta = dto.toConta();
        conta.setNumeroBanco(numeroBanco);
        System.out.println("Mostrando numero do banco " + conta.getNumeroBanco());
        return contaRepo.save(conta).getNumeroConta();
    }

    @Override
    public List<Conta> recuperarPorCliente(Cliente cliente) {
        List<Conta> res = contaRepo.findAllByCliente(cliente);
        if (res.isEmpty()){
            return null;
        }
        return res;
    }

    @Override
    public Conta recuperarPorNumeroConta(Integer numeroConta) {
        return contaRepo.findById(numeroConta).orElse(null);
    }
}
