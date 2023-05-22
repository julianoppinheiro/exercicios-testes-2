package br.com.canasvieiras.m3s02projetorevisao.controllers;

import br.com.canasvieiras.m3s02projetorevisao.entities.Despesa;
import br.com.canasvieiras.m3s02projetorevisao.services.DespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/despesas")
public class DespesaController {
    @Autowired
    private DespesaService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(service.listarDespesas());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Despesa despesa) {
        try {
            return ResponseEntity.ok(service.criarDespesa(despesa));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.lerDespesa(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>  put(@PathVariable Long id, @RequestBody Despesa despesa) {
        try {
            despesa.setId(id);
            return ResponseEntity.ok(service.atualizarDespesa(despesa));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteId(@PathVariable Long id) {
        service.excluirDespesa(id);
    }
}
