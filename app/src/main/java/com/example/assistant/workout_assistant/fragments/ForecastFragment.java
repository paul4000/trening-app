package com.example.assistant.workout_assistant.fragments;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.activities.PlanTrainingActivity;
import com.example.assistant.workout_assistant.bo.Training;
import com.example.assistant.workout_assistant.webService.forecast.Forecast;
import com.example.assistant.workout_assistant.webService.forecast.ForecastService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastFragment extends Fragment implements LocationListener{

    Calendar calendar;
    LocationManager locationManager;
    String locationProvider;
    Location location;
    ForecastService forecastService;

    public ForecastFragment() {
    }

    public static ForecastFragment newInstance(Calendar calendar) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putSerializable("CALENDAR", calendar);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        calendar = (Calendar) getArguments().getSerializable("CALENDAR");
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        locationProvider = locationManager.getBestProvider(criteria, false);
        if(locationProvider == null) return;

        if(!locationManager.isProviderEnabled(locationProvider)){
            Toast.makeText(getActivity(), getString(R.string.enable_gps), Toast.LENGTH_LONG).show();
            return;
        }

        locationManager.requestLocationUpdates(locationProvider, 400, 1, this);
        location = locationManager.getLastKnownLocation(locationProvider);

        forecastService = new ForecastService();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        if(location != null){

            Log.d("LONG", String.valueOf(location.getLongitude()));
            Log.d("LATITUDE", String.valueOf(location.getLatitude()));

            getForecast(view, (float)location.getLatitude(), (float) location.getLongitude());

        }
        return view;
    }

    public void getForecast(View view, float lat, float lon){
        forecastService.loadForecastData(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {

                Forecast forecast = response.body();

                Forecast.DetailsBean specificWeather = getWeather(forecast);

                TextView setTemperature = (TextView) view.findViewById(R.id.setTemperature);
                setTemperature.setText(String.valueOf(specificWeather.getMain().getTemp() - 273.15));

                TextView setRaining = (TextView) view.findViewById(R.id.setRaining);
                boolean rain = specificWeather.getRain() != null;

                setRaining.setText(rain ? R.string.yes : R.string.no);
                Log.d("NIC", "trol");
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.weather_error, Toast.LENGTH_LONG).show();
                Log.d("FORECAST_FAILURE", t.getMessage());
            }
        }, lat, lon);
    }

    private Forecast.DetailsBean getWeather(Forecast forecast) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

        Iterator<Forecast.DetailsBean> detailsBeanIterator = forecast.getList().iterator();

        int i = 0;

        for(; i<forecast.getList().size(); ++i) {

            Forecast.DetailsBean detailsBean = forecast.getList().get(i);

            String date = detailsBean.getDt_txt();
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(dateFormat.parse(date));
            } catch (ParseException e) {
                throw new IllegalArgumentException();
            }

            if(c.after(calendar)) break;
        }

        if(i == forecast.getList().size()) --i;

        return forecast.getList().get(i);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onLocationChanged(Location location) {
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
