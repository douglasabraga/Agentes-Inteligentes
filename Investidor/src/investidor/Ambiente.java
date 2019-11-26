
package investidor;

import java.util.ArrayList;

public class Ambiente {
    private ArrayList cotacoes;
    private double saldoReal;
    private double saldoDolar;
    private double renda;
    private double rendimento;
    private double m5;
    private double m10;
    private double m20;
    private boolean dolarAtualElevado;
    private boolean m5MenorQueM10;
    private boolean m5MenorQueM20;
    private double avaliador1;
    private double avaliador2;
    private double avaliador3;
    private double avaliador4;
    private double avaliador5;
    private double avaliador6;
    private Rede rede;
    
    public Ambiente(){
        cotacoes = new ArrayList();
        
        cotacoes.add(0.86);
        cotacoes.add(0.91);
        cotacoes.add(0.92);
        cotacoes.add(0.93);
        cotacoes.add(0.94);
        cotacoes.add(0.95);
        cotacoes.add(0.96);
        cotacoes.add(0.97);
        cotacoes.add(0.99);
        cotacoes.add(1.10);
        cotacoes.add(1.15);
        cotacoes.add(1.20);
        cotacoes.add(1.32);
        cotacoes.add(1.47);
        cotacoes.add(1.50);
        cotacoes.add(1.56);
        cotacoes.add(1.61);
        cotacoes.add(1.66);
        cotacoes.add(1.67);
        cotacoes.add(1.68);
        cotacoes.add(1.69);
        
        saldoReal = 1000;
        saldoDolar = 1000;
        rede = new Rede();
    }
    
    public void insereCotacao(double dolar){
        cotacoes.add(dolar);
        
        int tamanho = cotacoes.size();
        double somaM5 = 0;
        double somaM10 = 0;
        double somaM20 = 0;
        double dolarAnterior = 0;
        double dolarAtual = 0;
        
        int cont = 0;
        
        for(int pos = (tamanho - 1); pos > (tamanho - 21); pos--){
            if(cont < 5){
                somaM5 = somaM5 + (double) cotacoes.get(pos);
            }
            
            if(cont < 10){
                somaM10 = somaM10 + (double) cotacoes.get(pos);
            }
            
            if(cont < 20){
                somaM20 =  somaM20 + (double) cotacoes.get(pos);
            }
            cont++;
            
            if(pos == (tamanho - 1)){
                dolarAtual = (double)cotacoes.get(pos);
            } else if (pos == (tamanho - 2)){
                dolarAnterior = (double) cotacoes.get(pos);
            }
        }//for
        
        m5 = somaM5 / 5;
        m10 = somaM10 / 10;
        m20 = somaM20 / 20;
        
        if(dolarAtual > dolarAnterior){
            dolarAtualElevado = true;
        } else {
            dolarAtualElevado = false;
        }
        
        if(m5 < m10){
            m5MenorQueM10 = true;
        } else {
            m5MenorQueM10 = false;
        }
        
        if(m5 < m20){
            m5MenorQueM20 = true;
        } else {
            m5MenorQueM20 = false;
        }
        
        avaliador1 = avaliacao1(dolarAtual, dolarAnterior);
        avaliador2 = avaliacao2(dolarAtual, dolarAnterior);
        avaliador3 = avaliacao3(dolarAtual);
        avaliador4 = avaliacao4();
        avaliador5 = avaliacao5();
        avaliador6 = avaliacao6();
        
        rede.submeteEntrada(avaliador1, avaliador2, avaliador3, avaliador4, avaliador5, avaliador6);
    }
    
    public double avaliacao1(double dolarAtual, double dolarAnterior){
        //Objetijo: avaliar probabilidade de ALTA na próxima cotação.
        //Estratégia: baseia-se na ideia de que amanhã será um reflexo de hoje.
        double variacao = dolarAtual - dolarAnterior;
        double percentual = variacao / dolarAnterior;
        
        if(dolarAtual > dolarAnterior){
            return (1 - percentual);
        } else {
            return (0.50 + percentual);
        }
    }
    
