// Define em qual "pasta" (pacote) esta classe está localizada no seu projeto.
package com.suaestacao.dao;

// Importa a classe de modelo que representa os dados da estação meteorológica.
import com.suaestacao.modelo.DadoClimatico;
// Importa as classes do Hibernate necessárias para conectar e operar o banco de dados.
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

// DAO significa "Data Access Object" (Objeto de Acesso a Dados). 
// O objetivo desta classe é exclusivamente conversar com o banco de dados (inserir, buscar, etc).
public class DadoClimaticoDAO {
    
    // O SessionFactory é como se fosse o "motor" do Hibernate.
    // Ele é pesado e serve para gerenciar e fabricar conexões com o banco de dados.
    private SessionFactory sessionFactory;

    // Construtor: este bloco de código roda automaticamente toda vez que você usar um "new DadoClimaticoDAO()".
    public DadoClimaticoDAO() {
        // Lê o seu arquivo "hibernate.cfg.xml" (configure) e inicializa a fábrica de conexões (buildSessionFactory).
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    // Método responsável por receber um objeto "dado" preenchido e salvá-lo no SQL Server.
    public void salvar(DadoClimatico dado) {
        // Abre uma nova "Sessão" de comunicação com o banco (como se abrisse uma porta).
        Session session = sessionFactory.openSession();
        
        // Prepara uma "Transação". 
        // A transação garante a segurança: ou salva tudo com sucesso, ou desfaz tudo se der erro.
        Transaction transaction = null;
        
        try { // "Tente executar este bloco:"
            
            // Avisa o banco: "Estou começando uma operação, preste atenção".
            transaction = session.beginTransaction();
            
            // Pede ao Hibernate para "persistir" (salvar) o objeto 'dado' transformando-o numa linha na tabela.
            session.persist(dado);
            
            // "Commit" significa confirmar. Aqui ele efetivamente efetiva e grava os dados no SQL Server.
            transaction.commit();
            
            // Mostra no console (Output) do NetBeans que a operação deu certo.
            System.out.println("SUCESSO: Registro inserido no SQL Server!");
            
        } catch (Exception e) { // "Se acontecer QUALQUER erro no bloco 'try' acima, capture-o aqui:"
            
            // Se a transação já tinha sido iniciada antes do erro acontecer...
            if (transaction != null) {
                // "Rollback" desfaz qualquer alteração que tentou ser feita. Evita salvar dados pela metade.
                transaction.rollback();
            }
            // Repassa o erro (joga ele na tela) para você ver o texto em vermelho no console e descobrir o que falhou.
            throw e;
            
        } finally { // "Independente se deu sucesso ou erro, no final faça sempre o seguinte:"
            
            // Fecha a conexão com o banco. 
            // ISSO É OBRIGATÓRIO! Se não fechar, o banco de dados acumula conexões "fantasmas" e trava o sistema.
            session.close();
        }
    }
}