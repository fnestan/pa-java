package fr.core.model.customModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Email {
    private String object;
    private String email;
    private String message;
}
