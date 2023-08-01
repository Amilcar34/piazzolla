package com.example.service;


import com.example.DTO.BoxeadorDTO;
import com.example.DTO.BoxeadorInfoDTO;
import com.example.DTO.EntrenadorDTO;
import com.example.DTO.EntrenadorInfoDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.example.model.Boxeador;
import com.example.model.Categoria;
import org.modelmapper.ModelMapper;
import com.example.repository.BoxeadorRepository;


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
        EntrenadorDTO entrenadorDTO = this.entrenadorServiceImp.obtenerEntrenadorPorCategoria(categoria);


        boxeadorDTO.setEntrenador(modelMapper.map(entrenadorDTO, EntrenadorInfoDTO.class));
        boxeadorDTO.setCategoria(categoria);
        boxeadorDTO.setFechaIngreso(new Date(System.currentTimeMillis()));

        Boxeador boxeador = modelMapper.map(boxeadorDTO,Boxeador.class);
        this.boxeadorRepository.create(boxeador);
        BoxeadorInfoDTO boxeadorInfoDTO = modelMapper.map(boxeador, BoxeadorInfoDTO.class);
        this.entrenadorServiceImp.addBoxeador(entrenadorDTO,boxeadorInfoDTO);

        return boxeadorDTO;
    }
}
