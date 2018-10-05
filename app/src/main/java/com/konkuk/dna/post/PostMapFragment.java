package com.konkuk.dna.post;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konkuk.dna.R;
import com.konkuk.dna.map.NMapPOIflagType;
import com.konkuk.dna.map.NMapViewerResourceProvider;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapProjection;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapCircleData;
import com.nhn.android.maps.overlay.NMapCircleStyle;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.nhn.android.mapviewer.overlay.NMapPathDataOverlay;
import com.nhn.android.mapviewer.overlay.NMapResourceProvider;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostMapFragment extends Fragment
        implements NMapView.OnMapStateChangeListener, NMapPOIdataOverlay.OnStateChangeListener
{
    private NMapContext mapContext;
    private NMapView mapView;
    private NMapController mapController;
    private NMapOverlayManager mOverlayManager;
    private NMapResourceProvider mMapViewerResourceProvider;
    private NMapView.OnMapStateChangeListener nMapstateListener;

    private static final String CLIENT_ID = "d58JXyIkF7YXEmOLrYSD"; // 애플리케이션 클라이언트 아이디 값

    public PostMapFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapContext = new NMapContext(super.getActivity());
        mapContext.onCreate();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView = (NMapView)getView().findViewById(R.id.mapView);
        mapView.setClientId(CLIENT_ID);
        mapContext.setupMapView(mapView);
    }

    @Override
    public void onStart(){
        super.onStart();
        mapContext.onStart();

        mapView.setClickable(true);
        mapView.displayZoomControls(true);
        mapView.setEnabled(true);
        mapView.setOnMapStateChangeListener(OnMapViewStateChangeListener); //리스너 등록
        mapController = mapView.getMapController();
        mMapViewerResourceProvider = new NMapViewerResourceProvider(getActivity());
        mOverlayManager = new NMapOverlayManager(getActivity(),mapView,mMapViewerResourceProvider);
        NMapProjection nMapProjection;

        OnMapViewStateChangeListener.onMapInitHandler(mapView, null);
    }


    NMapView.OnMapStateChangeListener OnMapViewStateChangeListener = new NMapView.OnMapStateChangeListener() {
        @Override
        public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {
            if (nMapError == null) {
                // TODO : GPS로 현재 위치 잡아서 지정해줘야 합니다.
                // 지도를 내위치로 초기화합니다.
                double longitude = 127.07934279999995;
                double latitude = 37.5407625;
                float radius = 500;

                NMapPOIdata poiData = new NMapPOIdata(2,mMapViewerResourceProvider);
                poiData.addPOIitem(longitude, latitude, "", NMapPOIflagType.SPOT, 0);
                poiData.endPOIdata();

                NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
                poiDataOverlay.showAllPOIdata(0);
                mapController.setZoomLevel(11);

                NMapCircleData circleData = new NMapCircleData(1);
                circleData.initCircleData();
                circleData.addCirclePoint(longitude, latitude, radius);
                NMapCircleStyle circleStyle = new NMapCircleStyle(getActivity());
                circleStyle.setStrokeColor(getResources().getColor(R.color.grayLight), 50);
                circleStyle.setStrokeWidth(0.5F);
                circleStyle.setFillColor(getResources().getColor(R.color.red), 30);
                circleData.setCircleStyle(circleStyle);
                NMapPathDataOverlay pathDataOverlay = mOverlayManager.createPathDataOverlay();
                pathDataOverlay.addCircleData(circleData);

            } else {
                Log.d("초기화 중 에러 발생", nMapError.toString());
            }
        }

        @Override
        public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {}

        @Override
        public void onMapCenterChangeFine(NMapView nMapView) {}

        @Override
        public void onZoomLevelChange(NMapView nMapView, int i) {}

        @Override
        public void onAnimationStateChange(NMapView nMapView, int i, int i1) {}
    };

    @Override
    public void onResume() {
        super.onResume();
        mapContext.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapContext.onPause();
    }
    @Override
    public void onStop() {
        mapContext.onStop();
        super.onStop();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onDestroy() {
        mapContext.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {}

    @Override
    public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {}

    @Override
    public void onMapCenterChangeFine(NMapView nMapView) {}

    @Override
    public void onZoomLevelChange(NMapView nMapView, int i) {}

    @Override
    public void onAnimationStateChange(NMapView nMapView, int i, int i1) {}

    @Override
    public void onFocusChanged(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {}

    @Override
    public void onCalloutClick(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {return;}
}
