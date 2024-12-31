package com.demo.mazebank.Controllers.Admin;

import com.demo.mazebank.Models.Client;
import com.demo.mazebank.Models.Model;
import com.demo.mazebank.Views.ClientCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientsController implements Initializable {
    public ListView<Client> clients_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initClientsList();
        clients_listview.setItems(Model.getInstance().getClients());
        clients_listview.setCellFactory(e->new ClientCellFactory());
    }

    private void initClientsList(){
        // Check if the list is empty; if not checked, it might append data all over again to the existing list
        if(Model.getInstance().getClients().isEmpty()){
            Model.getInstance().setClients();
        }
    }
}
