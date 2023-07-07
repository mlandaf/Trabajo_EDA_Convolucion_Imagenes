/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquete.prueba;

import paquete.Convolucion.Convolucion;

public class Gaussian_Blur3X3 
{
    public static void main(String[] args) 
    { 
        int[][] kernel = {{ 1 , 2 , 1 },
                           { 2 , 4 , 2 },
                           { 1 , 2 , 1 }};
        Convolucion gaussian3 = new Convolucion();
        gaussian3.convolucion(kernel, false);
    }
}
