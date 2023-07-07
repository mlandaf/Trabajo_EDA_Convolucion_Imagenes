/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquete.prueba;

import java.util.Scanner;
import paquete.Convolucion.Convolucion;

public class BoxBlur 
{
    
    public static void main(String[] args) 
    {
        int[][] kernel = generarKernel();
        Convolucion boxBlur = new Convolucion();
        boxBlur.convolucion(kernel, false);

    }
    
    public static int[][] generarKernel()
    {
        
        System.out.println("Recuerde el tamaño del kernel tiene que ser impar");
        System.out.println("------------------------------------------------");
        System.out.println("Ingrese el tamaño del Kernel Box Blur:");
        int size;
        Scanner sc = new Scanner(System.in);
        size = sc.nextInt();
        while(size % 2 == 0 && size > 1)
        {
            System.out.println("Error, el tamaño del kernel tiene que ser impar ingrese nuevamente");
            System.out.println("------------------------------------------------------------------");
            System.out.println("Ingrese el tamaño del kernel Box Blur: ");
            size = sc.nextInt();
        }
        int[][] kernel = new int[size][size];
        for (int i = 0 ; i < size ; i++)
        {
            for(int j = 0 ; j < size ; j++)
            {
                kernel[i][j]=1;
            }
        }
        return kernel;
    }
}
