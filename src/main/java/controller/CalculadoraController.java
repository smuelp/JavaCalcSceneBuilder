package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;
import model.Calculadora;

/**
 * FXML Controller class
 *
 * @author samue
 */

public class CalculadoraController implements Initializable {

    @FXML
    private Button btnBackspace;

    @FXML
    private void btnBackspaceOnAction(ActionEvent event) {
        if (expressaoBuilder.length() > 0) {
            expressaoBuilder.deleteCharAt(expressaoBuilder.length() - 1);
            lblMostrar.setText(expressaoBuilder.toString());
        }
    }

    private enum Estado {
        NENHUM,
        DIGITO,
        OPERADOR,
        SEM
    }

    private Estado estadoAtual = Estado.NENHUM;
    private int nivelDeAninhamento = 0;

    @FXML
    private Button btnReset;
    @FXML
    private Button btnPorc;
    @FXML
    private Button btnDiv;
    @FXML
    private Button btnSete;
    @FXML
    private Button btnOito;
    @FXML
    private Button btnNove;
    @FXML
    private Button btnMult;
    @FXML
    private Button btnQuatro;
    @FXML
    private Button btnCinco;
    @FXML
    private Button btnSeis;
    @FXML
    private Button btnSub;
    @FXML
    private Button btnUm;
    @FXML
    private Button btnDois;
    @FXML
    private Button btnTres;
    @FXML
    private Button btnSom;
    @FXML
    private Button btnZero;
    @FXML
    private Button btnVir;
    @FXML
    private Button btnIgual;
    @FXML
    private Button btnPar;
    @FXML
    private Label lblMostrar;
    @FXML
    private Label lblOperador;

    private boolean IgualApertado = false;
    private StringBuilder expressaoBuilder = new StringBuilder();
    private String expressaoCompleta = "";
    private Calculadora calculadora = new Calculadora();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnResetOnAction(ActionEvent event) {
        expressaoBuilder.setLength(0);
        IgualApertado = false;
        lblMostrar.setText("0");
        lblOperador.setText(" ");
        expressaoCompleta = "";
        estadoAtual = Estado.NENHUM;
        nivelDeAninhamento = 0;
    }

    @FXML
    private void btnPorcOnAction(ActionEvent event) {
        adicionarValor("%");
        estadoAtual = Estado.OPERADOR;
    }

    @FXML
    private void btnDivOnAction(ActionEvent event) {
        adicionarValor("/");
        estadoAtual = Estado.OPERADOR;
    }

    @FXML
    private void btnSeteOnAction(ActionEvent event) {
        adicionarValor("7");
        estadoAtual = Estado.DIGITO;
    }

    @FXML
    private void btnOitoOnAction(ActionEvent event) {
        adicionarValor("8");
        estadoAtual = Estado.DIGITO;
    }

    @FXML
    private void btnNoveOnAction(ActionEvent event) {
        adicionarValor("9");
        estadoAtual = Estado.DIGITO;
    }

    @FXML
    private void btnMultOnAction(ActionEvent event) {
        adicionarValor("*");
        estadoAtual = Estado.OPERADOR;
    }

    @FXML
    private void btnQuatroOnAction(ActionEvent event) {
        adicionarValor("4");
        estadoAtual = Estado.DIGITO;
    }

    @FXML
    private void btnCincoOnAction(ActionEvent event) {
        adicionarValor("5");
        estadoAtual = Estado.DIGITO;
    }

    @FXML
    private void btnSeisOnAction(ActionEvent event) {
        adicionarValor("6");
        estadoAtual = Estado.DIGITO;
    }

    @FXML
    private void btnSubOnAction(ActionEvent event) {
        adicionarValor("-");
        estadoAtual = Estado.OPERADOR;
    }

    @FXML
    private void btnUmOnAction(ActionEvent event) {
        adicionarValor("1");
        estadoAtual = Estado.DIGITO;
    }

    @FXML
    private void btnDoisOnAction(ActionEvent event) {
        adicionarValor("2");
        estadoAtual = Estado.DIGITO;
    }

    @FXML
    private void btnTresOnAction(ActionEvent event) {
        adicionarValor("3");
        estadoAtual = Estado.DIGITO;
    }

    @FXML
    private void btnSomOnAction(ActionEvent event) {
        adicionarValor("+");
        estadoAtual = Estado.OPERADOR;
    }

    @FXML
    private void btnZeroOnAction(ActionEvent event) {
        adicionarValor("0");
        estadoAtual = Estado.DIGITO;
    }

    @FXML
    private void btnVirOnAction(ActionEvent event) {
        adicionarValor(",");
    }

    private void adicionarValor(String valor) {
        if (IgualApertado) {
            String valorIgual = lblOperador.getText();
            expressaoBuilder.setLength(0);
            expressaoBuilder.append(valorIgual);
            IgualApertado = false;
        }

        expressaoBuilder.append(valor);
        lblMostrar.setText(expressaoBuilder.toString());
    }

    @FXML
    private void btnIgualOnAction(ActionEvent event) {
        String expressao = lblMostrar.getText();

        try {
            double resultado = Calculadora.avaliarInfixa(expressao);
            lblOperador.setText(String.format("%.2f", resultado));
            IgualApertado = true;
        } catch (IllegalArgumentException ex) {
            lblOperador.setText("Expressão inválida");
        } catch (ArithmeticException ex) {
            lblOperador.setText("Divisão por zero");
        }
    }

    @FXML
    private void btnParOnAction(ActionEvent event) {
        if (estadoAtual == Estado.NENHUM || estadoAtual == Estado.OPERADOR) {
            adicionarValor("(");
            nivelDeAninhamento++;
            estadoAtual = Estado.SEM;
        } else if (nivelDeAninhamento > 0) {
            nivelDeAninhamento--;
            adicionarValor(")");
        }
    }
}
