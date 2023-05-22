package br.com.canasvieiras.m3s02projetorevisao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String credor;

    @Column
    private Date dataVencimento = new Date();

    @Column
    private Date dataPagamento = null;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column
    private String descricao;

    @Column(nullable = false)
    //aceitar os valores "Pendente" e "Paga", porém o valor padrão deve ser "Pendente"
    private String status = "pendente";

    public void pagar() {
        this.status = "paga";
        this.dataPagamento = new Date();
    }

}
