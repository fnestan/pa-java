package fr.core.service;

import fr.core.model.databaseModel.Type;
import fr.core.service.inter.IRestConnector;
import fr.core.service.inter.ITypeService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TypeService implements ITypeService {
    IRestConnector restConnector;

    public void setRestConnector(IRestConnector restConnector) {
        this.restConnector = restConnector;
    }

    public Optional<List<Type>> getAllTypes() throws Exception {

        Optional<List<Type>> optionalTypes = Optional.empty();
        try {
            List<Type> types = Arrays.asList(restConnector.get("types", Type[].class));
            optionalTypes = Optional.ofNullable(types);
        } catch (Exception e) {

        }
        return optionalTypes;
    }
}
