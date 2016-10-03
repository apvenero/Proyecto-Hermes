package lab.comunicador;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ignacio on 13/12/2015.
 */
public class ImageLoader {
    Context context;
    ArrayList<Integer> imagenes = new ArrayList<Integer>();

    public ImageLoader(Context context) {
        this.context = context;
    }

    public List<Integer>getImagenes(int page, Alumno alumno){

        switch(page){
            case Constantes.EMOCIONES:
                if(alumno.getSexo().equals("M"))
                    return getImagenesDeEmocionesMasc();
                else
                    return getImagenesDeEmocionesFem();
            case Constantes.ESTABLO:
                return getImagenesDeEstablo();
            case Constantes.NECESIDADES:
                if(alumno.getSexo().equals("M"))
                    return getImagenesDeNecesidadesMasc();
                else
                    return getImagenesDeNecesidadesFem();
            case Constantes.PISTA:
                return getImagenesDePista();
            case Constantes.ALUMNO:
                return getImagenesDelAlumno(alumno,context);
        }
        return  new ArrayList<Integer>();
        }



    private List<Integer> getImagenesDeEmocionesFem() {
        imagenes.add(R.drawable.asustada);
        imagenes.add(R.drawable.tristefem);
        imagenes.add(R.drawable.sorprendida);
        imagenes.add(R.drawable.enojada);
        imagenes.add(R.drawable.dolorida);
        imagenes.add(R.drawable.contenta);
        imagenes.add(R.drawable.cansada);
        return imagenes;
    }

    public List<Integer> getImagenesDeEmocionesMasc(){

        imagenes.add(R.drawable.asustado);
        imagenes.add(R.drawable.tristemasc);
        imagenes.add(R.drawable.sorprendido);
        imagenes.add(R.drawable.enojado);
        imagenes.add(R.drawable.dolorido);
        imagenes.add(R.drawable.contento);
        imagenes.add(R.drawable.cansado);
         return imagenes;
    }
    public List<Integer> getImagenesDeEstablo() {

        imagenes.add(R.drawable.cepillo);
        imagenes.add(R.drawable.limpieza);
        imagenes.add(R.drawable.escarbavasos);
        imagenes.add(R.drawable.montura);
        imagenes.add(R.drawable.matra);
        imagenes.add(R.drawable.rasquetadura);
        imagenes.add(R.drawable.rasquetablanda);
        imagenes.add(R.drawable.pasto);
        imagenes.add(R.drawable.zanahoria);
        imagenes.add(R.drawable.caballo1);
        imagenes.add(R.drawable.caballo2);
        imagenes.add(R.drawable.caballo3);

        return imagenes;
    }
    private List<Integer> getImagenesDeNecesidadesFem() {
        imagenes.add(R.drawable.femsed);
        imagenes.add(R.drawable.banio);

        return imagenes;
    }
    public List<Integer> getImagenesDeNecesidadesMasc() {

        imagenes.add(R.drawable.mascsed);
        imagenes.add(R.drawable.banio);

        return imagenes;
    }
    public List<Integer> getImagenesDePista(){


        imagenes.add(R.drawable.casco);
        imagenes.add(R.drawable.chapas);
        imagenes.add(R.drawable.letras);
        imagenes.add(R.drawable.cubos);
        imagenes.add(R.drawable.maracas);
        imagenes.add(R.drawable.palos);
        imagenes.add(R.drawable.pato);
        imagenes.add(R.drawable.pelota);
        imagenes.add(R.drawable.riendas);
        imagenes.add(R.drawable.burbujas);
        imagenes.add(R.drawable.aro);
        imagenes.add(R.drawable.broches);
        imagenes.add(R.drawable.tarima);

        return imagenes;

    }

    public List<Integer> getImagenesDelAlumno(Alumno alumno, Context context){

        for (String s: alumno.getPictogramas()) {
            imagenes.add(context.getResources().getIdentifier(s, "drawable", "lab.comunicador"));
        }
        return imagenes;
    }
    public String getCategoria(String contenido){
        String categoria="";
        int id = context.getResources().getIdentifier(contenido, "drawable", "lab.comunicador");
        if (getImagenesDeEmocionesFem().contains(id)){
            return "emociones";
        }
        if (getImagenesDeEmocionesMasc().contains(id)){
            return "emociones";
        }
        if (getImagenesDeEstablo().contains(id)){
            return "establo";
        }
        if (getImagenesDeNecesidadesFem().contains(id)){
            return "necesidades";
        }
        if (getImagenesDeNecesidadesMasc().contains(id)){
            return "necesidades";
        }
        if (getImagenesDePista().contains(id)){
            return "pista";
        }

        return categoria;
    }
}

