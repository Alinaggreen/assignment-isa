package com.accenture.jive.assignment.isa.commando;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InsertCommando implements Commando{

    @Override
    public boolean execute() throws CommandoException {

        String filePath = "C://dev1//isa//STOCK_DATA - Copy.csv";

        try {
            Scanner scanner = new Scanner(new File(filePath));
            scanner.useDelimiter(";");

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
                String[] fields = line.split(";");

                String name = fields[0];
                int price = Integer.parseInt(fields[1]);
                // TODO: Datatype -> date
                int date = Integer.parseInt(fields[2]);
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
        }

        return true;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "insert".equalsIgnoreCase(line);
    }
}