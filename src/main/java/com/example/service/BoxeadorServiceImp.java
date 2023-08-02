package com.example.service;


import com.example.dto.BoxeadorDTO;
import com.example.dto.BoxeadorInfoDTO;
import com.example.dto.EntrenadorDTO;
import com.example.dto.EntrenadorInfoDTO;
import com.example.model.Entrenador;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.example.model.Boxeador;
import com.example.model.Categoria;
import jakarta.ws.rs.NotFoundException;
import org.modelmapper.ModelMapper;
import com.example.repository.BoxeadorRepository;


import java.sql.Date;
import java.util.List;
import java.util.Optional;
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
        List<BoxeadorDTO> boxeadorDTOS = this.boxeadorRepository.getAll()
                .stream().map(b -> modelMapper.map(b, BoxeadorDTO.class))
                .collect(Collectors.toList());
        return boxeadorDTOS;
    }

    /** create
     *
     **/
    @Override
    public BoxeadorDTO create(BoxeadorDTO boxeadorDTO) throws Exception {

        Categoria categoria = this.categoriaServiceImp.obtenerCategoriaPorPeso(boxeadorDTO.getPeso());
        EntrenadorDTO entrenadorDTO = this.entrenadorServiceImp.obtenerEntrenadorPorCategoria(categoria);


        boxeadorDTO.setEntrenador(modelMapper.map(entrenadorDTO, EntrenadorInfoDTO.class));
        boxeadorDTO.setCategoria(categoria);
        boxeadorDTO.setFechaIngreso(new Date(System.currentTimeMillis()));

        Boxeador boxeador = modelMapper.map(boxeadorDTO, Boxeador.class);
        this.boxeadorRepository.create(boxeador);
        BoxeadorInfoDTO boxeadorInfoDTO = modelMapper.map(boxeador, BoxeadorInfoDTO.class);
        this.entrenadorServiceImp.addBoxeador(entrenadorDTO, boxeadorInfoDTO);

        return boxeadorDTO;
    }

    @Override
    public Boolean eliminar(String nombre) {
        Optional<Boxeador> boxeador = Optional.ofNullable(this.boxeadorRepository.find(nombre)
                .orElseThrow(() -> new NotFoundException("El boxeador " + nombre + " no fue encontrado.")));

        BoxeadorDTO boxeadorDTO = modelMapper.map(boxeador,BoxeadorDTO.class);
        EntrenadorDTO entrenadorDTO = modelMapper.map(boxeador.get().getEntrenador(),EntrenadorDTO.class);

        this.entrenadorServiceImp.eliminarBoxeador(entrenadorDTO,boxeadorDTO);

        return this.boxeadorRepository.delete(boxeador.get());
    }
}
