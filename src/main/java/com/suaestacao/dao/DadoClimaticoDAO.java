
package com.suaestacao.dao; // Define o pacote da camada de acesso ao banco.

// Importa a classe que representa os dados climáticos.
import com.suaestacao.modelo.DadoClimatico;

// Importa a sessão do Hibernate.
// Uma Session permite executar operações no banco de dados.
import org.hibernate.Session;

// Importa a fábrica responsável por criar sessões Hibernate.
import org.hibernate.SessionFactory;

// Importa os recursos utilizados para controlar transações.
import org.hibernate.Transaction;

// Importa a configuração necessária para iniciar o Hibernate.
import org.hibernate.cfg.Configuration;

// Declara a classe responsável pelo acesso aos dados climáticos.
public class DadoClimaticoDAO {

    // Declara uma variável para armazenar a fábrica de sessões.
    // private restringe seu acesso à própria classe.
    // final impede substituir a referência depois da inicialização.
    private final SessionFactory sessionFactory;

    // Construtor da classe DadoClimaticoDAO.
    // Será executado quando um novo DAO for criado.
    public DadoClimaticoDAO() {

        // Cria uma configuração do Hibernate.
        // configure() carrega o arquivo hibernate.cfg.xml.
        // buildSessionFactory() cria a fábrica de sessões.
        // A configuração informa como acessar o banco de dados.
        this.sessionFactory = new Configuration().configure().buildSessionFactory();

    } // Finaliza o construtor.

    // Método público responsável por salvar dados no banco.
    // Recebe um objeto DadoClimatico contendo informações da API.
    // void significa que o método não retorna um valor.
    public void salvar(DadoClimatico dado) {

        // Declara uma variável utilizada para controlar a transação.
        // Inicialmente recebe null porque a transação ainda não começou.
        Transaction transaction = null;

        // Abre uma sessão de comunicação com o banco de dados.
        // try-with-resources fecha automaticamente a sessão ao sair do bloco.
        try (Session session = sessionFactory.openSession()) {

            // Inicia uma transação no banco de dados.
            // A transação controla a confirmação ou reversão das alterações.
            transaction = session.beginTransaction();

            // Solicita ao Hibernate que persista o objeto recebido.
            // O Hibernate prepara a inserção dos dados na tabela.
            session.persist(dado);

            // Confirma a transação.
            // O Hibernate executa as alterações pendentes.
            transaction.commit();

        // Captura erros de execução ocorridos durante a gravação.
        } catch (RuntimeException e) {

            // Verifica se a transação foi criada.
            // Também verifica se continua ativa.
            if (transaction != null && transaction.isActive()) {

                // Desfaz as operações não confirmadas da transação.
                // Isso ajuda a preservar a consistência do banco.
                transaction.rollback();

            } // Finaliza a verificação da transação.

            // Propaga o erro para a classe que chamou o método.
            // A classe Principal poderá capturar e registrar essa falha.
            throw e;

        } // Finaliza o tratamento de erros.

    } // Finaliza o método salvar().

    // Método responsável por encerrar os recursos do Hibernate.
    public void fechar() {

        // Verifica se a fábrica de sessões existe.
        // Também verifica se ela ainda não foi fechada.
        if (sessionFactory != null && !sessionFactory.isClosed()) {

            // Fecha a fábrica de sessões do Hibernate.
            // Libera os recursos associados, incluindo o pool de conexões gerenciado.
            sessionFactory.close();

        } // Finaliza a verificação.

    } // Finaliza o método fechar().

} // Finaliza a classe DadoClimaticoDAO.
