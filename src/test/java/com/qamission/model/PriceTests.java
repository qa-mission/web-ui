package com.qamission.model;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PriceTests {
    @Test
    public void priceCreationTest() {
        Price p = new Price(3.0,"price type", Unit.KG);
        Assert.assertEquals(p.getValue(),3.0);
        Assert.assertEquals(p.getType(),"price type");
        Assert.assertEquals(p.getUnit(),Unit.KG);
    }

    @Test
    public void priceConvertionTest1() {
        Price p = new Price(3.0,"price type", Unit.KG);
        double valuePerLb = p.getPricePer(Unit.LB);
        Assert.assertEquals(valuePerLb,p.getValue()/Price.KG_TO_LB);
    }

    @Test
    public void priceConvertionTest2() {
        Price p = new Price(4.39,"price type", Unit.KG);
        double valuePerLb = p.getPricePer(Unit.LB);
        Assert.assertEquals(Price.formatDecimal.format(valuePerLb),"1.99");
    }

    @Test
    public void priceConvertionTest3() {
        Price p = new Price(1.99,"price type", Unit.LB);
        double valuePerLb = p.getPricePer(Unit.KG);
        Assert.assertEquals(Price.formatDecimal.format(valuePerLb),"4.39");
    }
}
