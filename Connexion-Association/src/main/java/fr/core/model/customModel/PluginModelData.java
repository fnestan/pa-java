package fr.core.model.customModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PluginModelData {
    public String screen;
    public Object input;
    public Object output;
}