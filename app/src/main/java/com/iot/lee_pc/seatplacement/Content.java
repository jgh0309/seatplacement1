package com.iot.lee_pc.seatplacement;

import java.io.Serializable;

public class Content implements Serializable{
    int totalseat;
    int totalman;

    public Content(){}

    public Content(int _totalseat, int _totalman) {
        this.totalseat = _totalseat;
        this.totalman = _totalman;
    }
    public int getTotalseat()
    {
        return totalseat;
    }

    public void setTotalseat(int totalseat)
    {
        this.totalseat = totalseat;
    }

    public int getTotalman()
    {
        return totalman;
    }

    public void setTotalman(int totalman)
    {
        this.totalman = totalman;
    }


}
