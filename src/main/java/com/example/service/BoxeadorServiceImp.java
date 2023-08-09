package com.example.service;


import com.example.dto.*;
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
    public List<BoxeadorDto> getAllBoxeadores() {
        return this.boxeadorRepository.getAll()
                .stream().map(b -> modelMapper.map(b, BoxeadorDto.class))
                .collect(Collectors.toList());

    }


    @Override
    public BoxeadorDto create(BoxeadorCreateDto boxeadorCreateDto) throws Exception {

        Categoria categoria = this.categoriaServiceImp.obtenerCategoriaPorPeso(boxeadorCreateDto.getPeso());
        EntrenadorDto entrenadorDTO = this.entrenadorServiceImp.obtenerEntrenadorPorCategoria(categoria);

        BoxeadorDto boxeadorDTO = modelMapper.map(boxeadorCreateDto,BoxeadorDto.class);

        boxeadorDTO.setEntrenador(modelMapper.map(entrenadorDTO, EntrenadorSinBoxDto.class));
        boxeadorDTO.setCategoria(categoria);
        boxeadorDTO.setFechaIngreso(new Date(System.currentTimeMillis()));

        Boxeador boxeador = modelMapper.map(boxeadorDTO, Boxeador.class);
        Boxeador boxeadorCreado = this.boxeadorRepository.create(boxeador);

        BoxeadorSinEntreDto boxeadorSinEntreDTO = modelMapper.map(boxeador, BoxeadorSinEntreDto.class);
        this.entrenadorServiceImp.addBoxeador(entrenadorDTO, boxeadorSinEntreDTO);

        return modelMapper.map(boxeadorCreado,BoxeadorDto.class);
    }

    @Override
    public BoxeadorDto actualizar(String nombre, BoxeadorDto boxeadorDto) {
        Optional<Boxeador> boxeador = Optional.ofNullable(this.boxeadorRepository.find(nombre)
                .orElseThrow(() -> new NotFoundException("El Boxeador " + nombre + " no fue encontrado.")));
        
          Boxeador boxeadorModificado = modelMapper.map(boxeadorDto,Boxeador.class);

          return modelMapper.map(this.boxeadorRepository.update(boxeador.get(),boxeadorModificado),BoxeadorDto.class);
    }

    @Override
    public Boolean eliminar(String nombre) {
        Optional<Boxeador> boxeador = Optional.ofNullable(this.boxeadorRepository.find(nombre)
                .orElseThrow(() -> new NotFoundException("El boxeador " + nombre + " no fue encontrado.")));

            BoxeadorDto boxeadorDTO = modelMapper.map(boxeador, BoxeadorDto.class);

            EntrenadorDto entrenadorDTO = modelMapper.map(boxeador.get().getEntrenador(), EntrenadorDto.class);

            this.entrenadorServiceImp.eliminarBoxeador(entrenadorDTO, boxeadorDTO);

            return this.boxeadorRepository.delete(boxeador.get());
    }
}
