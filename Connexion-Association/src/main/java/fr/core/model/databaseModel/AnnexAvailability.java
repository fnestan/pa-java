package fr.core.model.databaseModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.codehaus.jackson.annotate.JsonProperty;

import java.sql.Time;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Getter
@Setter
public class AnnexAvailability {

    private int id;
    @JsonProperty("openingTime")
    private Time openingTime;
    @JsonProperty("closingTime")
    private Time closingTime;
    @JsonProperty("DayId")
    private int DayId;
    @JsonProperty("Day")
    private Day day;
}
