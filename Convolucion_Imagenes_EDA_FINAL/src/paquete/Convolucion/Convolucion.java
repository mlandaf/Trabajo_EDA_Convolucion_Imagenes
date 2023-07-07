/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquete.Convolucion;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Convolucion implements Kernel_Sobel
{
    public Convolucion() 
    {
    }
    
   public void convolucion(int[][] kernel, boolean esSobel) 
   {
       /*INICIALIZA ESTA VARIABLE PARA KERNELS SOBEL*/
       BufferedImage edgesImage = null;
       
       /*LECTURA DE IMAGEN*/
       BufferedImage img = lecturaImagen();
       
       /*SI ES SOBEL CONVIERTE LA IMAGEN A ESCALA DE GRISES*/
       if(esSobel && kernel.length == 5)
       {
           /*CONVERTIR ESCALA DE GRISES*/
           img = convertToGrayscale(img);
           edgesImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
       }
       else if (esSobel && kernel.length == 3)
       {
           edgesImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
       }
    
       /*MITAD DE KERNEL, SIRVE PARA HACER EL RECORRIDO DE LOS PIXELES VECINOS*/
       int mitadKernel = kernel.length / 2;
    
       /*ALTO Y ANCHO DE LA IMAGEN*/
       int altoImagen = img.getHeight();
       int anchoImagen = img.getWidth();
    
       /*SE INCIALIZA LA VARIABLE PROMEDIO KERNEL, ESTA SE USA PARA LOS BLUR*/
       int promedioKernel = 0;
       
       /*SI EL KERNEL NO ES SOBEL, CALCULA CUAL ES EL VALOR CON EL QUE SE DIVIDE AL FINAL*/
       if (!esSobel) 
       {
           promedioKernel = isBox_isGaussian(kernel);
       }
    
       /*RECORRIDO DE LA IMAGEN PERO SOLO EL CENTRO*/
       for (int y = mitadKernel; y < altoImagen - mitadKernel; y++) 
       {
           for (int x = mitadKernel; x < anchoImagen - mitadKernel; x++) 
           {
               /*SE INICIALIZAN TANTO LAS VARIABLES PARA RGB COMO PARA SOBEL*/
               int sumaRed = 0;
               int sumaBlue = 0;
               int sumaGreen = 0;
               int sumX = 0;
               int sumY = 0;

               /*RECORRER EL KERNEL Y RECORRE LOS PIXELES VECINOS*/
               for (int i = -mitadKernel; i <= mitadKernel; i++) 
               {
                   for (int j = -mitadKernel; j <= mitadKernel; j++) 
                   {
                       /*RECORRE LOS PIXELES VECINOS*/
                       int pixel = img.getRGB(x + j, y + i);
                       
                       /*SI ES SOBEL REALIZA LA CONVOLUCION PARA ESTE TIPO DE KERNELS*/
                       if(esSobel)
                       {
                           /*SE INICIALIZA EL KERNEL Y*/
                           int[][] kernelY = null;
                           /*SI EL KERNEL ES 3X3 O 5X5 SE LE ASIGNA LA CONSTANTE QUE LE CORRESPONDE QUE SE ENCUENTRA EN LA INTERFAZ*/
                           if(kernel.length==3)
                           {
                               kernelY = sobel3x3_Y;
                           }
                           else
                           {
                               kernelY = sobel5x5_Y;
                           }
                           int gray = (pixel & 0xFF);
                           sumX += gray * kernel[j + mitadKernel][i + mitadKernel];
                           sumY += gray * kernelY[j + mitadKernel][i + mitadKernel];
                       }
                       /*SI NO ES SOBEL, SE REALIZA ESTA CONVOLUCION*/
                       else
                       { 
                           /*CREA UN OBJETO COLOR DEL VALOR DEL PIXEL*/
                           Color color = new Color(pixel, true);

                           /*RECUPERAR LOS VALORS RGB*/
                           int red = color.getRed();
                           int blue = color.getBlue();
                           int green = color.getGreen();

                           /*MULTIPLICANDO LOS COLORES POR EL KERNEL Y ALMACENANDOLOS*/
                           sumaRed = sumaRed + red * kernel[i + mitadKernel][j + mitadKernel];
                           sumaBlue = sumaBlue + blue * kernel[i + mitadKernel][j + mitadKernel];
                           sumaGreen = sumaGreen + green * kernel[i + mitadKernel][j + mitadKernel];
                       }
                   }
               }
        
               /*SI ES SOBEL SE REALIZA LA CONVOLUCION Y SE TRABAJA CON LA VARIABLE EDGES IMAGE*/ 
               if (esSobel) 
               {
                   int gradient = (int) Math.sqrt((sumX * sumX) + (sumY * sumY));
                   gradient = Math.min(gradient, 255);
                   gradient = Math.max(gradient, 0);
                   Color edgeColor = new Color(gradient, gradient, gradient);
                   edgesImage.setRGB(x, y, edgeColor.getRGB());
               }
               /*SI NO ES SOBEL SE REALIZA LA DIVISION Y SE TRABAJA CON LA VARIABLE IMG*/
               else 
               {
                   /*SACANDO EL PROMEDIO DE LAS SUMAS, DIVIENDO ENTRE LA VARIABLE PROMEDIOKERNEL*/
                   int nuevoRed = sumaRed / promedioKernel;
                   int nuevoBlue = sumaBlue / promedioKernel;
                   int nuevoGreen = sumaGreen / promedioKernel;

                   /*ASEGURARSE DE QUE LOS VALORES ESTÁN EN EL RANGO DE 0 A 255*/
                   nuevoRed = Math.min(255, Math.max(0, nuevoRed));
                   nuevoBlue = Math.min(255, Math.max(0, nuevoBlue));
                   nuevoGreen = Math.min(255, Math.max(0, nuevoGreen));

                   /*SE GENERA UN NUEVO COLOR CON LOS VALORES DE NUEVORED, NUEVOGREEN, NUEVOBLUE*/
                   Color nuevoColor = new Color(nuevoRed, nuevoGreen, nuevoBlue);

                   /*SE ACTUALIZA EL VALOR EN LA POSICIÓN XY CON NUEVOCOLOR*/
                   img.setRGB(x, y, nuevoColor.getRGB());
               }
           }
       }
       /*SI ES SOBEL SE GUARDA LA IMAGEN CON LA VARIABLE EDGESIMAGE*/
       if(esSobel)
       {
           guardarImagenModificada(edgesImage);
       }
       /*SI NO ES SOBEL SE GUARDA LA IMAGEN CON LA VARIABLE IMG*/
       else
       {
           guardarImagenModificada(img);
       }
   }

   /*FUNCION LECTURA DE LA IMAGEN*/
   public BufferedImage lecturaImagen() 
   {
       /*LECTURA DE IMAGEN*/
       System.out.println("Iniciando...");
       File file = null;
       BufferedImage img = null;
       try 
       {
           /*UBICACION IMAGEN*/
           file = new File("C:\\Users\\usuario\\Desktop\\ULIMA\\CICLO V\\Estructuras de Datos y Algoritmos\\Trabajo Convolucion De Imagenes Version Final\\IMAGENES\\input.jpg");

           /*GUARDA LA IMAGEN EN LA VARIABLE IMG*/
           img = ImageIO.read(file);
       } 
       catch (IOException e) 
       {
           System.out.println(e);
       }
       return img;
    }
   
   /*FUNCION GUARDAR IMAGEN*/
   public void guardarImagenModificada(BufferedImage img) 
   {
       /*GUARDANDO LA IMAGEN MODIFICADA*/
       try 
       {
           /*SE CREA UNA INSTANCIA DE TIPO FILE CON EL NOMBRE Y UBICACIÓN DEL ARCHIVO MODIFICADO*/
           File file = new File("C:\\Users\\usuario\\Desktop\\Trabajo Convolucion Imagenes\\output.jpg");

           /*SE GUARDA LA IMAGEN MODIFICADA*/
           ImageIO.write(img, "jpg", file);

           /*MENSAJE DE GUARDADO*/
           System.out.println("Se guardó exitosamente");
       } 
       catch (IOException e) 
       {
           System.out.println(e);  
       }
   }
    
   /*PARA KERNELS SOBREL*/
   public static BufferedImage convertToGrayscale(BufferedImage image) 
   {
     BufferedImage grayscaleImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
     grayscaleImage.getGraphics().drawImage(image, 0, 0, null);
     return grayscaleImage;
   }
   
   /*PARA SABER SI EL KERNEL QUE NO ES UN SOBEL ES UN GAUSSIAN O UN BOX*/
   public int isBox_isGaussian(int[][] kernel)
   {
       if(kernel[0][1]== 2)
       {
           return 16;
       }
       else if(kernel[0][1]== 4)
       {
           return 256;
       }
       else
       {
           return kernel.length * kernel.length;
       }
   }
}