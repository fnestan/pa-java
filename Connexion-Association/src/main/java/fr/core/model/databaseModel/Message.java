package fr.core.model.databaseModel;

import fr.core.model.customModel.UserTrunqued;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Getter
@Setter
public class Message {
   private Integer id;
   private String content;
   private Date sendDate;
   @JsonProperty("redactor")
   private UserTrunqued userTrunqued;
}
