
package com.suaestacao; // Define o pacote em que a classe Principal está localizada.

// Importa a classe responsável por consultar os dados climáticos na API.
import com.suaestacao.api.ClienteApiClima;

// Importa a classe responsável por salvar os dados no banco de dados.
import com.suaestacao.dao.DadoClimaticoDAO;

// Importa a classe que representa as informações climáticas.
import com.suaestacao.modelo.DadoClimatico;

// Importa os recursos necessários para criar um agendador de tarefas.
import java.util.concurrent.Executors;

// Importa a interface que permite executar tarefas periodicamente.
import java.util.concurrent.ScheduledExecutorService;

// Importa as unidades de tempo, como segundos, minutos e horas.
import java.util.concurrent.TimeUnit;

/**
 * Classe principal do sistema de coleta climática.
 *
 * Sua função é consultar uma API de meteorologia
 * e armazenar os resultados no SQL Server.
 *
 * A coleta acontece automaticamente a cada 60 segundos,
 * caso outro intervalo não seja informado.
 */

// Declara a classe pública chamada Principal.
public class Principal {

    // Método principal: é onde a execução do programa começa.
    // String[] args recebe parâmetros enviados ao iniciar o programa.
    public static void main(String[] args) {

        // Define a latitude utilizada para consultar a previsão do tempo.
        // Se o usuário informar um primeiro argumento, ele será utilizado.
        // Caso contrário, utiliza a latitude padrão de Sorocaba.
        double latitude = args.length > 0 ? Double.parseDouble(args[0]) : -23.5015;

        // Define a longitude da localização desejada.
        // Utiliza o segundo argumento, caso exista.
        // Se não existir, utiliza a longitude padrão de Sorocaba.
        double longitude = args.length > 1 ? Double.parseDouble(args[1]) : -47.4526;

        // Define o intervalo entre as coletas em segundos.
        // Se houver um terceiro argumento, utiliza esse valor.
        // Caso contrário, considera o intervalo padrão de 60 segundos.
        long intervalo = args.length > 2 ? Long.parseLong(args[2]) : 10;

        // Exibe no console uma mensagem informando o início da coleta.
        // Também apresenta latitude, longitude e intervalo configurados.
        System.out.println("Iniciando coletor (lat=" + latitude + ", lon=" + longitude
                + ", intervalo=" + intervalo + "s)...");

        // Cria um objeto DAO para trabalhar com o banco de dados.
        // Esse objeto será responsável por gravar as informações climáticas.
        DadoClimaticoDAO dao = new DadoClimaticoDAO();

        // Cria o cliente responsável pela comunicação com a API.
        // Envia a latitude e a longitude para configurar a consulta.
        ClienteApiClima api = new ClienteApiClima(latitude, longitude);

        // Cria um agendador que utiliza uma única thread.
        // A thread será responsável por executar as coletas programadas.
        ScheduledExecutorService agendador = Executors.newSingleThreadScheduledExecutor();

        // Cria uma tarefa que poderá ser executada repetidamente.
        // Runnable representa um conjunto de instruções executáveis.
        // A expressão () -> é uma função lambda do Java.
        Runnable coleta = () -> {

            // Inicia um bloco de tratamento de possíveis erros.
            try {

                // Consulta a API e recebe os dados climáticos coletados.
                // O retorno é armazenado em um objeto DadoClimatico.
                DadoClimatico dado = api.coletar();

                // Envia os dados recebidos para o DAO.
                // O DAO realizará a gravação no SQL Server.
                dao.salvar(dado);

                // Exibe no console um resumo da coleta realizada.
                // printf permite formatar números e textos.
                // %.1f apresenta uma casa decimal.
                // %.0f apresenta o número sem casas decimais.
                // %s apresenta um texto ou valor convertido em texto.
                // %n realiza uma quebra de linha.
                System.out.printf("Coletado e salvo: %.1f C | %.0f%% | chuva=%s | vento=%.1f km/h | %.1f hPa%n",
                        dado.getTemperatura(),          // Retorna a temperatura em graus Celsius.
                        dado.getUmidade(),              // Retorna a umidade relativa do ar.
                        dado.getPrecipitacao(),         // Informa se houve precipitação.
                        dado.getVelocidadeVento(),      // Retorna a velocidade do vento em km/h.
                        dado.getPressaoAtmosferica());  // Retorna a pressão atmosférica em hPa.

            // Captura uma exceção caso aconteça algum erro na coleta ou gravação.
            } catch (Exception e) {

                // Mostra uma mensagem de erro no console.
                // getMessage() retorna a descrição do problema ocorrido.
                // O erro é tratado para permitir uma nova tentativa no próximo ciclo.
                System.err.println("Falha na coleta: " + e.getMessage());

            } // Finaliza o tratamento de erros.

        }; // Finaliza a definição da tarefa de coleta.

        // Programa a execução automática da tarefa.
        // coleta: tarefa que deverá ser executada.
        // 0: primeira execução começa imediatamente.
        // intervalo: define o período entre os inícios das execuções.
        // TimeUnit.SECONDS: informa que o intervalo está em segundos.
        agendador.scheduleAtFixedRate(coleta, 0, intervalo, TimeUnit.SECONDS);

        // Adiciona uma rotina executada quando a JVM está encerrando.
        // O objetivo é liberar os recursos utilizados pelo programa.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            // Exibe uma mensagem informando o encerramento.
            System.out.println("Encerrando coletor...");

            // Solicita o encerramento do agendador de tarefas.
            agendador.shutdown();

            // Fecha os recursos de conexão do Hibernate.
            dao.fechar();

        })); // Finaliza a configuração da rotina de encerramento.

    } // Finaliza o método main.

} // Finaliza a classe Principal.
