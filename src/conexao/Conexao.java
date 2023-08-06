package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    /**
     * Método que retorna a conexão com o banco de dados
     *
     * @return
     */
    public Connection getConexao() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Cenaflix_bd ", "root", "JKDEUS10");

            return conn;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados " + e.getMessage());
            return null;
        }
    }
}
