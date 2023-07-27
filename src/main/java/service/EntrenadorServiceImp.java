package service;

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
    public Entrenador obtenerEntrenadorPorCategoria(Categoria categoria) {
        List<Entrenador> entrenadores = this.entrenadorRepository.getAllEntrenadores();

        for (Entrenador entrenador: entrenadores) {
            if(entrenador.getCategorias().contains(categoria)){
                return entrenador;
            }
        }
        return null;
    }

    @Override
    public Boolean addBoxeador(Entrenador entrenador, Boxeador boxeador) throws Exception {

        if(entrenador.getBoxeadores().size() < 5){
            entrenador.getBoxeadores().add(boxeador);
            return true;
        }
        throw new Exception("El entrenador " + entrenador.getNombre() + " ha alcanzado el límite de boxeadores (5).");
    }
}
