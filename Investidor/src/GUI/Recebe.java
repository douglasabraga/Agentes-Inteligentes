/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


import investidor.Ambiente;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2017122760129
 */
public class Recebe extends Thread {
    
    private static MulticastSocket socket = null;
    private static byte[] buf = new byte[256];
    private static InetAddress group;
    public TelaRecebe telaRecebe;
    Ambiente ambiente;
    
    public Recebe(TelaRecebe telaRecebe) {
        this.telaRecebe = telaRecebe;
        this.ambiente = new Ambiente();
    }
    
    
    public void run() {
            try {
                socket = new MulticastSocket(4446);
                
                group = InetAddress.getByName("230.0.0.0");

                socket.joinGroup(group);
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);

                    socket.receive(packet);

                    String received = new String(packet.getData(), 0, packet.getLength());
                    double cotacao = Double.parseDouble(received);
                    ambiente.insereCotacao(cotacao);
                    
                    telaRecebe.setTxtAmbiente(ambiente.resumo(), cotacao);
                    
                    if ("end".equals(received)) {
                        break;
                    }
                }

                    socket.leaveGroup(group);
            } catch (IOException ex) {
                Logger.getLogger(TelaRecebe.class.getName()).log(Level.SEVERE, null, ex);
            }
            socket.close();
        }
}
