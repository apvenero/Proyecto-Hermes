package lab.comunicador;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class ModoEdicion extends ActionBarActivity implements ActionBar.TabListener {


    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    Alumno alumno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //Set actionbar title
        Bundle b = getIntent().getExtras();
        int id = b.getInt("alumno");
        System.out.println("id alumno  : " + id);
        alumno = new BD(this).getAlumno(id);
        actionBar.setTitle(alumno.toString()+ " Edicion");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);



        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });


        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
                }

/*
    Modifico esto para que solo esten las paginas del alumno
        if (!alumno.getPages().isEmpty()) {
            for (Integer i : alumno.getPages()) {
                actionBar.addTab(
                        actionBar.newTab()
                                .setText(mSectionsPagerAdapter.getPageTitle(i))
                                .setTabListener(this));
            }
        }
    */

    }

    public void loadBitmap(int resId, ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView, getResources());
        task.execute(resId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edicion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(ModoEdicion.this, SettingsActivity.class);
            intent.putExtra("alumno", alumno.getId());

            startActivity(intent);
        }
        if (id == R.id.modo_alumno) {
            Intent intent = new Intent(ModoEdicion.this, AlumnoActivity.class);
            intent.putExtra("alumno", alumno.getId());

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.

        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below)

                switch(position) {
                    case Constantes.EMOCIONES:
                        return PlaceholderFragment.newInstance(Constantes.EMOCIONES,alumno);
                    case Constantes.ESTABLO:
                        return PlaceholderFragment.newInstance(Constantes.ESTABLO,alumno);
                    case Constantes.NECESIDADES:
                        return PlaceholderFragment.newInstance(Constantes.NECESIDADES,alumno);
                    case Constantes.PISTA:
                        return PlaceholderFragment.newInstance(Constantes.PISTA,alumno);
                   /* case Constantes.ALUMNO:
                        return PlaceholderFragment.newInstance(Constantes.ALUMNO);*/
                }
            return PlaceholderFragment.newInstance(position,alumno);
        }

        @Override
        public int getCount() {

            //return alumno.getPages().size();
            // Show 5 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case Constantes.EMOCIONES:
                    return "emociones".toUpperCase(l);
                case Constantes.ESTABLO:
                    return "establo".toUpperCase(l);
                case Constantes.NECESIDADES:
                    return "necesidades".toUpperCase(l);
                case Constantes.PISTA:
                    return "pista".toUpperCase(l);
                /*case Constantes.ALUMNO:
                    Bundle b = getIntent().getExtras();
                    return alumno.toString().toUpperCase(l);*/
            }
            return null;
        }
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        GridView gridView;
        private int anchoColumna;
        private static int page;
        Alumno alumno;
        private GridViewImageEdicionAdapter adapter;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber,Alumno alumno) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            fragment.alumno=alumno;
            return fragment;

        }

        public PlaceholderFragment() {
        }


        // Metodos para la grilla
        private void inicializarGrilla(int cantidadColumnas, int paddingGrilla) {

            anchoColumna = (int) ((this.getScreenWidth() - ((cantidadColumnas + 1) * paddingGrilla)) /cantidadColumnas);
            gridView.setNumColumns(cantidadColumnas);
            gridView.setColumnWidth(anchoColumna);
            gridView.setStretchMode(GridView.NO_STRETCH);
            gridView.setPadding( paddingGrilla,  paddingGrilla,  paddingGrilla, paddingGrilla);
            gridView.setHorizontalSpacing(paddingGrilla);
            gridView.setVerticalSpacing(paddingGrilla);

            /*
            * (400 - 5* 10) / 4
            *
            * */
        }

        public int getScreenWidth() {

            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width=dm.widthPixels;

            return width;
        }

        // fin de metodos para grilla ---

        // Crea el fragment
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            List<Integer> imagenes=null;
            View rootView = inflater.inflate(R.layout.fragment_edicion, container, false);
            gridView = (GridView) rootView.findViewById(R.id.gridViewImgs);
            //gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
            inicializarGrilla(Constantes.CANTIDAD_COLUMNAS, Constantes.PADDING_GRILLA);



           /* gridView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }

                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                }

            });*/

            imagenes = new ImageLoader(getActivity().getBaseContext()).getImagenes(this.getArguments().getInt(ARG_SECTION_NUMBER),alumno);

            adapter = new GridViewImageEdicionAdapter(getActivity(), imagenes, anchoColumna);
            adapter.setAlumno(alumno);

            gridView.setAdapter(adapter);

            //gridView.setLongClickable(true);

            final Integer section = this.getArguments().getInt(ARG_SECTION_NUMBER);

            gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    ImageView item = (ImageView) view;

                    BD database = new BD(view.getContext());

                    String nombre_imagen = getResources().getResourceEntryName((Integer) parent.getAdapter().getItem((int) id));

                    //System.out.println("id : "+ parent.getAdapter().getItem((int) id));
                    // imprime el id buscando por nombre
                    //System.out.println(getResources().getIdentifier(nombre_imagen, "drawable", "lab.comunicador"));
                    //Así recupero la imagen a traves del nombre!!

                    if (alumno.getPictogramas().contains(nombre_imagen)) {
                        item.setColorFilter(null);
                        alumno.deletePictograma(nombre_imagen);
                        database.editarAlumno(alumno);
                        System.out.println("Se eliminó el pictograma del alumno");
                    } else {

                        item.setColorFilter(Color.parseColor("#FF9AD082"), PorterDuff.Mode.LIGHTEN);
                        alumno.addPictograma(nombre_imagen);
                        database.editarAlumno(alumno);
                        System.out.println("Se agrego el pictograma al alumno");
                    }

                    return true;
                }
            });


            return rootView;
        }
    }
    public void onBackPressed() {
        Intent intent = new Intent(ModoEdicion.this, AlumnoActivity.class);
        intent.putExtra("alumno", alumno.getId());
        startActivity(intent);
    }
}
