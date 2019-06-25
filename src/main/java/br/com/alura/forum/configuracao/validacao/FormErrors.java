package br.com.alura.forum.configuracao.validacao;

public class FormErrors {
    private String campo;
    private String erro;

    public FormErrors(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }
}
