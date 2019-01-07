package com.emse.spring.local.model;

import org.omg.CORBA.INTERNAL;

import javax.persistence.criteria.CriteriaBuilder;
import java.awt.*;


import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class xyYtoRGB {



    public xyYtoRGB(){}


    public String ToHex(Double x, Double y) {

        if (x == 0.0 && y == 0.0) return "#000000";

        // For the hue bulb the corners of the triangle are:
        Double[] Red = {0.675, 0.322};
        Double[] Green = {0.4091, 0.518};
        Double[] Blue = {0.167, 0.04};




        Double normRed = sqrt(Math.pow(Red[0],2) + Math.pow(Red[1],2));
        Double normGreen = sqrt(Math.pow(Green[0],2) + Math.pow(Green[1],2));
        Double normBlue = sqrt(Math.pow(Blue[0],2) + Math.pow(Blue[1],2));

        Double norm = sqrt(Math.pow(x,2) + Math.pow(y,2));

        double distR =  Math.abs(norm - normRed);
        double distG =  Math.abs(norm - normGreen);
        double distB =  Math.abs(norm - normBlue);

//        System.out.println("distR: " + distR);
//        System.out.println("distG: " + distG);
//        System.out.println("distB: " + distB);

        double min = Math.min(distR,Math.min(distB,distG));

        if (min == distR) {
            return String.format("#%02x%02x%02x", 255, 0, 0);
        }
        else if (min == distG) {
            return String.format("#%02x%02x%02x", 0, 255, 0);
        }
        else {
            return String.format("#%02x%02x%02x", 0, 0, 255);
        }


//        Double Y = (double) brightness/255;
//        System.out.println(Y);
//        Double z = 1.0f - x - y;
//
//        Double X = (Y/y)*x;
//        Double Z = (Y/y)*z;
//
//        double r = X * 1.4628067f - Y * 0.1840623f - Z * 0.2743606f;
//        double g = -X * 0.5217933f + Y * 1.4472381f + Z * 0.0677227f;
//        double b = X * 0.0349342f - Y * 0.0968930f + Z * 1.2884099f;
//
//        //reverse gamma correction
//        double red = r <= 0.0031308f ? 12.92f * r : (1.0f + 0.055f) * pow(r, (1.0f / 2.4f)) - 0.055f;
//        double green = g <= 0.0031308f ? 12.92f * g : (1.0f + 0.055f) * pow(g, (1.0f / 2.4f)) - 0.055f;
//        double blue = b <= 0.0031308f ? 12.92f * b : (1.0f + 0.055f) * pow(b, (1.0f / 2.4f)) - 0.055f;



//        Long R = Math.round(red*255);
//        Long G = Math.round(green*255);
//        Long B = Math.round(blue*255);
//
//        //check closest rgb color and send back the result
//        System.out.println(r);
//        System.out.println(g);
//        System.out.println(b);
//
//        //convert to hex
//        return String.format("#%02x%02x%02x", 255, 0, 0);

    }



    public Double[] ToXY(String hex) {

        if (hex.equals("#000000")) return new Double[] {0.0, 0.0};

        Double[] Red = {0.675, 0.322};
        Double[] Green = {0.4091, 0.518};
        Double[] Blue = {0.167, 0.04};


        //only work with 6 characters hex strings

        hex = hex.substring(1);

        int r = Integer.parseInt(hex.substring(0,2),16);
        int g = Integer.parseInt(hex.substring(2,4),16);
        int b = Integer.parseInt(hex.substring(4,6),16);


        int diffR = 255 - r;
        int diffG = 255 - g;
        int diffB = 255 - b;


        if (diffR == 0) return Red;
        else if (diffG == 0) return Green;
        else if (diffB == 0) return Blue;
        else {
            double min = Math.min(diffR,Math.min(diffB,diffG));
            if (min == diffR) return Red;
            else if (min == diffG) return Green;
            else return Blue;
        }


//        Double r = (double) R/255;
//        Double g = (double) G/255;
//        Double b = (double) B/255;
//
//        //Apply a gamma correction to the RGB values, which makes the color more vivid
//        double red = (r > 0.04045f) ? pow((r + 0.055f) / (1.0f + 0.055f), 2.4f) : (r / 12.92f);
//        double green = (g > 0.04045f) ? pow((g + 0.055f) / (1.0f + 0.055f), 2.4f) : (g / 12.92f);
//        double blue = (b > 0.04045f) ? pow((b + 0.055f) / (1.0f + 0.055f), 2.4f) : (b / 12.92f);
//
//        //Convert the RGB values to XYZ using the Wide RGB D65 conversion formula
//        double X = red * 0.649926f + green * 0.103455f + blue * 0.197109f;
//        double Y = red * 0.234327f + green * 0.743075f + blue * 0.022598f;
//        double Z = red * 0.0000000f + green * 0.053077f + blue * 1.035763f;
//
//
//        //  check out of bounds values
//
//        double x = X / (X+Y+Z);
//        double y = Y / (X+Y+Z);
//
//
//        return new Double[] {x, y, Y};

    }










}
