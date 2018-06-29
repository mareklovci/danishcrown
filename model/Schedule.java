package model;

import java.io.Serializable;

public class Schedule implements Serializable {

    private static final long serialVersionUID = -43279402907064814L;
    private SubOrder          subOrder;
    private Dock              dock;
    private Trailer           trailer;

    public Schedule(Dock dock, Trailer trailer, SubOrder suborder) {
        this.dock = dock;
        this.trailer = trailer;
        this.subOrder = suborder;
        trailer.addSchedule(this);
        dock.addSchedule(this);
    }

    public SubOrder getSubOrder() {
        return subOrder;
    }

    public Dock getDock() {
        return dock;
    }

    public void setDock(Dock dock) {
        this.dock = dock;
    }

    public Trailer getTrailer() {
        return trailer;
    }

    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

    public void clear() {
        subOrder.setSchedule(null);
        dock.removeSchedule(this);
        trailer.removeSchedule(this);
        subOrder = null;
        dock = null;
        trailer = null;
    }

}
