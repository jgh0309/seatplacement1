package com.iot.lee_pc.seatplacement;

/**
 * Created by laby on 2017-05-26.
 */

public class Tender
{
    private String name;
    private String Price;
    private String tenderseat;

    public String getTenderseat()
    {
        return tenderseat;
    }

    public void setTenderseat(String tenderseat)
    {
        this.tenderseat = tenderseat;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPrice()
    {
        return Price;
    }

    public void setPrice(String price)
    {
        Price = price;
    }
}
