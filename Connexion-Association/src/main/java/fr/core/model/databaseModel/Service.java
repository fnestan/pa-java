package fr.core.model.databaseModel;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;


public class Service extends Need {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date_service;

    public Date getDate_service() {
        return date_service;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public void setDate_service(Date date_service) {
        this.date_service = date_service;
    }

    @Override
    public String toString() {
        return "Service{" +
                "date_service=" + date_service +
                '}';
    }
}
