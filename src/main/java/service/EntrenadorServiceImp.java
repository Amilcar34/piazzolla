package service;

import DTO.BoxeadorDTO;
import DTO.BoxeadorInfoDTO;
import DTO.EntrenadorDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.Boxeador;
import model.Categoria;
import model.Entrenador;
import org.modelmapper.ModelMapper;
import repository.EntrenadorRepository;

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
    public Boolean addBoxeador(EntrenadorDTO entrenador, BoxeadorDTO boxeador) throws Exception {

        if(entrenador.getBoxeadores().size() < 5){
           return this.entrenadorRepository.addBoxeador(modelMapper.map(entrenador,Entrenador.class),modelMapper.map(boxeador,Boxeador.class));
        }
        throw new Exception("El entrenador " + entrenador.getNombre() + " ha alcanzado el límite de boxeadores (5).");
    }
}
