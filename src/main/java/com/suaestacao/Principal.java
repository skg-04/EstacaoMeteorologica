package com.suaestacao;

import com.suaestacao.dao.DadoClimaticoDAO;
import com.suaestacao.modelo.DadoClimatico;

public class Principal {
    public static void main(String[] args) {
        System.out.println("Iniciando o teste de conexao com o SQL Server...");
        
        try {
            // 1. Inicializa o DAO (isso já testa a leitura do hibernate.cfg.xml)
            DadoClimaticoDAO dao = new DadoClimaticoDAO();
            
            // 2. Cria um dado simulado
            DadoClimatico novoDado = new DadoClimatico();
            novoDado.setTemperatura(28.5);
            novoDado.setUmidade(55.0);
            novoDado.setPrecipitacao(false);
            novoDado.setVelocidadeVento(18.2);
            novoDado.setPressaoAtmosferica(1015.0);
            
            // 3. Tenta salvar no banco
            dao.salvar(novoDado);
            
            System.out.println("Fim do teste. Verifique a aba Output (Saida).");
            
        } catch (Exception e) {
            System.err.println("Ocorreu um erro durante o teste:");
            e.printStackTrace();
        }
    }
}