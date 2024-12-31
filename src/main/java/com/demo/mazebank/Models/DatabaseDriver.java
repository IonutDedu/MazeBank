package com.demo.mazebank.Models;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseDriver {
    private Connection conn;

    public DatabaseDriver(){
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:mazebank.db");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Client section

    public ResultSet getClientData(String pAddress, String password){
        Statement statement;
        ResultSet resultSet = null;
        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients WHERE PayeeAddress ='" + pAddress + "'AND Password = '" + password +"';");
        } catch (SQLException e){
            e.printStackTrace();
        }

        return resultSet;
    }

    public void createClient(String fName, String lName, String pAddress, String password, LocalDate date){
        Statement statement;
        try{
            statement = this.conn.createStatement();
            statement.executeUpdate("insert into " +
                    "Clients (FirstName, LastName, PayeeAddress, Password, Date) " +
                    "values ('" + fName + "', '" + lName + "', '" + pAddress + "', '" + password + "', '" + date.toString() + "');");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createCheckingAccount(String owner, String number, double tLimit, double balance){
        Statement statement;
        try{
            statement = this.conn.createStatement();
            statement.executeUpdate("insert into " +
                    "CheckingAccounts (Owner, AccountNumber, TransactionLimit, Balance) " +
                    "values ('" + owner + "', '" + number + "', " + tLimit + ", " + balance + ");");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createSavingsAccount(String owner, String number, double wLimit, double balance){
        Statement statement;
        try{
            statement = this.conn.createStatement();
            statement.executeUpdate("insert into " +
                    "SavingsAccounts (Owner, AccountNumber, WithdrawalLimit, Balance) " +
                    "values ('" + owner + "', '" + number + "', " + wLimit + ", " + balance + ");");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public ResultSet getAllClientsData(){
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients;");
        } catch (Exception e){
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getTransactions(String pAddress, int limit){
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Transactions WHERE Sender ='" +
                    pAddress + "' or Receiver = '" + pAddress +"' limit " + limit + ";");
        } catch (SQLException e){
            e.printStackTrace();
        }

        return resultSet;
    }

    //Method returns savings account balance
    public double getSavingsAccountBalance(String pAddress){
        Statement statement;
        ResultSet resultSet;
        double balance = 0;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM SavingsAccounts WHERE Owner ='" + pAddress + "';");
            balance = resultSet.getDouble("Balance");
        } catch (SQLException e){
            e.printStackTrace();
        }

        return balance;
    }

    //Method to either add or subtract from balance given amount and operation
    public void updateBalance(String pAddress, double amount, String operation){
        Statement statement;
        ResultSet resultSet;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM SavingsAccounts WHERE Owner ='" + pAddress + "';");
            double newBalance;
            if(operation.equals("ADD")){
                newBalance = resultSet.getDouble("Balance") + amount;
                statement.executeUpdate("update SavingsAccounts set Balance = " + newBalance + " where Owner ='" + pAddress + "';");
            } else {
                if(resultSet.getDouble("Balance") >= amount) {
                    newBalance = resultSet.getDouble("Balance") - amount;
                    statement.executeUpdate("update SavingsAccounts set Balance = " + newBalance + " where Owner ='" + pAddress + "';");
                }
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Creates and records new transaction
    public void newTransaction(String sender, String receiver, double amount, String message){
        Statement statement;
        try {
            statement = this.conn.createStatement();
            LocalDate date = LocalDate.now();
            statement.executeUpdate("insert into Transactions ( Sender, Receiver, Amount, Date, Message) " +
                    "values( '" + sender + "', '" + receiver +"', " + amount + ", '" + date + "', '" + message + "');");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Admin section

    public ResultSet getAdminData (String username, String password){
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Admins WHERE Username ='" + username + "' AND Password = '" + password +"';");
        } catch (Exception e){
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet searchClient (String ppAddress){
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients WHERE PayeeAddress ='" + ppAddress + "';");
        } catch (SQLException e){
            e.printStackTrace();
        }

        return resultSet;
    }

    public void depositSavings(String pAddress, double amount){
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("update SavingsAccounts set Balance = " + amount + " where Owner = '" + pAddress + "';");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Utility Methods

    public int getLastClientsId(){
        Statement statement;
        ResultSet resultSet;
        int id = 0;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM sqlite_sequence WHERE name = 'Clients';");
            id = resultSet.getInt("seq");
        } catch (Exception e){
            e.printStackTrace();
        }

        return id;
    }

    public ResultSet getCheckingAccountData(String pAddress){
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM CheckingAccounts WHERE owner = '" + pAddress + "';");
        } catch (Exception e){
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getSavingsAccountData(String pAddress){
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM SavingsAccounts WHERE owner = '" + pAddress + "';");
        } catch (Exception e){
            e.printStackTrace();
        }

        return resultSet;
    }
}
