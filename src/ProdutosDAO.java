
/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;

public class ProdutosDAO {

    conectaDAO conn = new conectaDAO();
    PreparedStatement prep;
    ResultSet resultset;
    List<ProdutosDTO> listagem = new ArrayList<>();

    public int cadastrarProduto(ProdutosDTO produto) {

        int status;

        try {
            prep = conn.connectDB().prepareStatement("INSERT INTO produtos(nome, valor, status) VALUES (?,?,?)");
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            status = prep.executeUpdate();

            if (status == 1) {
                JOptionPane.showMessageDialog(null, "Produto Cadastrado com Sucesso");
            } else {
                JOptionPane.showConfirmDialog(null, "Produto Não Cadastrado");
            }

            return status;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Conectar: " + ex.getMessage());
            return ex.getErrorCode();
        } catch (NullPointerException np) {
            JOptionPane.showMessageDialog(null, np.getMessage());
            return 0;
        }

    }

    public List<ProdutosDTO> listarProdutos() {
        String sql = "SELECT * FROM produtos";

        try {

            prep = conn.connectDB().prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {

                ProdutosDTO p = new ProdutosDTO();

                p.setId(resultset.getInt("id"));
                p.setNome(resultset.getString("nome"));
                p.setValor(resultset.getInt("valor"));
                p.setStatus(resultset.getString("status"));

                listagem.add(p);

            }

            return listagem;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Buscar os dados " + ex.getMessage());
            return null;
        }

    }

    public int venderProdutos(ProdutosDTO pro) {

        int status;
        String sql = "Update produtos Set status = ? where id = ?";

        if (!pro.getStatus().equals("Vendido")) {
            try {
                prep = conn.connectDB().prepareStatement(sql);
                prep.setString(1, "Vendido");
                prep.setInt(2, pro.getId());

                status = prep.executeUpdate();
                return status;

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao Efetuar a Venda " + ex.getMessage());
                return 0;
            }

        } else {
            JOptionPane.showMessageDialog(null, "Produto Já está Vendido, Venda Não Efetuada");
            return 0;
        }

    }

}
