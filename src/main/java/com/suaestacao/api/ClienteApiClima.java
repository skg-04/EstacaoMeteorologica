
package com.suaestacao.api; // Define o pacote da classe responsável pela API.

// Importa uma classe do Jackson para acessar elementos de um JSON.
import com.fasterxml.jackson.databind.JsonNode;

// Importa a classe que converte textos JSON em objetos Java.
import com.fasterxml.jackson.databind.ObjectMapper;

// Importa o modelo de dados climáticos utilizado pelo sistema.
import com.suaestacao.modelo.DadoClimatico;

// Importa a classe que representa endereços de internet.
import java.net.URI;

// Importa o cliente HTTP responsável pela comunicação com servidores.
import java.net.http.HttpClient;

// Importa a classe utilizada para construir requisições HTTP.
import java.net.http.HttpRequest;

// Importa a classe que representa a resposta de uma requisição HTTP.
import java.net.http.HttpResponse;

// Importa os recursos utilizados para representar intervalos de tempo.
import java.time.Duration;

// Declara a classe responsável pela comunicação com a API de clima.
public class ClienteApiClima {

    // Define a URL base da API Open-Meteo.
    // static indica que a variável pertence à classe.
    // final impede que a referência receba outro valor.
    // String representa uma sequência de caracteres.
    private static final String URL_BASE = "https://api.open-meteo.com/v1/forecast"

            // Define os parâmetros de latitude e longitude.
            // %s será substituído pelos valores de localização.
            + "?latitude=%s&longitude=%s"

            // Define quais dados meteorológicos serão solicitados.
            // temperature_2m: temperatura a 2 metros do solo.
            // relative_humidity_2m: umidade relativa a 2 metros.
            // precipitation: quantidade de precipitação.
            // wind_speed_10m: velocidade do vento a 10 metros.
            // surface_pressure: pressão atmosférica na superfície.
            + "&current=temperature_2m,relative_humidity_2m,precipitation,wind_speed_10m,surface_pressure"

            // Define km/h como unidade de velocidade do vento.
            // Configura o fuso horário de São Paulo.
            // %% permite manter um símbolo % literal no String.format.
            + "&wind_speed_unit=kmh&timezone=America%%2FSao_Paulo";

    // Cria um cliente HTTP utilizado nas consultas à API.
    // private restringe o acesso à variável dentro desta classe.
    // final impede substituir a referência depois de inicializada.
    private final HttpClient http = HttpClient.newBuilder()

            // Define até 10 segundos para estabelecer uma conexão.
            .connectTimeout(Duration.ofSeconds(10))

            // Constrói o objeto HttpClient com as configurações.
            .build();

    // Cria o objeto Jackson responsável pela leitura dos dados JSON.
    private final ObjectMapper mapper = new ObjectMapper();

    // Armazena a latitude utilizada para consultar a API.
    private final double latitude;

    // Armazena a longitude utilizada para consultar a API.
    private final double longitude;

    // Construtor da classe ClienteApiClima.
    // É executado quando um objeto desta classe é criado.
    public ClienteApiClima(double latitude, double longitude) {

        // Armazena a latitude recebida no atributo do objeto.
        // this identifica a variável pertencente à própria classe.
        this.latitude = latitude;

        // Armazena a longitude recebida no atributo do objeto.
        this.longitude = longitude;

    } // Finaliza o construtor.

    // Método responsável pela consulta dos dados meteorológicos.
    // Retorna um objeto do tipo DadoClimatico.
    // throws Exception permite propagar erros para quem chamou o método.
    public DadoClimatico coletar() throws Exception {

        // Monta a URL completa substituindo os dois parâmetros %s.
        // Locale.US garante que números decimais utilizem ponto.
        // Isso evita problemas ao enviar coordenadas para a API.
        String url = String.format(java.util.Locale.US, URL_BASE, latitude, longitude);

        // Inicia a construção de uma requisição HTTP.
        HttpRequest request = HttpRequest.newBuilder()

                // Define o endereço da API que será consultado.
                .uri(URI.create(url))

                // Limita a duração da requisição a 15 segundos.
                .timeout(Duration.ofSeconds(15))

                // Define GET como método HTTP.
                // GET é utilizado para solicitar informações ao servidor.
                .GET()

                // Finaliza a criação da requisição.
                .build();

        // Envia a requisição para a API.
        // http.send realiza a comunicação HTTP de forma síncrona.
        // BodyHandlers.ofString() recebe a resposta como texto.
        // O resultado é armazenado em um HttpResponse<String>.
        HttpResponse<String> resposta = http.send(request, HttpResponse.BodyHandlers.ofString());

        // Verifica se o código HTTP retornado é diferente de 200.
        // HTTP 200 significa que a solicitação foi processada com sucesso.
        if (resposta.statusCode() != 200) {

            // Lança um erro informando o código HTTP recebido.
            throw new RuntimeException("API retornou HTTP " + resposta.statusCode());

        } // Finaliza a verificação do código HTTP.

        // Converte o texto JSON da resposta em uma estrutura navegável.
        // readTree interpreta o conteúdo recebido.
        // path("current") acessa os dados meteorológicos atuais.
        JsonNode atual = mapper.readTree(resposta.body()).path("current");

        // Verifica se o bloco current não foi encontrado no JSON.
        if (atual.isMissingNode()) {

            // Lança um erro porque os dados esperados não estão presentes.
            throw new RuntimeException("Resposta da API sem o bloco 'current'");

        } // Finaliza a verificação da estrutura JSON.

        // Cria um objeto vazio para receber as informações climáticas.
        DadoClimatico dado = new DadoClimatico();

        // Obtém a temperatura da resposta JSON.
        // asDouble converte o valor para número decimal.
        // setTemperatura armazena o valor no objeto.
        dado.setTemperatura(atual.path("temperature_2m").asDouble());

        // Obtém a umidade relativa do ar em porcentagem.
        // Armazena a informação no atributo umidade.
        dado.setUmidade(atual.path("relative_humidity_2m").asDouble());

        // Lê a quantidade de precipitação informada pela API.
        // Verifica se a quantidade recebida é maior que zero.
        // true significa que ocorreu precipitação no período informado.
        // false significa que a precipitação informada foi zero.
        dado.setPrecipitacao(atual.path("precipitation").asDouble() > 0);

        // Obtém a velocidade do vento.
        // O valor estará em km/h conforme a configuração da URL.
        dado.setVelocidadeVento(atual.path("wind_speed_10m").asDouble());

        // Obtém a pressão atmosférica na superfície.
        // Armazena o valor em hPa no objeto.
        dado.setPressaoAtmosferica(atual.path("surface_pressure").asDouble());

        // Devolve o objeto preenchido ao código que chamou coletar().
        return dado;

    } // Finaliza o método coletar().

} // Finaliza a classe ClienteApiClima.
