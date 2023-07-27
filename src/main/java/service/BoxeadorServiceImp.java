package service;


import DTO.BoxeadorDTO;
import DTO.EntrenadorInfoDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.Boxeador;
import model.Categoria;
import model.Entrenador;
import org.modelmapper.ModelMapper;
import repository.BoxeadorRepository;


import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BoxeadorServiceImp implements IBoxeadorService {
    @Inject
    BoxeadorRepository boxeadorRepository;
    @Inject
    CategoriaServiceImp categoriaServiceImp;
    @Inject
    EntrenadorServiceImp entrenadorServiceImp;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<BoxeadorDTO> getAllBoxeadores() {
        List<BoxeadorDTO> boxeadorDTOS = this.boxeadorRepository.getAllBoxeadores()
                                        .stream().map(b -> modelMapper.map(b,BoxeadorDTO.class))
                                        .collect(Collectors.toList());
        return boxeadorDTOS;
    }

    @Override
    public BoxeadorDTO create(BoxeadorDTO boxeadorDTO) throws Exception {

        Categoria categoria = this.categoriaServiceImp.obtenerCategoriaPorPeso(boxeadorDTO.getPeso());
        Entrenador entrenador = this.entrenadorServiceImp.obtenerEntrenadorPorCategoria(categoria);


        boxeadorDTO.setEntrenador(modelMapper.map(entrenador, EntrenadorInfoDTO.class));
        boxeadorDTO.setCategoria(categoria);
        boxeadorDTO.setFechaIngreso(new Date(System.currentTimeMillis()));

        Boxeador boxeador = modelMapper.map(boxeadorDTO,Boxeador.class);
        this.entrenadorServiceImp.addBoxeador(entrenador,boxeador);
        this.boxeadorRepository.create(boxeador);

        return boxeadorDTO;
    }
}
