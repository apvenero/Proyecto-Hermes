package lab.comunicador;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ignacio on 19/02/2016.
 */
public class SoundsLoader {
    Context context;

    ArrayList<Integer> sonidos = new ArrayList<Integer>();

    public SoundsLoader(Context context){
        this.context = context;
    }
    public List<Integer> getSonidos(int page,Alumno alumno){

        switch(page){
            case Constantes.EMOCIONES:
                if(alumno.getSexo().equals("M"))
                    return getSonidosDeEmocionesMasc();
                else
                    return getSonidosDeEmocionesFem();
            case Constantes.ESTABLO:
                return getSonidosDeEstablo();
            case Constantes.NECESIDADES:
                    return getSonidosDeNecesidades();
            case Constantes.PISTA:
                return getSonidosDePista();
            case Constantes.ALUMNO:
                return  getSonidosDeAlumno(alumno, context);
        }
        return  new ArrayList<Integer>();
    }

    private List<Integer> getSonidosDeEmocionesFem() {
        sonidos.add(R.raw.asustada);
        sonidos.add(R.raw.triste);
        sonidos.add(R.raw.sorprendida);
        sonidos.add(R.raw.enojada);
        sonidos.add(R.raw.meduele);
        sonidos.add(R.raw.contenta);
        sonidos.add(R.raw.cansada);

        return sonidos;
    }

    private List<Integer> getSonidosDeEmocionesMasc() {
        sonidos.add(R.raw.asustado);
        sonidos.add(R.raw.triste);
        sonidos.add(R.raw.sorprendido);
        sonidos.add(R.raw.enojado);
        sonidos.add(R.raw.meduele);
        sonidos.add(R.raw.contento);
        sonidos.add(R.raw.cansado);

        return sonidos;
    }
    public List<Integer> getSonidosDeEstablo() {

        sonidos.add(R.raw.cepillo);
        sonidos.add(R.raw.limpieza);
        sonidos.add(R.raw.escarbavasos);
        sonidos.add(R.raw.montura);
        sonidos.add(R.raw.matra);
        sonidos.add(R.raw.rasquetadura);
        sonidos.add(R.raw.rasquetablanda);
        sonidos.add(R.raw.pasto);
        sonidos.add(R.raw.zanahoria);
        sonidos.add(R.raw.caballo);
        sonidos.add(R.raw.caballo);
        sonidos.add(R.raw.caballo);

        return sonidos;
    }

    public List<Integer> getSonidosDeNecesidades() {

        sonidos.add(R.raw.sed);
        sonidos.add(R.raw.banio);

        return sonidos;
    }

    public List<Integer> getSonidosDePista(){


        sonidos.add(R.raw.casco);
        sonidos.add(R.raw.chapas);
        sonidos.add(R.raw.letras);
        sonidos.add(R.raw.cubos);
        sonidos.add(R.raw.maracas);
        sonidos.add(R.raw.palos);
        sonidos.add(R.raw.pato);
        sonidos.add(R.raw.pelota);
        sonidos.add(R.raw.riendas);
        sonidos.add(R.raw.burbujas);
        sonidos.add(R.raw.aro);
        sonidos.add(R.raw.broches);
        sonidos.add(R.raw.tarima);

        return sonidos;

    }

    public ArrayList<Integer> getSonidosDeAlumno(Alumno alumno, Context context) {

        for (String s: alumno.getPictogramas()) {
            if ((s.equals("tristefem")) || (s.equals("tristemasc"))){
                sonidos.add(context.getResources().getIdentifier("triste", "raw", "lab.comunicador"));
            }
            if ((s.equals("femsed")) || (s.equals("mascsed"))){
                sonidos.add(context.getResources().getIdentifier("sed", "raw", "lab.comunicador"));
            }
            if((s.startsWith("caballo"))){
                sonidos.add(context.getResources().getIdentifier("caballo", "raw", "lab.comunicador"));
            }
            if ((s.equals("dolorido")) || (s.equals("dolorida"))){
                sonidos.add(context.getResources().getIdentifier("meduele", "raw", "lab.comunicador"));
            }
            else{

                int sound_id = context.getResources().getIdentifier(s, "raw", "lab.comunicador");
                if (sound_id!=0)
                sonidos.add(sound_id);
            }
        }

        return sonidos;
    }
}
