package com.example.mo.shareyousport;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowCloseListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;
import java.util.Vector;


/**
 * Created by Max on 13/03/2016.
 */


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnInfoWindowClickListener, LocationListener {
    private SportEventGroup userEvents = new SportEventGroup();

    //mettre en place en methode pour récupérer tous les évenements en bdd ici

    private GoogleMap mMap;

    private LocationManager locationManager;

    private static final long MIN_TIME = 400;

    private static final float MIN_DISTANCE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Utilisé pour récupérer la position de l'utilisateur
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
       // SportEvent intermUserEvent;
        mMap = googleMap;
        //Création d'un evenement test
        //userEvents.addSports(new SportEvent(0, "Stade de Deuil", "6 Rue Jean Bouin, 95170","Deuil-la-Barre" , new LatLng(48.968628, 2.3222098999999616), 11, 1, true,"Basket"));
//Communication php
        /*


        /* Test inutilisé désormais
        mMap.setInfoWindowAdapter(new MapsWindowInter(getLayoutInflater()));
         Add a marker in Sydney and move the camera
        LatLng SYDNEY = new LatLng(-34, 151);
        Marker sydney = mMap.addMarker(new MarkerOptions().position(SYDNEY);
        */


        //Méthode utilisé pour parcourir l'ensemble des évenements existants
        /*Iterator<SportEvent> it = userEvents.iterator();
        while (it.hasNext()) {

            intermUserEvent = it.next();

            addMarker(mMap, intermUserEvent.getCoord().latitude, intermUserEvent.getCoord().longitude);
            // sydney.showInfoWindow();
        }*/


        /* Auparavant utilisé pour adapter la taille de l'infoWindow
        mMap.setInfoWindowAdapter(new MapsWindowInter(getLayoutInflater()));
        */
        // Setting a custom info window adapter for the google map
        googleMap.setInfoWindowAdapter(new InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker arg0) {

                // Getting view from the layout file info_window_layout
                View v = getLayoutInflater().inflate(R.layout.info_window_inter, null);

                // Getting the position from the marker
                LatLng latLng = arg0.getPosition();

                SportEvent markerEvent = userEvents.findSportByCoord(latLng);

                //AJOUTER SANS TEST
                TextView tvSports = ((TextView)v.findViewById(R.id.sport_practice));
                tvSports.setText(markerEvent.getTypeSport());
                //AJOUTER SANS TEST

                TextView tvParticipants = ((TextView)v.findViewById(R.id.participants_number));
                tvParticipants.setText(markerEvent.getPlayerIn()+"/"+markerEvent.getPlayersNeeded());

                TextView tvEquipments = ((TextView)v.findViewById(R.id.equipments));
                tvEquipments.setText("Equipements à déterminer via script");

                TextView tvGps = ((TextView)v.findViewById(R.id.gps_coordonate));
                tvGps.setText("Coordonnée GPS: "+latLng.latitude+","+latLng.longitude);

                TextView tvName = ((TextView)v.findViewById(R.id.localName));
                tvName.setText(markerEvent.getName());

                TextView tvAddress = ((TextView)v.findViewById(R.id.address));
                tvAddress.setText(markerEvent.getAdress());

                TextView tvCity = ((TextView)v.findViewById(R.id.city));
                tvCity.setText(markerEvent.getCity());

                /*à mettre en place quand la récuération de la date et heure se fera au bon format
                TextView tvHour = ((TextView)v.findViewById(R.id.hour));
                tvHour.setText(markerEvent.getHour());*/

                // Returning the view containing InfoWindow contents
                return v;

            }
        });

        PostClassMapJoin requetteHttp = new PostClassMapJoin();
        requetteHttp.execute(userEvents);

        mMap.setOnInfoWindowClickListener(this);


        //mMap.moveCamera(CameraUpdateFactory.newLatLng(SYDNEY));
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        LatLng currentPos = marker.getPosition();

        //SportEvent currentMarker = userEvents.findSportByCoord(currentPos);


        Toast.makeText(getBaseContext(), marker.getTitle(), Toast.LENGTH_LONG).show();
        PostClassJoin requetteHttp2 = new PostClassJoin();
        requetteHttp2.execute(marker.getTitle());

    }


    //Methode utilisée pour créer un marker avec les informations d'un objet SportEvent
    private void addMarker(GoogleMap map,String idEvent, double lat, double lon) {
        map.addMarker(new MarkerOptions().title(new String(idEvent)).position(new LatLng(lat, lon)).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
    }

    //Methode pour récupérer la position de l'utilisateur au cas où elle changerait pendant l'utilisation
    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        mMap.animateCamera(cameraUpdate);
        //locationManager.removeUpdates(this);
    }


    //Les 3 méthodes qui suivent sont "redéfinis" pour permettre d'"implements" LocationListener
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }



    private class PostClassMapJoin extends AsyncTask<SportEventGroup, SportEventGroup, SportEventGroup> {
        final ProgressDialog progressDialog = new ProgressDialog(MapsActivity.this, R.style.AppTheme_Dark_Dialog);
        SportEventGroup myEvents;
        @Override //Cette méthode s'execute en premier, elle ouvre une simple boite de dialogue
        protected void onPreExecute() {
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        protected SportEventGroup doInBackground(SportEventGroup... params) {
            myEvents = params[0];
            ConnectivityManager check = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] info = check.getAllNetworkInfo();
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {

                    SportFieldGroup testField = new SportFieldGroup();
                    String result;
                    try {


                        System.out.println("ON EST DAAAAAAANNNNNNNNNNNSSSSSSSSSS LLLLLLEEEEEEEE TTTTTTTRRRRRRRRYYYYYYY");

                        URL url = new URL("http://humanapp.assos.efrei.fr/shareyoursport/script/shareyoursportcontroller.php");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setConnectTimeout(3000);
                        connection.setRequestMethod("POST");
                        connection.setDoInput(true);
                        connection.setDoOutput(true);

                        /// Mise en place des differents parametre necessaire ////

                        Uri.Builder builder = new Uri.Builder()
                                .appendQueryParameter("OBJET", "tousEvent");

                        String query = builder.build().getEncodedQuery();

                        OutputStream os = connection.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(
                                new OutputStreamWriter(os, "UTF-8"));
                        writer.write(query);
                        writer.flush();
                        writer.close();
                        os.close();

                        connection.connect();


                        InputStream inputStream = connection.getInputStream();

                        // InputStreamOperations est une classe complémentaire:
                        //Elle contient une méthode InputStreamToString.

                        result = InputStreamOperations.InputStreamToString(inputStream);

                /*Reader reader = new InputStreamReader(connection.getInputStream(), "UTF-8");
                char[] buffer = new char[1024];
                reader.read(buffer);  /// On recupere ce que nous a envoyés le fichier php
                result = new String(buffer);
                reader.close();*/

                        try{
                            System.out.println("ON EST COOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOONNNNNNNNNEEEEEEEECCCCCCCCCTTTTTTTTEEEEEEEE");


                            //JSONArray array = new JSONArray(result);



                            // On récupère le JSON complet
                            JSONObject jsonObject = new JSONObject(result);
                            System.out.println(jsonObject.toString());
                            // On récupère le tableau d'objets qui nous concernent
                            JSONArray array = jsonObject.getJSONArray("evenement");
                            //JSONArray array = new JSONArray(jsonObject.getString("terrain"));


                            LatLng coordInter;
                            // Pour tous les objets on récupère les infos
                            for (int j = 0; j < array.length(); j++) {
                                System.out.println("ON EST DANS LA BOUUUUUUUUUUUUUUUUUCCLLLLLLLLLEEEEEEEEEE");

                                // On récupère un objet JSON du tableau
                                //JSONObject obj = array.getJSONObject(j);
                                JSONObject obj = new JSONObject(array.getString(j));
                                SportEvent events = new SportEvent();
                                // On fait le lien Terrain - Objet JSON

                                events.setId(obj.getInt("id"));
                                events.setName(obj.getString("name"));
                                events.setAdress(obj.getString("adress"));
                                events.setCity(obj.getString("city"));
                                coordInter = new LatLng(obj.getDouble("latitude"), obj.getDouble("longitude"));
                                events.setCoord(coordInter);
                                events.setPlayersNeeded(obj.getInt("playersNeeded"));
                                events.setPlayerIn(obj.getInt("playersIn"));
                                events.setEquipment(obj.getBoolean("equipment"));
                                events.setTypeSport(obj.getString("typeSport"));
                                // On ajoute la personne à la liste
                                myEvents.addSports(events);



                            }


                        } catch (JSONException e) {
                            Log.e("log_tag", "Error parsing data " + e.toString());

                        }
                    }
                    catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            return myEvents;
        }

        protected void onPostExecute(SportEventGroup th) {
            progressDialog.dismiss();
            SportEvent intermUserEvent;
            Toast.makeText(getBaseContext(), "The Job is done", Toast.LENGTH_LONG).show();
            Iterator<SportEvent> it = th.iterator();
            while (it.hasNext()) {

                intermUserEvent = it.next();

                addMarker(mMap, Integer.toString(intermUserEvent.getId()), intermUserEvent.getCoord().latitude, intermUserEvent.getCoord().longitude);
                // sydney.showInfoWindow();
            }
        }


    }



    private class PostClassJoin extends AsyncTask<String, Void, String> {
        final ProgressDialog progressDialog = new ProgressDialog(MapsActivity.this, R.style.AppTheme_Dark_Dialog);
        String eventToJoin;
        @Override //Cette méthode s'execute en premier, elle ouvre une simple boite de dialogue
        protected void onPreExecute() {
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        protected String doInBackground(String... strings) {

            eventToJoin = strings[0];
            ConnectivityManager check = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] info = check.getAllNetworkInfo();
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {

                    String result;
                    try {


                        System.out.println("ON EST DAAAAAAANNNNNNNNNNNSSSSSSSSSS LLLLLLEEEEEEEE TTTTTTTRRRRRRRRYYYYYYY");

                        URL url = new URL("http://humanapp.assos.efrei.fr/shareyoursport/script/shareyoursportcontroller.php");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setConnectTimeout(3000);
                        connection.setRequestMethod("POST");
                        connection.setDoInput(true);
                        connection.setDoOutput(true);

                        /// Mise en place des differents parametre necessaire ////

                        Uri.Builder builder = new Uri.Builder()
                                .appendQueryParameter("OBJET", "joinEvent")
                                .appendQueryParameter("ID_Event", eventToJoin);

                        String query = builder.build().getEncodedQuery();

                        OutputStream os = connection.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(
                                new OutputStreamWriter(os, "UTF-8"));
                        writer.write(query);
                        writer.flush();
                        writer.close();
                        os.close();

                        connection.connect();


                        InputStream inputStream = connection.getInputStream();

                        // InputStreamOperations est une classe complémentaire:
                        //Elle contient une méthode InputStreamToString.

                        result = InputStreamOperations.InputStreamToString(inputStream);



                        try {

                            JSONObject object = new JSONObject(result);
                            connection.disconnect();
                            return object.getString("value"); // On retourne true ou false


                        } catch (JSONException e) {
                            Log.e("log_tag", "Error parsing data " + e.toString());

                        }
                    }
                    catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            return "false";
        }

        protected void onPostExecute(String th) {
            progressDialog.dismiss();
            SportEvent intermUserEvent;
            if(th.equals("true")){
                Toast.makeText(getBaseContext(), "Evenement Rejoins", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(MapsActivity.this, Interface.class);
                startActivity(myIntent);
            }
            else{
                Toast.makeText(getBaseContext(), "Evenement impossible à rejoindre", Toast.LENGTH_LONG).show();
            }



        }


    }




}
