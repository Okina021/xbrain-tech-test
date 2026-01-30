package com.amaraltech.xbrain_tech_test.controllers;

import com.amaraltech.xbrain_tech_test.model.Venda;
import com.amaraltech.xbrain_tech_test.model.VendedorDTO;
import com.amaraltech.xbrain_tech_test.services.VendaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping
    public ResponseEntity<Venda> criarVenda(@RequestBody Venda venda) {
        Venda novaVenda = vendaService.criarVenda(venda);
        return ResponseEntity.ok(novaVenda);
    }

    @GetMapping("/relatorio-vendedores")
    public ResponseEntity<List<VendedorDTO>> getRelatorioVendedores(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        if (inicio == null) inicio = LocalDate.of(2010, 1, 1);
        if (fim == null) fim = LocalDate.now();

        List<VendedorDTO> relatorio = vendaService.getVendedoresComVendas(inicio, fim);
        return ResponseEntity.ok(relatorio);
    }
}
