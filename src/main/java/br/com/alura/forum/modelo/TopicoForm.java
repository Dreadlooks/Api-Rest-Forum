package br.com.alura.forum.modelo;

import br.com.alura.forum.repository.CursoRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TopicoForm {

    @NotNull
    @NotBlank
    @Size(min = 2)
    private String titulo;
    private String mensagem;
    private String nomeCurso;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public Topico converter(CursoRepository cursoRepository){
        Curso curso  = cursoRepository.findByNome(nomeCurso);

        return new Topico(titulo, nomeCurso, curso) ;
    }
}
