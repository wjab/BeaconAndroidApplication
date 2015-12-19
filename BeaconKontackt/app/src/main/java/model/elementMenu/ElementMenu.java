package model.elementMenu;

/**
 * Created by Jairo on 16/12/2015.
 */
public class ElementMenu {
    public String elemento;
    public int Imagen;
    public ElementMenu( String elemento,int Imagen) {

        this.Imagen = Imagen;
        this.elemento = elemento;
    }
    public String getElemento() {
        return elemento;
    }

    public void setElemento(String elemento) {
        this.elemento = elemento;
    }

    public int getImagen() {
        return Imagen;
    }

    public void setImagen(int imagen) {
        Imagen = imagen;
    }
}
