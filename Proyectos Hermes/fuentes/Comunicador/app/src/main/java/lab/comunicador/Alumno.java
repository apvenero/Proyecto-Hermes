package lab.comunicador;

import java.util.ArrayList;

/**
 * Created by Ignacio on 12/12/2015.
 */
public class Alumno {
    private String nombre;
    private String apellido;
    private String sexo;
    private String tamanio;
    private ArrayList<Integer> pages;
    private int id;
    private ArrayList<String> pictogramas;


    public Alumno(){}
    public Alumno(String nombre, String apellido, String sexo, String tamanio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.tamanio = tamanio;
        this.pages = new ArrayList<Integer>();
        this.pictogramas = new ArrayList<String>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public ArrayList<Integer> getPages() {
        return pages;
    }

    public void setPages(ArrayList<Integer> pages) {
        this.pages = pages;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return this.getApellido()+","+this.getNombre();
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getPictogramas() {
        return pictogramas;
    }

    public void setPictogramas(ArrayList<String> pictogramas) {
        this.pictogramas = pictogramas;
    }

    public void addPictograma (String pictograma){
        if (!pictogramas.contains(pictograma)){
            pictogramas.add(pictograma);}
    }

    public void deletePictograma(String pictograma){
        if (pictogramas.contains(pictograma)){
            pictogramas.remove(pictograma);
        }
    }

}
