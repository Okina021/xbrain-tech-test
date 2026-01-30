package com.amaraltech.xbrain_tech_test.services;

import com.amaraltech.xbrain_tech_test.model.Venda;
import com.amaraltech.xbrain_tech_test.model.VendedorDTO;
import com.amaraltech.xbrain_tech_test.repositories.VendaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;

    public VendaService(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    public Venda criarVenda(Venda venda) {
        if (venda.getDataVenda() == null) {
            venda.setDataVenda(LocalDate.now());
        }
        return vendaRepository.save(venda);
    }

    public List<VendedorDTO> getVendedoresComVendas(LocalDate inicio, LocalDate fim) {
        List<Object[]> resultados = vendaRepository.findVendasPorVendedorNoPeriodo(inicio, fim);

        return resultados.stream().map(resultado -> {
            String nome = (String) resultado[0];
            Double total = (Double) resultado[1];

            long dias = inicio.until(fim).getDays() + 1;
            Double mediaDiaria = dias > 0 ? total / dias : 0.0;

            return new VendedorDTO(nome, total, mediaDiaria);
        }).toList();
    }
}
