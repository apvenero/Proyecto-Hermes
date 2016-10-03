package lab.comunicador;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ignacio on 22/02/2016.
 */
public class GridViewImageEdicionAdapter extends BaseAdapter  {

    private Activity _activity;
    private List<Integer> listaIdImagenes = new ArrayList<Integer>();
    private int imageWidth;
    private Alumno alumno;


    public GridViewImageEdicionAdapter(Activity activity, List<Integer> listaIdImagenes,
                                int imageWidth) {
        this._activity = activity;
        this.listaIdImagenes = listaIdImagenes;
        this.imageWidth = imageWidth;

    }

    @Override
    public int getCount() {
        return this.listaIdImagenes.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listaIdImagenes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;

       // if (convertView == null) {
            imageView = new ImageView(_activity);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));

       // } else {
        //    imageView = (ImageView) convertView;
        //}


        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(imageWidth, imageWidth));
        imageView.setLongClickable(true);


        //imageView.setImageDrawable(_activity.getResources().getDrawable(this.listaIdImagenes.get(position)));
        ((ModoEdicion) _activity).loadBitmap(this.listaIdImagenes.get(position), imageView);
        for (String s : alumno.getPictogramas()){
            if (_activity.getResources().getIdentifier(s, "drawable", "lab.comunicador") == this.listaIdImagenes.get(position)) {

                imageView.setColorFilter(Color.parseColor("#FF9AD082"), PorterDuff.Mode.LIGHTEN);
            }

        }


        return imageView;
    }

    public void setAlumno(Alumno alumno){
        this.alumno=alumno;
    }

}
