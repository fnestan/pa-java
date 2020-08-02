package fr.core.model.customModel;

import fr.core.model.databaseModel.Service;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomService {
    private Service service;
    private boolean isAnswer;
}
