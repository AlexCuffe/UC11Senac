
/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;

public class ProdutosDAO {

    conectaDAO conn = new conectaDAO();
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public int cadastrarProduto(ProdutosDTO produto) {

        int status;

        try {
            prep = conn.connectDB().prepareStatement("INSERT INTO produtos(nome, valor, status) VALUES (?,?,?)");
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3,produto.getStatus());
            status = prep.executeUpdate();
            return status;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Conectar: " + ex.getMessage());
            return ex.getErrorCode();
        } catch (NullPointerException np) {
            JOptionPane.showMessageDialog(null, np.getMessage());
            return 0;
        }

    }

    public ArrayList<ProdutosDTO> listarProdutos() {

        return listagem;
    }

}
