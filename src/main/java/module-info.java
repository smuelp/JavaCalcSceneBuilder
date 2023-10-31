module start.projetopadrao {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;


    // Adicione a entrada 'requires' para o módulo 'java.scripting'
    requires java.scripting;

    opens controller to javafx.fxml;
    exports controller;
    opens start.projetopadrao to javafx.fxml;
    exports start.projetopadrao;
    
    
}
