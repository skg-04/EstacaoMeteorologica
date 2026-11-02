// Define que esta classe pertence à "pasta" (pacote) destinada aos modelos do sistema.
package com.suaestacao.modelo;

// Importa todas as anotações do JPA (Jakarta Persistence API) usadas para mapear o banco (@Entity, @Id, etc).
import jakarta.persistence.*;

// Importa a classe do Java específica para trabalhar com datas e horas modernas.
import java.time.LocalDateTime;

// @Entity avisa ao Hibernate: "Esta classe representa uma tabela no banco de dados".
@Entity

// @Table diz ao Hibernate o nome exato da tabela lá no SQL Server.
@Table(name = "DadosClimaticos")

public class DadoClimatico {

    // @Id avisa que a variável logo abaixo (o 'id') é a Chave Primária.
    @Id
    // @GeneratedValue avisa que o SQL Server vai gerar esse número sozinho (Auto Increment).
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Conecta a variável 'dataHora' com a coluna 'data_hora' do banco.
    // 'insertable = false, updatable = false' proíbe o Java de enviar a data, deixando o banco preencher sozinho.
    @Column(name = "data_hora", insertable = false, updatable = false)
    private LocalDateTime dataHora;

    // Variáveis com nomes idênticos aos das colunas no banco (não precisam de @Column).
    private Double temperatura;
    private Double umidade;
    private Boolean precipitacao; 
    
    // Conecta a variável Java 'velocidadeVento' com a coluna 'velocidade_vento' no SQL.
    @Column(name = "velocidade_vento")
    private Double velocidadeVento;
    
    // Conecta a variável 'pressaoAtmosferica' com a coluna 'pressao_atmosferica' no SQL.
    @Column(name = "pressao_atmosferica")
    private Double pressaoAtmosferica;


    // ========================================================================
    // MÉTODOS GETTERS E SETTERS
    // ========================================================================

    // Retorna (lê) o número de identificação do registro.
    public Integer getId() { return id; } 
    
    // Recebe um número e grava na variável 'id' (geralmente usado pelo próprio Hibernate).
    public void setId(Integer id) { this.id = id; }

    // Retorna a data e hora em que a medição ocorreu.
    public LocalDateTime getDataHora() { return dataHora; } 
    
    // Grava uma data e hora específica no objeto.
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    // Retorna o valor atual da temperatura.
    public Double getTemperatura() { return temperatura; } 
    
    // Recebe o valor do sensor de temperatura e guarda na variável.
    public void setTemperatura(Double temperatura) { this.temperatura = temperatura; }

    // Retorna o valor atual da umidade do ar.
    public Double getUmidade() { return umidade; } 
    
    // Recebe o valor do sensor de umidade e guarda na variável.
    public void setUmidade(Double umidade) { this.umidade = umidade; }

    // Retorna se está chovendo ou não (True = sim, False = não).
    public Boolean getPrecipitacao() { return precipitacao; } 
    
    // Grava a informação de chuva na variável.
    public void setPrecipitacao(Boolean precipitacao) { this.precipitacao = precipitacao; }

    // Retorna a velocidade do vento captada pelo anemômetro.
    public Double getVelocidadeVento() { return velocidadeVento; } 
    
    // Grava a velocidade do vento na variável.
    public void setVelocidadeVento(Double velocidadeVento) { this.velocidadeVento = velocidadeVento; }

    // Retorna a medição da pressão atmosférica.
    public Double getPressaoAtmosferica() { return pressaoAtmosferica; } 
    
    // Grava a medição da pressão atmosférica na variável.
    public void setPressaoAtmosferica(Double pressaoAtmosferica) { this.pressaoAtmosferica = pressaoAtmosferica; }
}