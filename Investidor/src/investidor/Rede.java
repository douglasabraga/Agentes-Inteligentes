
package investidor;

public class Rede {
   private double camada0[];
   private double camada1[];
   private double camada2[];
   private Neuronio decisor;
   
   public Rede(){
       camada0 = new double[6];
       camada1 = new double[3];
       camada2 = new double[2];
       decisor = new Neuronio(0.4);    
   }
   
   public double submeteEntrada(double a1, double a2, double a3, double a4, double a5, double a6){
       camada0[0] = a1;
       camada0[1] = a2;
       camada0[2] = a3;
       camada0[3] = a4;
       camada0[4] = a5;
       camada0[5] = a6;
       
       //Aplicando Operador OR (MAXIMIZE) na Camada0 para gerar a Camada1:
       if(camada0[0] > camada0[1]){
           camada1[0] = camada0[0];
       } else {
           camada1[0] = camada0[1];
       }//if
       
       if(camada0[2] > camada0[3]){
           camada1[1] = camada0[2];
       } else {
           camada1[1] = camada0[3];
       }//if

       if(camada0[4] > camada0[5]){
           camada1[2] = camada0[4];
       } else {
           camada1[2] = camada0[5];
       }//if
       
        //Aplicando Operador AND (MINIMIZE) na Camada1 para gerar a Camada2:
        if(camada1[0] > camada1[1]){
            camada2[0] = camada1[1];
        } else {
            camada2[0] = camada1[0];
        }//if
        
        camada2[1] = (1 - camada1[2]);
        
        //Utilizando a Lógica Paraconsistente Anotada de 2 Valores
        decisor.decide(camada2[0], camada2[1]);
        return decisor.getResultadoFinal();
        
   }
   
   public String resumo(){
       String s = "ENTRADAS da REDE NEURAL PARACONSISTENTE:\n\n";
       for(int i = 0; i < 6; i++){
           s = s + "    " +camada0[i];
       }//for
       s = s + "\n\n";

       for(int i = 0; i < 3; i++){
           s = s + "    " +camada1[i];
       }//for
       s = s + "\n\n    "+camada2[0]+"    "+camada2[1]+"\n\n"+decisor.resumo();
       return s;
   }

    public Neuronio getDecisor() {
        return decisor;
    }
   
   
}
