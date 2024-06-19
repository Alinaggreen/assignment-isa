package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Industry;
import com.accenture.jive.assignment.isa.service.IndustryService;
import java.sql.SQLException;
import java.util.List;

public class ListCommando implements Commando {

    private final IndustryService industryService;

    public ListCommando(IndustryService industryService) {
        this.industryService = industryService;
    }

    @Override
    public boolean execute() throws CommandoException {

        try {
            List<Industry> industries = industryService.listIndustry();

            for (Industry industry : industries) {
                System.out.println("ID: " + industry.getId() + " - " + industry.getName()
                        + " - currently " + industry.getStockCount() + " stocks assigned");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "list".equalsIgnoreCase(line);
    }
}