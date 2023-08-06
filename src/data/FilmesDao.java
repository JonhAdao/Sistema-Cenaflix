package data;

import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class FilmesDao {

    private Conexao conexao;
    private Connection conn;

    public FilmesDao() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }

    /**
     * Método que recebe como parâmetro um objeto filme e salva ele no banco
     *
     * @param filme
     */
    public void salvarFilmes(Filmes filme) {
        String sql = "INSERT INTO filmes(nome, datalancamento, categoria) VALUES (?, ?, ?)";
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
            PreparedStatement st = this.conn.prepareStatement(sql);
            st.setString(1, filme.getNome());
            st.setString(2, formato.format(filme.getData()));
            st.setString(3, filme.getCategoria());
            st.execute();
            

        } catch (SQLException e) {
            System.out.println("Erro ao inserir empresa: " + e.getMessage());
        }
    }

    /**
     * Método que retorna dados dos filmes registrados no banco
     *
     * @return dados dos filmes
     */
    public List<Filmes> getFilmes() {
        String sql = "SELECT* FROM filmes ";

        try {
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<Filmes> lista = new ArrayList<>();
            while (rs.next()) {
                Filmes filmes = new Filmes();

                filmes.setId(rs.getInt("id"));
                filmes.setNome(rs.getString("nome"));
                filmes.setData(rs.getDate("datalancamento"));
                filmes.setCategoria(rs.getString("categoria"));

                lista.add(filmes);

            }
            return lista;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Método que recebe como parâmetro uma categoria de filme e filtra na
     * Jtable
     *
     * @param categoria
     * @return uma lista de filmes populada com dados resgatados do banco de
     * dados
     */
    public List<Filmes> filtrarFilmes(String categoria) {
        String sql = "SELECT* FROM filmes WHERE categoria LIKE ?";

        try {

            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, "%" + categoria + "%");
            ResultSet rs = stmt.executeQuery();

            List<Filmes> listaFilmes = new ArrayList<>();

            while (rs.next()) {
                Filmes filmes = new Filmes();

                filmes.setId(rs.getInt("id"));
                filmes.setNome(rs.getString("nome"));
                filmes.setData(rs.getDate("datalancamento"));
                filmes.setCategoria(rs.getString("categoria"));

                listaFilmes.add(filmes);
            }

            return listaFilmes;

        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Método que recebe como parâmetro um id e exclui os dados relacionados
     *
     * @param id
     */
    public void excluir(int id) {

        String sql = "DELETE FROM filmes WHERE id = ?";
        try {

            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao excluir Filme: " + e.getMessage());
        }

    }

    /**
     * Método que recebe como parâmetro um id e retorna os dados do banco
     *
     * @param id
     * @return um objeto filme com dados preenchidos do banco
     */
    public Filmes consultarFilme(int id) {
        String sql = "SELECT * FROM filmes WHERE id = ?";
        try {

            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            Filmes filme = new Filmes();
            rs.next();

            filme.setId(id);
            filme.setNome(rs.getString("nome"));
            filme.setData(rs.getDate("datalancamento"));
            filme.setCategoria(rs.getString("categoria"));
            return filme;

        } catch (SQLException e) {
            System.out.println("erro: " + e.getMessage());
            return null;
        }
    }

    /**
     * Método que recebe como parâmetro um objeto filme e recebe os novos dados
     * editados no formulário
     *
     * @param filme
     */
    public void editarFilme(Filmes filme) {

        String sql = "UPDATE filmes SET nome=?, datalancamento=?, categoria=? WHERE id=?";
        try {

            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
            String Data = formato.format(filme.getData());

            stmt.setString(1, filme.getNome());
            stmt.setString(2, Data);
            stmt.setString(3, filme.getCategoria());
            stmt.setInt(4, filme.getId());

            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao editar empresa: " + e.getMessage());
        }
    }

}

/**
 * Classe Data Access Object
 *
 * @author Jhon
 * @version 1.0
 * @since Primeira versão
 */
