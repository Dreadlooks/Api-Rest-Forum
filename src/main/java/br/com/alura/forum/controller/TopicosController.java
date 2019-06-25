package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import br.com.alura.forum.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.modelo.AtualizacaoTopicoForm;
import br.com.alura.forum.modelo.TopicoForm;
import br.com.alura.forum.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping("/topicos")
    public List<TopicoDto> lista() {
        List<Topico> topicos = topicoRepository.findAll();
        return TopicoDto.converter(topicos);
    }

    @GetMapping("/topico")
    public List<TopicoDto> listaPorNome(String nomeCurso) {
        List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
        return TopicoDto.converter(topicos);
    }

    @PostMapping("/novoTopico")
    @Transactional
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriBuilder) {
        Topico topico = topicoForm.converter(cursoRepository);
        topicoRepository.save(topico);
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @GetMapping("/topico/{id}")
    public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable("id") Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/atualizar/{id}")
    @Transactional
    public ResponseEntity<TopicoDto> atualizar(@PathVariable("id") Long id, @RequestBody @Valid AtualizacaoTopicoForm topicoForm) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            Topico atualizado = topicoForm.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicoDto(atualizado));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deletar/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable("id") Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
