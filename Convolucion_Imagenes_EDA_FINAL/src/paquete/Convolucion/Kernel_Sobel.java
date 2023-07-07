/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquete.Convolucion;

public interface Kernel_Sobel 
{
    int[][] sobel3x3_Y = {{1, 2, 1},
                          {0, 0, 0},
                          {-1, -2, -1}};

    int[][] sobel5x5_Y = {{2, 2, 4, 2, 2},
                          {1, 1, 2, 1, 1},
                          {0, 0, 0, 0, 0},
                          {-1, -1, -2, -1, -1},
                          {-2, -2, -4, -2, -2}}; 
}
