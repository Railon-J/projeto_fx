package application.view;

import java.time.LocalDate;
import java.util.List;

import application.model.MovimentacaoEstoqueModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class HistoricoController {

    @FXML private Button btnBuscar;

    @FXML private TableColumn<MovimentacaoEstoqueModel, Integer> colID;
    @FXML private TableColumn<MovimentacaoEstoqueModel, Integer> colIdProd;
    @FXML private TableColumn<MovimentacaoEstoqueModel, String> colData;
    @FXML private TableColumn<MovimentacaoEstoqueModel, String> colNome;
    @FXML private TableColumn<MovimentacaoEstoqueModel, String> colTipo;
    @FXML private TableColumn<MovimentacaoEstoqueModel, Integer> colQtd;

    @FXML private DatePicker dtInicio;
    @FXML private DatePicker dtFinal;

    @FXML private TableView<MovimentacaoEstoqueModel> tabHistorico;

    private ObservableList<MovimentacaoEstoqueModel> listaMovimentacao;

    private MovimentacaoEstoqueModel movimentacao =
            new MovimentacaoEstoqueModel(0, 0, null, null, 0, null);

    @FXML
    public void initialize() {
        // Configura colunas
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colIdProd.setCellValueFactory(new PropertyValueFactory<>("idProd"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeProd"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        // Define datas padrão (primeiro e último dia do mês atual)
        LocalDate hoje = LocalDate.now();
        dtInicio.setValue(hoje.withDayOfMonth(1));
        dtFinal.setValue(hoje.withDayOfMonth(hoje.lengthOfMonth()));

        // Ação do botão Buscar
        btnBuscar.setOnAction(e ->
                buscarHistorico(movimentacao.getIdProd(), dtInicio.getValue(), dtFinal.getValue())
        );
    }

    public void buscarHistorico(int idProd, LocalDate dataInicio, LocalDate dataFinal) {
        List<MovimentacaoEstoqueModel> listaHistorico =
                movimentacao.HistoricoMovimentacao(idProd, dataInicio, dataFinal);

        listaMovimentacao = FXCollections.observableArrayList(listaHistorico);
        tabHistorico.setItems(listaMovimentacao);
    }
}
