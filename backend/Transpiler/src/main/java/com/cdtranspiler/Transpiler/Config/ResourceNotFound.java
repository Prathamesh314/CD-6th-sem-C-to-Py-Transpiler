package com.cdtranspiler.Transpiler.Config;

public class ResourceNotFound extends RuntimeException{
    String name;
    Long id;

    public ResourceNotFound(String name, Long id)
    {
        super(String.format("%s not found with id: %d", name, id));
        this.name = name;
        this.id = id;
    }


}
