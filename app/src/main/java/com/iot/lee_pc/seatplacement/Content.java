package com.iot.lee_pc.seatplacement;

import java.io.Serializable;

public class Content implements Serializable{
    String totalseat;
    String totalman;

    public Content(){ }

    public String getTotalseat()
    {
        return totalseat;
    }

    public void setTotalseat(String totalseat)
    {
        this.totalseat = totalseat;
    }

    public String getTotalman()
    {
        return totalman;
    }

    public void setTotalman(String totalman)
    {
        this.totalman = totalman;
    }

    public Content(String _totalseat, String _totalman) {
        this.totalseat = _totalseat;
        this.totalman = _totalman;
    }
}
