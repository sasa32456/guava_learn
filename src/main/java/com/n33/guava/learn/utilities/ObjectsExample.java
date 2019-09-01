package com.n33.guava.learn.utilities;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

import java.util.Calendar;

public class ObjectsExample {

    public static void main(String[] args) {
        final Guava guava = new Guava("Google", "28.0", Calendar.getInstance());
        System.out.println(guava);
        System.out.println(guava.hashCode());
    }

    static class Guava implements Comparable<Guava>{
        private final String manufacturer;
        private final String version;
        private final Calendar releaseDate;

        public Guava(String manufacturer, String version, Calendar releaseDate) {
            this.manufacturer = manufacturer;
            this.version = version;
            this.releaseDate = releaseDate;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).omitNullValues()
                    .add("manufacturer", manufacturer)
                    .add("version", version)
                    .add("releaseDate", releaseDate).toString();
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(manufacturer, version, releaseDate);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Guava guava = (Guava) obj;
            return Objects.equal(manufacturer, guava.manufacturer) &&
                    Objects.equal(version, guava.version) &&
                    Objects.equal(releaseDate, guava.releaseDate);
        }

        @Override
        public int compareTo(Guava o) {
            return ComparisonChain.start().compare(manufacturer, o.manufacturer)
                    .compare(version, o.version).compare(releaseDate, o.releaseDate).result();
        }


        /*        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Guava guava = (Guava) o;
            return Objects.equals(manufacturer, guava.manufacturer) &&
                    Objects.equals(version, guava.version) &&
                    Objects.equals(releaseDate, guava.releaseDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(manufacturer, version, releaseDate);
        }*/

    }
}
