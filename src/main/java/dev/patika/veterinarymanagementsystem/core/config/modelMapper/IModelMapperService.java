package dev.patika.veterinarymanagementsystem.core.config.modelMapper;

import org.modelmapper.ModelMapper;

public interface IModelMapperService {
    public ModelMapper forRequest();
    public ModelMapper forResponse();
}
