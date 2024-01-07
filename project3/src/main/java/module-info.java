module com.example.javafx_bank {
    requires javafx.controls;
    requires javafx.fxml;
            
                        
    opens com.example.javafx_bank to javafx.fxml;
    exports com.example.javafx_bank;
    exports com.example.javafx_bank.controller;
    opens com.example.javafx_bank.controller to javafx.fxml;
    exports com.example.javafx_bank.incident;
    opens com.example.javafx_bank.incident to javafx.fxml;
}