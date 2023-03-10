// Generated by view binder compiler. Do not edit!
package com.hankim.smokingarea.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hankim.smokingarea.R;
import com.naver.maps.map.MapView;
import com.naver.maps.map.widget.LocationButtonView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentHomeBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final FloatingActionButton addFloatingButton;

  @NonNull
  public final LocationButtonView btCurrentLocation;

  @NonNull
  public final ViewPager2 homeViewPager;

  @NonNull
  public final MapView mapView;

  private FragmentHomeBinding(@NonNull ConstraintLayout rootView,
      @NonNull FloatingActionButton addFloatingButton,
      @NonNull LocationButtonView btCurrentLocation, @NonNull ViewPager2 homeViewPager,
      @NonNull MapView mapView) {
    this.rootView = rootView;
    this.addFloatingButton = addFloatingButton;
    this.btCurrentLocation = btCurrentLocation;
    this.homeViewPager = homeViewPager;
    this.mapView = mapView;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_home, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentHomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.addFloatingButton;
      FloatingActionButton addFloatingButton = ViewBindings.findChildViewById(rootView, id);
      if (addFloatingButton == null) {
        break missingId;
      }

      id = R.id.bt_CurrentLocation;
      LocationButtonView btCurrentLocation = ViewBindings.findChildViewById(rootView, id);
      if (btCurrentLocation == null) {
        break missingId;
      }

      id = R.id.homeViewPager;
      ViewPager2 homeViewPager = ViewBindings.findChildViewById(rootView, id);
      if (homeViewPager == null) {
        break missingId;
      }

      id = R.id.mapView;
      MapView mapView = ViewBindings.findChildViewById(rootView, id);
      if (mapView == null) {
        break missingId;
      }

      return new FragmentHomeBinding((ConstraintLayout) rootView, addFloatingButton,
          btCurrentLocation, homeViewPager, mapView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
