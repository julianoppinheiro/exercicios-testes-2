package br.com.canasvieiras.m3s02projetorevisao.services;

import br.com.canasvieiras.m3s02projetorevisao.entities.Despesa;
import br.com.canasvieiras.m3s02projetorevisao.repositories.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    public List<Despesa> listarDespesas() {
        return despesaRepository.findAll();
    }

    public Despesa criarDespesa(Despesa despesa) {

        Despesa despesaBanco = new Despesa();
        if (despesa.getId() != null) {
            despesaBanco = lerDespesa(despesa.getId());
        }

        despesaBanco.setDescricao(despesa.getDescricao());
        despesaBanco.setValor(despesa.getValor());
        despesaBanco.setCredor(despesa.getCredor());
        despesaBanco.setStatus("Pendente");
        despesaBanco.setDataPagamento(null);

        if (despesa.getDataVencimento() == null) { // valida o valor da propriedade antes de salvar a entidade
            despesaBanco.setDataVencimento(new Date());
        } else {
            despesaBanco.setDataVencimento(despesa.getDataVencimento());
        }

        return despesaRepository.save(despesaBanco);
    }


    public Despesa lerDespesa(Long id) {
        return despesaRepository.findById(id).orElse(null);
    }


    public Despesa atualizarDespesa(Despesa despesa) throws Exception {

        Optional<Despesa> despesanova = despesaRepository.findById(despesa.getId());

        if (despesanova.isPresent()) {
            Despesa despesaBanco = despesanova.get();
            if (despesaBanco.getStatus().equals("Paga")) {
                throw new Exception("Despesa já paga!");
            }
            //pagar a despesa
            if (despesa.getStatus().equals("Paga")) {
                despesaBanco.pagar();
                return despesaRepository.save(despesaBanco);
            }

            //atualizar a despesa
            despesaBanco.setDescricao(despesa.getDescricao());
            despesaBanco.setValor(despesa.getValor());
            despesaBanco.setCredor(despesa.getCredor());
            despesaBanco.setDataVencimento(despesa.getDataVencimento());

            return despesaRepository.save(despesaBanco);
        } else {
            //retornar que nao foi encontrado
            throw new Exception("Despesa não encontrada!");
        }
    }


    public void excluirDespesa(Long id) {
        despesaRepository.deleteById(id);
    }

}