    public double avaliacao2(double dolarAtual, double dolarAnterior){
        //Objetivo: avaliar probabilidade de ALTA na próxima cotação.
        //Estratégia: baseia-se na frequência recente de altas na cotação.
        double variacao = 0;
        double atual, anterior, frequencia;
        
        int tamanho = cotacoes.size();
        double cont = 0;
        double contAlta = 0;
        
        for(int p=(tamanho -11); p < tamanho; p++){
            cont++;
            atual = (double)cotacoes.get(p);
            anterior = (double)cotacoes.get(p - 1);
            variacao = atual - anterior;
            if(variacao > 0){
                contAlta++;
            }
        }//for
        
        frequencia = (contAlta / cont);
        
        if(dolarAtual > m5){
            frequencia = frequencia + 0.05;
        } 
        
        if(dolarAtual > dolarAnterior){
            frequencia = frequencia + 0.15;
        }
        
        if(frequencia > 1){
            frequencia = 1;
        }
        
        return frequencia;
    }
    
    public double avaliacao3(double dolarAtual){
        //Objetivo: avaliar a probabilidade de ALTA na próxima cotação.
        //Estratégia: compara m5 (curto prazo) com m10 (médio prazo).
        double resultado = 0;
        
        if(m5 > m10){
            if(dolarAtual > m5){
               resultado = 1; 
            } else {
                resultado = 0.85;
            }            
        } else {
            if(dolarAtual < m5){
                resultado = 0.15;
            } else {
                resultado = 0.35;
            }
        }
        return resultado;
    }
    
    public double avaliacao4(){
        //Objetivo: determinar a probabilidade de ALTA na próxima cotação.
        //Estratégia: compara m5 com m10 e m20(Longo Prazo).
        double res = 0;
        if(m5 > m20){
            if(m5 > m10){
                res = 1.0;
            } else {
                res = 0.75;
            }
        } else {
            if (m5 > m10){
                res = 0.50;
            } else {
                res = 0.30;
            }
        }
        return res;
    }
    
    public double avaliacao5(){
        //Objetivo: determinar a probabilidade de ALTA na próxima cotação.
        //Estratégia: segue os outros (imitação).
        return ((avaliador1 + avaliador2 + avaliador3 + avaliador4)/4);
    }
    
    public double avaliacao6(){
        //Objetivo: determinar a probabilidade de ALTA na próxima cotação.
        //Estratégia: frequencia menor de altas.
        double variacao = 0;
        double atual, anterior, frequencia;
        
        int tamanho = cotacoes.size();
        double cont = 0;
        double contAlta = 0;
        
        for(int p=(tamanho -8); p < tamanho; p++){
            cont++;
            atual = (double)cotacoes.get(p);
            anterior = (double)cotacoes.get(p - 1);
            variacao = atual - anterior;
            if(variacao > 0){
                contAlta++;
            }
        }//for
        
        frequencia = (contAlta / cont);
        
        return frequencia;        

    }
    
    public String resumo(){
        String s = "     =====| AMBIENTE |=====\n\nCOTACOES do DOLAR:\n";
        int tamanho = cotacoes.size();
        for(int pos = (tamanho - 11); pos <= (tamanho - 1); pos++){
            s = s + "["+pos+"]  "+cotacoes.get(pos)+"\n";
        }//for
        s = s + "\navaliador 1: "+avaliador1+"\navaliador 2: "+avaliador2;
        s = s + "\navaliador 3: "+avaliador3+"\navaliador 4: "+avaliador4;
        s = s + "\navaliador 5: "+avaliador5+"\navaliador 6: "+avaliador6+"\n\n";
        s = s + "M5: "+m5+"     M10: "+m10+"     M20: "+m20+"\n\n";
        s = s + "M5 MENOR QUE M10?  "+m5MenorQueM10+"\nM5 MENOR QUE M20?  "+m5MenorQueM20+"\n\n";
        s = s + rede.resumo();
        return s;
    }
}
