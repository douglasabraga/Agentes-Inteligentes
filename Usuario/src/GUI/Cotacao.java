/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author 2017122760099
 */
public class Cotacao {
    private String cotacao;

    public Cotacao(String valor) {
        this.cotacao = valor;
    }

    @Override
    public String toString() {
        return cotacao;
    }
       
    
    public Object[] toArray() {
        return new Object[]{this};
            
    }
       
    public String getCotacao() {
        return cotacao;
    }

    public void setCotacao(String cotacao) {
        this.cotacao = cotacao;
    }
       
       
}
