/*
 Author: Juan Rada-Vilela, Ph.D.
 Copyright (C) 2010-2014 FuzzyLite Limited
 All rights reserved

 This file is part of jfuzzylite.

 jfuzzylite is free software: you can redistribute it and/or modify it under
 the terms of the GNU Lesser General Public License as published by the Free
 Software Foundation, either version 3 of the License, or (at your option)
 any later version.

 jfuzzylite is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with jfuzzylite.  If not, see <http://www.gnu.org/licenses/>.

 fuzzylite™ is a trademark of FuzzyLite Limited.
 jfuzzylite™ is a trademark of FuzzyLite Limited.

 */
package com.fuzzylite.term;

import com.fuzzylite.Op;
import java.util.Iterator;
import java.util.List;

public class Trapezoid extends Term {

    private double vertexA, vertexB, vertexC, vertexD;

    public Trapezoid() {
        this("");
    }

    public Trapezoid(String name) {
        this(name, Double.NaN, Double.NaN);
    }

    public Trapezoid(String name, double vertexA, double vertexD) {
        this.name = name;
        double range = vertexD - vertexA;
        this.vertexA = vertexA;
        this.vertexD = vertexD;
        this.vertexB = vertexA + range * 1.0 / 5.0;
        this.vertexC = vertexA + range * 4.0 / 5.0;
    }

    public Trapezoid(String name, double vertexA, double vertexB, double vertexC, double vertexD) {
        this.name = name;
        this.vertexA = vertexA;
        this.vertexB = vertexB;
        this.vertexC = vertexC;
        this.vertexD = vertexD;
    }

    @Override
    public String parameters() {
        return Op.join(" ", vertexA, vertexB, vertexC, vertexD)
                + (!Op.isEq(height, 1.0) ? " " + Op.str(height) : "");
    }

    @Override
    public void configure(String parameters) {
        if (parameters.isEmpty()) {
            return;
        }
        List<String> values = Op.split(parameters, " ");
        int required = 4;
        if (values.size() < required) {
            throw new RuntimeException(String.format(
                    "[configuration error] term <%s> requires <%d> parameters",
                    this.getClass().getSimpleName(), required));
        }
        Iterator<String> it = values.iterator();
        setVertexA(Op.toDouble(it.next()));
        setVertexB(Op.toDouble(it.next()));
        setVertexC(Op.toDouble(it.next()));
        setVertexD(Op.toDouble(it.next()));
        if (values.size() > required){
            setHeight(Op.toDouble(it.next()));
        }
    }

    @Override
    public double membership(double x) {
        if (Double.isNaN(x)) {
            return Double.NaN;
        }

        if (Op.isLE(x, vertexA) || Op.isGE(x, vertexD)) {
            return 0.0;
        } else if (Op.isLt(x, vertexB)) {
            return Math.min(1.0, (x - vertexA) / (vertexB - vertexA));
        } else if (Op.isLE(x, vertexC)) {
            return 1.0;
        } else if (Op.isLt(x, vertexD)) {
            return (vertexD - x) / (vertexD - vertexC);
        }
        return 0.0;
    }

    public double getVertexA() {
        return vertexA;
    }

    public void setVertexA(double vertexA) {
        this.vertexA = vertexA;
    }

    public double getVertexB() {
        return vertexB;
    }

    public void setVertexB(double vertexB) {
        this.vertexB = vertexB;
    }

    public double getVertexC() {
        return vertexC;
    }

    public void setVertexC(double vertexC) {
        this.vertexC = vertexC;
    }

    public double getVertexD() {
        return vertexD;
    }

    public void setVertexD(double vertexD) {
        this.vertexD = vertexD;
    }
}