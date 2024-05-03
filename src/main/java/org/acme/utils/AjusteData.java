package org.acme.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AjusteData {
    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static final DateTimeFormatter FORMATTER_BRASIL = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    static public LocalDate convertTextToLocalDate(String data){
        LocalDate dataLocalDate = LocalDate.parse(data.trim(),FORMATTER);

        return dataLocalDate;
    }
}
