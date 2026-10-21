package com.suaestacao.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "DadosClimaticos")
public class DadoClimatico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_hora", insertable = false, updatable = false)
    private LocalDateTime dataHora;

    private Double temperatura;
    private Double umidade;
    private Boolean precipitacao;
    
    @Column(name = "velocidade_vento")
    private Double velocidadeVento;
    
    @Column(name = "pressao_atmosferica")
    private Double pressaoAtmosferica;

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public Double getTemperatura() { return temperatura; }
    public void setTemperatura(Double temperatura) { this.temperatura = temperatura; }

    public Double getUmidade() { return umidade; }
    public void setUmidade(Double umidade) { this.umidade = umidade; }

    public Boolean getPrecipitacao() { return precipitacao; }
    public void setPrecipitacao(Boolean precipitacao) { this.precipitacao = precipitacao; }

    public Double getVelocidadeVento() { return velocidadeVento; }
    public void setVelocidadeVento(Double velocidadeVento) { this.velocidadeVento = velocidadeVento; }

    public Double getPressaoAtmosferica() { return pressaoAtmosferica; }
    public void setPressaoAtmosferica(Double pressaoAtmosferica) { this.pressaoAtmosferica = pressaoAtmosferica; }
}