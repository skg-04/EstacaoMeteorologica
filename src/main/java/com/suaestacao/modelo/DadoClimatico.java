
package com.suaestacao.modelo; // Define o pacote dos modelos de dados.

// Importa as anotações e recursos da Jakarta Persistence.
// Esses recursos são utilizados no mapeamento de entidades JPA.
import jakarta.persistence.*;

// Importa a classe utilizada para armazenar data e hora.
import java.time.LocalDateTime;

// Informa ao Hibernate que esta classe representa uma entidade do banco.
@Entity

// Associa esta entidade à tabela chamada DadosClimaticos.
@Table(name = "DadosClimaticos")

// Declara a classe responsável por representar um registro climático.
public class DadoClimatico {

    // Indica que o atributo seguinte representa a chave primária.
    @Id

    // Define que o identificador será gerado pelo banco de dados.
    // IDENTITY utiliza a geração automática do identificador.
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // Armazena o identificador único de cada registro.
    // Integer permite representar um número inteiro ou null.
    private Integer id;

    // Associa o atributo à coluna data_hora do SQL Server.
    // insertable=false impede que o Hibernate envie essa coluna no INSERT.
    // updatable=false impede que o Hibernate altere essa coluna no UPDATE.
    // A geração da data/hora deverá ser tratada pelo banco.
    @Column(name = "data_hora", insertable = false, updatable = false)

    // Armazena a data e a hora relacionadas ao registro climático.
    // LocalDateTime representa data e hora sem informação de fuso horário.
    private LocalDateTime dataHora;

    // Armazena a temperatura em graus Celsius.
    // Double permite armazenar números com casas decimais.
    private Double temperatura;

    // Armazena a umidade relativa do ar em porcentagem.
    private Double umidade;

    // Armazena se houve precipitação.
    // Boolean aceita true, false ou null.
    private Boolean precipitacao;

    // Associa o atributo à coluna velocidade_vento.
    @Column(name = "velocidade_vento")

    // Armazena a velocidade do vento em km/h.
    private Double velocidadeVento;

    // Associa o atributo à coluna pressao_atmosferica.
    @Column(name = "pressao_atmosferica")

    // Armazena a pressão atmosférica em hPa.
    private Double pressaoAtmosferica;

    // ====================================================
    // MÉTODOS GETTERS E SETTERS
    // ====================================================
    // Getter: retorna o valor armazenado em um atributo.
    // Setter: modifica o valor armazenado em um atributo.
    // ====================================================

    // Método que retorna o identificador do registro.
    public Integer getId() {
        return id; // Devolve o valor armazenado em id.
    } // Finaliza getId().

    // Método que permite modificar o identificador.
    public void setId(Integer id) {
        this.id = id; // Atribui o valor recebido ao atributo id.
    } // Finaliza setId().

    // Método que retorna a data e hora do registro.
    public LocalDateTime getDataHora() {
        return dataHora; // Devolve a data e hora armazenadas.
    } // Finaliza getDataHora().

    // Método que permite definir a data e hora no objeto Java.
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora; // Armazena a data e hora recebidas.
    } // Finaliza setDataHora().

    // Método que retorna a temperatura do registro.
    public Double getTemperatura() {
        return temperatura; // Devolve a temperatura armazenada.
    } // Finaliza getTemperatura().

    // Método que permite modificar a temperatura.
    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura; // Armazena a temperatura recebida.
    } // Finaliza setTemperatura().

    // Método que retorna a umidade relativa do ar.
    public Double getUmidade() {
        return umidade; // Devolve a umidade armazenada.
    } // Finaliza getUmidade().

    // Método que permite modificar a umidade.
    public void setUmidade(Double umidade) {
        this.umidade = umidade; // Armazena a umidade recebida.
    } // Finaliza setUmidade().

    // Método que retorna a informação de precipitação.
    public Boolean getPrecipitacao() {
        return precipitacao; // Devolve true, false ou null.
    } // Finaliza getPrecipitacao().

    // Método que permite modificar a informação de precipitação.
    public void setPrecipitacao(Boolean precipitacao) {
        this.precipitacao = precipitacao; // Armazena o valor recebido.
    } // Finaliza setPrecipitacao().

    // Método que retorna a velocidade do vento.
    public Double getVelocidadeVento() {
        return velocidadeVento; // Devolve a velocidade em km/h.
    } // Finaliza getVelocidadeVento().

    // Método que permite modificar a velocidade do vento.
    public void setVelocidadeVento(Double velocidadeVento) {
        this.velocidadeVento = velocidadeVento; // Armazena o valor recebido.
    } // Finaliza setVelocidadeVento().

    // Método que retorna a pressão atmosférica.
    public Double getPressaoAtmosferica() {
        return pressaoAtmosferica; // Devolve a pressão em hPa.
    } // Finaliza getPressaoAtmosferica().

    // Método que permite modificar a pressão atmosférica.
    public void setPressaoAtmosferica(Double pressaoAtmosferica) {
        this.pressaoAtmosferica = pressaoAtmosferica; // Armazena o valor recebido.
    } // Finaliza setPressaoAtmosferica().

} // Finaliza a classe DadoClimatico.
