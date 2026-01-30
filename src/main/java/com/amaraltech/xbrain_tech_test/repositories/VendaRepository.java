package com.amaraltech.xbrain_tech_test.repositories;

import com.amaraltech.xbrain_tech_test.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByDataVendaBetween(LocalDate inicio, LocalDate fim);

    @Query("SELECT v.vendedorNome, SUM(v.valor) as total, COUNT(v) as quantidade " +
            "FROM Venda v " +
            "WHERE v.dataVenda BETWEEN :inicio AND :fim " +
            "GROUP BY v.vendedorId, v.vendedorNome")
    List<Object[]> findVendasPorVendedorNoPeriodo(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim
    );
}
