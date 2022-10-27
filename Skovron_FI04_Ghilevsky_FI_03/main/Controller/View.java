package Skovron_FI04_Ghilevsky_FI_03.main.Controller;

import Skovron_FI04_Ghilevsky_FI_03.main.DataBase.Table;
import Skovron_FI04_Ghilevsky_FI_03.main.Parser.Parser;
import Skovron_FI04_Ghilevsky_FI_03.main.Parser.Query.Create;
import Skovron_FI04_Ghilevsky_FI_03.main.Parser.Query.Insert;
import Skovron_FI04_Ghilevsky_FI_03.main.Parser.Query.SQLCommand;
import Skovron_FI04_Ghilevsky_FI_03.main.Parser.Query.Select;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class View {
    private final ArrayList<Table> tables = new ArrayList<>();

    public String readQueryFromConsole(){

        Scanner scanner = new Scanner(System.in);
        StringBuilder query = new StringBuilder();

        while (scanner.hasNextLine()) {
            query.append(" ").append(scanner.nextLine());

            if (Objects.equals(query.substring(query.length() - 1), ";"))
                break;
        }

        scanner.close();
        return query.toString();
    }

    public void action(Parser parser) {
        try {
            SQLCommand sqlCommand = parser.createSQLCommand();

            if(sqlCommand instanceof Create){
                Table table = new Table(
                        sqlCommand.getTableName(),
                        ((Create) sqlCommand).getNameOfColum());
                // + перевірка чи існує вже таблиця
                tables.add(table);
            }else if(sqlCommand instanceof Insert){
                String name = sqlCommand.getTableName();
                Table table;

                if(isTableExist(name)){
                    table = findTable(name);
                    table.rowsInsert(
                            ((Insert) sqlCommand).getNumberOfRows(),
                            ((Insert) sqlCommand).getRows());
                }else{
                    throw new NoSuchElementException("No such table (Error Insert)");
                }
            }else if(sqlCommand instanceof Select){
                System.out.println("Select");
            }else {
                System.out.println("Error");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private Table findTable(String name){
        Table table = null;

        for (Table value : tables) {
            if (value.getTableName().equals(name))
                table = value;
        }
        return table;
    }

    private boolean isTableExist(String name){
        for (Table table : tables) {
            if (table.getTableName().equals(name))
                return true;
        }

        return false;
    }

    public void run() {

        while (true) {
            Parser parser = new Parser(readQueryFromConsole());
            action(parser);
        }

    }
}
