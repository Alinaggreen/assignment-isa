package com.accenture.jive.assignment.isa.commando;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InsertCommando implements Commando{

    private final Connection connection;

    public InsertCommando(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean execute() throws CommandoException {

        // TODO: change path back to STOCK_DATA.csv
        String filePath = "C://dev1//isa//STOCK_DATA - Copy.csv";

        try {
            Scanner scanner = new Scanner(new File(filePath));
            scanner.useDelimiter(";");

            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(";");

                String name = fields[0];
                String price = fields[1].replace(",", ".").substring(2);
                float priceParsed = Float.parseFloat(price);
                // TODO: Datatype -> date
                // int dateInt = Integer.parseInt(fields[2]);
                // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-mm-yyyy");
                // LocalDate date = LocalDate.parse(fields[2], formatter);

                LocalDate localdate = LocalDate.of(1900, 2, 2);
                Date date = Date.valueOf(localdate);

                System.out.println(date);

                String sql = "INSERT INTO stockmarket VALUES (1, 1, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setDate(1, Date.valueOf("1900-02-02"));
                System.out.println(preparedStatement.executeUpdate());


                String industry = fields[3];

                // TODO: insert industry if not yet exist
                // TODO: get id of industry
                // TODO: insert stock with indsutry_id if not yet exists
                // TODO: insert stockmarket with price & date if not yet exists

                for (String field : fields) {
                    System.out.println(field + " ");
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "insert".equalsIgnoreCase(line);
    }
}