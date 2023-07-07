/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquete.prueba;

import paquete.Convolucion.Convolucion;

/**
 *
 * @author usuario
 */
public class Sobel_3X3 
{
    public static void main(String[] args) 
    {
        int[][] kernel = {{-1, 0, 1},
                          {-2, 0, 2},
                          {-1, 0, 1}};
        boolean esSobel=true;
        Convolucion sobel3x3 = new Convolucion();
        sobel3x3.convolucion(kernel, esSobel);
    }
}
