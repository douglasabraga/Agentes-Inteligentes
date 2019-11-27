/*
 *  Baseado no artigo: Tomadas de Decisão com Ferramentas da Lógica
 *                     Paraconsistente Anotada.
 *  
 *     apresentado no: XXIII Encontro Nac. de Eng. de Produção
 *                     (Ouro Preto - MG - 21 a 24 de Outubro de 2003)
 *
*/
package investidor;

public class Neuronio {
    private String avaliacao;
    private double resultadoFinal;
    private double evidenciaFavoravel;
    private double evidenciaDesfavoravel;
    private double grauCerteza;
    private double grauContradicao;
    private double grauInconsistencia;
    private double grauIndeterminacao;
    private double grauVerdade;
    private double grauFalsidade;
    private double nivelDeExigencia; //recomendável = 0.7
    
    public Neuronio(double nivelDeExigencia){
        avaliacao = " ***** NENHUMA *****";
        grauCerteza = 0;
        grauContradicao = 0;
        grauVerdade = 0;
        grauFalsidade = 0;
        this.nivelDeExigencia = nivelDeExigencia;
        grauInconsistencia = 0;
        grauIndeterminacao = 0;
    }
    
    public void decide(double evidFav, double evidDesf){
        evidenciaFavoravel = evidFav;
        evidenciaDesfavoravel = evidDesf;
        
        grauContradicao = evidFav + evidDesf - 1;
        
        if((evidFav + evidDesf) >= 1){
            //Inconsistência: crença(evidFav) e descrença(evidDesf) totais.
            grauInconsistencia = grauContradicao;
            grauIndeterminacao = 0;
        }//if
        
        if((evidFav + evidDesf) < 1){
            //Indeterminação: ausência de crença(evidFav) e descrença (evidDesf).
            grauIndeterminacao = grauContradicao;
            grauInconsistencia = 0;
        }//if
        
        grauCerteza = evidFav - evidDesf;
        
        if(evidFav >= evidDesf){
           grauVerdade = grauCerteza;
           grauFalsidade = 0;
        } else {
           grauFalsidade = grauCerteza;
           grauVerdade = 0;
        }//if
        
        if(grauCerteza >= nivelDeExigencia){
            avaliacao = "Tendência de ALTA";
            resultadoFinal = 1;
        } else if (grauCerteza <= (nivelDeExigencia * -1)){
            avaliacao = "Tendência de BAIXA";
            resultadoFinal = 0;
        } else if ((grauCerteza > (nivelDeExigencia * -1)) && (grauCerteza < nivelDeExigencia)){
            avaliacao = "NÃO CONCLUSIVO";
            resultadoFinal = 0.5;
        }
    }
    
    public String resumo(){
        String s = "+==============================+\nDECISOR:\n\nNível de Exigência: "+nivelDeExigencia+"\n\n";
        s = s + "      Evidência Favorável (ou grau de crença): "+evidenciaFavoravel+"\n";
        s = s + "Evidência Desfavorável (ou grau de descrença): "+evidenciaDesfavoravel+"\n\n";
        s = s + "Grau de Certeza: "+grauCerteza+"     Grau de Contradição: "+grauContradicao+"\n\n";
        s = s + "Grau de Inconsistência: "+grauInconsistencia+"     Grau de Indeterminação: "+grauIndeterminacao+"\n\n";
        s = s + "Grau de Verdade: "+grauVerdade+"     Grau de Falsidade: "+grauFalsidade+"\n\n";
        s = s + "RESULTADO FINAL: "+resultadoFinal+"   ("+avaliacao+").\n";
        s = s + "+==============================+\n\n";
        return s;
    }
    
    public double getResultadoFinal(){
        return resultadoFinal;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public double getEvidenciaFavoravel() {
        return evidenciaFavoravel;
    }

    public double getEvidenciaDesfavoravel() {
        return evidenciaDesfavoravel;
    }

    public double getGrauCerteza() {
        return grauCerteza;
    }

    public double getGrauContradicao() {
        return grauContradicao;
    }

    public double getGrauInconsistencia() {
        return grauInconsistencia;
    }

    public double getGrauIndeterminacao() {
        return grauIndeterminacao;
    }

    public double getGrauVerdade() {
        return grauVerdade;
    }

    public double getGrauFalsidade() {
        return grauFalsidade;
    }
    
    
}
