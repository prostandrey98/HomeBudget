package com.homebudget.util;

import org.springframework.stereotype.Component;

@Component("thymeleafUtils")
public class ThymeleafUtils {
    public String formatFrequency(String frequency) {
        if (frequency == null) return "";
        return switch (frequency) {
            case "WEEKLY" -> "Еженедельно";
            case "MONTHLY" -> "Ежемесячно";
            case "BIMONTHLY" -> "Раз в 2 месяца";
            case "QUARTERLY" -> "Ежеквартально";
            case "YEARLY" -> "Ежегодно";
            default -> "Неизвестно";
        };
    }
}