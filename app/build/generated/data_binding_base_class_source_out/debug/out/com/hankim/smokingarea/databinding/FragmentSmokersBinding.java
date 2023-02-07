// Generated by view binder compiler. Do not edit!
package com.hankim.smokingarea.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.hankim.smokingarea.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentSmokersBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView ivFilter;

  @NonNull
  public final TextView ivFilter2;

  @NonNull
  public final TextView ivFilter3;

  @NonNull
  public final ImageView ivSmokingMarker;

  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final SearchView searchBar;

  @NonNull
  public final TextView tvFilter1;

  @NonNull
  public final View vFilterBar;

  private FragmentSmokersBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView ivFilter,
      @NonNull TextView ivFilter2, @NonNull TextView ivFilter3, @NonNull ImageView ivSmokingMarker,
      @NonNull RecyclerView recyclerView, @NonNull SearchView searchBar,
      @NonNull TextView tvFilter1, @NonNull View vFilterBar) {
    this.rootView = rootView;
    this.ivFilter = ivFilter;
    this.ivFilter2 = ivFilter2;
    this.ivFilter3 = ivFilter3;
    this.ivSmokingMarker = ivSmokingMarker;
    this.recyclerView = recyclerView;
    this.searchBar = searchBar;
    this.tvFilter1 = tvFilter1;
    this.vFilterBar = vFilterBar;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentSmokersBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentSmokersBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_smokers, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentSmokersBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.iv_filter;
      ImageView ivFilter = ViewBindings.findChildViewById(rootView, id);
      if (ivFilter == null) {
        break missingId;
      }

      id = R.id.iv_filter2;
      TextView ivFilter2 = ViewBindings.findChildViewById(rootView, id);
      if (ivFilter2 == null) {
        break missingId;
      }

      id = R.id.iv_filter3;
      TextView ivFilter3 = ViewBindings.findChildViewById(rootView, id);
      if (ivFilter3 == null) {
        break missingId;
      }

      id = R.id.iv_smoking_marker;
      ImageView ivSmokingMarker = ViewBindings.findChildViewById(rootView, id);
      if (ivSmokingMarker == null) {
        break missingId;
      }

      id = R.id.recyclerView;
      RecyclerView recyclerView = ViewBindings.findChildViewById(rootView, id);
      if (recyclerView == null) {
        break missingId;
      }

      id = R.id.searchBar;
      SearchView searchBar = ViewBindings.findChildViewById(rootView, id);
      if (searchBar == null) {
        break missingId;
      }

      id = R.id.tv_filter1;
      TextView tvFilter1 = ViewBindings.findChildViewById(rootView, id);
      if (tvFilter1 == null) {
        break missingId;
      }

      id = R.id.v_filter_bar;
      View vFilterBar = ViewBindings.findChildViewById(rootView, id);
      if (vFilterBar == null) {
        break missingId;
      }

      return new FragmentSmokersBinding((ConstraintLayout) rootView, ivFilter, ivFilter2, ivFilter3,
          ivSmokingMarker, recyclerView, searchBar, tvFilter1, vFilterBar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
