/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquete.prueba;

import paquete.Convolucion.Convolucion;

public class Sobel_5X5 
{
    public static void main(String[] args) 
    {
        int[][] kernel = {{2, 1, 0, -1, -2},
                          {2, 1, 0, -1, -2},
                          {4, 2, 0, -2, -4},
                          {2, 1, 0, -1, -2},
                          {2, 1, 0, -1, -2}};
        boolean esSobel=true;
        Convolucion sobel3x3 = new Convolucion();
        sobel3x3.convolucion(kernel, esSobel);
    }
    
}
