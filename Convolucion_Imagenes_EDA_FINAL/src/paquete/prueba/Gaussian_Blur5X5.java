/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquete.prueba;

import paquete.Convolucion.Convolucion;

public class Gaussian_Blur5X5 
{
    public static void main(String[] args) 
    {
        int[][] kernel = {{ 1 , 4 , 6 , 4 , 1 },
                          { 4 , 16 , 24 , 16 , 4 },
                          { 6 , 24 , 36 , 24 , 6 },
                          { 4 , 16 , 24 , 16 , 14 },
                          { 1 , 4 , 6 , 4 , 1 }};
    
        Convolucion gaussian5 = new Convolucion();
        gaussian5.convolucion(kernel, false);
    }
}
