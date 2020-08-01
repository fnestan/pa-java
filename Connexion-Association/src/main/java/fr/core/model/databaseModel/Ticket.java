package fr.core.model.databaseModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Getter
@Setter
public class Ticket {
   private Integer id;
   private Integer number;
   private String label;
   private Date creationDate;
   private boolean active;
   @JsonProperty("Messages")
   private List<Message> messages = new ArrayList<>();
}
