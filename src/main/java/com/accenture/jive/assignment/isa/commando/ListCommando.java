package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Industry;
import com.accenture.jive.assignment.isa.service.IndustryService;
import java.sql.SQLException;
import java.util.List;

public class ListCommando implements Commando {

    private final IndustryService industryService;
    private final UserInteraction userInteraction;

    public ListCommando(IndustryService industryService, UserInteraction userInteraction) {
        this.industryService = industryService;
        this.userInteraction = userInteraction;
    }

    @Override
    public boolean execute() throws CommandoException {
        try {
            List<Industry> industries = industryService.listIndustry();
            userInteraction.printIndustry(industries);
        } catch (SQLException e) {
            throw new CommandoException(userInteraction.failedCommandoSQL(), e);
        }
        return true;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "list".equalsIgnoreCase(line);
    }
}