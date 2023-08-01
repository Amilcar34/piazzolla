package com.example.service;

import com.example.DTO.BoxeadorInfoDTO;
import com.example.DTO.EntrenadorDTO;
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
    public List<EntrenadorDTO> getAllEntrenadores() {
        List<EntrenadorDTO> entrenadorDTOS = this.entrenadorRepository.getAllEntrenadores()
                                          .stream().map(e -> modelMapper.map(e, EntrenadorDTO.class))
                                          .collect(Collectors.toList());
        return entrenadorDTOS;
    }

    @Override
    public EntrenadorDTO obtenerEntrenadorPorCategoria(Categoria categoria) {
       Entrenador entrenador = this.entrenadorRepository.obtenerEntrenadorPorCategoria(categoria);

       return modelMapper.map(entrenador,EntrenadorDTO.class);
    }

    @Override
    public EntrenadorDTO addBoxeador(EntrenadorDTO entrenadorDTO, BoxeadorInfoDTO boxeadorDTO) throws Exception {

        if(entrenadorDTO.getBoxeadores().size() < 4){
            Entrenador entrenador = modelMapper.map(entrenadorDTO,Entrenador.class);
            Boxeador boxeador = modelMapper.map(boxeadorDTO,Boxeador.class);
            return modelMapper.map(this.entrenadorRepository.addBoxeador(entrenador,boxeador),EntrenadorDTO.class);
        }
        throw new Exception("El entrenador " + entrenadorDTO.getNombre() + " ha alcanzado el límite de boxeadores (5).");
    }
}
