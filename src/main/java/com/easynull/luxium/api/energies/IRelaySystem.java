package com.easynull.luxium.api.energies;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public interface IRelaySystem {
    ArrayList<WeakReference<IRelaySystem>> children = new ArrayList<>();
    WeakReference<IRelaySystem> parent = null;
    default WeakReference<IRelaySystem> getParent(){
        return parent;
    }
    default ArrayList<WeakReference<IRelaySystem>> getChildren(){
        return children;
    }
}
