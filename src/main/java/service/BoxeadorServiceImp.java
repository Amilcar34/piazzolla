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
    public List<Boxeador> getAllBoxeadores() {
        return this.boxeadorRepository.getAllBoxeadores();
    }

    @Override
    public BoxeadorDTO create(BoxeadorDTO boxeadorDTO) {

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
