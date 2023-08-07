package com.example.service;

import com.example.dto.BoxeadorDto;
import com.example.dto.BoxeadorSinEntreDto;
import com.example.dto.EntrenadorDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.example.model.Boxeador;
import com.example.model.Categoria;
import com.example.model.Entrenador;
import org.modelmapper.ModelMapper;
import com.example.repository.EntrenadorRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class EntrenadorServiceImp implements IEntrenadorService {
    @Inject
    EntrenadorRepository entrenadorRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<EntrenadorDto> getAllEntrenadores() {
        List<EntrenadorDto> entrenadorDtos = this.entrenadorRepository.getAll()
                                          .stream().map(e -> modelMapper.map(e, EntrenadorDto.class))
                                          .collect(Collectors.toList());
        return entrenadorDtos;
    }

    @Override
    public Boolean crearEntrenador(EntrenadorDto entrenadorDto) throws Exception {
        if(entrenadorDto.getCategorias().size() <= 2) {
            if (!this.existEntrenadorConCategoria(entrenadorDto.getCategorias())) {
                return this.entrenadorRepository.create(modelMapper.map(entrenadorDto, Entrenador.class));
            }

            throw new Exception("No puede haber 2 entrenadores con la misma categoria");
        }

        throw new Exception("El entrenador no puede tener más de 2 categorias");
    }

    public Boolean existEntrenadorConCategoria(List<Categoria> categorias){
        for (Categoria c: categorias) {
            if(this.obtenerEntrenadorPorCategoria(c) != null){
                return true;
            }
        }
        return false;
    }

    @Override
    public EntrenadorDto obtenerEntrenadorPorCategoria(Categoria categoria) {
       Entrenador entrenador = this.entrenadorRepository.obtenerEntrenadorPorCategoria(categoria);
       if(entrenador != null) {
           return modelMapper.map(entrenador, EntrenadorDto.class);
       }
       return null;
    }

    @Override
    public EntrenadorDto addBoxeador(EntrenadorDto entrenadorDTO, BoxeadorSinEntreDto boxeadorDTO) throws Exception {

        if(entrenadorDTO.getBoxeadores().size() < 5){
            Entrenador entrenador = modelMapper.map(entrenadorDTO,Entrenador.class);
            Boxeador boxeador = modelMapper.map(boxeadorDTO,Boxeador.class);
            return modelMapper.map(this.entrenadorRepository.addBoxeador(entrenador,boxeador), EntrenadorDto.class);
        }
        throw new Exception("El entrenador " + entrenadorDTO.getNombre() + " ha alcanzado el límite de boxeadores (5).");
    }

    @Override
    public Boolean eliminarBoxeador(EntrenadorDto entrenadorDTO, BoxeadorDto boxeadorDTO) {

        Entrenador entrenador = modelMapper.map(entrenadorDTO,Entrenador.class);
        Boxeador boxeador = modelMapper.map(boxeadorDTO,Boxeador.class);

        return this.entrenadorRepository.deleteBoxeador(entrenador,boxeador);
    }
}
